package net.draconia.jobsemailcollector.model;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.StringUtils;

enum Comparison
{	EQUALS("=")
,	GREATERTHAN(">")
,	GREATERTHANEQUALS(">=")
,	LESSTHAN("<")
,	LESSTHANEQUALS("<=");
	
	private String msOperator;
	
	private Comparison(final String sOperator)
	{
		setOperator(sOperator);
	}
	
	protected String getOperator()
	{
		return(msOperator);
	}
	
	protected void setOperator(final String sOperator)
	{
		msOperator = sOperator;
	}
	
	public String toString()
	{
		return(getOperator());
	}
}

public class Filter implements Serializable
{
	private static final long serialVersionUID = 4219936111597630773L;
	
	private Comparison meComparison;
	private Object mObjValue;
	private String msField, msTable;
	
	public Filter()
	{ }
	
	public Filter(final String sTable, final String sField)
	{
		setField(sField);
		setTable(sTable);
	}
	
	public Filter(final String sTable, final String sField, final Comparison eComparison, final Object objValue)
	{
		setComparison(eComparison);
		setField(sField);
		setTable(sTable);
		setValue(objValue);
	}
	
	public Filter between(final Object objValue1, final Object objValue2) throws IllegalAccessException, IllegalArgumentException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		Class<Comparison> clsComparison = Comparison.class;
		Comparison eComparison;
		Constructor<Comparison> funcConstructor = clsComparison.getDeclaredConstructor(new Class<?>[] {String.class});
		
		if(!funcConstructor.isAccessible())
			funcConstructor.setAccessible(true);
		
		eComparison = funcConstructor.newInstance(new Object[] {"between"});
		
		setComparison(eComparison);
		setValue(new Object[] {objValue1, objValue2});
		
		return(this);
	}
	
	protected Comparison getComparison()
	{
		return(meComparison);
	}
	
	protected String getField()
	{
		if(msField == null)
			msField = "";
		
		return(msField);
	}
	
	protected String getTable()
	{
		if(msTable == null)
			msTable = "";
		
		return(msTable);
	}
	
	protected Object getValue()
	{
		return(mObjValue);
	}
	
	public Filter in(final Object[] arrObjValues) throws IllegalAccessException, IllegalArgumentException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		Class<Comparison> clsComparison = Comparison.class;
		Comparison eComparison;
		Constructor<Comparison> funcConstructor = clsComparison.getDeclaredConstructor(new Class<?>[] {String.class});
		
		if(!funcConstructor.isAccessible())
			funcConstructor.setAccessible(true);
		
		eComparison = funcConstructor.newInstance(new Object[] {"in"});
		
		setComparison(eComparison);
		setValue(arrObjValues);
		
		return(this);
	}
	
	public void setComparison(final Comparison eComparison)
	{
		meComparison = eComparison;
	}
	
	public void setField(final String sField)
	{
		if(sField == null)
			msField = "";
		else
			msField = sField;
	}
	
	public void setTable(final String sTable)
	{
		if(sTable == null)
			msTable = "";
		else
			msTable = sTable;
	}
	
	public void setValue(final Object objValue)
	{
		mObjValue = objValue;
	}
	
	public String toString()
	{	
		Object objValue = getValue();
		String sField = getTable(), sValue;
		
		if(sField.length() > 0)
			sField = sField.concat(".");
		else
			sField = getField();
		
		if(objValue.getClass().isArray())
			sValue = StringUtils.join(new Object[] {"(", getValue(), "}"});
		else
			sValue = objValue.toString();
		
		return(StringUtils.join(new String[] {getField(), getComparison().toString(), sValue}, " "));
	}
}