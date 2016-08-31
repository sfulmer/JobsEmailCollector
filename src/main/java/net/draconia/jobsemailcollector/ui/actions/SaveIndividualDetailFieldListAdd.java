package net.draconia.jobsemailcollector.ui.actions;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import net.draconia.jobsemailcollector.model.Individual;
import net.draconia.jobsemailcollector.ui.model.IndividualDetailFieldListAddDialogModel;

public class SaveIndividualDetailFieldListAdd extends AbstractAction
{
	private static final long serialVersionUID = -6271577325853015425L;
	
	private Individual mObjIndividual;
	private IndividualDetailFieldListAddDialogModel mObjModel;
	private String msField;
	private Window mWndParent;
	
	public SaveIndividualDetailFieldListAdd(final Window wndParent, final Individual objIndividual, final String sField, final IndividualDetailFieldListAddDialogModel objModel)
	{
		super("Save");
		
		try
			{
			putValue(SMALL_ICON, new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/save.png"))));
			}
		catch(IOException objIOException)
			{
			objIOException.printStackTrace(System.err);
			}
		
		setField(sField);
		setIndividual(objIndividual);
		setParent(wndParent);
		setModel(objModel);
	}
	
	@SuppressWarnings("unchecked")
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		try
			{
			if(getModel().getOriginal().equals(""))
				getAddMethod().invoke(getIndividual(), new Object[] {getModel().getValue()});
			else
				{
				List<String> lstValues = ((List<String>)(getGetProtectedListMethod().invoke(getIndividual(), new Object[0])));
				
				lstValues.set(lstValues.indexOf(getModel().getOriginal()), getModel().getValue());
				}
			}
		catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
			{
			objException.printStackTrace(System.err);
			}
		finally
			{
			getParent().dispose();
			}
	}
	
	protected Method getAddMethod() throws NoSuchMethodException, SecurityException
	{
		Class<?> clsModel = getIndividual().getClass();
		Method funcAdd;
		String sMethodName = "add" + getField();
		
		funcAdd = clsModel.getDeclaredMethod(sMethodName, new Class<?>[] {String.class});
		
		if(!funcAdd.isAccessible())
			funcAdd.setAccessible(true);
		
		return(funcAdd);
	}
	
	protected String getField()
	{
		return(msField);
	}
	
	protected Method getGetProtectedListMethod() throws NoSuchMethodException, SecurityException
	{
		Class<?> clsModel = getIndividual().getClass();
		Method funcGetProtected;
		String sMethodName = "get" + getField() + (getField().endsWith("s") ? "es" : "s");
		
		funcGetProtected = clsModel.getDeclaredMethod(sMethodName, new Class<?>[0]);
		
		if(!funcGetProtected.isAccessible())
			funcGetProtected.setAccessible(true);
		
		return(funcGetProtected);
	}
	
	protected Individual getIndividual()
	{
		return(mObjIndividual);
	}
	
	protected IndividualDetailFieldListAddDialogModel getModel()
	{
		return(mObjModel);
	}
	
	protected Window getParent()
	{
		return(mWndParent);
	}
	
	protected void setField(final String sField)
	{
		msField = sField;
	}
	
	protected void setIndividual(final Individual objIndividual)
	{
		mObjIndividual = objIndividual;
	}
	
	protected void setModel(final IndividualDetailFieldListAddDialogModel objModel)
	{
		mObjModel = objModel;
	}
	
	protected void setParent(final Window wndParent)
	{
		mWndParent = wndParent;
	}
}