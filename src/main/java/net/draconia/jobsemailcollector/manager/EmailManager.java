package net.draconia.jobsemailcollector.manager;

import java.io.Serializable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import net.draconia.jobsemailcollector.model.Email;
import net.draconia.jobsemailcollector.model.Individual;

public class EmailManager implements Serializable
{
	private static final long serialVersionUID = 5665531183604694201L;
	
	private DatabaseManager mObjDatabaseManager;
	
	public EmailManager(final DatabaseManager objDatabaseManager)
	{
		setDatabaseManager(objDatabaseManager);
	}
	
	public void createEmailTable() throws SQLException
	{
		Connection objConnection = getConnection();
		PreparedStatement objPreparedStatement = objConnection.prepareStatement("create table if not exists Email (Id integer primary key autoincrement unique, Individual integer not null, DateReceived integer not null, EmailFrom text not null default ' ', EmailTo text not null default ' ', Subject text not null default ' ', Body text not null default ' ', CharacterSet text not null default ' ', ContentTransferEncoding text not null default ' ', ContentType text not null default ' ', foreign key('Individual') references Individual(Id))");
		
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
	
	public Email getEmailByIndividual(final Individual objIndividual) throws SQLException
	{
		Connection objConnection = null;
		PreparedStatement objPreparedStatement = null;
		ResultSet objResultSet = null;
		
		try
			{
			objConnection = getConnection();
			objPreparedStatement = objConnection.prepareStatement("select Id, Individual, DateReceived, EmailFrom, EmailTo, Subject, Body, CharacterSet, ContentTransferEncoding, ContentType from Email where Individual = ?");
			
			objPreparedStatement.setInt(1, objIndividual.getId());
			
			objResultSet = objPreparedStatement.executeQuery();
			
			if(objResultSet.next())
				{
				Email objEmail = new Email();
				Integer iId;
				Long lDateReceived;
				String sBody, sCharacterSet, sContentType, sContentTransferEncoding, sFrom, sSubject, sTo;
				
				iId = objResultSet.getInt("Id");
				
				if(objResultSet.wasNull())
					iId = 0;
				
				lDateReceived = objResultSet.getLong("DateReceived");
				
				if(objResultSet.wasNull())
					lDateReceived = null;
				
				sFrom = objResultSet.getString("EmailFrom");
				
				if(objResultSet.wasNull())
					sFrom = "";
				
				sTo = objResultSet.getString("EmailTo");
				
				if(objResultSet.wasNull())
					sTo = "";
					
				sSubject = objResultSet.getString("Subject");
				
				if(objResultSet.wasNull())
					sSubject = "";
				
				sBody = objResultSet.getString("Body");
				
				if(objResultSet.wasNull())
					sBody = "";
				
				sCharacterSet = objResultSet.getString("CharacterSet");
				
				if(objResultSet.wasNull())
					sCharacterSet = "";
				
				sContentTransferEncoding = objResultSet.getString("ContentTransferEncoding");
				
				if(objResultSet.wasNull())
					sContentTransferEncoding = "";
				
				sContentType = objResultSet.getString("ContentType");
				
				if(objResultSet.wasNull())
					sContentType = "";
				
				objEmail.setId(iId);
				objEmail.setIndividual(objIndividual);
				objEmail.setDate(new Date(lDateReceived));
				objEmail.setFrom(sFrom);
				objEmail.setTo(sTo);
				objEmail.setSubject(sSubject);
				objEmail.setBody(sBody);
				objEmail.setCharacterSet(sCharacterSet);
				objEmail.setContentTransferEncoding(sContentTransferEncoding);
				objEmail.setContentType(sContentType);
				
				return(objEmail);
				}
			else
				return(null);
			}
		finally
			{
			if(objResultSet != null)
				objResultSet.close();
			if(objPreparedStatement != null)
				objPreparedStatement.close();
			}
	}
	
	public Email insertEmail(final Email objEmail) throws SQLException
	{
		Connection objConnection = null;
		PreparedStatement objPreparedStatement = null;
		ResultSet objResultSet = null;
		
		try
			{
			objConnection = getConnection();
			objPreparedStatement = objConnection.prepareStatement("insert into Email (Individual, DateReceived, EmailFrom, EmailTo, Subject, Body, CharacterSet, ContentTransferEncoding, ContentType) values(?, ?, ?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			
			objPreparedStatement.setInt(1, objEmail.getIndividual().getId());
			objPreparedStatement.setLong(2, objEmail.getDate().getTime());
			objPreparedStatement.setString(3, objEmail.getFrom());
			objPreparedStatement.setString(4, objEmail.getTo());
			objPreparedStatement.setString(5, objEmail.getSubject());
			objPreparedStatement.setString(6, objEmail.getBody());
			objPreparedStatement.setString(7, objEmail.getCharacterSet());
			objPreparedStatement.setString(8, objEmail.getContentTransferEncoding());
			objPreparedStatement.setString(9, objEmail.getContentType());
			
			if(objPreparedStatement.executeUpdate() == 1)
				{
				objResultSet = objPreparedStatement.getGeneratedKeys();
				
				if(objResultSet.next())
					objEmail.setId(objResultSet.getInt(1));
				}
			}
		finally
			{
			if(objResultSet != null)
				objResultSet.close();
			if(objPreparedStatement != null)
				objPreparedStatement.close();
			}
		
		return(objEmail);
	}
	
	protected void setDatabaseManager(final DatabaseManager objDatabaseManager)
	{
		mObjDatabaseManager = objDatabaseManager;
	}
	
	public boolean updateEmail(final Email objEmail) throws SQLException
	{
		Connection objConnection = null;
		PreparedStatement objPreparedStatement = null;
		
		try
			{
			objConnection = getConnection();
			objPreparedStatement = objConnection.prepareStatement("update Email set Individual = ?, DateReceived = ?, EmailFrom = ?, EmailTo = ?, Subject = ?, Body = ?, CharacterSet = ?, ContentTransferEncoding = ?, ContentType = ? where Id = ?");
					
			objPreparedStatement.setInt(1, objEmail.getIndividual().getId());
			objPreparedStatement.setLong(2, objEmail.getDate().getTime());
			objPreparedStatement.setString(3, objEmail.getFrom());
			objPreparedStatement.setString(4, objEmail.getTo());
			objPreparedStatement.setString(5, objEmail.getSubject());
			objPreparedStatement.setString(6, objEmail.getBody());
			objPreparedStatement.setString(7, objEmail.getCharacterSet());
			objPreparedStatement.setString(8, objEmail.getContentTransferEncoding());
			objPreparedStatement.setString(9, objEmail.getContentType());
			objPreparedStatement.setInt(10, objEmail.getId());
			
			return(objPreparedStatement.executeUpdate() == 1);
			}
		finally
			{
			if(objPreparedStatement != null)
				objPreparedStatement.close();
			}
	}
}