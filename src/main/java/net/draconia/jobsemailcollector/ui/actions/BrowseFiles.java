package net.draconia.jobsemailcollector.ui.actions;

import java.awt.Window;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import net.draconia.jobsemailcollector.model.Model;

public class BrowseFiles extends AbstractAction
{
	private static final long serialVersionUID = 3553558934990151314L;
	
	private Model mObjModel;
	private Window mWndParent;
	
	public BrowseFiles(final Window wndParent, final Model objModel)
	{
		super("Browse...");
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_B);
		
		setParent(wndParent);
		setModel(objModel);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		JFileChooser objFileChooser = new JFileChooser();
		
		objFileChooser.setMultiSelectionEnabled(true);
		objFileChooser.setCurrentDirectory(new File("I:\\Thunderbird\\profiles\\54zwbhpt.default\\Mail\\Local Folders"));
		
		if(objFileChooser.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION)
			for(File objFile : objFileChooser.getSelectedFiles())
				getModel().addFileToImport(objFile);
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