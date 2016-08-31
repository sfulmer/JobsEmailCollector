package net.draconia.jobsemailcollector.parsers;

import net.draconia.jobsemailcollector.model.MimePart;

public class SinglePartParser extends MultipartParser
{
	private static final long serialVersionUID = 6330956075640235166L;
	
	private String msCharacterSet, msContentTransferEncoding, msContentType, msText;
	
	public String getCharacterSet()
	{
		if(msCharacterSet == null)
			msCharacterSet = "";
		
		return(msCharacterSet);
	}
	
	public String getContentTransferEncoding()
	{
		if(msContentTransferEncoding == null)
			msContentTransferEncoding = "";
		
		return(msContentTransferEncoding);
	}
	
	public String getContentType()
	{
		if(msContentType == null)
			msContentType = "";
		
		return(msContentType);
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
		objPart.setText(getText());
		
		setPart(objPart);
	}
	
	/*public void setCharacterSet(final String sCharacterSet)
	{
		if(sCharacterSet == null)
			msCharacterSet = "";
		else
			msCharacterSet = sCharacterSet;
	}
	
	public void setContentTransferEncoding(final String sContentTransferEncoding)
	{
		if(sContentTransferEncoding == null)
			msContentTransferEncoding = "";
		else
			msContentTransferEncoding = sContentTransferEncoding;
	}
	
	public void setContentType(final String sContentType)
	{
		if(sContentType == null)
			msContentType = "";
		else
			msContentType = sContentType;
	}*/
	
	public void setText(final String sText)
	{
		if(sText == null)
			msText = "";
		else
			msText = sText;
	}
}