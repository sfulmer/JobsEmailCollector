package net.draconia.jobsemailcollector.model;

import java.io.File;
import java.io.Serializable;

import java.util.Observable;

import net.draconia.jobsemailcollector.parsers.TextParser;

public class FileToImport extends Observable implements Serializable
{
	private static final long serialVersionUID = -911000140854363442L;
	
	private Boolean mbCancelled, mbComplete;
	private File mFileToImport;
	private Long mlNumberOfBytesRead, mlTotalNumberOfBytes;
	private Model mObjModel;
	private TextParser mObjTextParser;
	
	public FileToImport()
	{ }
	
	public FileToImport(final File fileToImport)
	{
		setFileToImport(fileToImport);
	}
	
	public File getFileToImport()
	{
		return(mFileToImport);
	}
	
	public Model getModel()
	{
		return(mObjModel);
	}
	
	public long getNumberOfBytesRead()
	{
		if(mlNumberOfBytesRead == null)
			mlNumberOfBytesRead = 0l;
		
		return(mlNumberOfBytesRead);
	}
	
	public TextParser getTextParser()
	{
		if(mObjTextParser == null)
			{
			mObjTextParser = new TextParser();
			
			mObjTextParser.setModel(getModel());
			}
		
		return(mObjTextParser);
	}
	
	public long getTotalNumberOfBytes()
	{
		if(mlTotalNumberOfBytes == null)
			mlTotalNumberOfBytes = 0l;
		
		return(mlTotalNumberOfBytes);
	}
	
	public boolean isCancelled()
	{
		if(mbCancelled == null)
			mbCancelled = false;
		
		return(mbCancelled);
	}
	
	public boolean isComplete()
	{
		if(mbComplete == null)
			mbComplete = false;
		
		return(mbComplete);
	}
	
	public void setCancelled(final Boolean bCancelled)
	{
		if(bCancelled == null)
			mbCancelled = false;
		else
			mbCancelled = bCancelled;
		
		setChanged();
		notifyObservers();
	}
	
	public void setComplete(final Boolean bComplete)
	{
		if(bComplete == null)
			mbComplete = false;
		else
			mbComplete = bComplete;
		
		setChanged();
		notifyObservers();
	}
	
	public void setFileToImport(final File fileToImport)
	{
		mFileToImport = fileToImport;
		
		setNumberOfBytesRead(0l);
		setTotalNumberOfBytes(fileToImport.length());
		
		setChanged();
		notifyObservers();
	}
	
	public void setModel(final Model objModel)
	{
		mObjModel = objModel;
	}
	
	public void setNumberOfBytesRead(final Long lNumberOfBytesRead)
	{
		if(lNumberOfBytesRead == null)
			mlNumberOfBytesRead = 0l;
		else
			mlNumberOfBytesRead = lNumberOfBytesRead;
		
		setChanged();
		notifyObservers();
	}
	
	public void setTotalNumberOfBytes(final Long lTotalNumberOfBytes)
	{
		if(lTotalNumberOfBytes == null)
			mlTotalNumberOfBytes = 0l;
		else
			mlTotalNumberOfBytes = lTotalNumberOfBytes;
		
		setChanged();
		notifyObservers();
	}
}