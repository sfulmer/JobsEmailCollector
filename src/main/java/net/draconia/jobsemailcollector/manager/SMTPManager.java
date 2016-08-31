package net.draconia.jobsemailcollector.manager;

import java.io.File;
import java.io.Serializable;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

public class SMTPManager implements Serializable
{
	private static final long serialVersionUID = 2064834623033945069L;
	
	private Properties mObjProperties;
	private Session mObjSession;
	private String msHost;
	
	public SMTPManager()
	{ }
	
	public SMTPManager(final String sHost)
	{
		setHost(sHost);
	}
	
	protected void attachFile(final Multipart objMultipart, final File fileAttachment) throws MessagingException
	{
		MimeBodyPart objPart = new MimeBodyPart();
		
		objPart.setDataHandler(new DataHandler(new FileDataSource(new File("I:\\Jobs\\Seth Resume.doc"))));
		objPart.setFileName("Resume.doc");
		
		objMultipart.addBodyPart(objPart);
	}
	
	protected void attachFiles(final Multipart objMultipart, final File[] arrFileAttachment) throws MessagingException
	{
		for(File fileAttachment : arrFileAttachment)
			attachFile(objMultipart, fileAttachment);
	}
	
	protected MimeMessage createMessage(final String sFrom) throws AddressException, MessagingException
	{
		MimeMessage objMimeMessage = new MimeMessage(getSession());
		
		objMimeMessage.addFrom(InternetAddress.parse(sFrom));
		
		return(objMimeMessage);
	}
	
	public String getHost()
	{
		if(msHost == null)
			msHost = "smtp.gmail.com";
		
		return(msHost);
	}
	
	protected Properties getProperties()
	{
		if(mObjProperties == null)
			{
			mObjProperties = System.getProperties();
			
			mObjProperties.put("mail.smtp.host", "smtp.gmail.com");
			mObjProperties.put("mail.smtp.socketFactory.port", "465");
			mObjProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			mObjProperties.put("mail.smtp.auth", "true");
			mObjProperties.put("mail.smtp.port", "465");
			}
		
		return(mObjProperties);
	}
	
	protected Session getSession()
	{
		if(mObjSession == null)
			mObjSession = Session.getDefaultInstance(getProperties(), new Authenticator()
			{
				protected PasswordAuthentication getPasswordAuthentication()
				{
					return(new PasswordAuthentication("seth.fulmer@gmail.com", "You $uck"));
				}
			});
		
		return(mObjSession);
	}
	
	public boolean sendEmail(final String sTo, final String sSubject, final String sBody) throws AddressException
	{
		try
			{
			MimeBodyPart objPart = new MimeBodyPart();
			MimeMessage objMessage = createMessage("seth.fulmer@gmail.com");
			Multipart objMultipart = new MimeMultipart();
			
			objMessage.addRecipient(RecipientType.TO, new InternetAddress("seth.fulmer@gmail.com"));
			objMessage.setSubject("Testing");
			
			objPart.setText("This is a test");
			objPart.setDataHandler(new DataHandler(new FileDataSource(new File("I:\\Jobs\\Seth Resume.doc"))));
			objPart.setFileName("Resume.doc");
			objMultipart.addBodyPart(objPart);
			
			objMessage.setContent(objMultipart);
			
			Transport.send(objMessage);
			
			return(true);
			}
		catch(MessagingException objMessagingException)
			{
			return(false);
			}
	}
	
	public boolean sendEmail(final String sTo, final String sSubject, final String sBody, final File fileAttachment) throws AddressException
	{
		try
			{
			MimeBodyPart objPart = new MimeBodyPart();
			MimeMessage objMessage = createMessage("seth.fulmer@gmail.com");
			Multipart objMultipart = new MimeMultipart();
			
			objMessage.addRecipient(RecipientType.TO, new InternetAddress("seth.fulmer@gmail.com"));
			objMessage.setSubject("Testing");
			
			objPart.setText(sBody);
			objMultipart.addBodyPart(objPart);
			
			attachFile(objMultipart, fileAttachment);
			
			objMessage.setContent(objMultipart);
			
			Transport.send(objMessage);
			
			return(true);
			}
		catch(MessagingException objMessagingException)
			{
			return(false);
			}
	}
	
	public boolean sendEmail(final String sTo, final String sSubject, final String sBody, final File[] arrFileAttachments) throws AddressException
	{
		try
			{
			MimeBodyPart objPart = new MimeBodyPart();
			MimeMessage objMessage = createMessage("seth.fulmer@gmail.com");
			Multipart objMultipart = new MimeMultipart();
			
			objMessage.addRecipient(RecipientType.TO, new InternetAddress("seth.fulmer@gmail.com"));
			objMessage.setSubject("Testing");
			
			objPart.setText(sBody);
			objMultipart.addBodyPart(objPart);
			
			attachFiles(objMultipart, arrFileAttachments);
			
			objMessage.setContent(objMultipart);
			
			Transport.send(objMessage);
			
			return(true);
			}
		catch(MessagingException objMessagingException)
			{
			return(false);
			}
	}
	
	public void setHost(final String sHost)
	{
		if(sHost == null)
			msHost = "smtp.gmail.com";
		else
			msHost = sHost;
	}
}