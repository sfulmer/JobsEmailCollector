package net.draconia.jobsemailcollector.model;

import java.io.File;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import java.util.concurrent.ConcurrentHashMap;

import net.draconia.jobsemailcollector.manager.DatabaseManager;
import net.draconia.jobsemailcollector.parsers.FileParser;

public class Model extends Observable implements Serializable
{
	private static final long serialVersionUID = 6631904656143398651L;
	
	private boolean mbNoUserInterface = false;
	private DatabaseManager mObjDatabaseManager;
	private FileParser mObjFileParser;
	private List<File> mLstFilesToImport;
	private List<Email> mLstEmails;
	private List<Individual> mLstIndividuals;
	private Map<Integer, ShallowIndividual> mMapTableData;
	private String msDatabaseFilename;
	
	public Model(final boolean bNoUserInterface)
	{
		setNoUserInterface(bNoUserInterface);
	}
	
	public synchronized boolean addEmail(final Email objEmail)
	{
		boolean bReturnValue = getEmailsInternal().add(objEmail);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public synchronized void addEmail(final int iIndex, final Email objEmail)
	{
		getEmailsInternal().add(iIndex, objEmail);
		
		setChanged();
		notifyObservers();
	}
	
	public synchronized boolean addEmails(final Collection<Email> collEmails)
	{
		boolean bReturnValue = getEmailsInternal().addAll(collEmails);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public synchronized boolean addEmails(final int iIndex, final Collection<Email> collEmails)
	{
		boolean bReturnValue = getEmailsInternal().addAll(iIndex, collEmails);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public synchronized boolean addFileToImport(final File fileToImport)
	{
		boolean bReturnValue = getFilesToImportInternal().add(fileToImport);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public synchronized void addFileToImport(final int iIndex, final File fileToImport)
	{
		getFilesToImportInternal().add(iIndex, fileToImport);
		
		setChanged();
		notifyObservers();
	}
	
	public synchronized boolean addFilesToImport(final Collection<File> filesToImport)
	{
		boolean bReturnValue = getFilesToImportInternal().addAll(filesToImport);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public synchronized boolean addFilesToImport(final int iIndex, final Collection<File> filesToImport)
	{
		boolean bReturnValue = getFilesToImportInternal().addAll(iIndex, filesToImport);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public synchronized boolean addIndividual(final Individual objIndividual)
	{
		boolean bReturnValue = getIndividualsInternal().add(objIndividual);
		
		getTableDataInternal().put(getTableDataInternal().size(), new ShallowIndividual(objIndividual.getId(), objIndividual.getName(), objIndividual.getEmail().getDate()));
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public synchronized void addIndividual(final int iIndex, final Individual objIndividual)
	{
		getIndividualsInternal().add(iIndex, objIndividual);
		
		getTableDataInternal().put(getTableDataInternal().size(), new ShallowIndividual(objIndividual.getId(), objIndividual.getName(), objIndividual.getEmail().getDate()));
		
		setChanged();
		notifyObservers();
	}
	
	public synchronized boolean addIndividuals(final Collection<Individual> collIndividuals)
	{
		boolean bReturnValue = getIndividualsInternal().addAll(collIndividuals);
		
		for(Individual objIndividual : collIndividuals)
			getTableDataInternal().put(getTableDataInternal().size(), new ShallowIndividual(objIndividual.getId(), objIndividual.getName(), objIndividual.getEmail().getDate()));
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public synchronized boolean addIndividuals(final int iIndex, final Collection<Individual> collIndividuals)
	{
		boolean bReturnValue = getIndividualsInternal().addAll(iIndex, collIndividuals);
		
		for(Individual objIndividual : collIndividuals)
			getTableDataInternal().put(getTableDataInternal().size(), new ShallowIndividual(objIndividual.getId(), objIndividual.getName(), objIndividual.getEmail().getDate()));
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public synchronized ShallowIndividual addTableData(final ShallowIndividual objShallowIndividual)
	{
		ShallowIndividual objReturnValue = getTableDataInternal().put(getTableDataInternal().size(), objShallowIndividual);
		
		setChanged();
		notifyObservers();
		
		return(objReturnValue);
	}
	
	public synchronized void addTableData(final Map<? extends Integer, ? extends ShallowIndividual> mapTableData)
	{
		getTableDataInternal().putAll(mapTableData);
		
		setChanged();
		notifyObservers();
	}
	
	public synchronized void clearEmails()
	{
		getEmailsInternal().clear();
		
		setChanged();
		notifyObservers();
	}
	
	public synchronized void clearFilesToImport()
	{
		getFilesToImportInternal().clear();
		
		setChanged();
		notifyObservers();
	}
	
	public synchronized void clearIndividuals()
	{
		getIndividualsInternal().clear();
		
		setChanged();
		notifyObservers();
	}
	
	public synchronized void clearTableData()
	{
		getTableDataInternal().clear();
		
		setChanged();
		notifyObservers();
	}
	
	public String getDatabaseFilename()
	{
		if(msDatabaseFilename == null)
			msDatabaseFilename = "";
		
		return(msDatabaseFilename);
	}
	
	public DatabaseManager getDatabaseManager()
	{
		if(mObjDatabaseManager == null)
			mObjDatabaseManager = new DatabaseManager(getDatabaseFilename());
		
		return(mObjDatabaseManager);
	}
	
	public  List<Email> getEmails()
	{
		return(Collections.unmodifiableList(getEmailsInternal()));
	}
	
	protected List<Email> getEmailsInternal()
	{
		if(mLstEmails == null)
			mLstEmails = Collections.synchronizedList(new ArrayList<Email>());
		
		return(mLstEmails);
	}
	
	public FileParser getFileParser()
	{
		if(mObjFileParser == null)
			mObjFileParser = new FileParser();
		
		return(mObjFileParser);
	}
	
	public List<File> getFilesToImport()
	{
		return(Collections.unmodifiableList(getFilesToImportInternal()));
	}
	
	protected List<File> getFilesToImportInternal()
	{
		if(mLstFilesToImport == null)
			mLstFilesToImport = Collections.synchronizedList(new ArrayList<File>());
		
		return(mLstFilesToImport);
	}
	
	public List<Individual> getIndividuals()
	{
		return(Collections.unmodifiableList(getIndividualsInternal()));
	}
	
	protected List<Individual> getIndividualsInternal()
	{
		if(mLstIndividuals == null)
			mLstIndividuals = Collections.synchronizedList(new ArrayList<Individual>());
		
		return(mLstIndividuals);
	}
	
	public Map<Integer, ShallowIndividual> getTableData()
	{
		return(Collections.unmodifiableMap(getTableDataInternal()));
	}
	
	protected Map<Integer, ShallowIndividual> getTableDataInternal()
	{
		if(mMapTableData == null)
			mMapTableData = new ConcurrentHashMap<Integer, ShallowIndividual>();
		
		return(mMapTableData);
	}
	
	public boolean isNoUserInterface()
	{
		return(mbNoUserInterface);
	}
	
	public synchronized boolean removeEmail(final Email objEmail)
	{
		boolean bReturnValue = getEmailsInternal().remove(objEmail);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public synchronized boolean removeEmails(final Collection<Email> collEmails)
	{
		boolean bReturnValue = getEmailsInternal().removeAll(collEmails);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public synchronized boolean removeFile(final File fileToImport)
	{
		boolean bReturnValue = getFilesToImportInternal().remove(fileToImport);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public synchronized boolean removeFiles(final Collection<File> filesToImport)
	{
		boolean bReturnValue = getFilesToImportInternal().removeAll(filesToImport);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public synchronized boolean removeIndividual(final Individual objIndividual)
	{
		boolean bReturnValue = getIndividualsInternal().remove(objIndividual);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public synchronized boolean removeIndividuals(final Collection<Individual> collIndividuals)
	{
		boolean bReturnValue = getIndividualsInternal().removeAll(collIndividuals);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public synchronized ShallowIndividual removeTableData(final ShallowIndividual objShallowIndividual)
	{
		ShallowIndividual objReturnValue = getTableDataInternal().remove(objShallowIndividual.getId());
		
		setChanged();
		notifyObservers();
		
		return(objReturnValue);
	}
	
	public void setDatabaseFilename(final String sDatabaseFilename)
	{
		if(sDatabaseFilename == null)
			msDatabaseFilename = "";
		else
			msDatabaseFilename = sDatabaseFilename;
	}
	
	protected void setNoUserInterface(final boolean bNoUserInterface)
	{
		mbNoUserInterface = bNoUserInterface;
	}
}