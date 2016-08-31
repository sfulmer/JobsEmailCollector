package net.draconia.jobsemailcollector.ui;

import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.text.JTextComponent;

import net.draconia.jobsemailcollector.model.Individual;

import net.draconia.jobsemailcollector.ui.listeners.IndividualNameChangeListener;

import net.draconia.jobsemailcollector.ui.utilities.TextUtilities;

public class IndividualTabPanel extends JPanel
{
	private static final long serialVersionUID = 5630126156207026697L;
	
	private Individual mObjModel;
	private JTextComponent mTxtName;
	private JTextField mTxtId;
	private TextUtilities mObjTextUtilities;
	
	public IndividualTabPanel(final Individual objModel)
	{
		setModel(objModel);
		
		initPanel();
	}
	
	protected JPanel getEmailPanel()
	{
		return(new IndividualDetailFieldListPanel(getModel(), "EmailAddress", "Email"));
	}
	
	public JPanel getEmailPhonePanel()
	{
		JPanel pnlEmailPhone = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		
		pnlEmailPhone.add(getEmailPanel());
		pnlEmailPhone.add(getPhoneNumberPanel());
		
		return(pnlEmailPhone);
	}
	
	protected JTextField getIdField()
	{
		if(mTxtId == null)
			{
			mTxtId = new JTextField(30);
			
			mTxtId.setBorder(BorderFactory.createEmptyBorder());
			mTxtId.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
			mTxtId.setEditable(false);
			mTxtId.setFont(getFont());
			mTxtId.setText(String.valueOf(getModel().getId()));
			
			getTextUtilities().setupUnRedoableMenuTextComponent(mTxtId);
			}
		
		return(mTxtId);
	}
	
	protected JLabel getIdLabel()
	{
		JLabel lblId = new JLabel("Id:");
		
		lblId.setFont(getFont().deriveFont(Font.BOLD));
		lblId.setOpaque(false);
		
		return(lblId);
	}
	
	protected Individual getModel()
	{
		return(mObjModel);
	}
	
	protected JTextComponent getNameField()
	{
		if(mTxtName == null)
			{
			mTxtName = new JTextField(30);
			
			mTxtName.setBorder(BorderFactory.createLoweredBevelBorder());
			mTxtName.setFont(getFont());
			mTxtName.setName("NameField");
			mTxtName.setText(getModel().getName());
			
			getTextUtilities().setupUnRedoableMenuTextComponent(mTxtName, new IndividualNameChangeListener(getModel()));
			}
		
		return(mTxtName);
	}
	
	protected JLabel getNameLabel()
	{
		JLabel lblIndividualName = new JLabel("Name:");
		
		lblIndividualName.setDisplayedMnemonic(KeyEvent.VK_N);
		lblIndividualName.setFont(getFont().deriveFont(Font.BOLD));
		lblIndividualName.setLabelFor(getNameField());
		lblIndividualName.setOpaque(false);
		
		return(lblIndividualName);
	}
	
	protected JPanel getPhoneNumberPanel()
	{
		return(new IndividualDetailFieldListPanel(getModel(), "PhoneNumber", "Phone #"));
	}
	
	protected TextUtilities getTextUtilities()
	{
		if(mObjTextUtilities == null)
			mObjTextUtilities = new TextUtilities();
		
		return(mObjTextUtilities);
	}
	
	protected void initPanel()
	{
		GridBagConstraints objConstraints = new GridBagConstraints();
			
		setLayout(new GridBagLayout());
			
		objConstraints.gridx = objConstraints.gridy = 0;
		objConstraints.gridheight = objConstraints.gridwidth = 1;
		objConstraints.anchor = GridBagConstraints.NORTHWEST;
		objConstraints.fill = GridBagConstraints.NONE;
		objConstraints.insets = new Insets(0, 10, 2, 5);
		objConstraints.weightx = 0;
		add(getIdLabel(), objConstraints);
			
		objConstraints.gridx++;
		objConstraints.insets = new Insets(0, 0, 2, 5);
		objConstraints.weightx = 1;
		add(getIdField(), objConstraints);
		
		objConstraints.gridx = 0;
		objConstraints.gridy++;
		objConstraints.insets = new Insets(3, 10, 2, 5);
		objConstraints.weightx = 0;
		add(getNameLabel(), objConstraints);
		
		objConstraints.gridx++;
		objConstraints.insets = new Insets(3, 0, 2, 5);
		objConstraints.weightx = 1;
		add(getNameField(), objConstraints);
		
		objConstraints.fill = GridBagConstraints.BOTH;
		objConstraints.gridwidth = 2;
		objConstraints.gridx = 0;
		objConstraints.gridy++;
		objConstraints.insets = new Insets(5, 5, 5, 5);
		objConstraints.weightx = 1.0;
		objConstraints.weighty = 1.0;
		add(getEmailPhonePanel(), objConstraints);
	}
	
	protected void setModel(final Individual objModel)
	{
		mObjModel = objModel;
	}
}