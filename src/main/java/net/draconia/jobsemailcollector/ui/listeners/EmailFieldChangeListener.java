package net.draconia.jobsemailcollector.ui.listeners;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;

import net.draconia.jobsemailcollector.domain.Email;
import net.sourceforge.jdatepicker.DateModel;

public class EmailFieldChangeListener implements ChangeListener, Serializable
{
	private static final long serialVersionUID = 7379472177148471373L;
	
	private Email mObjModel;
	private String msField;
	
	public EmailFieldChangeListener(final Email objModel, final String sField)
	{
		setField(sField);
		setModel(objModel);
	}
	
	protected String getField()
	{
		return(msField);
	}
	
	protected String getFieldValue() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		return(((String)(getGetter().invoke(getModel(), new Object[0]))));
	}
	
	protected Method getGetter() throws NoSuchMethodException, SecurityException
	{
		Class<?> clsModel = getModel().getClass();
		Class<?>[] arrParameters = new Class<?>[0];
		Method funcGetter;
		String sMethodName = "get" + getField();
		
		funcGetter = clsModel.getDeclaredMethod(sMethodName, arrParameters);
		
		if(!funcGetter.isAccessible())
			funcGetter.setAccessible(true);
		
		return(funcGetter);
	}
	
	protected Email getModel()
	{
		return(mObjModel);
	}
	
	protected Method getSetter() throws NoSuchMethodException, SecurityException
	{
		Class<?> clsModel = getModel().getClass();
		Class<?>[] arrParameters = new Class<?>[] {String.class};
		Method funcSetter;
		String sMethodName = "set" + getField();
		
		funcSetter = clsModel.getDeclaredMethod(sMethodName, arrParameters);
		
		if(!funcSetter.isAccessible())
			funcSetter.setAccessible(true);
		
		return(funcSetter); 
	}
	
	protected void setField(final String sField)
	{
		msField = sField;
	}
	
	protected void setFieldValue(final Object objValue) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		getSetter().invoke(getModel(), new Object[] {objValue});
	}
	
	protected void setModel(final Email objEmail)
	{
		mObjModel = objEmail;
	}
	
	@SuppressWarnings("unchecked")
	public void stateChanged(final ChangeEvent objChangeEvent)
	{
		Object objControlValue = null;
		
		if(objChangeEvent.getSource() instanceof JTextComponent)
			{
			JTextComponent txtSource = ((JTextComponent)(objChangeEvent.getSource()));
			
			objControlValue = txtSource.getText();
			}
		else if(objChangeEvent.getSource() instanceof DateModel<?>)
			{
			DateModel<Date> dtSource = ((DateModel<Date>)(objChangeEvent.getSource()));
			
			objControlValue = dtSource.getValue();
			}
		
		if(objControlValue != null)
			try
				{
				if(!objControlValue.equals(getFieldValue()))
					setFieldValue(objControlValue);
				}
			catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
				{
				objException.printStackTrace(System.err);
				}
	}
}