package net.draconia.jobsemailcollector.ui.observers;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Action;

import net.draconia.jobsemailcollector.domain.Individual;

public class IndividualDetailDialogApplySaveObserver implements Observer, Serializable
{
	private static final long serialVersionUID = 7417932052079119927L;
	
	private Action mActApply, mActSave;
	private Individual mObjClean;
	
	public IndividualDetailDialogApplySaveObserver(final Individual objClean, final Action actApply, final Action actSave)
	{
		setApplyAction(actApply);
		setCleanModel(objClean);
		setSaveAction(actSave);
	}
	
	protected Action getApplyAction()
	{
		return(mActApply);
	}
	
	protected Individual getCleanModel()
	{
		return(mObjClean);
	}
	
	protected Action getSaveAction()
	{
		return(mActSave);
	}
	
	protected void setApplyAction(final Action actApply)
	{
		mActApply = actApply;
	}
	
	protected void setCleanModel(final Individual objClean)
	{
		mObjClean = objClean;
	}
	
	protected void setSaveAction(final Action actSave)
	{
		mActSave = actSave;
	}
	
	public void update(final Observable objObservable, final Object objArgument)
	{
		boolean bDirty;
		Individual objDirty = ((Individual)(objObservable));
		
		bDirty = !(objDirty.equals(getCleanModel()));
		
		getApplyAction().setEnabled(bDirty);
		getSaveAction().setEnabled(bDirty);
	}
}