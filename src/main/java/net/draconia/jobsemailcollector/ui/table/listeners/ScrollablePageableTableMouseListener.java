package net.draconia.jobsemailcollector.ui.table.listeners;

import java.awt.Point;
import java.awt.Window;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JDialog;
import javax.swing.JTable;

import net.draconia.jobsemailcollector.ui.table.model.ScrollablePageableModel;

public class ScrollablePageableTableMouseListener extends MouseAdapter implements Serializable
{
	private static final long serialVersionUID = -6595399234864558477L;
	
	private ScrollablePageableModel mObjModel;
	private Window mWndParent;
	
	public ScrollablePageableTableMouseListener(final Window wndParent, final ScrollablePageableModel objModel)
	{
		setParent(wndParent);
		setModel(objModel);
	}
	
	protected ScrollablePageableModel getModel()
	{
		return(mObjModel);
	}
	
	protected Window getParent()
	{
		return(mWndParent);
	}
	
	public void mousePressed(final MouseEvent objMouseEvent)
	{
		Object objRow = null;
		int iRow = -1;
		JTable tbl = ((JTable)(objMouseEvent.getComponent()));
		Point pt = objMouseEvent.getPoint();
		
		iRow = tbl.rowAtPoint(pt);
		
		if((iRow >= 0) && (objMouseEvent.getClickCount() == 2))
			try
				{
				int iId = ((int)(tbl.getValueAt(iRow, 0)));
				
				objRow = getModel().getRow(iId);
				}
			catch(IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException objException)
				{
				objRow = null;
				}
		
		if(objRow != null)
			try
				{
				Constructor<? extends JDialog> dlgConstructor;
				JDialog dlg;
				
				dlgConstructor = getModel().getDetailDialogClass().getDeclaredConstructor(new Class<?>[] {Window.class, objRow.getClass(), getModel().getService().getClass()});
				
				if(!dlgConstructor.isAccessible())
					dlgConstructor.setAccessible(true);
				
				dlg = ((JDialog)(dlgConstructor.newInstance(new Object[] {getParent(), objRow, getModel().getService()})));
				
				dlg.setVisible(true);
				}
			catch(IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException objException)
				{
				objException.printStackTrace(System.err);
				}
	}
	
	protected void setModel(final ScrollablePageableModel objModel)
	{
		mObjModel = objModel;
	}
	
	protected void setParent(final Window wndParent)
	{
		mWndParent = wndParent;
	}
}