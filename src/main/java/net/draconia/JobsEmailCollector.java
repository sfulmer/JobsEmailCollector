package net.draconia;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.draconia.jobsemailcollector.model.FileToImport;
import net.draconia.jobsemailcollector.model.Model;

import net.draconia.jobsemailcollector.observers.EmailObserver;

import net.draconia.jobsemailcollector.parsers.FileParser;

import net.draconia.jobsemailcollector.ui.JobsEmailCollectorMainFrame;

import org.apache.commons.lang3.StringUtils;

public class JobsEmailCollector implements Runnable
{
	private Boolean mbNoUserInterface;
	private Model mObjModel;
	private Properties mObjArguments;
	
	public JobsEmailCollector(final Boolean bNoUserInterface, final Properties objArguments)
	{
		setNoUserInterface(bNoUserInterface);
		setArguments(objArguments);
	}
	
	public Properties getArguments()
	{
		if(mObjArguments == null)
			mObjArguments = new Properties();
		
		return(mObjArguments);
	}
	
	protected String getDatabaseFilename()
	{
		return(getArguments().getProperty("databaseFilename".toLowerCase()));
	}
	
	protected File getFileToParse()
	{
		return(new File(getArguments().getProperty("FileToImport".toLowerCase())));
	}
	
	protected Model getModel()
	{
		if(mObjModel == null)
			{
			mObjModel = new Model(isNoUserInterface());
			
			mObjModel.setDatabaseFilename(getDatabaseFilename());
			
			mObjModel.addObserver(new EmailObserver());
			}
		
		return(mObjModel);
	}
	
	protected boolean isNoUserInterface()
	{
		if(mbNoUserInterface == null)
			mbNoUserInterface = false;
		
		return(mbNoUserInterface);
	}
	
	protected static Properties parseCommandLineArguments(final String[] sArrArgs)
	{
		Matcher objKeyMatcher, objValueMatcher;
		Pattern objKeyPattern, objValuePattern;
		Properties objArguments = new Properties();
		String sKey = "", sValue = "";
		
		objKeyPattern = Pattern.compile("^(?:-?)(.*)(=|:)?");
		objValuePattern = Pattern.compile("[=:](.*)");
		
		for(int iLength = sArrArgs.length, iLoop = 0; iLoop < iLength; iLoop++)
			{
			String sArg = sArrArgs[iLoop];
			
			if((objKeyMatcher = objKeyPattern.matcher(sArg)).find())
				sKey = StringUtils.removePattern(StringUtils.removePattern(sArg.substring(objKeyMatcher.start(), objKeyMatcher.end()), "([=:].*)"), "-").toLowerCase();
			if((objValueMatcher = objValuePattern.matcher(sArg)).find())
				sValue = StringUtils.stripStart(sArg.substring(objValueMatcher.start(), objValueMatcher.end()), ":=");
			
			objArguments.setProperty(sKey, sValue);
			}
		
		return(objArguments);
	}
	
	public void run()
	{
		new Thread(new Librarian(getModel())).start();
		
		try
			{
			if(!isNoUserInterface())
				{
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
					
				new JobsEmailCollectorMainFrame(getModel()).setVisible(true);
				}
			else
				{
				FileToImport fileToImport = new FileToImport(getFileToParse());
				FileParser objFileParser = new FileParser();
				
				fileToImport.setModel(getModel());
				
				objFileParser.setModel(fileToImport);
				
				Executors.newFixedThreadPool(1).submit(objFileParser);
				}
			}
		catch(Exception objException)
			{
			ByteArrayOutputStream objStream = new ByteArrayOutputStream();
			
			objException.printStackTrace(new PrintStream(objStream));
			JOptionPane.showMessageDialog(null, new String(objStream.toByteArray()));
			}
	}
	
	public void setArguments(final Properties objArgs)
	{
		if(objArgs == null)
			mObjArguments = new Properties();
		else
			mObjArguments = objArgs;
	}
	
	protected void setNoUserInterface(final Boolean bNoUserInterface)
	{
		if(bNoUserInterface == null)
			mbNoUserInterface = false;
		else
			mbNoUserInterface = bNoUserInterface;
	}
	
	public static void main(final String[] args)
	{
		boolean bNoUI = false;
		Properties objArgs = null;
		
		objArgs = parseCommandLineArguments(args);
		
		bNoUI = objArgs.containsKey("noui");
		
		if(bNoUI)
			{
			Thread objThread;
			
			objThread = new Thread(new JobsEmailCollector(true, objArgs));
			
			objThread.start();
			}
		else
			SwingUtilities.invokeLater(new JobsEmailCollector(false, objArgs));
	}
}