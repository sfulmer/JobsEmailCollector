package net.draconia.jobsemailcollector.manager;

import java.io.Serializable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager implements Serializable
{
	private static final long serialVersionUID = -1926050208507821580L;
	
	private Connection mObjConnection;
	private String msFilename;
	
	public DatabaseManager(final String sFilename)
	{
		setFilename(sFilename);
	}
	
	protected void close() throws SQLException
	{
		if(mObjConnection != null)
			{
			mObjConnection.close();
			
			mObjConnection = null;
			}
	}
	
	protected Connection getConnection() throws SQLException
	{
		if((mObjConnection != null) && (!mObjConnection.isClosed()))
			return(mObjConnection);
		else
			return(mObjConnection = DriverManager.getConnection("jdbc:sqlite:" + getFilename()));
	}
	
	public String getFilename()
	{
		if(msFilename == null)
			msFilename = "";
		
		return(msFilename);
	}
	
	public boolean isClosed() throws SQLException
	{
		if(mObjConnection != null)
			return(mObjConnection.isClosed());
		else
			return(true);
	}
	
	public void setFilename(final String sFilename)
	{
		if(sFilename == null)
			msFilename = "";
		else
			msFilename = sFilename;
		
		try
			{
			if(!isClosed())
				close();
			}
		catch(SQLException objSQLException)
			{
			objSQLException.printStackTrace(System.err);
			}
	}
	
	public boolean tableExists(final String sTableName)
	{
		try
			{
			PreparedStatement objPreparedStatement = getConnection().prepareStatement("SELECT name FROM sqlite_master WHERE type='table' AND name=?");
			ResultSet objResultSet;
			
			objPreparedStatement.setString(1, sTableName);
			
			objResultSet = objPreparedStatement.executeQuery();
			
			return(objResultSet.next());
			}
		catch(SQLException objSQLException)
			{
			return(false);
			}
	}
}