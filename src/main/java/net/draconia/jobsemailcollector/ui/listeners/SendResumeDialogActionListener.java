package net.draconia.jobsemailcollector.ui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.Serializable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JComboBox;

import net.draconia.jobsemailcollector.ui.model.SendResumeDialogModel;

public class SendResumeDialogActionListener implements ActionListener, Serializable
{
	private static final long serialVersionUID = -5858122929734771619L;
	
	private SendResumeDialogModel mObjModel;
	private String msField;
	
	public SendResumeDialogActionListener(final SendResumeDialogModel objModel, final String sField)
	{
		setField(sField);
		setModel(objModel);
	}
	
	@SuppressWarnings("unchecked")
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		try
			{
			JComboBox<String> cboSource = ((JComboBox<String>)(objActionEvent.getSource()));
			String sControlValue = ((String)(cboSource.getSelectedItem()));
			String sModelValue = getModelValue();
			
			if(!sControlValue.equals(sModelValue))
				setModelValue(sControlValue);
			}
		catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
			{
			objException.printStackTrace(System.err);
			}
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
}