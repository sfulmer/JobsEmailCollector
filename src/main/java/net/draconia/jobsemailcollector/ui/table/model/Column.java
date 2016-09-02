package net.draconia.jobsemailcollector.ui.table.model;

import java.io.Serializable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;

public class Column extends Observable implements Serializable
{
	private static final long serialVersionUID = -3132995981235020048L;
	
	private Boolean mbEditable;
	private Class<?> mClsType, mClsRowType;
	private String msGetterName, msName, msSetterName;
	
	public Column(final String sName, final Class<?> clsType, final Class<?> clsRowType, final String sGetterName)
	{
		this(sName, clsType, clsRowType, sGetterName, null, null);
	}
	
	public Column(final String sName, final Class<?> clsType, final Class<?> clsRowType, final String sGetterName, final String sSetterName)
	{
		this(sName, clsType, clsRowType, sGetterName, sSetterName, null);
	}
	
	public Column(final String sName, final Class<?> clsType, final Class<?> clsRowType, final String sGetterName, final Boolean bEditable)
	{
		this(sName, clsType, clsRowType, sGetterName, null, bEditable);
	}
	
	public Column(final String sName, final Class<?> clsType, final Class<?> clsRowType, final String sGetterName, final String sSetterName, final Boolean bEditable)
	{
		setEditable(bEditable);
		setGetterName(sGetterName);
		setName(sName);
		setRowType(clsRowType);
		setSetterName(sSetterName);
		setType(clsType);
	}
	
	public Method getGetter() throws NoSuchMethodException, SecurityException
	{
		Method funcGetter = getRowType().getDeclaredMethod(getGetterName(), new Class<?>[0]);
		
		if(!funcGetter.isAccessible())
			funcGetter.setAccessible(true);
		
		return(funcGetter);
	}
	
	public String getGetterName()
	{
		if(msGetterName == null)
			msGetterName = "";
		
		return(msGetterName);
	}
	
	public String getName()
	{
		if(msName == null)
			msName = "";
		
		return(msName);
	}
	
	public Class<?> getRowType()
	{
		return(mClsRowType);
	}
	
	public Method getSetter() throws NoSuchMethodException, SecurityException
	{
		Method funcSetter = getRowType().getDeclaredMethod(getSetterName(), new Class<?>[] {getType()});
		
		if(!funcSetter.isAccessible())
			funcSetter.setAccessible(true);
		
		return(funcSetter);
	}
	
	public String getSetterName()
	{
		if(msSetterName == null)
			msSetterName = "";
		
		return(msSetterName);
	}
	
	public Class<?> getType()
	{
		return(mClsType);
	}
	
	public Object getValue(final Object objRowModel) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		return(getGetter().invoke(objRowModel, new Object[0]));
	}
	
	public boolean isEditable()
	{
		if(mbEditable == null)
			mbEditable = false;
		
		return(mbEditable);
	}
	
	public void setEditable(final Boolean bEditable)
	{
		if(bEditable == null)
			mbEditable = false;
		else
			mbEditable = bEditable;
		
		setChanged();
		notifyObservers();
	}
	
	public void setGetterName(final String sGetterName)
	{
		if(sGetterName == null)
			msGetterName = "";
		else
			msGetterName = sGetterName;
		
		setChanged();
		notifyObservers();
	}
	
	public void setName(final String sName)
	{
		if(sName == null)
			msName = "";
		else
			msName = sName;
		
		setChanged();
		notifyObservers();
	}
	
	public void setRowType(final Class<?> clsRowType)
	{
		mClsRowType = clsRowType;
		
		setChanged();
		notifyObservers();
	}
	
	public void setSetterName(final String sSetterName)
	{
		if(sSetterName == null)
			msSetterName = "";
		else
			msSetterName = sSetterName;
		
		setChanged();
		notifyObservers();
	}
	
	public void setType(final Class<?> clsType)
	{
		mClsType = clsType;
		
		setChanged();
		notifyObservers();
	}
	
	public void setValue(final Object objRowModel, final Object objValue)
	{
		try
			{
			getSetter().invoke(objRowModel, new Object[] {objValue});
			}
		catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
			{
			objException.printStackTrace();
			}
	}
}