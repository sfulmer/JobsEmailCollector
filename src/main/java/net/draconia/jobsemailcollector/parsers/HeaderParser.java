package net.draconia.jobsemailcollector.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import net.draconia.jobsemailcollector.model.Header;

public class HeaderParser implements Parser
{
	private static final long serialVersionUID = -2947665654187920718L;
	
	private Header mObjHeader;
	private String msText;
	
	public String getBoundary()
	{
		String sBoundary = null, sContentType = getField("Content-Type"), sSplitPattern = "boundary=";
		String[] sArrContentTypeParts = sContentType.split(";");
		
		if(sArrContentTypeParts.length > 1)
			{
			for(String sPart : sArrContentTypeParts)
				if(sPart.trim().toLowerCase().contains(sSplitPattern))
					{
					sBoundary = sPart.trim();
					
					break;
					}
			
			if(sBoundary == null)
				sBoundary = "";
			}
		else
			sBoundary = "";
		
		if(sBoundary.toLowerCase().contains(sSplitPattern))
			{
			sBoundary = reduceFieldInStringIfExists(sBoundary, sSplitPattern);
			
			sArrContentTypeParts = sBoundary.split(sSplitPattern);
			}
		else
			sArrContentTypeParts = new String[0];
		
		if(sArrContentTypeParts.length > 1)
			sBoundary = stripQuotesFromValue(sArrContentTypeParts[1]);
		else
			sBoundary = "";
		
		return(sBoundary);
	}
	
	public String getCharacterSet()
	{
		String sCharacterSet, sContentType = getField("Content-Type"), sSplitPattern = "charset=";
		String[] sArrContentTypeParts = sContentType.split(";");
		
		if(sArrContentTypeParts.length > 1)
			sCharacterSet = sArrContentTypeParts[1].trim();
		else
			sCharacterSet = "";
		
		if(sCharacterSet.toLowerCase().contains(sSplitPattern))
			{
			sCharacterSet = reduceFieldInStringIfExists(sCharacterSet, sSplitPattern);
			
			sArrContentTypeParts = sCharacterSet.split(sSplitPattern);
			}
		else
			sArrContentTypeParts = new String[0];
		
		if(sArrContentTypeParts.length > 1)
			sCharacterSet = stripQuotesFromValue(sArrContentTypeParts[1]);
		else
			sCharacterSet = "";
		
		return(sCharacterSet);
	}
	
	public String getContentTransferEncoding()
	{
		return(getField("Content-Transfer-Encoding"));
	}
	
	public String getContentType()
	{
		String sContentType;
		String[] sArrContentType = getField("Content-Type").split(";");
		
		if(sArrContentType.length > 1)
			sContentType = sArrContentType[0];
		else
			sContentType = "";
		
		return(sContentType);
	}
	
	public String getDate()
	{
		return(getField("Date"));
	}
	
	protected String getField(final String sField)
	{
		boolean bFieldValid = false;
		int iFieldIndex = -1;
		
		do
			{
			if((iFieldIndex = getHeaderText().toLowerCase().indexOf(sField.toLowerCase() + ":", iFieldIndex + 1)) >= 1)
				bFieldValid = 	Character.valueOf(getHeaderText().charAt(iFieldIndex - 1)).equals('\n');
			else
				bFieldValid = (iFieldIndex == 0);
			}
		while((!bFieldValid) && (iFieldIndex >= 0));
		
		if(bFieldValid)
			{
			int iColonIndex = -1, iEndIndex = -1, iNewLineIndex = -1;
			String sFieldValueParts = getHeaderText().substring(iFieldIndex + sField.length() + 1);
			
			iNewLineIndex = sFieldValueParts.indexOf("\n");
			
			if(iNewLineIndex >= 0)
				{
				iColonIndex = sFieldValueParts.indexOf(":", iNewLineIndex);
				
				if(iColonIndex >= 0)
					{
					iEndIndex = sFieldValueParts.lastIndexOf("\n", iColonIndex);
					
					if(iEndIndex >= 0)
						return(sFieldValueParts.substring(0, iEndIndex).trim());
					}
				else
					return(sFieldValueParts.trim());
				}
			else
				return(sFieldValueParts.trim());
			}
		
		return("");
	}
	
	public String getFrom()
	{
		return(getField("From"));
	}
	
	public Header getHeader()
	{
		if(mObjHeader == null)
			mObjHeader = new Header();
		
		return(mObjHeader);
	}
	
	protected String getHeaderText()
	{
		int iIndexOfBlankLineSeparator = getText().indexOf("\\r\\n\\r\\n");
		
		if(iIndexOfBlankLineSeparator >= 0)
			return(getText().substring(0, getText().indexOf("\\r\\n\\r\\n")));
		else
			return(getText());
	}
	
	public String getName()
	{
		String sContentType = getField("Content-Type"), sName, sSplitPattern = "name=";
		String[] sArrContentTypeParts = sContentType.split(";");
		
		if(sArrContentTypeParts.length > 1)
			sName = sArrContentTypeParts[1].trim();
		else
			sName = "";
		
		if(sName.toLowerCase().contains("name="))
			{
			sName = reduceFieldInStringIfExists(sName, sSplitPattern);
			
			sArrContentTypeParts = sName.split("name=");
			}
		else
			sArrContentTypeParts = new String[0];
		
		if(sArrContentTypeParts.length > 1)
			sName = stripQuotesFromValue(sArrContentTypeParts[1]);
		else
			sName = "";
		
		return(sName);
	}
	
	public String getSubject()
	{
		return(getField("Subject"));
	}
	
	public String getText()
	{
		if(msText == null)
			msText = "";
		
		return(msText);
	}
	
	public String getTo()
	{
		String sTo = getField("To");
		
		return(sTo.trim());
	}
	
	public void parse() throws Exception
	{
		getHeader().setBoundary(getBoundary());
		getHeader().setCharacterSet(getCharacterSet());
		getHeader().setContentTransferEncoding(getContentTransferEncoding());
		getHeader().setContentType(getContentType());
		getHeader().setDate(getDate());
		getHeader().setFrom(getFrom());
		getHeader().setName(getName());
		getHeader().setSubject(getSubject());
		getHeader().setTo(getTo());
	}
	
	public String reduceFieldInStringIfExists(final String sString, final String sField)
	{
		final int iPatternFlags	=	Pattern.CASE_INSENSITIVE
								|	Pattern.UNICODE_CASE
								|	Pattern.MULTILINE;
		Matcher objMatcher;
		Pattern objPattern 		= 	Pattern.compile(sField, iPatternFlags);
		
		objMatcher				=	objPattern.matcher(sString);
		
		return(objMatcher.replaceAll(sField));
	}
	
	public void setText(final String sText)
	{
		if(sText == null)
			msText = "";
		else
			msText = sText;
	}
	
	public String stripQuotesFromValue(final String sToRemoveQuotes)
	{
		int[] iQuoteIndex = new int[2];
		
		iQuoteIndex[0] = sToRemoveQuotes.indexOf("\"");
		
		if(iQuoteIndex[0] >= 0)
			{
			iQuoteIndex[1] = sToRemoveQuotes.indexOf("\"", iQuoteIndex[0] + 1);
			
			if(iQuoteIndex[1] >= 0)
				return(sToRemoveQuotes.substring(iQuoteIndex[0] + 1, iQuoteIndex[1]));
			else
				return(sToRemoveQuotes);
			}
		else if((iQuoteIndex[0] = StringUtils.indexOfAny(sToRemoveQuotes, "\n")) >= 0)
			return(sToRemoveQuotes.substring(0, iQuoteIndex[0]).trim());
		else
			return(sToRemoveQuotes.trim());
	}
}