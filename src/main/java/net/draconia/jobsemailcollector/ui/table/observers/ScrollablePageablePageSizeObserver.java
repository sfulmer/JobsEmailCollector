package net.draconia.jobsemailcollector.ui.table.observers;

import java.io.Serializable;

import java.lang.reflect.InvocationTargetException;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JComboBox;
import javax.swing.SpinnerNumberModel;

import javax.swing.text.JTextComponent;

import net.draconia.jobsemailcollector.ui.table.model.ScrollablePageableModel;

public class ScrollablePageablePageSizeObserver implements Observer, Serializable
{
	private static final long serialVersionUID = -3201722438345617743L;
	
	private JComboBox<Integer> mCboPageSize;
	private JTextComponent mTxtMaxPage;
	private SpinnerNumberModel mObjCurrentPageSpinnerModel;
	
	public ScrollablePageablePageSizeObserver(final JTextComponent txtMaxPage, final JComboBox<Integer> cboPageSize, final SpinnerNumberModel objCurrentPageSpinnerModel)
	{
		setCurrentPageSpinnerModel(objCurrentPageSpinnerModel);
		setMaxPageField(txtMaxPage);
		setPageSizeField(cboPageSize);
	}
	
	protected SpinnerNumberModel getCurrentPageSpinnerModel()
	{
		return(mObjCurrentPageSpinnerModel);
	}
	
	protected JTextComponent getMaxPageField()
	{
		return(mTxtMaxPage);
	}
	
	protected JComboBox<Integer> getPageSizeField()
	{
		return(mCboPageSize);
	}
	
	protected void setCurrentPageSpinnerModel(final SpinnerNumberModel objCurrentPageSpinerModel)
	{
		mObjCurrentPageSpinnerModel = objCurrentPageSpinerModel;
	}
	
	protected void setMaxPageField(final JTextComponent txtMaxPage)
	{
		mTxtMaxPage = txtMaxPage;
	}
	
	protected void setPageSizeField(final JComboBox<Integer> cboPageSize)
	{
		mCboPageSize = cboPageSize;
	}
	
	public void update(final Observable objObservable, final Object objArgument)
	{
		Integer iPageSize;
		ScrollablePageableModel objModel = ((ScrollablePageableModel)(objObservable));
		
		iPageSize = objModel.getPageSize();
		
		try
			{
			Integer iPageQuantity = objModel.getPageQuantity();
			
			getCurrentPageSpinnerModel().setMaximum(iPageQuantity);
			
			getPageSizeField().setSelectedItem(iPageSize);
			
			getMaxPageField().setText(iPageQuantity.toString());
			}
		catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
			{
			objException.printStackTrace(System.err);
			}
	}
}