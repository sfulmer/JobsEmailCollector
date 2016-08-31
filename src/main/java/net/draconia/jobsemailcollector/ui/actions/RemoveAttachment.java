package net.draconia.jobsemailcollector.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JTable;

import net.draconia.jobsemailcollector.ui.model.SendResumeDialogModel;

public class RemoveAttachment extends AbstractAction
{
	private static final long serialVersionUID = -2601978951563785412L;
	
	private JTable mTblAttachments;
	private SendResumeDialogModel mObjModel;
	
	public RemoveAttachment(final JTable tblAttachments, final SendResumeDialogModel objModel)
	{
		super("Remove");
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_R);
		
		try
			{
			putValue(SMALL_ICON, new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/delete.png"))));
			}
		catch(IOException objIOException)
			{
			objIOException.printStackTrace(System.err);
			}
		
		setAttachmentTable(tblAttachments);
		setModel(objModel);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		for(int iRow : getAttachmentTable().getSelectedRows())
			{
			File fileAttachment = ((File)(getAttachmentTable().getValueAt(iRow, 0)));
			
			getModel().removeAttachment(fileAttachment);
			}
	}
	
	protected JTable getAttachmentTable()
	{
		return(mTblAttachments);
	}
	
	protected SendResumeDialogModel getModel()
	{
		return(mObjModel);
	}
	
	protected void setAttachmentTable(final JTable tblAttachments)
	{
		mTblAttachments = tblAttachments;
	}
	
	protected void setModel(final SendResumeDialogModel objModel)
	{
		mObjModel = objModel;
	}
}