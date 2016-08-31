package net.draconia.jobsemailcollector.parsers;

import java.awt.Window;
import java.io.File;
import java.io.Serializable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.draconia.jobsemailcollector.model.FileToImport;
import net.draconia.jobsemailcollector.model.Model;
import net.draconia.jobsemailcollector.ui.ProgressDialog;

public class FileParserExecutor implements Callable<Void>, Serializable
{
	private static final long serialVersionUID = -5483089558024871982L;
	
	private Model mObjModel;
	private Window mWndParent;
	
	public FileParserExecutor(final Model objModel, final Window wndParent)
	{
		setModel(objModel);
		setParent(wndParent);
	}
	
	public Void call() throws Exception
	{
		ExecutorService objFileParserExecutor;
		File[] files = getModel().getFilesToImport().toArray(new File[0]);
		FileToImport fileToImport = new FileToImport();
		FileParser objFileParser = getModel().getFileParser();
		
		fileToImport.setModel(getModel());
		
		objFileParserExecutor = Executors.newFixedThreadPool(1);
		
		for(File fileToParse : files)
			{
			Future<Boolean> bResult;
			fileToImport.setFileToImport(fileToParse);
			ProgressDialog dlgProgress = new ProgressDialog(getParent(), fileToImport);
			
			dlgProgress.setVisible(true);
			
			objFileParser.setModel(fileToImport);
			
			bResult = objFileParserExecutor.submit(objFileParser);
			
			bResult.get();
			
			dlgProgress.dispose();
			}
		
		return(null);
	}
	
	protected Model getModel()
	{
		return(mObjModel);
	}
	
	protected Window getParent()
	{
		return(mWndParent);
	}
	
	protected void setModel(final Model objModel)
	{
		mObjModel = objModel;
	}
	
	protected void setParent(final Window wndParent)
	{
		mWndParent = wndParent;
	}
}