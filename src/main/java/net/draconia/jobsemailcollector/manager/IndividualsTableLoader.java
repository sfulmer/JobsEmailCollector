package net.draconia.jobsemailcollector.manager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JOptionPane;

import net.draconia.jobsemailcollector.model.Model;
import net.draconia.jobsemailcollector.model.ShallowIndividual;

public class IndividualsTableLoader implements Runnable, Serializable
{
	private static final long serialVersionUID = -7383805227716705979L;
	
	private Model mObjModel;
	
	public IndividualsTableLoader(final Model objModel)
	{
		setModel(objModel);
	}
	
	public DatabaseManager getDatabaseManager()
	{
		return(getModel().getDatabaseManager());
	}
	
	public Model getModel()
	{
		return(mObjModel);
	}
	
	public void run()
	{
		Connection objConnection = null;
		PreparedStatement objPreparedStatement = null;
		ResultSet objResultSet = null;
		
		try
			{
			new IndividualManager(getModel()).createIndividualTable();
			new EmailManager(getDatabaseManager()).createEmailTable();
			
			objConnection = getDatabaseManager().getConnection();
			objPreparedStatement = objConnection.prepareStatement("select Individual.Id as Id, Individual.Name as Name, (select LatestDate from (select Email.Individual, max(Email.DateReceived) as LatestDate from Email where Email.Individual = Individual.Id group by Email.Individual)) as LastDate from Individual order by Individual.Id");
			
			objPreparedStatement.closeOnCompletion();
			
			objResultSet = objPreparedStatement.executeQuery();
			
			while(objResultSet.next())
				{
				Date dtLast;
				Integer iId;
				String sName;
				
				dtLast = new Date(objResultSet.getDate("LastDate").getTime());
				iId = objResultSet.getInt("Id");
				sName = objResultSet.getString("Name");
				
				getModel().addTableData(new ShallowIndividual(iId, sName, dtLast));
				
				Thread.yield();
				}
			}
		catch(SQLException objSQLException)
			{
			ByteArrayOutputStream objStream = new ByteArrayOutputStream();
			
			objSQLException.printStackTrace(new PrintStream(objStream));
			JOptionPane.showMessageDialog(null, new String(objStream.toByteArray()));
			}
		finally
			{
			try
				{
				if(objResultSet != null)
					objResultSet.close();
				if(objPreparedStatement != null)
					objPreparedStatement.close();
				}
			catch(SQLException objSQLException)
				{
				ByteArrayOutputStream objStream = new ByteArrayOutputStream();
				
				objSQLException.printStackTrace(new PrintStream(objStream));
				JOptionPane.showMessageDialog(null, new String(objStream.toByteArray()));
				}
			}
	}
	
	public void setModel(final Model objModel)
	{
		mObjModel = objModel;
	}
}