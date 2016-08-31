package net.draconia.jobsemailcollector.ui.model;

import java.io.Serializable;

import java.util.Observable;

public class IndividualDetailFieldListAddDialogModel extends Observable implements Serializable
{
	private static final long serialVersionUID = 5721436264597339655L;
	
	private String msOriginal, msValue;
	
	public IndividualDetailFieldListAddDialogModel()
	{ }
	
	public IndividualDetailFieldListAddDialogModel(final String sValue)
	{
		setOriginal(sValue);
	}
	
	public String getOriginal()
	{
		if(msOriginal == null)
			msOriginal = "";
		
		return(msOriginal);
	}
	
	public String getValue()
	{
		if(msValue == null)
			msValue = "";
		
		return(msValue);
	}
	
	public boolean isDirty()
	{
		return(!getOriginal().equals(getValue()));
	}
	
	public void setOriginal(final String sOriginal)
	{
		if(sOriginal == null)
			msOriginal = "";
		else
			msOriginal = sOriginal;
		
		setValue(new String(msOriginal));
	}
	
	public void setValue(final String sValue)
	{
		if(sValue == null)
			msValue = "";
		else
			msValue = sValue;
		
		setChanged();
		notifyObservers();
	}
}