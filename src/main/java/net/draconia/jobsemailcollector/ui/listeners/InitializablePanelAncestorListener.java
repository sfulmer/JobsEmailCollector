package net.draconia.jobsemailcollector.ui.listeners;

import java.awt.Window;

import java.io.Serializable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import net.draconia.jobsemailcollector.ui.InitializablePanel;

public class InitializablePanelAncestorListener implements AncestorListener, Serializable
{
	private static final long serialVersionUID = -6681341010511604293L;
	
	private InitializablePanel mObjPanel;
	
	public InitializablePanelAncestorListener(final InitializablePanel objPanel)
	{
		setPanel(objPanel);
	}
	
	public void ancestorAdded(final AncestorEvent objAncestorEvent)
	{
		try
			{
			Method funcInit = getPanel().getClass().getDeclaredMethod("initPanel", new Class<?>[0]);
			
			if(!funcInit.isAccessible())
				funcInit.setAccessible(true);
			
			funcInit.invoke(getPanel(), new Object[0]);
			}
		catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
			{
			objException.printStackTrace(System.err);
			}
		
		((Window)(objAncestorEvent.getComponent().getTopLevelAncestor())).pack();
	}
	
	public void ancestorMoved(final AncestorEvent objAncestorEvent)
	{ }
	
	public void ancestorRemoved(final AncestorEvent objAncestorEvent)
	{ }
	
	protected InitializablePanel getPanel()
	{
		return(mObjPanel);
	}
	
	protected void setPanel(final InitializablePanel objPanel)
	{
		mObjPanel = objPanel;
	}
}