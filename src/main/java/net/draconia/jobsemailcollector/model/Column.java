package net.draconia.jobsemailcollector.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class Column implements Serializable
{
	private static final long serialVersionUID = 5147552453819544868L;
	
	private String msAlias, msField, msTable;
	
	public Column()
	{ }
	
	public Column(final String sTable, final String sField)
	{
		this(sTable, sField, null);
	}
	
	public Column(final String sTable, final String sField, final String sAlias)
	{
		setAlias(sAlias);
		setField(sField);
		setTable(sTable);
	}
	
	public String getAlias()
	{
		if(msAlias == null)
			msAlias = "";
		
		return(msAlias);
	}
	
	public String getField()
	{
		if(msField == null)
			msField = "";
		
		return(msField);
	}
	
	public String getTable()
	{
		if(msTable == null)
			msTable = "";
		
		return(msTable);
	}
	
	public void setAlias(final String sAlias)
	{
		if(sAlias == null)
			msAlias = "";
		else
			msAlias = sAlias;
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
	
	public String toString()
	{
		String sAlias = "as \"" + getAlias() + "\"";
		String sToString = StringUtils.join(new String[] {StringUtils.join(new String[] {getTable(), getField()}, "."), sAlias}, " ");
		
		return(sToString);
	}
}