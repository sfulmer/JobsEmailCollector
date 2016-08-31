package net.draconia.jobsemailcollector.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.draconia.jobsemailcollector.model.Individual;

public class EmailAddressManager
{
	private DatabaseManager mObjDatabaseManager;
	
	public EmailAddressManager(final DatabaseManager objDatabaseManager)
	{
		setDatabaseManager(objDatabaseManager);
	}
	
	public boolean addEmailAddresses(final Individual objIndividual, final List<String> lstDB, final List<String> lstLocal) throws SQLException
	{
		boolean bReturnValue = true;
		List<String> lstWork = Collections.synchronizedList(new ArrayList<String>(lstLocal));
		
		lstWork.removeAll(lstDB);
		
		for(String sEmailAddress : lstWork)
			if(bReturnValue)
				bReturnValue = (insertEmailAddress(sEmailAddress, objIndividual) > 0);
			else
				break;
		
		return(bReturnValue);
	}
	
	public void createEmailTable() throws SQLException
	{
		Connection objConnection = getConnection();
		PreparedStatement objPreparedStatement = objConnection.prepareStatement("create table if not exists EmailAddress (Id integer primary key autoincrement unique, Individual integer not null, EmailAddress text not null default ' ', foreign key('Individual') references Individual(Id))");
		
		objPreparedStatement.executeUpdate();
		objPreparedStatement.close();
	}
	
	protected Connection getConnection() throws SQLException
	{
		return(getDatabaseManager().getConnection());
	}
	
	protected DatabaseManager getDatabaseManager()
	{
		return(mObjDatabaseManager);
	}
	
	public List<String> getEmailAddressesByIndividual(final Individual objIndividual) throws SQLException
	{
		Connection objConnection = null;
		PreparedStatement objPreparedStatement = null;
		ResultSet objResultSet = null;
		
		try
			{
			List<String> lstEmailAddresses = Collections.synchronizedList(new ArrayList<String>());
			
			objConnection = getConnection();
			objPreparedStatement = objConnection.prepareStatement("select EmailAddress from EmailAddress where Individual = ?");
			
			objPreparedStatement.setInt(1, objIndividual.getId());
			
			objResultSet = objPreparedStatement.executeQuery();
			
			while(objResultSet.next())
				lstEmailAddresses.add(objResultSet.getString("EmailAddress"));
			
			return(lstEmailAddresses);
			}
		finally
			{
			if(objResultSet != null)
				objResultSet.close();
			if(objPreparedStatement != null)
				objPreparedStatement.close();
			}
	}
	
	public int getEmailAddressIdByIndividualAndAddress(final String sAddress, final Individual objIndividual) throws SQLException
	{
		Connection objConnection = null;
		PreparedStatement objPreparedStatement = null;
		ResultSet objResultSet = null;
		
		try
			{
			objConnection = getConnection();
			objPreparedStatement = objConnection.prepareStatement("select Id from EmailAddress where EmailAddress = ? and Individual = ?");
			
			objPreparedStatement.setString(1, sAddress);
			objPreparedStatement.setInt(2, objIndividual.getId());
			
			objResultSet = objPreparedStatement.executeQuery();
			
			if(objResultSet.next())
				return(objResultSet.getInt(1));
			else
				return(0);
			}
		finally
			{
			if(objResultSet != null)
				objResultSet.close();
			if(objPreparedStatement != null)
				objPreparedStatement.close();
			}
	}
	
	public int insertEmailAddress(final String sEmailAddress, final Individual objIndividual)  throws SQLException
	{
		Connection objConnection = null;
		PreparedStatement objPreparedStatement = null;
		ResultSet objResultSet = null;
		
		try
			{
			objConnection = getConnection();
			objPreparedStatement = objConnection.prepareStatement("insert into EmailAddress (Individual, EmailAddress) values(?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			objPreparedStatement.setInt(1, objIndividual.getId());
			objPreparedStatement.setString(2, sEmailAddress);
			
			if(objPreparedStatement.executeUpdate() == 1)
				{
				objResultSet = objPreparedStatement.getGeneratedKeys();
				
				if(objResultSet.next())
					return(objResultSet.getInt(1));
				}
			
			return(0);
			}
		finally
			{
			if(objResultSet != null)
				objResultSet.close();
			if(objPreparedStatement != null)
				objPreparedStatement.close();
			}
	}
	
	public boolean removeDeletedEmailAddresses(final Individual objIndividual, final List<String> lstDB, final List<String> lstLocal) throws SQLException
	{
		boolean bReturnValue = true;
		Connection objConnection = null;
		List<String> lstWork = Collections.synchronizedList(new ArrayList<String>(lstDB));
		PreparedStatement objPreparedStatement = null;
		
		try
			{
			objConnection = getConnection();
			
			lstWork.removeAll(lstLocal);
			
			for(String sEmailAddress : lstWork)
				if(bReturnValue)
					{
					objPreparedStatement = objConnection.prepareStatement("delete from EmailAddress where Individual = ? and EmailAddress = ?");
					
					objPreparedStatement.setInt(1, objIndividual.getId());
					objPreparedStatement.setString(2, sEmailAddress);
					
					bReturnValue = (objPreparedStatement.executeUpdate() == 1);
					}
				else
					break;
			
			return(bReturnValue);
			}
		finally
			{
			if(objPreparedStatement != null)
				objPreparedStatement.close();
			}
	}
	
	protected void setDatabaseManager(final DatabaseManager objDatabaseManager)
	{
		mObjDatabaseManager = objDatabaseManager;
	}
	
	public boolean synchronizeEmailAddresses(final Individual objIndividual)  throws SQLException
	{
		boolean bReturnValue = true;
		List<String> lstDB = getEmailAddressesByIndividual(objIndividual), lstLocal = objIndividual.getEmailAddressList();
		
		if(bReturnValue)
			if(bReturnValue = removeDeletedEmailAddresses(objIndividual, lstDB, lstLocal))
				bReturnValue = addEmailAddresses(objIndividual, lstDB, lstLocal);

		return(bReturnValue);
	}
}