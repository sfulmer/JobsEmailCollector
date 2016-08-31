package net.draconia.jobsemailcollector.ui.actions;

import java.awt.Window;

import java.awt.event.ActionEvent;

import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

public class SaveIndividualDetails extends AbstractAction
{
	private static final long serialVersionUID = 2346913073668569164L;
	
	private Action mActApply;
	private Window mWndParent;
	
	public SaveIndividualDetails(final Action actApply, final Window wndParent)
	{
		super("Save");
		
		try
			{
			putValue(SMALL_ICON, new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/save.png"))));
			}
		catch(IOException objIOException)
			{
			objIOException.printStackTrace(System.err);
			}
		
		setApplyAction(actApply);
		setParent(wndParent);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		getApplyAction().actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
		
		getParent().dispose();
	}
	
	protected Action getApplyAction()
	{
		return(mActApply);
	}
	
	protected Window getParent()
	{
		return(mWndParent);
	}
	
	protected void setApplyAction(final Action actApply)
	{
		mActApply = actApply;
	}
	
	protected void setParent(final Window wndParent)
	{
		mWndParent = wndParent;
	}
}