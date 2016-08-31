package net.draconia.jobsemailcollector.ui.observers;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import javax.swing.text.JTextComponent;

import net.draconia.jobsemailcollector.model.FileToImport;

public class StatisticsProgressDialogObserver implements Observer, Serializable
{
	private static final long serialVersionUID = -1615432845247087748L;
	
	private JTextComponent mTxtNumRead, mTxtTotalBytes;
	
	public StatisticsProgressDialogObserver(final JTextComponent txtNumRead, final JTextComponent txtTotalBytes)
	{
		setNumRead(txtNumRead);
		setTotalBytes(txtTotalBytes);
	}
	
	protected JTextComponent getNumRead()
	{
		return(mTxtNumRead);
	}
	
	protected JTextComponent getTotalBytes()
	{
		return(mTxtTotalBytes);
	}
	
	protected void setNumRead(final JTextComponent txtNumRead)
	{
		mTxtNumRead = txtNumRead;
	}
	
	protected void setTotalBytes(final JTextComponent txtTotalBytes)
	{
		mTxtTotalBytes = txtTotalBytes;
	}
	
	public void update(final Observable objObservable, final Object objArgument)
	{
		FileToImport objModel = ((FileToImport)(objObservable));
		
		getNumRead().setText(String.valueOf(objModel.getNumberOfBytesRead()));
		getTotalBytes().setText(String.valueOf(objModel.getTotalNumberOfBytes()));
	}
}