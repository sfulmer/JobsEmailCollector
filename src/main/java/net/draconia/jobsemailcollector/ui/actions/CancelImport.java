package net.draconia.jobsemailcollector.ui.actions;

import java.awt.Window;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import net.draconia.jobsemailcollector.model.FileToImport;

public class CancelImport extends AbstractAction
{
	private static final long serialVersionUID = 534814585141835092L;
	
	private FileToImport mFileToImport;
	private Window mWndParent;
	
	public CancelImport(final FileToImport fileToImport, final Window wndParent)
	{
		super("Cancel");
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_C);
		
		setFileToImport(fileToImport);
		setParent(wndParent);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		getFileToImport().setCancelled(true);
	}
	
	protected FileToImport getFileToImport()
	{
		return(mFileToImport);
	}
	
	protected Window getParent()
	{
		return(mWndParent);
	}
	
	protected void setFileToImport(final FileToImport fileToImport)
	{
		mFileToImport = fileToImport;
	}
	
	protected void setParent(final Window wndParent)
	{
		mWndParent = wndParent;
	}
}