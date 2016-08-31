package net.draconia.jobsemailcollector.ui.actions;

import java.awt.Window;

import java.awt.event.ActionEvent;

import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JList;

import net.draconia.jobsemailcollector.model.Individual;

import net.draconia.jobsemailcollector.ui.IndividualDetailFieldListEditDialog;

public class IndividualDetailFieldListEdit extends IndividualDetailFieldListCommand
{
	private static final long serialVersionUID = -5577210250392301029L;
	
	private JList<String> mLstIndividualField;
	
	public IndividualDetailFieldListEdit(final Window wndParent, final Individual objModel, final String sField, final JList<String> lstIndividualField)
	{
		super(wndParent, objModel, sField, "Edit");
		
		try
			{
			putValue(SMALL_ICON, new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/edit.png"))));
			}
		catch(IOException objIOException)
			{
			objIOException.printStackTrace(System.err);
			}
		
		setIndividualFieldList(lstIndividualField);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		IndividualDetailFieldListEditDialog dlg = new IndividualDetailFieldListEditDialog(getParentWindow(), getModel(), getField(), getFieldValue());
		
		dlg.setVisible(true);
	}
	
	protected String getFieldValue()
	{
		return(getIndividualFieldList().getSelectedValue());
	}
	
	protected JList<String> getIndividualFieldList()
	{
		return(mLstIndividualField);
	}
	
	protected void setIndividualFieldList(final JList<String> lstIndividualField)
	{
		mLstIndividualField = lstIndividualField;
	}
}