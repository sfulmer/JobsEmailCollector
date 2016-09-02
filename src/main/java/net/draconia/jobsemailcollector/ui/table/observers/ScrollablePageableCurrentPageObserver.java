package net.draconia.jobsemailcollector.ui.table.observers;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Action;
import javax.swing.SpinnerNumberModel;

import net.draconia.jobsemailcollector.ui.table.model.ScrollablePageableModel;

public class ScrollablePageableCurrentPageObserver implements Observer, Serializable
{
	private static final long serialVersionUID = 7281127808347050692L;
	
	private Action mActFirst, mActLast, mActNext, mActPrevious;
	private SpinnerNumberModel mObjCurrentPageSpinnerModel;
	
	public ScrollablePageableCurrentPageObserver(final SpinnerNumberModel objCurrentPageSpinnerModel, final Action actFirst, final Action actLast, final Action actNext, final Action actPrevious)
	{
		setCurrentPageSpinnerModel(objCurrentPageSpinnerModel);
		setFirstAction(actFirst);
		setLastAction(actLast);
		setNextAction(actNext);
		setPreviousAction(actPrevious);
	}
	
	protected SpinnerNumberModel getCurrentPageSpinnerModel()
	{
		return(mObjCurrentPageSpinnerModel);
	}
	
	protected Action getFirstAction()
	{
		return(mActFirst);
	}
	
	protected Action getLastAction()
	{
		return(mActLast);
	}
	
	protected Action getNextAction()
	{
		return(mActNext);
	}
	
	protected Action getPreviousAction()
	{
		return(mActPrevious);
	}
	
	protected void setCurrentPageSpinnerModel(final SpinnerNumberModel objCurrentPageSpinnerModel)
	{
		mObjCurrentPageSpinnerModel = objCurrentPageSpinnerModel;
	}
	
	protected void setFirstAction(final Action actFirst)
	{
		mActFirst = actFirst;
	}
	
	protected void setLastAction(final Action actLast)
	{
		mActLast = actLast;
	}
	
	protected void setNextAction(final Action actNext)
	{
		mActNext = actNext;
	}
	
	protected void setPreviousAction(final Action actPrevious)
	{
		mActPrevious = actPrevious;
	}
	
	public void update(final Observable objObservable, final Object objArgument)
	{
		Integer iPageQuantity, iValue;
		ScrollablePageableModel objModel = ((ScrollablePageableModel)(objObservable));
		
		iValue = objModel.getCurrentPage();
		
		try
			{
			iPageQuantity = objModel.getPageQuantity();
			}
		catch(IllegalAccessException  | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
			{
			objException.printStackTrace(System.err);
			iPageQuantity = 0;
			}
		
		if(!iPageQuantity.equals(getCurrentPageSpinnerModel().getMaximum()))
			{
			getCurrentPageSpinnerModel().setMaximum(iPageQuantity);
			
			if(iPageQuantity > 0)
				{
				getCurrentPageSpinnerModel().setMinimum(1);
				
				if(iValue == 0)
					iValue = 1;
				}
			else
				{
				getCurrentPageSpinnerModel().setMinimum(0);
				
				if(iValue > 0)
					iValue = 0;
				}
			}
		
		if(!getCurrentPageSpinnerModel().getValue().equals(iValue))
			getCurrentPageSpinnerModel().setValue(iValue);
		
		if(iValue > 1)
			{
			getFirstAction().setEnabled(true);
			getPreviousAction().setEnabled(true);
			}
		else
			{
			getFirstAction().setEnabled(false);
			getPreviousAction().setEnabled(false);
			}
		if(iValue < iPageQuantity)
			{
			getLastAction().setEnabled(true);
			getNextAction().setEnabled(true);
			}
		else
			{
			getLastAction().setEnabled(false);
			getNextAction().setEnabled(false);
			}
	}
}