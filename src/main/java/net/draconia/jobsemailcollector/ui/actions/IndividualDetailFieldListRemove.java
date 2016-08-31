package net.draconia.jobsemailcollector.ui.actions;

import java.awt.Window;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JList;

import net.draconia.jobsemailcollector.model.Individual;

public class IndividualDetailFieldListRemove extends IndividualDetailFieldListCommand
{
	private static final long serialVersionUID = 471280663678355023L;
	
	private JList<String> mLstField;
	
	public IndividualDetailFieldListRemove(final Window wndParent, final Individual objModel, final String sField, final JList<String> lstField)
	{
		super(wndParent, objModel, sField, "Remove");
		
		try
			{
			putValue(SMALL_ICON, new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/delete.png"))));
			}
		catch(IOException objIOException)
			{
			objIOException.printStackTrace(System.err);
			}
		
		setFieldList(lstField);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		for(String sValue : getToRemove())
			try
				{
				getRemoveMethod().invoke(getModel(), new Object[] {sValue});
				}
			catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
				{
				objException.printStackTrace(System.err);
				}
		
		getFieldList().repaint();
	}
	
	protected JList<String> getFieldList()
	{
		return(mLstField);
	}
	
	protected Method getRemoveMethod() throws NoSuchMethodException, SecurityException
	{
		Class<?> clsModel = getModel().getClass();
		Method funcRemove;
		String sMethodName = "remove" + getField();
		
		funcRemove = clsModel.getDeclaredMethod(sMethodName, new Class<?>[] {String.class});
		
		if(!funcRemove.isAccessible())
			funcRemove.setAccessible(true);
		
		return(funcRemove);
	}
	
	protected String[] getToRemove()
	{
		return(getFieldList().getSelectedValuesList().toArray(new String[0]));
	}
	
	protected void setFieldList(final JList<String> lstField)
	{
		mLstField = lstField;
	}
}