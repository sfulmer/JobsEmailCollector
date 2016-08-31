package net.draconia.jobsemailcollector.ui.actions;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

public class Quit extends AbstractAction
{
	private static final long serialVersionUID = 6988674983186549938L;
	
	private Window mWndParent;
	
	public Quit(final Window wndParent)
	{
		super("Quit...");
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_Q);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.VK_ALT, false));
		
		setParent(wndParent);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		getParent().dispose();
		
		System.exit(0);
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