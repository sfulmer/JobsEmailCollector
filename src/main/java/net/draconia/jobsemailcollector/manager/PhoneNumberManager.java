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

public class PhoneNumberManager
{
	private DatabaseManager mObjDatabaseManager;
	
	public PhoneNumberManager(final DatabaseManager objDatabaseManager)
	{
		setDatabaseManager(objDatabaseManager);
	}
	
	public boolean addPhoneNumbers(final Individual objIndividual, final List<String> lstDB, final List<String> lstLocal) throws SQLException
	{
		boolean bReturnValue = true;
		List<String> lstWork = Collections.synchronizedList(new ArrayList<String>(lstLocal));
		
		lstWork.removeAll(lstDB);
		
		for(String sPhoneNumber : lstWork)
			if(bReturnValue)
				bReturnValue = (insertPhoneNumber(sPhoneNumber, objIndividual) > 0);
			else
				break;
		
		return(bReturnValue);
	}
	
	public void createPhoneNumberTable() throws SQLException
	{
		Connection objConnection = getConnection();
		PreparedStatement objPreparedStatement = objConnection.prepareStatement("create table if not exists PhoneNumber (Id integer primary key autoincrement unique, Individual integer not null, PhoneNumber text not null default ' ', foreign key('Individual') references Individual(Id))");
		
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
	
	public List<String> getPhoneNumbersByIndividual(final Individual objIndividual) throws SQLException
	{
		Connection objConnection = null;
		List<String> lstPhoneNumbers = Collections.synchronizedList(new ArrayList<String>());
		PreparedStatement objPreparedStatement = null;
		ResultSet objResultSet = null;
		
		try
			{
			objConnection = getConnection();
			objPreparedStatement = objConnection.prepareStatement("select PhoneNumber from PhoneNumber where Individual = ?");
			
			objPreparedStatement.setInt(1, objIndividual.getId());
			
			objResultSet = objPreparedStatement.executeQuery();
			
			while(objResultSet.next())
				lstPhoneNumbers.add(objResultSet.getString("PhoneNumber"));
			
			return(lstPhoneNumbers);
			}
		finally
			{
			if(objResultSet != null)
				objResultSet.close();
			if(objPreparedStatement != null)
				objPreparedStatement.close();
			}
	}
	
	public int getPhoneNumberIdByIndividualAndNumber(final String sPhoneNumber, final Individual objIndividual) throws SQLException
	{
		Connection objConnection = null;
		PreparedStatement objPreparedStatement = null;
		ResultSet objResultSet = null;
		
		try
			{
			objConnection = getConnection();
			objPreparedStatement = objConnection.prepareStatement("select Id from PhoneNumber where PhoneNumber = ? and Individual = ?");
			
			objPreparedStatement.setString(1, sPhoneNumber);
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
	
	public int insertPhoneNumber(final String sPhoneNumber, final Individual objIndividual)  throws SQLException
	{
		Connection objConnection = null;
		PreparedStatement objPreparedStatement = null;
		ResultSet objResultSet = null;
		
		try
			{
			objConnection = getConnection();
			objPreparedStatement = objConnection.prepareStatement("insert into PhoneNumber (Individual, PhoneNumber) values(?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			objPreparedStatement.setInt(1, objIndividual.getId());
			objPreparedStatement.setString(2, sPhoneNumber);
			
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
	
	public boolean removeDeletedPhoneNumbers(final Individual objIndividual, final List<String> lstDB, final List<String> lstLocal) throws SQLException
	{
		boolean bReturnValue = true;
		Connection objConnection = null;
		List<String> lstWork = Collections.synchronizedList(new ArrayList<String>(lstDB));
		PreparedStatement objPreparedStatement = null;
		
		try
			{
			objConnection = getConnection();
			
			lstWork.removeAll(lstLocal);
			
			for(String sPhoneNumber : lstWork)
				if(bReturnValue)
					{
					objPreparedStatement = objConnection.prepareStatement("delete from PhoneNumber where Individual = ? and PhoneNumber = ?");
					
					objPreparedStatement.setInt(1, objIndividual.getId());
					objPreparedStatement.setString(2, sPhoneNumber);
					
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
	
	public boolean synchronizePhoneNumbers(final Individual objIndividual)  throws SQLException
	{
		boolean bReturnValue = true;
		List<String> lstDB = getPhoneNumbersByIndividual(objIndividual), lstLocal = objIndividual.getPhoneNumberList();
		
		if(bReturnValue)
			if(bReturnValue = removeDeletedPhoneNumbers(objIndividual, lstDB, lstLocal))
				bReturnValue = addPhoneNumbers(objIndividual, lstDB, lstLocal);

		return(bReturnValue);
	}
}