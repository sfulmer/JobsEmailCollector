package net.draconia.jobsemailcollector.ui.listeners;

import java.awt.Point;
import java.awt.Window;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.Serializable;

import java.sql.SQLException;

import javax.swing.JTable;

import net.draconia.jobsemailcollector.manager.IndividualManager;

import net.draconia.jobsemailcollector.model.Individual;

import net.draconia.jobsemailcollector.ui.IndividualDetailDialog;

public class IndividualTableMouseListener extends MouseAdapter implements Serializable
{	
	private static final long serialVersionUID = -8151415983767938609L;
	
	private IndividualManager mObjIndividualManager;
	private Window mWndParent;
	
	public IndividualTableMouseListener(final Window wndParent, final IndividualManager objIndividualManager)
	{
		setParent(wndParent);
		setIndividualManager(objIndividualManager);
	}
	
	protected IndividualManager getIndividualManager()
	{
		return(mObjIndividualManager);
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
			try
				{
				int iId = ((int)(tbl.getValueAt(iRow, 0)));
				
				objIndividual = getIndividualManager().getIndividualById(iId);
				}
			catch(SQLException objException)
				{
				objIndividual = null;
				}
		
		if(objIndividual != null)
			{
			IndividualDetailDialog dlgIndividualDetail = new IndividualDetailDialog(getParent(), objIndividual, getIndividualManager());
			
			dlgIndividualDetail.setVisible(true);
			}
	}
	
	protected void setIndividualManager(final IndividualManager objIndividualManager)
	{
		mObjIndividualManager = objIndividualManager;
	}
	
	protected void setParent(final Window wndParent)
	{
		mWndParent = wndParent;
	}
}