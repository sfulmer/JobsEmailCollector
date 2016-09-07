package net.draconia.jobsemailcollector.manager;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import net.draconia.jobsemailcollector.model.Column;
import net.draconia.jobsemailcollector.model.FilterList;
import net.draconia.jobsemailcollector.model.Individual;
import net.draconia.jobsemailcollector.model.Model;

public class IndividualManager implements Serializable
{
	private static final long serialVersionUID = -6906555965149685323L;
	
	private Model mObjModel;
	
	public IndividualManager(final Model objModel)
	{
		setModel(objModel);
	}
	
	public void createIndividualTable() throws SQLException
	{
		Connection objConnection = getConnection();
		PreparedStatement objPreparedStatement = objConnection.prepareStatement("create table if not exists Individual (Id integer primary key autoincrement unique, Name text not null unique)");
		
		objPreparedStatement.executeUpdate();
	}
	
	protected Connection getConnection() throws SQLException
	{
		return(getDatabaseManager().getConnection());
	}
	
	public DatabaseManager getDatabaseManager()
	{
		return(getModel().getDatabaseManager());
	}
	
	public Long getIndividualCount() throws SQLException
	{
		Connection objConnection = null;
		PreparedStatement objPreparedStatement = null;
		ResultSet objResultSet = null;
		
		objConnection = getConnection();
		
		if(!getDatabaseManager().tableExists("Individual"))
			createIndividualTable();
		
		objPreparedStatement = objConnection.prepareStatement("select count(Id) from Individual;");
		objResultSet = objPreparedStatement.executeQuery();
		
		if(objResultSet.next())
			return(objResultSet.getLong(1));
		else
			return(0L);
	}
	
	public Individual getIndividualById(final Integer iId) throws SQLException
	{
		Connection objConnection = null;
		Individual objIndividual = new Individual();
		PreparedStatement objPreparedStatement = null;
		ResultSet objResultSet = null;
		
		try
			{
			objConnection = getConnection();
			
			if(!getDatabaseManager().tableExists("Individual"))
				createIndividualTable();
			
			objPreparedStatement = objConnection.prepareStatement("select Id, Name from Individual where Id = ?;");
			objPreparedStatement.setInt(1, iId);
			
			objResultSet = objPreparedStatement.executeQuery();
			
			if(objResultSet.next())
				{
				objIndividual.setId(objResultSet.getInt("Id"));
				objIndividual.setName(objResultSet.getString("Name"));
				objIndividual.setEmail(new EmailManager(getDatabaseManager()).getEmailByIndividual(objIndividual));
				
				objIndividual.addEmailAddresses(new EmailAddressManager(getDatabaseManager()).getEmailAddressesByIndividual(objIndividual));
				objIndividual.addPhoneNumbers(new PhoneNumberManager(getDatabaseManager()).getPhoneNumbersByIndividual(objIndividual));
				}
			
			return(objIndividual);
			}
		finally
			{
			if(objResultSet != null)
				objResultSet.close();
			if(objPreparedStatement != null)
				objPreparedStatement.close();
			}
	}
	
	public Individual getIndividualByName(final String sName) throws SQLException
	{
		Connection objConnection = null;
		Individual objIndividual = new Individual();
		PreparedStatement objPreparedStatement = null;
		ResultSet objResultSet = null;
		
		try
			{
			objConnection = getConnection();
			
			if(!getDatabaseManager().tableExists("Individual"))
				createIndividualTable();
			
			objPreparedStatement = objConnection.prepareStatement("select Id, Name from Individual where Name = ?;");
			objPreparedStatement.setString(1, sName);
			
			objResultSet = objPreparedStatement.executeQuery();
			
			if(objResultSet.next())
				{
				objIndividual.setId(objResultSet.getInt("Id"));
				objIndividual.setName(objResultSet.getString("Name"));
				objIndividual.setEmail(new EmailManager(getDatabaseManager()).getEmailByIndividual(objIndividual));
				
				objIndividual.addEmailAddresses(new EmailAddressManager(getDatabaseManager()).getEmailAddressesByIndividual(objIndividual));
				objIndividual.addPhoneNumbers(new PhoneNumberManager(getDatabaseManager()).getPhoneNumbersByIndividual(objIndividual));
				}
			
			return(objIndividual);
			}
		finally
			{
			if(objResultSet != null)
				objResultSet.close();
			if(objPreparedStatement != null)
				objPreparedStatement.close();
			}
	}
	
	public List<Individual> getIndividuals(final int iPage, final int iPageSize) throws SQLException
	{
		return(getIndividuals(null, iPage, iPageSize));
	}
	
	public List<Individual> getIndividuals(final FilterList lstFilters, final int iPage, final int iPageSize) throws SQLException
	{
		Connection objConnection = null;
		List<Individual> lstIndividuals = Collections.synchronizedList(new ArrayList<Individual>());
		PreparedStatement objPreparedStatement = null;
		ResultSet objResultSet = null;
		String sSQL = getSelectSQL	(	new Column[]
										{	new Column("Individual", "Id", "Individual.Id")
										,	new Column("Individual", "Name", "Individual.Name")
										}
									,	new String[]
										{	"Individual"	}
									,	lstFilters
									, 	iPage
									, 	iPageSize
									);
		
		objConnection = getConnection();
		objPreparedStatement = objConnection.prepareStatement(sSQL);
		
		objResultSet = objPreparedStatement.executeQuery();
		
		while(objResultSet.next())
			{
			Individual objIndividual = new Individual();
			
			objIndividual.setId(objResultSet.getInt("Id"));
			objIndividual.setName(objResultSet.getString("Name"));
			objIndividual.setEmail(new EmailManager(getDatabaseManager()).getEmailByIndividual(objIndividual));
			
			objIndividual.addEmailAddresses(new EmailAddressManager(getDatabaseManager()).getEmailAddressesByIndividual(objIndividual));
			objIndividual.addPhoneNumbers(new PhoneNumberManager(getDatabaseManager()).getPhoneNumbersByIndividual(objIndividual));
			}
		
		return(lstIndividuals);
	}
	
	public Model getModel()
	{
		return(mObjModel);
	}
	
	protected String getSelectSQL(final Column[] arrObjColumns, final String[] sArrTables, final FilterList lstFilters)
	{
		return(getSelectSQL(arrObjColumns, sArrTables, lstFilters, null, null));
	}
	
	protected String getSelectSQL(final Column[] arrObjColumns, final String[] sArrTables, final FilterList lstFilters, final Integer iPage, final Integer iPageSize)
	{
		String sSQL = "select ";
		
		sSQL = sSQL.concat(StringUtils.join(arrObjColumns, ", "));
		
		sSQL = sSQL.concat(" from Individual");
		
		return(sSQL);
	}
	
	public Individual insertIndividual(Individual objIndividual) throws SQLException
	{
		Connection objConnection = null;
		PreparedStatement objPreparedStatement = null;
		ResultSet objResultSet = null;
		
		try
			{
			objConnection = getConnection();
			objPreparedStatement = objConnection.prepareStatement("insert into Individual (Name) values (?)", Statement.RETURN_GENERATED_KEYS);
			
			objPreparedStatement.setString(1, objIndividual.getName());
			
			if(objPreparedStatement.executeUpdate() == 1)
				{
				objResultSet = objPreparedStatement.getGeneratedKeys();
				
				if(objResultSet.next())
					{
					EmailManager objEmailManager = new EmailManager(getDatabaseManager());
					EmailAddressManager objEmailAddressManager = new EmailAddressManager(getDatabaseManager());
					PhoneNumberManager objPhoneNumberManager = new PhoneNumberManager(getDatabaseManager());
					
					objIndividual.setId(objResultSet.getInt(1));
					
					objEmailAddressManager.addEmailAddresses(objIndividual, objEmailAddressManager.getEmailAddressesByIndividual(objIndividual), objIndividual.getEmailAddressList());
					objPhoneNumberManager.addPhoneNumbers(objIndividual, objPhoneNumberManager.getPhoneNumbersByIndividual(objIndividual), objIndividual.getPhoneNumberList());
					
					objEmailManager.insertEmail(objIndividual.getEmail());
					}
				}
			
			return(objIndividual);
			}
		finally
			{
			if(objResultSet != null)
				objResultSet.close();
			if(objPreparedStatement != null)
				objPreparedStatement.close();
			}
	}
	
	protected void setModel(final Model objModel)
	{
		mObjModel = objModel;
	}
	
	public boolean updateIndividual(final Individual objIndividual) throws SQLException
	{
		Connection objConnection = null;
		PreparedStatement objPreparedStatement = null;
		
		try
			{
			objConnection = getConnection();
			objPreparedStatement = objConnection.prepareStatement("update Individual set Name = ? where Id = ?");
			
			(new EmailManager(getDatabaseManager())).updateEmail(objIndividual.getEmail());
			
			objPreparedStatement.setString(1, objIndividual.getName());
			objPreparedStatement.setInt(2, objIndividual.getId());
			
			objPreparedStatement.executeUpdate();
			new EmailAddressManager(getDatabaseManager()).synchronizeEmailAddresses(objIndividual);
			new PhoneNumberManager(getDatabaseManager()).synchronizePhoneNumbers(objIndividual);
			new EmailManager(getDatabaseManager()).updateEmail(objIndividual.getEmail());
			
			return(true);
			}
		finally
			{
			if(objPreparedStatement != null)
				objPreparedStatement.close();
			}
	}
}