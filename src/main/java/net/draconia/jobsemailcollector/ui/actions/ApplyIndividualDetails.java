package net.draconia.jobsemailcollector.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import net.draconia.jobsemailcollector.domain.Individual;

import net.draconia.jobsemailcollector.service.IndividualService;

import org.springframework.beans.factory.annotation.Autowired;

public class ApplyIndividualDetails extends AbstractAction
{
	private static final long serialVersionUID = 8546204449320269130L;
	
	private Individual mObjDirty, mObjClean;
	
	@Autowired
	private IndividualService mObjIndividualService;
	
	public ApplyIndividualDetails(final Individual objDirty, final Individual objClean)
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
		
		setDirty(objDirty);
		setClean(objClean);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		getIndividualService().save(getDirty());
		
		getClean().set(getDirty());
	}
	
	protected Individual getClean()
	{
		return(mObjClean);
	}
	
	protected Individual getDirty()
	{
		return(mObjDirty);
	}
	
	protected IndividualService getIndividualService()
	{
		return(mObjIndividualService);
	}
	
	protected void setClean(final Individual objClean)
	{
		mObjClean = objClean;
	}
	
	protected void setDirty(final Individual objDirty)
	{
		mObjDirty = objDirty;
	}
	
	protected void setIndividualService(final IndividualService objIndividualService)
	{
		mObjIndividualService = objIndividualService;
	}
}