package net.draconia.jobsemailcollector.parsers;

import java.io.FileReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.concurrent.Callable;

import net.draconia.jobsemailcollector.model.FileToImport;

public class FileParser implements Callable<Boolean>, Serializable
{	
	private static final long serialVersionUID = -8680604620421923651L;
	
	private FileToImport mFileToImport;
	
	public FileParser()
	{ }
	
	public FileParser(final FileToImport fileToImport)
	{
		setModel(fileToImport);
	}
	
	public Boolean call() throws Exception
	{
		char[] cbReadTemp = new char[500000];
		Reader objReader = new FileReader(getModel().getFileToImport());
		TextParser objTextParser = getModel().getTextParser();
		
		try
			{
			while(objReader.ready())
				if(!isCancelled())
					{
					objReader.read(cbReadTemp);
					
					getModel().setNumberOfBytesRead(getModel().getNumberOfBytesRead() + cbReadTemp.length);
					
					objTextParser.setText(objTextParser.getText() + new String(cbReadTemp));
					
					objTextParser.parse();
					}
				else
					break;
			
			if(!isCancelled())
				objTextParser.spawnEmailParser(objTextParser.getText());
			
			setComplete(!isCancelled());
			}
		finally
			{
			if(objReader != null)
				objReader.close();
			}
		
		return(isComplete());
	}
	
	public FileToImport getModel()
	{
		return(mFileToImport);
	}
	
	protected boolean isCancelled()
	{
		return(getModel().isCancelled());
	}
	
	protected boolean isComplete()
	{
		return(getModel().isComplete());
	}
	
	protected void setCanceled(final Boolean bCancelled)
	{
		getModel().setCancelled(bCancelled);
	}
	
	protected void setComplete(final Boolean bComplete)
	{
		getModel().setComplete(bComplete);
	}
	
	public void setModel(final FileToImport fileToImport)
	{
		mFileToImport = fileToImport;
	}
}