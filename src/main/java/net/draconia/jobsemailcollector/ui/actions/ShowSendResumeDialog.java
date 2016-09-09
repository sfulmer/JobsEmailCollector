package net.draconia.jobsemailcollector.ui.actions;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import net.draconia.jobsemailcollector.domain.Individual;
import net.draconia.jobsemailcollector.ui.SendResumeDialog;

public class ShowSendResumeDialog extends AbstractAction
{
	private static final long serialVersionUID = -2029972569572736735L;
	
	private Individual mObjModel;
	private Window mWndParent;
	
	public ShowSendResumeDialog(final Window wndParent, final Individual objModel)
	{
		super("Send Resume...");
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_R);
		
		try
			{
			putValue(SMALL_ICON, new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/mail.png"))));
			}
		catch(IOException objIOException)
			{
			objIOException.printStackTrace(System.err);
			}
		
		setModel(objModel);
		setParentWindow(wndParent);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		new SendResumeDialog(getParentWindow(), getModel()).setVisible(true);
	}
	
	protected Individual getModel()
	{
		return(mObjModel);
	}
	
	protected Window getParentWindow()
	{
		return(mWndParent);
	}
	
	protected void setModel(final Individual objModel)
	{
		mObjModel = objModel;
	}
	
	protected void setParentWindow(final Window wndParent)
	{
		mWndParent = wndParent;
	}
}