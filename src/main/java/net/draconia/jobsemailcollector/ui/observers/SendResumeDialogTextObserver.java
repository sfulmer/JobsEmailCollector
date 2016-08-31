package net.draconia.jobsemailcollector.ui.observers;

import java.io.Serializable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Observable;
import java.util.Observer;

import javax.swing.text.JTextComponent;

import net.draconia.jobsemailcollector.ui.model.SendResumeDialogModel;

public class SendResumeDialogTextObserver implements Observer, Serializable
{
	private static final long serialVersionUID = 4287698213145674018L;
	
	private JTextComponent mTxtControl;
	private String msField;
	
	public SendResumeDialogTextObserver(final JTextComponent txtControl, final String sField)
	{
		setField(sField);
		setTextField(txtControl);
	}
	
	protected String getField()
	{
		return(msField);
	}
	
	protected JTextComponent getTextField()
	{
		return(mTxtControl);
	}
	
	protected void setField(final String sField)
	{
		msField = sField;
	}
	
	protected void setTextField(final JTextComponent txtControl)
	{
		mTxtControl = txtControl;
	}
	
	public void update(final Observable objObservable, final Object objArgument)
	{
		try
			{
			Class<?> clsModel = SendResumeDialogModel.class;
			Method funcGetter;
			SendResumeDialogModel objModel = ((SendResumeDialogModel)(objObservable));
			String sModelValue = "";
			
			funcGetter = clsModel.getDeclaredMethod("get" + getField(), new Class<?>[0]);
			
			if(!funcGetter.isAccessible())
				funcGetter.setAccessible(true);
			
			sModelValue = ((String)(funcGetter.invoke(objModel, new Object[0])));
			
			if(!sModelValue.equals(getTextField().getText()))
				getTextField().setText(sModelValue);
			}
		catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException objException)
			{
			objException.printStackTrace(System.err);
			}
	}
}