package net.draconia.jobsemailcollector.ui.listeners;

import java.io.Serializable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javax.swing.text.JTextComponent;

import net.draconia.jobsemailcollector.ui.model.SendResumeDialogModel;

public class SendResumeDialogChangeListener implements ChangeListener, Serializable
{
	private static final long serialVersionUID = -4137067612403401089L;
	
	private SendResumeDialogModel mObjModel;
	private String msField;
	
	public SendResumeDialogChangeListener(final SendResumeDialogModel objModel, final String sField)
	{
		setField(sField);
		setModel(objModel);
	}
	
	protected String getField()
	{
		return(msField);
	}
	
	protected SendResumeDialogModel getModel()
	{
		return(mObjModel);
	}
	
	protected String getModelValue() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		Class<?> clsModel = getModel().getClass();
		Method funcGetter = clsModel.getDeclaredMethod("get" + getField(), new Class<?>[0]);
		
		if(!funcGetter.isAccessible())
			funcGetter.setAccessible(true);
		
		return((String)(funcGetter.invoke(getModel(), new Object[0])));
	}
	
	protected void setField(final String sField)
	{
		msField = sField;
	}
	
	protected void setModel(final SendResumeDialogModel objModel)
	{
		mObjModel = objModel;
	}
	
	protected void setModelValue(final String sModelValue) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		Class<?> clsModel = getModel().getClass();
		Method funcSetter = clsModel.getDeclaredMethod("set" + getField(), new Class<?>[] {String.class});
		
		if(!funcSetter.isAccessible())
			funcSetter.setAccessible(true);
		
		funcSetter.invoke(getModel(), new Object[] {sModelValue});
	}
	
	public void stateChanged(final ChangeEvent objChangeEvent)
	{
		try
			{
			String sControlValue = ((JTextComponent)(objChangeEvent.getSource())).getText();
			String sModelValue = getModelValue();
			
			if(!sControlValue.equals(sModelValue))
				setModelValue(sControlValue);
			}
		catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
			{
			objException.printStackTrace(System.err);
			}
	}
}