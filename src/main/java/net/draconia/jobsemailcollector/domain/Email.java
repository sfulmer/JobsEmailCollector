package net.draconia.jobsemailcollector.domain;

import java.io.Serializable;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Observable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="Email")
public class Email extends Observable implements Cloneable, Serializable
{
	private static final long serialVersionUID = 449619382894598442L;
	
	@Column(columnDefinition="integer", insertable=true, name="DateReceived", nullable=false, updatable=true)
	private Date mDtDate;
	
	@JoinColumn(name="Individual")
	private Individual mObjIndividual;
	
	@Id
	@Column(name="Id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer miId;
	
	@Column(columnDefinition="text", insertable=true, name="Body", nullable=false, updatable=true)
	private String msBody;
	
	@Column(columnDefinition="text", insertable=true, name="CharacterSet", nullable=false, updatable=true)
	private String msCharacterSet;
	
	@Column(columnDefinition="text", insertable=true, name="ContentTransferEncoding", nullable=false, updatable=true)
	private String msContentTransferEncoding;
	
	@Column(columnDefinition="text", insertable=true, name="ContentType", nullable=false, updatable=true)
	private String msContentType;
	
	@Column(columnDefinition="text", insertable=true, name="From", nullable=false, updatable=true)
	private String msFrom;
	
	@Column(columnDefinition="text", insertable=true, name="Subject", nullable=false, updatable=true)
	private String msSubject;
	
	@Column(columnDefinition="text", insertable=true, name="To", nullable=false, updatable=true)
	private String msTo;
	
	public Email()
	{ }
	
	public Object clone()
	{
		Email objClone = new Email();
		
		objClone.setId(getId());
		objClone.setDate(getDate());
		objClone.setBody(getBody());
		objClone.setCharacterSet(getCharacterSet());
		objClone.setContentTransferEncoding(getContentTransferEncoding());
		objClone.setContentType(getContentType());
		objClone.setFrom(getFrom());
		objClone.setSubject(getSubject());
		objClone.setTo(getTo());
		
		return(objClone);
	}
	
	public boolean equals(final Object objOther)
	{
		if((objOther instanceof Email) && (objOther != null))
			{
			boolean bEquals = true;
			Email objEmail = ((Email)(objOther));
			
			bEquals = bEquals && (Integer.valueOf(getId()).equals(objEmail.getId()));
			
			return(bEquals && equalsIgnoreId(objEmail));
			}
		else
			return(false);
	}
	
	public boolean equalsIgnoreId(final Email objEmail)
	{
		boolean bEquals = true;
		
		bEquals = bEquals && (getBody().equals(objEmail.getBody()));
		bEquals = bEquals && (getCharacterSet().equals(objEmail.getCharacterSet()));
		bEquals = bEquals && (getContentTransferEncoding().equals(objEmail.getContentTransferEncoding()));
		bEquals = bEquals && (getContentType().equals(objEmail.getContentType()));
		bEquals = bEquals && (getDate().equals(objEmail.getDate()));
		bEquals = bEquals && (getFrom().equals(objEmail.getFrom()));
		bEquals = bEquals && (getSubject().equals(objEmail.getSubject()));
		bEquals = bEquals && (getTo().equals(objEmail.getTo()));
		
		return(bEquals);
	}
	
	public String getBody()
	{
		if(msBody == null)
			msBody = "";
		
		return(msBody);
	}
	
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
	
	public Date getDate()
	{
		return(mDtDate);
	}
	
	public String getFrom()
	{
		if(msFrom == null)
			msFrom = "";
		
		return(msFrom);
	}
	
	public Integer getId()
	{
		if(miId == null)
			miId = 0;
		
		return(miId);
	}
	
	public Individual getIndividual()
	{
		return(mObjIndividual);
	}
	
	public String getSubject()
	{
		if(msSubject == null)
			msSubject = "";
		
		return(msSubject);
	}
	
	public String getTo()
	{
		if(msTo == null)
			msTo = "";
		
		return(msTo);
	}
	
	public void set(final Email objEmail)
	{
		if(objEmail != null)
			{
			setId(objEmail.getId());
			setBody(objEmail.getBody());
			setCharacterSet(objEmail.getCharacterSet());
			setContentTransferEncoding(objEmail.getContentTransferEncoding());
			setContentType(objEmail.getContentType());
			setDate(objEmail.getDate());
			setFrom(objEmail.getFrom());
			setSubject(objEmail.getSubject());
			setTo(objEmail.getTo());
			}
	}
	
	public void setBody(final String sBody)
	{
		if(sBody == null)
			msBody = "";
		else
			msBody = sBody;
		
		setChanged();
		notifyObservers();
	}
	
	public void setCharacterSet(final String sCharacterSet)
	{
		if(sCharacterSet == null)
			msCharacterSet = "";
		else
			msCharacterSet = sCharacterSet;
		
		setChanged();
		notifyObservers();
	}
	
	public void setContentTransferEncoding(final String sContentTransferEncoding)
	{
		if(sContentTransferEncoding == null)
			msContentTransferEncoding = "";
		else
			msContentTransferEncoding = sContentTransferEncoding;
		
		setChanged();
		notifyObservers();
	}
	
	public void setContentType(final String sContentType)
	{
		if(sContentType == null)
			msContentType = "";
		else
			msContentType = sContentType;
		
		setChanged();
		notifyObservers();
	}
	
	public void setDate(final Date dtDate)
	{
		mDtDate = dtDate;
		
		setChanged();
		notifyObservers();
	}
	
	public void setDate(final String sDate) throws ParseException
	{
		String[] sArrDateFormats = new String[]	{	"EEE, d MMM yyyy HH:mm:ss Z"
												,	"EEE d MMM yyyy HH:mm:ss Z"
												,	"d MMM yyyy HH:mm:ss Z"
												};
		
		for(int iLength = sArrDateFormats.length, iLoop = 0; iLoop < iLength; iLoop++)
			{
			String sFormat = sArrDateFormats[iLoop];
			
			try
				{
				setDate(new SimpleDateFormat(sFormat).parse(sDate));
				
				break;
				}
			catch(ParseException objParseException)
				{
				if(iLoop == (iLength - 1))
					{
					System.err.println("Unparsable Date: " + sDate);
				
					throw objParseException;
					}
				}
			}
	}
	
	public void setFrom(final String sFrom)
	{
		if(sFrom == null)
			msFrom = "";
		else
			msFrom = sFrom;
		
		setChanged();
		notifyObservers();
	}
	
	public void setId(final Integer iId)
	{
		if(iId == null)
			miId = 0;
		else
			miId = iId;
		
		setChanged();
		notifyObservers();
	}
	
	public void setIndividual(final Individual objIndividual)
	{
		mObjIndividual = objIndividual;
		
		setChanged();
		notifyObservers();
	}
	
	public void setSubject(final String sSubject)
	{
		if(sSubject == null)
			msSubject = "";
		else
			msSubject = sSubject;
		
		setChanged();
		notifyObservers();
	}
	
	public void setTo(final String sTo)
	{
		if(sTo == null)
			msTo = "";
		else
			msTo = sTo;
		
		setChanged();
		notifyObservers();
	}
}