package net.draconia.jobsemailcollector.ui.observers;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JProgressBar;

import net.draconia.jobsemailcollector.model.FileToImport;

public class ProgressBarProgessDialogObserver implements Observer, Serializable
{
	private static final long serialVersionUID = -5134341326169992130L;
	
	private JProgressBar mBarProgress;
	
	public ProgressBarProgessDialogObserver(final JProgressBar barProgress)
	{
		setProgressBar(barProgress);
	}
	
	protected JProgressBar getProgressBar()
	{
		return(mBarProgress);
	}
	
	protected void setProgressBar(final JProgressBar barProgress)
	{
		mBarProgress = barProgress;
	}
	
	public void update(final Observable objObservable, final Object objArgument)
	{
		FileToImport objModel = ((FileToImport)(objObservable));
		int iPercentage;
		
		if(objModel.getTotalNumberOfBytes() > 0)
			iPercentage = ((int)(((double)(objModel.getNumberOfBytesRead())) / ((double)(objModel.getTotalNumberOfBytes())) * 100));
		else
			iPercentage = 0;
		
		getProgressBar().setValue(iPercentage);
	}
}