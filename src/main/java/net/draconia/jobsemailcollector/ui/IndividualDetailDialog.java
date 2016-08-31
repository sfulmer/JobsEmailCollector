package net.draconia.jobsemailcollector.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;

import java.awt.event.KeyEvent;

import java.util.Observer;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import net.draconia.jobsemailcollector.manager.IndividualManager;

import net.draconia.jobsemailcollector.model.Individual;

import net.draconia.jobsemailcollector.ui.actions.ApplyIndividualDetails;
import net.draconia.jobsemailcollector.ui.actions.Close;
import net.draconia.jobsemailcollector.ui.actions.SaveIndividualDetails;
import net.draconia.jobsemailcollector.ui.actions.ShowSendResumeDialog;

import net.draconia.jobsemailcollector.ui.listeners.IndividualDetailDialogWindowListener;
import net.draconia.jobsemailcollector.ui.observers.IndividualDetailDialogApplySaveObserver;

public class IndividualDetailDialog extends JDialog
{
	private static final long serialVersionUID = -1291190398551472354L;
	
	private Action mActApply, mActClose, mActSave, mActSendResume;
	private Component mCmpEmail, mCmpIndividual;
	private Individual mObjModel, mObjWorkingModel;
	private IndividualManager mObjIndividualManager;
	private JTabbedPane mPnlTabs;
	private Observer mObjSaveApplyObserver;
	
	public IndividualDetailDialog(final Window wndParent)
	{
		super(wndParent, "Edit Individual...");
		
		initDialog();
	}
	
	public IndividualDetailDialog(final Window wndParent, final IndividualManager objIndividualManager)
	{
		super(wndParent, "Edit Individual...");
		
		setIndividualManager(objIndividualManager);
		
		initDialog();
	}
	
	public IndividualDetailDialog(final Window wndParent, final Individual objModel)
	{
		super(wndParent, "Edit Individual...");
		
		setModel(objModel);
		
		initDialog();
	}
	
	public IndividualDetailDialog(final Window wndParent, final Individual objModel, final IndividualManager objIndividualManager)
	{
		super(wndParent, "Edit Individual...");
		
		setIndividualManager(objIndividualManager);
		setModel(objModel);
		
		initDialog();
	}
	
	private Action getApplyAction()
	{
		if(mActApply == null)
			{
			mActApply = new ApplyIndividualDetails(getWorkingModel(), getModel(), getIndividualManager());
			
			mActApply.setEnabled(!getWorkingModel().equals(getModel()));
			}
		
		return(mActApply);
	}
	
	private JButton getApplyButton()
	{
		JButton btnApply = new JButton(getApplyAction());
		
		btnApply.setFont(getFont().deriveFont(Font.BOLD));
		
		return(btnApply);
	}
	
	private JPanel getButtonPanel()
	{
		JPanel pnl = new JPanel(new BorderLayout(10, 0));
		JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		pnlButtons.add(getApplyButton());
		pnlButtons.add(getSaveButton());
		pnlButtons.add(getCloseButton());
		
		getWorkingModel().addObserver(getSaveApplyObserver());
		
		pnl.add(getResumeButtonPanel(), BorderLayout.WEST);
		pnl.add(pnlButtons, BorderLayout.EAST);
		
		return(pnl);
	}
	
	private Action getCloseAction()
	{
		if(mActClose == null)
			{
			mActClose = new Close(this);
			
			getRootPane().getActionMap().put("CANCEL", mActClose);
			getRootPane().getInputMap(JRootPane.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "CANCEL");
			}
		
		return(mActClose);
	}
	
	private JButton getCloseButton()
	{
		JButton btnClose = new JButton(getCloseAction());
		
		btnClose.setFont(getFont().deriveFont(Font.BOLD));
		
		return(btnClose);
	}
	
	private Component getEmailTabPane()
	{
		if(mCmpEmail == null)
			mCmpEmail = new EmailTabPanel(getWorkingModel().getEmail());
		
		return(mCmpEmail);
	}
	
	private IndividualManager getIndividualManager()
	{
		return(mObjIndividualManager);
	}
	
	private Component getIndividualTabPane()
	{
		if(mCmpIndividual == null)
			mCmpIndividual = new IndividualTabPanel(getWorkingModel());
		
		return(mCmpIndividual);
	}
	
	protected Individual getModel()
	{
		if(mObjModel == null)
			mObjModel = new Individual();
		
		return(mObjModel);
	}
	
	private JPanel getResumeButtonPanel()
	{
		JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		pnlButtons.add(getSendResumeButton());
		
		return(pnlButtons);
	}
	
	private Action getSaveAction()
	{
		if(mActSave == null)
			{
			mActSave = new SaveIndividualDetails(getApplyAction(), this);
			
			mActSave.setEnabled(!getWorkingModel().equals(getModel()));
			}
		
		return(mActSave);
	}
	
	private JButton getSaveButton()
	{
		JButton btnSave = new JButton(getSaveAction());
		
		btnSave.setFont(getFont().deriveFont(Font.BOLD));
		
		return(btnSave);
	}
	
	private Observer getSaveApplyObserver()
	{
		if(mObjSaveApplyObserver == null)
			mObjSaveApplyObserver = new IndividualDetailDialogApplySaveObserver(getModel(), getApplyAction(), getSaveAction());
		
		return(mObjSaveApplyObserver);
	}
	
	private Action getSendResumeAction()
	{
		if(mActSendResume == null)
			mActSendResume = new ShowSendResumeDialog(this, getModel());
		
		return(mActSendResume);
	}
	
	private JButton getSendResumeButton()
	{
		JButton btnSendResume = new JButton(getSendResumeAction());
		
		btnSendResume.setFont(getFont().deriveFont(Font.BOLD));
		
		return(btnSendResume);
	}
	
	private JTabbedPane getTabbedPane()
	{
		if(mPnlTabs == null)
			{
			mPnlTabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
			mPnlTabs.add("Individual", getIndividualTabPane());
			mPnlTabs.add("Email", getEmailTabPane());
			}
		
		return(mPnlTabs);
	}
	
	private Individual getWorkingModel()
	{
		if(mObjWorkingModel == null)
			mObjWorkingModel = ((Individual)(getModel().clone()));
		
		return(mObjWorkingModel);
	}
	
	protected void initControls()
	{
		add(getTabbedPane(), BorderLayout.CENTER);
		add(getButtonPanel(), BorderLayout.SOUTH);
	}
	
	protected void initDialog()
	{
		Dimension szScreen = getToolkit().getScreenSize();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setFont(getParent().getFont());
		setLayout(new BorderLayout());
		
		addWindowListener(new IndividualDetailDialogWindowListener(getModel(), getSaveApplyObserver()));
		
		initControls();
		
		pack();
		setLocation((int)((szScreen.getWidth() - getWidth()) / 2), (int)((szScreen.getHeight() - getHeight()) / 2));
	}
	
	protected void setIndividualManager(final IndividualManager objIndividualManager)
	{
		mObjIndividualManager = objIndividualManager;
	}
	
	protected void setModel(final Individual objModel)
	{
		if(objModel == null)
			mObjModel = new Individual();
		else
			mObjModel = objModel;
	}
}