package net.draconia.jobsemailcollector.ui.actions;

import java.awt.Window;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import net.draconia.jobsemailcollector.ui.model.SendResumeDialogModel;

public class Browse extends AbstractAction
{
	private static final long serialVersionUID = 3553558934990151314L;
	
	private SendResumeDialogModel mObjModel;
	private Window mWndParent;
	
	public Browse(final Window wndParent, final SendResumeDialogModel objModel)
	{
		super("Browse...");
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_B);
		
		try
			{
			putValue(SMALL_ICON, new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/browse.png"))));
			}
		catch(IOException objIOException)
			{
			objIOException.printStackTrace(System.err);
			}
		
		setParent(wndParent);
		setModel(objModel);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		JFileChooser objFileChooser = new JFileChooser();
		
		objFileChooser.setMultiSelectionEnabled(true);
		
		if(objFileChooser.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION)
			for(File objFile : objFileChooser.getSelectedFiles())
				getModel().addAttachment(objFile);
	}
	
	protected SendResumeDialogModel getModel()
	{
		return(mObjModel);
	}
	
	protected Window getParent()
	{
		return(mWndParent);
	}
	
	protected void setModel(final SendResumeDialogModel objModel)
	{
		mObjModel = objModel;
	}
	
	protected void setParent(final Window wndParent)
	{
		mWndParent = wndParent;
	}
}