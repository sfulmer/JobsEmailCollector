package net.draconia.jobsemailcollector.hibernate.dialect;

import java.sql.Types;

import org.hibernate.dialect.Dialect;

public class Sqlite extends Dialect
{
	public Sqlite()
	{
		super();
		
		registerColumnType(Types.DATE, "integer");
		registerColumnType(Types.TIME, "integer");
		registerColumnType(Types.TIMESTAMP, "integer");
	}
}