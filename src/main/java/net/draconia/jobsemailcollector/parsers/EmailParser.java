 package net.draconia.jobsemailcollector.parsers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.draconia.jobsemailcollector.model.MimePart;
import net.draconia.jobsemailcollector.model.Email;
import net.draconia.jobsemailcollector.model.Header;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

public class EmailParser implements Callable<Email>, Parser
{
	private static final long serialVersionUID = 2973624931147709261L;
	
	private Email mObjEmail;
	private Header mObjHeader;
	private String msText;
	
	public EmailParser()
	{ }
	
	public EmailParser(final String sText)
	{
		setText(sText);
	}
	
	public Email call() throws Exception
	{
		parse();
		
		return(getEmail());
	}
	
	public List<MimePart> getBodyParts() throws Exception
	{
		ExecutorService objExecutorService = Executors.newFixedThreadPool(1);
		Future<List<MimePart>> objBodyParts = objExecutorService.submit(new BodyParser(getHeader(), getBodyText()));
		
		return(objBodyParts.get());
	}
	
	public String getBodyText()
	{
		int iDivider = getIndexOfDividerBetweenHeadersAndBody();
		String sBodyText, sSubString;
		
		iDivider = getIndexOfDividerBetweenHeadersAndBody();
		sSubString = getText().substring(iDivider + 1);
		sBodyText = StringUtils.stripStart(StringUtils.stripEnd(sSubString, null), null);
		
		return(sBodyText);
	}
	
	public Email getEmail()
	{
		if(mObjEmail == null)
			mObjEmail = new Email();
		
		return(mObjEmail);
	}
	
	public Header getHeader() throws Exception
	{
		if(mObjHeader == null)
			{
			HeaderParser objHeaderParser = new HeaderParser();
			
			objHeaderParser.setText(getHeaderText());
			objHeaderParser.parse();
			
			mObjHeader = objHeaderParser.getHeader();
			}
		
		return(mObjHeader);
	}
	
	public String getHeaderText()
	{
		return(StringUtils.stripStart(StringUtils.stripEnd(getText().substring(0, getIndexOfDividerBetweenHeadersAndBody()), null), null));
	}
	
	public int getIndexOfDividerBetweenHeadersAndBody()
	{
		return(getText().indexOf("\r\n\r\n"));
	}
	
	public MimePart getMainTextPart() throws Exception
	{
		List<MimePart> lstParts = Collections.synchronizedList(new ArrayList<MimePart>(getBodyParts()));//, lstPartsToRemove = new ArrayList<MimePart>();
		MimePart objPart = null;
		
		for(MimePart objMimePart : lstParts)
			if(objMimePart.getContentType().contains("text/plain"))
				{
				objPart = objMimePart;
				
				break;
				}
		
		if(objPart == null)
			for(MimePart objMimePart : lstParts)
				if(objMimePart.getContentType().contains("text/html"))
					{
					objPart = objMimePart;
					
					break;
					}
		
		if(objPart == null)
			if(lstParts.size() <= 0)
				getEmail().setBody(getBodyText());
			else
				objPart = lstParts.get(0);
		
		return(objPart);
	}
	
	public String getText()
	{
		if(msText == null)
			msText = "";
		
		return(msText);
	}
	
	public void parse() throws Exception 
	{
		Header objHeader = getHeader();
		MimePart objPart = getMainTextPart();
		
		getEmail().setCharacterSet(objPart.getCharacterSet());
		getEmail().setContentTransferEncoding(objPart.getContentTransferEncoding());
		getEmail().setContentType(objPart.getContentType());
		getEmail().setBody(StringEscapeUtils.unescapeHtml4(replaceCodesWithCharacters(objPart.getText())));
		
		getEmail().setDate(objHeader.getDate());
		getEmail().setFrom(objHeader.getFrom());
		getEmail().setSubject(objHeader.getSubject());
		getEmail().setTo(objHeader.getTo());
	}
	
	public String replaceCodesWithCharacters(final String sText)
	{
		Matcher objMatcher;
		Pattern objPattern = Pattern.compile("%([0-9a-f]{1,2})", Pattern.MULTILINE);
		String sWorkingText = sText;
		
		objMatcher = objPattern.matcher(sWorkingText);
		
		if(objMatcher.find())
			do
				{
				int iCode, iEnd, iStart;
				String sCode, sOriginal;
				
				iStart = objMatcher.start();
				iEnd = objMatcher.end();
				
				sOriginal = sWorkingText.substring(iStart, iEnd);
				iCode = Integer.valueOf(sOriginal.substring(1), 16);
				sCode = Character.toString((char)(iCode));
				
				sWorkingText = sWorkingText.replace(sOriginal, sCode);
				
				objMatcher = objPattern.matcher(sWorkingText);
				}
			while(objMatcher.find());
		
		return(sWorkingText);
	}
	
	public void resetEmail()
	{
		setEmail(null);
	}
	
	public void setEmail(final Email objEmail)
	{
		mObjEmail = objEmail;
	}
	
	public void setText(final String sText)
	{
		if(sText == null)
			msText = "";
		else
			msText = sText;
	}
}