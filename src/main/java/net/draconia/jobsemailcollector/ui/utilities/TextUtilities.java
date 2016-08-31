package net.draconia.jobsemailcollector.ui.utilities;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;

import java.util.Objects;

import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import javax.swing.undo.UndoManager;

import net.draconia.jobsemailcollector.ui.actions.popup.TextCopy;
import net.draconia.jobsemailcollector.ui.actions.popup.TextCut;
import net.draconia.jobsemailcollector.ui.actions.popup.TextDelete;
import net.draconia.jobsemailcollector.ui.actions.popup.TextPaste;
import net.draconia.jobsemailcollector.ui.actions.popup.TextRedo;
import net.draconia.jobsemailcollector.ui.actions.popup.TextSelectAll;
import net.draconia.jobsemailcollector.ui.actions.popup.TextUndo;
import net.draconia.jobsemailcollector.ui.listeners.TextComponentCaretListener;

public class TextUtilities implements Serializable
{
	private static final long serialVersionUID = 986707425578607868L;
	
	private MouseUtilities mObjMouseUtilities;
	
	protected MouseUtilities getMouseUtilities()
	{
		if(mObjMouseUtilities == null)
			mObjMouseUtilities = new MouseUtilities();
		
		return(mObjMouseUtilities);
	}
	
	protected void installPopupMenu(final JTextComponent txtComponent, final UnRedoablePopupMenu mnuPopup)
	{
		new MouseUtilities().installPopupListener(txtComponent, mnuPopup);
	}
	
	public JTextComponent setupUnRedoableMenuTextComponent(final JTextComponent txtComponent)
	{
		UnRedoablePopupMenu mnuPopup = new UnRedoablePopupMenu();
		
		Objects.requireNonNull(txtComponent);
		
		txtComponent.addCaretListener(new TextComponentCaretListener(mnuPopup.getCopyAction(), mnuPopup.getCutAction()));
		
		installPopupMenu(txtComponent, mnuPopup);
		
		return(txtComponent);
	}
	
	public JTextComponent setupUnRedoableMenuTextComponent(final JTextComponent txtComponent, final ChangeListener objChangeListener)
	{
		Document objDocument;
		DocumentListener objDocumentListener;
		UndoableEditListener objUndoableEditListener;
		UndoManager objUndoManager = new UndoManager();
		UnRedoablePopupMenu mnuPopup = new UnRedoablePopupMenu(objUndoManager);
		
		Objects.requireNonNull(txtComponent);
		Objects.requireNonNull(objChangeListener);
		
		objDocumentListener = new UnRedoableMenuDocumentListener(txtComponent, objChangeListener);
		
		txtComponent.addPropertyChangeListener("document", new UnRedoablePropertyChangeListener(objDocumentListener));
		
		objUndoableEditListener = new UnRedoableUndoableEditListener(objUndoManager, txtComponent, objChangeListener, mnuPopup.getRedoAction(), mnuPopup.getUndoAction());
		
		objDocument = txtComponent.getDocument();
		
		if(objDocument != null)
			{
			objDocument.addDocumentListener(objDocumentListener);
			objDocument.addUndoableEditListener(objUndoableEditListener);

			txtComponent.getActionMap().put("REDO", mnuPopup.getRedoAction());
			txtComponent.getActionMap().put("UNDO", mnuPopup.getUndoAction());
			
			txtComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK), "REDO");
			txtComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK), "UNDO");
			}
		
		txtComponent.addCaretListener(new TextComponentCaretListener(mnuPopup.getCopyAction(), mnuPopup.getCutAction()));
		
		installPopupMenu(txtComponent, mnuPopup);
		
		return(txtComponent);
	}
	
	protected class UnRedoableMenuDocumentListener implements DocumentListener, Serializable
	{
		private static final long serialVersionUID = -573332939747855497L;
		
		private ChangeListener mObjChangeListener;
		private int miLastChange = 0, miLastNotifiedChange = 0; 
		private JTextComponent mTxtComponent;
		
		public UnRedoableMenuDocumentListener(final JTextComponent txtComponent, final ChangeListener objChangeListener)
		{
			setChangeListener(objChangeListener);
			setTextComponent(txtComponent);
		}
		
		public void changedUpdate(final DocumentEvent objDocumentEvent)
		{
			incrementLastChange();
			
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run()
				{
					if(getLastNotifiedChange() != getLastChange())
						{
						setLastNotifiedChange(getLastChange());
						
						getChangeListener().stateChanged(new ChangeEvent(getTextComponent()));
						}
				}
			});
		}
		
		protected ChangeListener getChangeListener()
		{
			return(mObjChangeListener);
		}
		
		protected int getLastChange()
		{
			return(miLastChange);
		}
		
		protected int getLastNotifiedChange()
		{
			return(miLastNotifiedChange);
		}
		
		protected JTextComponent getTextComponent()
		{
			return(mTxtComponent);
		}
		
		protected void incrementLastChange()
		{
			setLastChange(getLastChange() + 1);
		}
		
		public void insertUpdate(final DocumentEvent objDocumentEvent)
		{
			changedUpdate(objDocumentEvent);
		}
		
		public void removeUpdate(final DocumentEvent objDocumentEvent)
		{
			changedUpdate(objDocumentEvent);
		}
		
		protected void setChangeListener(final ChangeListener objChangeListener)
		{
			mObjChangeListener = objChangeListener;
		}
		
		protected void setLastChange(final int iLastChange)
		{
			miLastChange = iLastChange;
		}
		
		protected void setLastNotifiedChange(final int iLastNotifiedChange)
		{
			miLastNotifiedChange = iLastNotifiedChange;
		}
		
		protected void setTextComponent(final JTextComponent txtComponent)
		{
			mTxtComponent = txtComponent;
		}
	}
	
	protected class UnRedoablePropertyChangeListener implements PropertyChangeListener, Serializable
	{
		private static final long serialVersionUID = -1147976152927530833L;
		
		private DocumentListener mObjDocumentListener;
		
		public UnRedoablePropertyChangeListener(final DocumentListener objDocumentListener)
		{
			setDocumentListener(objDocumentListener);
		}
		
		protected DocumentListener getDocumentListener()
		{
			return(mObjDocumentListener);
		}
		
		public void propertyChange(final PropertyChangeEvent objPropertyChangeEvent)
		{
			Document objDocumentOld = ((Document)(objPropertyChangeEvent.getOldValue()));
			Document objDocumentNew = ((Document)(objPropertyChangeEvent.getNewValue()));
			
			if(objDocumentOld != null)
				objDocumentOld.removeDocumentListener(getDocumentListener());
			if(objDocumentNew != null)
				objDocumentNew.addDocumentListener(getDocumentListener());
			
			getDocumentListener().changedUpdate(null);
		}
		
		protected void setDocumentListener(final DocumentListener objDocumentListener)
		{
			mObjDocumentListener = objDocumentListener;
		}
	}
	
	protected class UnRedoableUndoableEditListener implements Serializable, UndoableEditListener
	{
		private static final long serialVersionUID = -8803785009120703430L;
		
		private Action mActRedo, mActUndo;
		private ChangeListener mObjChangeListener;
		private JTextComponent mTxtComponent;
		private UndoManager mObjUndoManager;
		
		public UnRedoableUndoableEditListener(final UndoManager objUndoManager, final JTextComponent txtComponent, final ChangeListener objChangeListener, final Action actRedo, final Action actUndo)
		{
			setChangeListener(objChangeListener);
			setTextComponent(txtComponent);
			setUndoManager(objUndoManager);
			setUndoAction(actUndo);
			setRedoAction(actRedo);
		}
		
		protected ChangeListener getChangeListener()
		{
			return(mObjChangeListener);
		}
		
		protected Action getRedoAction()
		{
			return(mActRedo);
		}
		
		protected JTextComponent getTextComponent()
		{
			return(mTxtComponent);
		}
		
		protected Action getUndoAction()
		{
			return(mActUndo);
		}
		
		protected UndoManager getUndoManager()
		{
			return(mObjUndoManager);
		}
		
		protected void setChangeListener(final ChangeListener objChangeListener)
		{
			mObjChangeListener = objChangeListener;
		}
		
		protected void setRedoAction(final Action actRedo)
		{
			mActRedo = actRedo;
		}
		
		protected void setTextComponent(final JTextComponent txtComponent)
		{
			mTxtComponent = txtComponent;
		}
		
		protected void setUndoAction(final Action actUndo)
		{
			mActUndo = actUndo;
		}
		
		protected void setUndoManager(final UndoManager objUndoManager)
		{
			mObjUndoManager = objUndoManager;
		}
		
		public void undoableEditHappened(final UndoableEditEvent objUndoableEditEvent)
		{
			getUndoManager().addEdit(objUndoableEditEvent.getEdit());
						
			getUndoAction().setEnabled(getUndoManager().canUndo());
			getRedoAction().setEnabled(getUndoManager().canRedo());
						
			getChangeListener().stateChanged(new ChangeEvent(getTextComponent()));
		}
	}

	protected class UnRedoablePopupMenu extends JPopupMenu implements PopupMenuListener
	{
		private static final long serialVersionUID = -358551677702431214L;
		
		private Action mActCopy, mActCut, mActDelete, mActPaste;
		private Action mActRedo, mActSelectAll, mActUndo;
		private UndoManager mObjUndoManager;
		
		public UnRedoablePopupMenu()
		{
			this(null);
		}
		
		public UnRedoablePopupMenu(final UndoManager objUndoManager)
		{
			setUndoManager(objUndoManager);
			
			addPopupMenuListener(this);
			
			add(getUndoAction());
			add(getRedoAction());
			
			((TextRedo)(getRedoAction())).setUndoAction(getUndoAction());
			((TextUndo)(getUndoAction())).setRedoAction(getRedoAction());
			
			addSeparator();
			add(getCutAction());
			add(getCopyAction());
			add(getPasteAction());
			add(getDeleteAction());
			addSeparator();
			add(getSelectAllAction());
		}
		
		public Action getCopyAction()
		{
			if(mActCopy == null)
				mActCopy = new TextCopy();
			
			return(mActCopy);
		}
		
		public Action getCutAction()
		{
			if(mActCut == null)
				mActCut = new TextCut();
			
			return(mActCut);
		}
		
		public Action getDeleteAction()
		{
			if(mActDelete == null)
				mActDelete = new TextDelete();
			
			return(mActDelete);
		}
		
		public Action getPasteAction()
		{
			if(mActPaste == null)
				mActPaste = new TextPaste();
			
			return(mActPaste);
		}
		
		public Action getRedoAction()
		{
			if(mActRedo == null)
				mActRedo = new TextRedo(getUndoManager());
			
			return(mActRedo);
		}
		
		public Action getSelectAllAction()
		{
			if(mActSelectAll == null)
				mActSelectAll = new TextSelectAll();
			
			return(mActSelectAll);
		}
		
		public Action getUndoAction()
		{
			if(mActUndo == null)
				mActUndo = new TextUndo(getUndoManager());
			
			return(mActUndo);
		}
		
		protected UndoManager getUndoManager()
		{
			return(mObjUndoManager);
		}
		
		public void popupMenuWillBecomeVisible(final PopupMenuEvent objPopupMenuEvent)
		{
			JPopupMenu mnuPopup = ((JPopupMenu)(objPopupMenuEvent.getSource()));
			JTextComponent txt = ((JTextComponent)(mnuPopup.getInvoker()));
			
			if(txt.isEditable())
				{
				Clipboard objClipboard = getToolkit().getSystemClipboard();
				Transferable objContents = objClipboard.getContents(null);
				
				if(objContents.isDataFlavorSupported(DataFlavor.stringFlavor))
					getPasteAction().setEnabled(true);
				else
					getPasteAction().setEnabled(false);
				
				if(getUndoManager() != null)
					{
					getUndoAction().setEnabled(getUndoManager().canUndo());
					getRedoAction().setEnabled(getUndoManager().canRedo());
					}
				}
			else
				{
				getCutAction().setEnabled(false);
				getDeleteAction().setEnabled(false);
				getPasteAction().setEnabled(false);
				
				getUndoAction().setEnabled(false);
				getRedoAction().setEnabled(false);
				}
		}
		
		public void popupMenuWillBecomeInvisible(final PopupMenuEvent objPopupMenuEvent)
		{ }
		
		public void popupMenuCanceled(final PopupMenuEvent objPopupMenuEvent)
		{ }
		
		protected void setUndoManager(final UndoManager objUndoManager)
		{
			mObjUndoManager = objUndoManager;
		}
	}
}