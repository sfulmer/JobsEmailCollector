package net.draconia.jobsemailcollector.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import net.draconia.jobsemailcollector.model.Individual;
import net.draconia.jobsemailcollector.ui.actions.Close;
import net.draconia.jobsemailcollector.ui.actions.SaveIndividualDetailFieldListAdd;
import net.draconia.jobsemailcollector.ui.listeners.IndividualDetailFieldListAddChangeListener;
import net.draconia.jobsemailcollector.ui.model.IndividualDetailFieldListAddDialogModel;

import net.draconia.jobsemailcollector.ui.observers.IndividualDetailFieldListToAddSaveObserver;
import net.draconia.jobsemailcollector.ui.utilities.TextUtilities;

public class IndividualDetailFieldListEditDialog extends JDialog
{
	private static final long serialVersionUID = 3262169889009966917L;
	
	private Action mActCancel, mActSave;
	private Individual mObjIndividual;
	private IndividualDetailFieldListAddDialogModel mObjModel;
	private JTextField mTxtToAdd;
	private String msField;
	
	public IndividualDetailFieldListEditDialog(final Window wndParent, final Individual objIndividual, final String sField, final String sToAdd)
	{
		super(wndParent, "Add " + sField, ModalityType.DOCUMENT_MODAL);
		
		setFont(wndParent.getFont());
		
		setField(sField);
		setIndividual(objIndividual);
		
		getModel().setOriginal(sToAdd);
		
		initDialog();
	}
	
	protected JPanel getButtonPanel()
	{
		JButton btnSave = new JButton(getSaveAction());
		JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		pnlButtons.add(btnSave);
		pnlButtons.add(new JButton(getCancelAction()));
		
		getRootPane().setDefaultButton(btnSave);
		
		return(pnlButtons);
	}
	
	protected Action getCancelAction()
	{
		if(mActCancel == null)
			{
			mActCancel = new Close(this);
			
			getRootPane().getActionMap().put("CANCEL", mActCancel);
			getRootPane().getInputMap(JRootPane.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "CANCEL");
			}
		
		return(mActCancel);
	}
	
	protected JButton getCancelButton()
	{
		JButton btnCancel = new JButton(getCancelAction());
		
		btnCancel.setFont(getFont().deriveFont(Font.BOLD));
		
		return(btnCancel);
	}
	
	protected String getField()
	{
		return(msField);
	}
	
	protected Individual getIndividual()
	{
		return(mObjIndividual);
	}
	
	protected JPanel getMainPanel()
	{
		GridBagConstraints objConstraints = new GridBagConstraints();
		JPanel pnlMain = new JPanel(new GridBagLayout());
		
		objConstraints.anchor = GridBagConstraints.NORTHWEST;
		objConstraints.fill = GridBagConstraints.NONE;
		objConstraints.gridx = objConstraints.gridy = 0;
		objConstraints.gridheight = objConstraints.gridwidth = 1;
		objConstraints.insets = new Insets(5, 5, 5, 5);
		objConstraints.weightx = 0;
		pnlMain.add(getToAddLabel(), objConstraints);
		
		objConstraints.gridx++;
		objConstraints.weightx = 1;
		pnlMain.add(getToAddField(), objConstraints);
		
		objConstraints.gridwidth = 2;
		objConstraints.gridx = 0;
		objConstraints.gridy++;
		objConstraints.fill = GridBagConstraints.HORIZONTAL;
		pnlMain.add(getButtonPanel(), objConstraints);
		
		return(pnlMain);
	}
	
	protected IndividualDetailFieldListAddDialogModel getModel()
	{
		if(mObjModel == null)
			mObjModel = new IndividualDetailFieldListAddDialogModel();
		
		return(mObjModel);
	}
	
	protected Action getSaveAction()
	{
		if(mActSave == null)
			{
			mActSave = new SaveIndividualDetailFieldListAdd(this, getIndividual(), getField(), getModel());
			
			mActSave.setEnabled(false);
			
			getModel().addObserver(new IndividualDetailFieldListToAddSaveObserver(mActSave));
			}
		
		return(mActSave);
	}
	
	protected JButton getSaveButton()
	{
		JButton btnSave = new JButton(getSaveAction());
		
		btnSave.setFont(getFont().deriveFont(Font.BOLD));
		
		return(btnSave);
	}
	
	protected JTextField getToAddField()
	{
		if(mTxtToAdd == null)
			{
			mTxtToAdd = new JTextField(30);
			
			mTxtToAdd.setBorder(BorderFactory.createLoweredBevelBorder());
			mTxtToAdd.setFont(getFont());
			mTxtToAdd.setText(getModel().getValue());
			
			new TextUtilities().setupUnRedoableMenuTextComponent(mTxtToAdd, new IndividualDetailFieldListAddChangeListener(getModel(), getSaveAction()));
			}
		
		return(mTxtToAdd);
	}
	
	protected JLabel getToAddLabel()
	{
		JLabel lblToAdd = new JLabel("Add:");
		
		lblToAdd.setDisplayedMnemonic(KeyEvent.VK_A);
		lblToAdd.setFont(getFont().deriveFont(Font.BOLD));
		lblToAdd.setLabelFor(getToAddField());
		lblToAdd.setOpaque(false);
		
		return(lblToAdd);
	}
	
	protected void initControls()
	{
		add(getMainPanel(), BorderLayout.CENTER);
	}
	
	protected void initDialog()
	{
		Dimension szScreen = getToolkit().getScreenSize();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout(2, 2));
		
		initControls();
		
		pack();		
		setLocation(new Point((szScreen.width - getWidth()) / 2, (szScreen.height - getHeight()) / 2));
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
}