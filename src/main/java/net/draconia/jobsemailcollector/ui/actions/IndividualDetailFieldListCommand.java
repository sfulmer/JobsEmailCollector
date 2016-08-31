package net.draconia.jobsemailcollector.ui.actions;

import java.awt.Window;
import java.lang.reflect.Method;

import javax.swing.AbstractAction;

import net.draconia.jobsemailcollector.model.Individual;

public abstract class IndividualDetailFieldListCommand extends AbstractAction
{
	private static final long serialVersionUID = 3188803369123289495L;
	
	private Individual mObjModel;
	private String msCommand, msField;
	private Window mWndParent;
	
	public IndividualDetailFieldListCommand(final Window wndParent, final Individual objModel, final String sField, final String sCommand)
	{
		super(sCommand);
		
		setCommand(sCommand);
		setField(sField);
		setModel(objModel);
		setParentWindow(wndParent);
	}
	
	protected String getCommand()
	{
		return(msCommand);
	}
	
	protected String getField()
	{
		return(msField);
	}
	
	protected Method getFieldListMethod()
	{
		try
			{
			return(getModelClass().getMethod(getFieldListMethodName(), new Class<?>[] {String.class}));
			}
		catch(NoSuchMethodException objNoSuchMethodException)
			{
			objNoSuchMethodException.printStackTrace(System.err);
			
			return(null);
			}
	}
	
	protected String getFieldListMethodName()
	{
		return(getCommand().toLowerCase() + getField());
	}
	
	protected Individual getModel()
	{
		return(mObjModel);
	}
	
	protected Class<?> getModelClass()
	{
		return(getModel().getClass());
	}
	
	protected Window getParentWindow()
	{
		return(mWndParent);
	}
	
	protected void setCommand(final String sCommand)
	{
		msCommand = sCommand;
	}
	
	protected void setField(final String sField)
	{
		msField = sField;
	}
	
	protected void setModel(final Individual objModel)
	{
		mObjModel = objModel;
	}
	
	protected void setParentWindow(final Window wndParent)
	{
		mWndParent = wndParent;
	}
}