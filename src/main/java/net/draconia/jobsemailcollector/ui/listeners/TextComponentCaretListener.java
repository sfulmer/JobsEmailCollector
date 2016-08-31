package net.draconia.jobsemailcollector.ui.listeners;

import java.io.Serializable;

import javax.swing.Action;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class TextComponentCaretListener implements CaretListener, Serializable
{
	private static final long serialVersionUID = 7912432094161467048L;
	
	private Action mActCopy, mActCut;
	
	public TextComponentCaretListener(final Action actCopy, final Action actCut)
	{
		setCopyAction(actCopy);
		setCutAction(actCut);
	}
	
	public void caretUpdate(final CaretEvent objCaretEvent)
	{
		if(objCaretEvent.getDot() != objCaretEvent.getMark())
			{
			getCopyAction().setEnabled(true);
			getCutAction().setEnabled(true);
			}
		else
			{
			getCopyAction().setEnabled(false);
			getCutAction().setEnabled(false);
			}
	}
	
	protected Action getCopyAction()
	{
		return(mActCopy);
	}
	
	protected Action getCutAction()
	{
		return(mActCut);
	}
	
	protected void setCopyAction(final Action actCopy)
	{
		mActCopy = actCopy;
	}
	
	protected void setCutAction(final Action actCut)
	{
		mActCut = actCut;
	}
}