package net.draconia.jobsemailcollector.ui.observers;

import java.util.Observable;
import java.util.Observer;

import javax.swing.text.JTextComponent;

import net.draconia.jobsemailcollector.model.FileToImport;

public class LocationProgressDialogObserver implements Observer
{
	private JTextComponent mTxtFileLocation;
	
	public LocationProgressDialogObserver(final JTextComponent txtFileLocation)
	{
		setFileLocation(txtFileLocation);
	}
	
	protected JTextComponent getFileLocation()
	{
		return(mTxtFileLocation);
	}
	
	protected void setFileLocation(final JTextComponent txtFileLocation)
	{
		mTxtFileLocation = txtFileLocation;
	}
	
	public void update(final Observable objObservable, final Object objArgument)
	{
		FileToImport objModel = ((FileToImport)(objObservable));
		
		getFileLocation().setText(objModel.getFileToImport().getAbsolutePath());
	}
}