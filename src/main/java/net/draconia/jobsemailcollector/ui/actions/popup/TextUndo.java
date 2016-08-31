package net.draconia.jobsemailcollector.ui.actions.popup;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import javax.swing.undo.UndoManager;

public class TextUndo extends AbstractAction
{
	private static final long serialVersionUID = 3965383888469417477L;
	
	private Action mActRedo;
	private UndoManager mObjUndoManager;
	
	public TextUndo(final UndoManager objUndoManager)
	{
		super("Undo");
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_U);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));
		
		setUndoManager(objUndoManager);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		if(getUndoManager().canUndo())
			getUndoManager().undo();
	}
	
	public Action getRedoAction()
	{
		return(mActRedo);
	}
	
	protected UndoManager getUndoManager()
	{
		return(mObjUndoManager);
	}
	
	public void setRedoAction(final Action actRedo)
	{
		mActRedo = actRedo;
	}
	
	protected void setUndoManager(final UndoManager objUndoManager)
	{
		mObjUndoManager = objUndoManager;
		
		if(objUndoManager != null)
			setEnabled(mObjUndoManager.canUndo());
		
		if(getRedoAction() != null)
			getRedoAction().setEnabled(getUndoManager().canRedo());
	}
}