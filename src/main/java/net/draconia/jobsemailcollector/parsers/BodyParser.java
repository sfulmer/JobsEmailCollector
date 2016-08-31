package net.draconia.jobsemailcollector.parsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import net.draconia.jobsemailcollector.model.Header;
import net.draconia.jobsemailcollector.model.MimePart;

public class BodyParser implements Callable<List<MimePart>>, Parser
{
	private static final long serialVersionUID = -2590631424393014103L;
	
	private Header mObjHeader;
	private List<MimePart> mLstParts;
	private String msText;
	
	public BodyParser()
	{ }
	
	public BodyParser(final Header objHeader, final String sBodyText)
	{
		setHeader(objHeader);
		setText(sBodyText);
	}
	
	protected boolean addPart(final MimePart objPart)
	{
		return(getPartsInternal().add(objPart));
	}
	
	protected boolean addParts(final Collection<MimePart> collMimeParts)
	{
		return(getPartsInternal().addAll(collMimeParts));
	}
	
	public List<MimePart> call() throws Exception
	{
		parse();
		
		return(getParts());
	}
	
	public void filterDownParts() throws Exception
	{
		List<MimePart> arrPartsToAdd = Collections.synchronizedList(new ArrayList<MimePart>());
		List<MimePart> arrPartsToRemove = Collections.synchronizedList(new ArrayList<MimePart>());
		
		do
			{
			arrPartsToAdd.clear();
			arrPartsToRemove.clear();
			
			for(MimePart objPart : getParts())
				if((objPart.getContentType().length() > 0) || (objPart.getText().length() > 0))
					{
					BodyParser objBodyParser = new BodyParser();
										
					Header objHeader = new Header();
					objHeader.setBoundary(objPart.getBoundary());
					objHeader.setCharacterSet(objPart.getCharacterSet());
					objHeader.setContentTransferEncoding(objPart.getContentTransferEncoding());
					objHeader.setContentType(objPart.getContentType());
					objHeader.setName(objPart.getName());
					
					if(objHeader.getContentType().toLowerCase().contains("multipart"))
						{
						objBodyParser.setHeader(objHeader);
						objBodyParser.setText(objPart.getText());
						objBodyParser.parse();
						
						//if(objBodyParser.getParts().size() > 1)
							arrPartsToAdd.addAll(objBodyParser.getParts());
						
						//if(objBodyParser.getParts().size() > 1)
							arrPartsToRemove.add(objPart);
						}
					}
			
			getPartsInternal().addAll(arrPartsToAdd);
			getPartsInternal().removeAll(arrPartsToRemove);
			}
		while((arrPartsToAdd.size() > 0) || (arrPartsToRemove.size() > 0));
	}
	
	public Pattern getBoundaryPattern()
	{
		return(Pattern.compile("^--" + getHeader().getBoundary() + "(.*)([\\r\\n]*)", Pattern.MULTILINE));
	}
	
	public Header getHeader()
	{
		if(mObjHeader == null)
			mObjHeader = new Header();
		
		return(mObjHeader);
	}
	
	public List<MimePart> getParts()
	{
		return(Collections.unmodifiableList(getPartsInternal()));
	}
	
	private List<MimePart> getPartsInternal()
	{
		if(mLstParts == null)
			mLstParts = Collections.synchronizedList(new ArrayList<MimePart>());
		
		return(mLstParts);
	}
	
	public String getText()
	{
		if(msText == null)
			msText = "";
		
		return(StringUtils.stripEnd(StringUtils.stripStart(msText, null), null));
	}
	
	public String[] getTextParts()
	{
		int[] iArrBoundaries = new int[] {0, -1};
		List<String> lstParts = new ArrayList<String>();
		
		if((iArrBoundaries[1] = getText().indexOf("--" + getHeader().getBoundary())) > 0)
			lstParts.add(getText().substring(iArrBoundaries[0],  iArrBoundaries[1]));
		
		while(iArrBoundaries[1] >= 0)
			{
			iArrBoundaries[0] = iArrBoundaries[1];
			
			if((iArrBoundaries[1] = getText().indexOf("--" + getHeader().getBoundary(), iArrBoundaries[0] + 1)) >= 0)
				lstParts.add(getText().substring(iArrBoundaries[0],  iArrBoundaries[1]));
			else
				lstParts.add(getText());
			}
		
		return(lstParts.toArray(new String[0]));
	}
	
	public boolean isMultipart()
	{
		return(getHeader().getContentType().toLowerCase().contains("multipart".toLowerCase()));
	}
	
	public void parse() throws Exception
	{
		if(getHeader().getBoundary().length() > 0)
			{
			String[] sArrParts = getTextParts();
			
			for(String sPart : sArrParts)
				if(sPart.trim().length() > 0)
					{
					MultipartParser objParser = new MultipartParser();
					
					objParser.setText(stripBoundariesFromPart(sPart));
					objParser.parse();
					
					addPart(objParser.getPart());
					}
			
			filterDownParts();
			}
		else
			{
			SinglePartParser objParser = new SinglePartParser();
			
			objParser.setHeader(getHeader());
			objParser.setText(getText());
			objParser.parse();
			
			addPart(objParser.getPart());
			}
	}
	
	public boolean removePart(final MimePart objMimePart)
	{
		return(getPartsInternal().remove(objMimePart));
	}
	
	public boolean removeParts(final Collection<MimePart> collMimePart)
	{
		return(getPartsInternal().removeAll(collMimePart));
	}
	
	public void setHeader(final Header objHeader)
	{
		if(objHeader == null)
			mObjHeader = new Header();
		else
			mObjHeader = objHeader;
	}
	
	public void setText(final String sText)
	{
		if(sText == null)
			msText = "";
		else
			msText = sText;
	}
	
	public String stripBoundariesFromPart(final String sPart)
	{
		int iBoundary = -1, iNewLine = -1;
		int[] iArrBoundaries = new int[] {-1, -1};
		
		if((iBoundary = sPart.indexOf("--" + getHeader().getBoundary())) >= 0)
			if((iNewLine = sPart.indexOf("\n", iBoundary)) >= 0)
				{
				iArrBoundaries[0] = iNewLine + 1;
				
				if((iBoundary = sPart.indexOf("--" + getHeader().getBoundary(), iNewLine)) >= 0)
					iArrBoundaries[1] = iBoundary;
				else
					iArrBoundaries[1] = sPart.length();
				
				return(sPart.substring(iArrBoundaries[0], iArrBoundaries[1]));
				}
		
		return(sPart);
	}
}