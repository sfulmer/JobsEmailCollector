package net.draconia.jobsemailcollector.model;

import java.io.File;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import net.draconia.jobsemailcollector.manager.DatabaseManager;
import net.draconia.jobsemailcollector.manager.IndividualManager;
import net.draconia.jobsemailcollector.parsers.FileParser;

public class Model extends Observable implements Serializable
{
	private static final long serialVersionUID = 6631904656143398651L;
	
	private boolean mbNoUserInterface = false;
	private DatabaseManager mObjDatabaseManager;
	private FileParser mObjFileParser;
	private Integer miPage, miPageSize;
	private List<File> mLstFilesToImport;
	private List<Email> mLstEmails;
	private List<Individual> mLstIndividuals, mLstTableData;
	//private Map<Integer, ShallowIndividual> mMapTableData;
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
		
		getTableDataInternal().add(objIndividual);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public synchronized boolean addIndividuals(final Collection<Individual> collIndividuals)
	{
		boolean bReturnValue = getIndividualsInternal().addAll(collIndividuals);
		
		getTableDataInternal().addAll(collIndividuals);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public synchronized boolean addTableData(final Individual objIndividual)
	{
		Boolean bReturnValue = getTableDataInternal().add(objIndividual);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public synchronized void addTableData(final Collection<? extends Individual> collTableData)
	{
		getTableDataInternal().addAll(collTableData);
		
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
			try
				{
				mLstIndividuals = new IndividualManager(this).getIndividuals(getPage(), getPageSize());
				//mLstIndividuals = Collections.synchronizedList(new ArrayList<Individual>());
				}
			catch(SQLException objSQLException)
				{
				objSQLException.printStackTrace(System.err);
				
				mLstIndividuals = Collections.synchronizedList(new ArrayList<Individual>());
				}
		
		return(mLstIndividuals);
	}
	
	protected int getPage()
	{
		if(miPage == null)
			miPage = 1;
		
		return(miPage);
	}
	
	protected int getPageSize()
	{
		if(miPageSize == null)
			miPageSize = 0;
		
		return(miPageSize);
	}
	
	public List<Individual> getTableData()
	{
		return(Collections.unmodifiableList(getTableDataInternal()));
	}
	
	protected List<Individual> getTableDataInternal()
	{
		if(mLstTableData == null)
			try
				{
				mLstTableData = new IndividualManager(this).getIndividuals(getPage(), getPageSize());
				}
			catch(SQLException objSQLException)
				{
				objSQLException.printStackTrace(System.err);
				
				mLstTableData = Collections.synchronizedList(new ArrayList<Individual>());
				}
		
		return(mLstTableData);
	}
	
	/*public Map<Integer, ShallowIndividual> getTableData()
	{
		return(Collections.unmodifiableMap(getTableDataInternal()));
	}*/
	
	/*protected Map<Integer, ShallowIndividual> getTableDataInternal()
	{
		if(mMapTableData == null)
			mMapTableData = new ConcurrentHashMap<Integer, ShallowIndividual>();
		
		return(mMapTableData);
	}*/
	
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
	
	public synchronized boolean removeTableData(final Individual objIndividual)
	{
		boolean bReturnValue = getTableDataInternal().remove(objIndividual);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
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
	
	protected void setPage(final Integer iPage)
	{
		if(iPage == null)
			miPage = 1;
		else
			miPage = iPage;
		
		setChanged();
		notifyObservers();
	}
	
	protected void setPageSize(final Integer iPageSize)
	{
		if(iPageSize == null)
			miPageSize = 0;
		else
			miPageSize = iPageSize;
	}
}