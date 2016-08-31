package net.draconia.jobsemailcollector.observers;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Action;

import net.draconia.jobsemailcollector.model.Model;

public class FileListImportJobsObserver implements Observer, Serializable
{
	private static final long serialVersionUID = -1860832406154719364L;
	
	private Action mActImportJobs;
	
	public FileListImportJobsObserver(final Action actImportJobs)
	{
		setImportJobsAction(actImportJobs);
	}
	
	protected Action getImportJobsAction()
	{
		return(mActImportJobs);
	}
	
	protected void setImportJobsAction(final Action actImportJobs)
	{
		mActImportJobs = actImportJobs;
	}
	
	public void update(final Observable objObservable, final Object objArgument)
	{
		getImportJobsAction().setEnabled(((Model)(objObservable)).getFilesToImport().size() > 0);
	}
}