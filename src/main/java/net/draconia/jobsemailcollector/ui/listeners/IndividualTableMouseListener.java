package net.draconia.jobsemailcollector.ui.listeners;

import java.awt.Point;
import java.awt.Window;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.Serializable;

import javax.swing.JTable;

import org.springframework.beans.factory.annotation.Autowired;

import net.draconia.jobsemailcollector.domain.Individual;

import net.draconia.jobsemailcollector.service.IndividualService;

import net.draconia.jobsemailcollector.ui.IndividualDetailDialog;

public class IndividualTableMouseListener extends MouseAdapter implements Serializable
{	
	private static final long serialVersionUID = -8151415983767938609L;
	
	@Autowired
	private IndividualService mObjIndividualService;
	
	private Window mWndParent;
	
	public IndividualTableMouseListener(final Window wndParent)
	{
		setParent(wndParent);
	}
	
	protected IndividualService getIndividualService()
	{
		return(mObjIndividualService);
	}
	
	protected Window getParent()
	{
		return(mWndParent);
	}
	
	public void mousePressed(final MouseEvent objMouseEvent)
	{
		Individual objIndividual = null;
		int iRow = -1;
		JTable tbl = ((JTable)(objMouseEvent.getComponent()));
		Point pt = objMouseEvent.getPoint();
		
		iRow = tbl.rowAtPoint(pt);
		
		if((iRow >= 0) && (objMouseEvent.getClickCount() == 2))
			{
			int iId = ((int)(tbl.getValueAt(iRow, 0)));
				
			objIndividual = getIndividualService().getIndividualById(iId);
			}
		
		if(objIndividual != null)
			{
			IndividualDetailDialog dlgIndividualDetail = new IndividualDetailDialog(getParent(), objIndividual);
			
			dlgIndividualDetail.setVisible(true);
			}
	}
	
	protected void setIndividualService(final IndividualService objIndividualService)
	{
		mObjIndividualService = objIndividualService;
	}
	
	protected void setParent(final Window wndParent)
	{
		mWndParent = wndParent;
	}
}