package net.draconia.jobsemailcollector.observers;

import java.util.Observable;
import java.util.Observer;

import net.draconia.jobsemailcollector.ui.model.FileListModel;

public class FileListObserver implements Observer
{
	private FileListModel mObjFileListModel;
	
	public FileListObserver(final FileListModel objFileListModel)
	{
		setFileListModel(objFileListModel);
	}
	
	protected FileListModel getFileListModel()
	{
		return(mObjFileListModel);
	}
	
	protected void setFileListModel(final FileListModel objFileListModel)
	{
		mObjFileListModel = objFileListModel;
	}
	
	public void update(final Observable objObservable, final Object objArgument)
	{
		getFileListModel().fireContentsChanged();
	}
}