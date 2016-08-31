package net.draconia.jobsemailcollector.ui.actions;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import net.draconia.jobsemailcollector.model.Individual;
import net.draconia.jobsemailcollector.ui.IndividualDetailFieldListEditDialog;

public class IndividualDetailFieldListAdd extends IndividualDetailFieldListCommand
{
	private static final long serialVersionUID = -5577210250392301029L;
	
	public IndividualDetailFieldListAdd(final Window wndParent, final Individual objModel, final String sField)
	{
		super(wndParent, objModel, sField, "Add");
		
		try
			{
			putValue(SMALL_ICON, new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/add.png"))));
			}
		catch(IOException objIOException)
			{
			objIOException.printStackTrace(System.err);
			}
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		IndividualDetailFieldListEditDialog dlg = new IndividualDetailFieldListEditDialog(getParentWindow(), getModel(), getField(), null);
		
		dlg.setVisible(true);
	}
}