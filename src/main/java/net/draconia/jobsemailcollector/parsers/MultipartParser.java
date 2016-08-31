package net.draconia.jobsemailcollector.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.draconia.jobsemailcollector.model.Header;
import net.draconia.jobsemailcollector.model.MimePart;

public class MultipartParser implements Parser
{
	private static final long serialVersionUID = -7029492327866767102L;
	
	private Header mObjHeader;
	private MimePart mObjPart;
	private String msText;
	
	protected String getBodyText()
	{
		return(getText().trim().substring(getHeadersText().length()).trim());
	}
	
	public Header getHeader() throws Exception
	{
		if(mObjHeader == null)
			{
			HeaderParser objHeaderParser = new HeaderParser();
			
			objHeaderParser.setText(getHeadersText());
			objHeaderParser.parse();
			
			mObjHeader = objHeaderParser.getHeader();
			}
		
		return(mObjHeader);
	}
	
	protected String getHeadersText()
	{
		Matcher objMatcher;
		Pattern objPattern = Pattern.compile("((\\n|\\r){2}){2,}", Pattern.MULTILINE);
		
		objMatcher = objPattern.matcher(getText().trim());
		
		if(objMatcher.find())
			return(getText().trim().substring(0, objMatcher.end()).trim());
		else
			return("");
	}
	
	public MimePart getPart()
	{
		if(mObjPart == null)
			mObjPart = new MimePart();
		
		return(mObjPart);
	}
	
	public String getText()
	{
		if(msText == null)
			msText = "";
		
		return(msText);
	}
	
	public void parse() throws Exception
	{
		MimePart objPart = new MimePart();
		
		objPart.setBoundary(getHeader().getBoundary());
		objPart.setCharacterSet(getHeader().getCharacterSet());
		objPart.setContentTransferEncoding(getHeader().getContentTransferEncoding());
		objPart.setContentType(getHeader().getContentType());
		objPart.setName(getHeader().getName());
		objPart.setText(getBodyText().trim());
		
		setPart(objPart);
	}
	
	public void setHeader(final Header objHeader)
	{
		if(objHeader == null)
			mObjHeader = new Header();
		else
			mObjHeader = objHeader;
	}
	
	public void setPart(final MimePart objPart)
	{
		mObjPart = objPart;
	}
	
	public void setText(final String sText)
	{
		if(sText == null)
			msText = "";
		else
			msText = sText;
	}
}