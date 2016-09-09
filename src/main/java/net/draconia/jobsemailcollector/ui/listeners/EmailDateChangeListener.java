package net.draconia.jobsemailcollector.ui.listeners;

import java.io.Serializable;

import java.util.Date;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.draconia.jobsemailcollector.domain.Email;
import net.sourceforge.jdatepicker.DateModel;

public class EmailDateChangeListener implements ChangeListener, Serializable
{
	private static final long serialVersionUID = 7379472177148471373L;
	
	private Email mObjModel;
	
	public EmailDateChangeListener(final Email objModel)
	{
		setModel(objModel);
	}
	
	protected Email getModel()
	{
		return(mObjModel);
	}
	
	protected Date getValue()
	{
		return(getModel().getDate());
	}
	
	protected void setModel(final Email objEmail)
	{
		mObjModel = objEmail;
	}
	
	protected void setValue(final Date dtValue)
	{
		getModel().setDate(dtValue);
	}
	
	@SuppressWarnings("unchecked")
	public void stateChanged(final ChangeEvent objChangeEvent)
	{
		Date dtValue = null;
		
		if(objChangeEvent.getSource() instanceof DateModel<?>)
			{
			DateModel<Date> dtSource = ((DateModel<Date>)(objChangeEvent.getSource()));
			
			dtValue = dtSource.getValue();
			}
		
		if(dtValue != null)
			if(!dtValue.equals(getValue()))
				setValue(dtValue);
	}
}