package net.draconia.jobsemailcollector.ui.observers;

import java.io.File;
import java.io.Serializable;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.apache.commons.lang3.tuple.Pair;

import net.draconia.jobsemailcollector.ui.SendResumeAttachmentTableHeaderRenderer;
import net.draconia.jobsemailcollector.ui.model.SendResumeDialogModel;

public class SendResumeAttachmentTableModelSelectObserver implements Observer, Serializable
{
	private static final long serialVersionUID = -576032222654397440L;
	
	private JTable mTblAttachments;
	
	public SendResumeAttachmentTableModelSelectObserver(final JTable tblAttachments)
	{
		setAttachmentsTable(tblAttachments);
	}
	
	protected JTable getAttachmentsTable()
	{
		return(mTblAttachments);
	}
	
	protected SendResumeAttachmentTableHeaderRenderer getHeaderRenderer()
	{
		return((SendResumeAttachmentTableHeaderRenderer)(getTableHeader().getColumnModel().getColumn(1).getHeaderRenderer()));
	}
	
	protected JTableHeader getTableHeader()
	{
		return(getAttachmentsTable().getTableHeader());
	}
	
	protected void setAttachmentsTable(final JTable tblAttachments)
	{
		mTblAttachments = tblAttachments;
	}
	
	public void update(final Observable objObservable, final Object objArgument)
	{
		boolean bAllSelected = true;
		JCheckBox chkSelectAll;
		List<Pair<File, Boolean>> lstAttachments;
		SendResumeDialogModel objModel = ((SendResumeDialogModel)(objObservable));
		SendResumeAttachmentTableHeaderRenderer objHeaderRenderer = getHeaderRenderer();
		lstAttachments = objModel.getAttachmentList();
		
		chkSelectAll = objHeaderRenderer;
		
		chkSelectAll.setEnabled(lstAttachments.size() > 0);
		
		for(Pair<File, Boolean> pairAttachment : lstAttachments)
			if(!(pairAttachment.getValue() && bAllSelected))
				bAllSelected = false;
		
		chkSelectAll.setSelected(bAllSelected);
		
		getTableHeader().repaint();
	}
}