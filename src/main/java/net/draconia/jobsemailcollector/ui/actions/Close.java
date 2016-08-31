package net.draconia.jobsemailcollector.ui.actions;

import java.awt.Window;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

public class Close extends AbstractAction
{
	private static final long serialVersionUID = -7417508788446403216L;
	
	private Window mWndParent;
	
	public Close(final Window wndParent)
	{
		super("Close");
		
		try
			{
			putValue(SMALL_ICON, new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/cancel.png"))));
			}
		catch(IOException objIOException)
			{
			objIOException.printStackTrace(System.err);
			}
		
		setParent(wndParent);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		getParent().dispose();
	}
	
	protected Window getParent()
	{
		return(mWndParent);
	}
	
	protected void setParent(final Window wndParent)
	{
		mWndParent = wndParent;
	}
}