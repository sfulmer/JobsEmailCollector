package net.draconia.jobsemailcollector.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import net.draconia.jobsemailcollector.manager.IndividualManager;
import net.draconia.jobsemailcollector.model.Individual;

public class ApplyIndividualDetails extends AbstractAction
{
	private static final long serialVersionUID = 8546204449320269130L;
	
	private Individual mObjDirty, mObjClean;
	private IndividualManager mObjIndividualManager;
	
	public ApplyIndividualDetails(final Individual objDirty, final Individual objClean, final IndividualManager objIndividualManager)
	{
		super("Apply");
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_A);
		
		try
			{
			putValue(SMALL_ICON, new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/save.png"))));
			}
		catch(IOException objIOException)
			{
			objIOException.printStackTrace(System.err);
			}
		
		setIndividualManager(objIndividualManager);
		setDirty(objDirty);
		setClean(objClean);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		try
			{
			getIndividualManager().updateIndividual(getDirty());
			
			getClean().set(getDirty());
			}
		catch(SQLException objException)
			{
			objException.printStackTrace(System.err);
			}
	}
	
	protected IndividualManager getIndividualManager()
	{
		return(mObjIndividualManager);
	}
	
	protected Individual getClean()
	{
		return(mObjClean);
	}
	
	protected Individual getDirty()
	{
		return(mObjDirty);
	}
	
	protected void setIndividualManager(final IndividualManager objIndividualManager)
	{
		mObjIndividualManager = objIndividualManager;
	}
	
	protected void setClean(final Individual objClean)
	{
		mObjClean = objClean;
	}
	
	protected void setDirty(final Individual objDirty)
	{
		mObjDirty = objDirty;
	}
}