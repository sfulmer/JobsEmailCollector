package net.draconia.jobsemailcollector.ui.actions;

import java.awt.Window;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.util.concurrent.Executors;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import net.draconia.jobsemailcollector.model.Model;

import net.draconia.jobsemailcollector.parsers.FileParserExecutor;

public class ImportJobs extends AbstractAction
{
	private static final long serialVersionUID = -911543312561431129L;
	
	private Window mWndParent;
	private Model mObjModel;
	
	public ImportJobs(final Model objModel, final Window wndParent)
	{
		super("Import");
		
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		
		setModel(objModel);
		setParent(wndParent);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				try
					{
					Executors.newFixedThreadPool(1).submit(new FileParserExecutor(getModel(), getParent()));
					}
				catch(Exception objException)
					{
					ByteArrayOutputStream objStream = new ByteArrayOutputStream();
					
					objException.printStackTrace(new PrintStream(objStream));
					JOptionPane.showMessageDialog(null, new String(objStream.toByteArray()));
					}
			}
		});
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