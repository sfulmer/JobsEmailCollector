package net.draconia.jobsemailcollector.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;

import net.draconia.jobsemailcollector.model.Email;
import net.draconia.jobsemailcollector.ui.listeners.EmailDateChangeListener;
import net.draconia.jobsemailcollector.ui.listeners.EmailFieldChangeListener;

import net.draconia.jobsemailcollector.ui.utilities.TextUtilities;

import net.sourceforge.jdatepicker.JDatePicker;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class EmailTabPanel extends JPanel
{
	private static final long serialVersionUID = 5630126156207026697L;
	
	private Email mObjModel;
	private JDatePicker mDPDate;
	private JTextComponent mTxtBody, mTxtCharacterSet, mTxtContentTransferEncoding;
	private JTextComponent mTxtContentType, mTxtFrom, mTxtId;
	private JTextComponent mTxtSubject, mTxtTo;
	private TextUtilities mObjTextUtilities;
	
	public EmailTabPanel(final Email objModel)
	{
		setModel(objModel);
		
		initPanel();
	}
	
	protected JTextComponent getBodyField()
	{
		if(mTxtBody == null)
			{
			mTxtBody = new JEditorPane();
			
			mTxtBody.setBorder(BorderFactory.createLoweredBevelBorder());
			mTxtBody.setFont(getFont());
			mTxtBody.setPreferredSize(new Dimension(mTxtBody.getPreferredScrollableViewportSize().width, mTxtBody.getPreferredScrollableViewportSize().height * 5));
			mTxtBody.setText(getModel().getBody());
			
			getTextUtilities().setupUnRedoableMenuTextComponent(mTxtBody, new EmailFieldChangeListener(getModel(), "Body"));
			}
		
		return(mTxtBody);
	}
	
	protected JLabel getBodyLabel()
	{
		JLabel lblBody = new JLabel("Body:");
		
		lblBody.setDisplayedMnemonic(KeyEvent.VK_B);
		lblBody.setFont(getFont().deriveFont(Font.BOLD));
		lblBody.setLabelFor(getFromField());
		lblBody.setOpaque(false);
		
		return(lblBody);
	}
	
	protected JScrollPane getBodyScrollPane()
	{
		JScrollPane scrBody = new JScrollPane(getBodyField(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		return(scrBody);
	}
	
	protected JTextComponent getCharacterSetField()
	{
		if(mTxtCharacterSet == null)
			{
			mTxtCharacterSet = new JTextField(30);
			
			mTxtCharacterSet.setBorder(BorderFactory.createLoweredBevelBorder());
			mTxtCharacterSet.setFont(getFont());
			mTxtCharacterSet.setText(getModel().getCharacterSet());
			
			getTextUtilities().setupUnRedoableMenuTextComponent(mTxtCharacterSet, new EmailFieldChangeListener(getModel(), "CharacterSet"));
			}
		
		return(mTxtCharacterSet);
	}
	
	protected JLabel getCharacterSetLabel()
	{
		JLabel lblCharacterSet = new JLabel("Character Set:");
		
		lblCharacterSet.setDisplayedMnemonic(KeyEvent.VK_H);
		lblCharacterSet.setFont(getFont().deriveFont(Font.BOLD));
		lblCharacterSet.setLabelFor(getCharacterSetField());
		lblCharacterSet.setOpaque(false);
		
		return(lblCharacterSet);
	}
	
	protected JTextComponent getContentTransferEncodingField()
	{
		if(mTxtContentTransferEncoding == null)
			{
			mTxtContentTransferEncoding = new JTextField(30);
			
			mTxtContentTransferEncoding.setBorder(BorderFactory.createLoweredBevelBorder());
			mTxtContentTransferEncoding.setFont(getFont());
			mTxtContentTransferEncoding.setText(getModel().getContentTransferEncoding());
			
			getTextUtilities().setupUnRedoableMenuTextComponent(mTxtContentTransferEncoding, new EmailFieldChangeListener(getModel(), "ContentTransferEncoding"));
			}
		
		return(mTxtContentTransferEncoding);
	}
	
	protected JLabel getContentTransferEncodingLabel()
	{
		JLabel lblContentTransferEncoding = new JLabel("Content Transfer Encoding:");
		
		lblContentTransferEncoding.setDisplayedMnemonic(KeyEvent.VK_E);
		lblContentTransferEncoding.setFont(getFont().deriveFont(Font.BOLD));
		lblContentTransferEncoding.setLabelFor(getContentTransferEncodingField());
		lblContentTransferEncoding.setOpaque(false);
		
		return(lblContentTransferEncoding);
	}
	
	protected JTextComponent getContentTypeField()
	{
		if(mTxtContentType == null)
			{
			mTxtContentType = new JTextField(30);
			
			mTxtContentType.setBorder(BorderFactory.createLoweredBevelBorder());
			mTxtContentType.setFont(getFont());
			mTxtContentType.setText(getModel().getContentType());
			
			getTextUtilities().setupUnRedoableMenuTextComponent(mTxtContentType, new EmailFieldChangeListener(getModel(), "ContentType"));
			}
		
		return(mTxtContentType);
	}
	
	protected JLabel getContentTypeLabel()
	{
		JLabel lblContentType = new JLabel("Content Type:");
		
		lblContentType.setDisplayedMnemonic(KeyEvent.VK_Y);
		lblContentType.setFont(getFont().deriveFont(Font.BOLD));
		lblContentType.setLabelFor(getCharacterSetField());
		lblContentType.setOpaque(false);
		
		return(lblContentType);
	}
	
	protected JDatePicker getDateField()
	{
		if(mDPDate == null)
			{
			ChangeListener objChangeListener;
			JDatePanelImpl pnlDate;
			JFormattedTextField txtDate;
			UtilDateModel objModel = new UtilDateModel();
			
			pnlDate = new JDatePanelImpl(objModel);
			mDPDate =  new JDatePickerImpl(pnlDate);
			
			pnlDate.setBorder(BorderFactory.createLoweredBevelBorder());
			pnlDate.setFont(getFont());
			
			mDPDate.setTextEditable(true);
			mDPDate.setDoubleClickAction(true);
			
			objChangeListener = new EmailDateChangeListener(getModel());
			txtDate = ((JDatePickerImpl)(mDPDate)).getJFormattedTextField();
			
			txtDate.setFont(getFont());
			
			getTextUtilities().setupUnRedoableMenuTextComponent(txtDate, objChangeListener);
			
			objModel.setValue(getModel().getDate());
			
			objModel.addChangeListener(objChangeListener);
			}
		
		return(mDPDate);
	}
	
	protected JLabel getDateLabel()
	{
		JLabel lblDate = new JLabel("Date:");
		
		lblDate.setDisplayedMnemonic(KeyEvent.VK_D);
		lblDate.setFont(getFont().deriveFont(Font.BOLD));
		lblDate.setLabelFor(((JDatePickerImpl)(getDateField())));
		lblDate.setOpaque(false);
		
		return(lblDate);
	}
	
	protected JTextComponent getFromField()
	{
		if(mTxtFrom == null)
			{
			mTxtFrom = new JTextField(30);
			
			mTxtFrom.setBorder(BorderFactory.createLoweredBevelBorder());
			mTxtFrom.setFont(getFont());
			mTxtFrom.setText(getModel().getFrom());
			
			getTextUtilities().setupUnRedoableMenuTextComponent(mTxtFrom, new EmailFieldChangeListener(getModel(), "From"));
			}
		
		return(mTxtFrom);
	}
	
	protected JLabel getFromLabel()
	{
		JLabel lblFrom = new JLabel("From:");
		
		lblFrom.setDisplayedMnemonic(KeyEvent.VK_F);
		lblFrom.setFont(getFont().deriveFont(Font.BOLD));
		lblFrom.setLabelFor(getFromField());
		lblFrom.setOpaque(false);
		
		return(lblFrom);
	}
	
	protected JTextComponent getIdField()
	{
		if(mTxtId == null)
			{
			mTxtId = new JTextField(30);
			
			mTxtId.setBackground(getForeground());
			mTxtId.setBorder(BorderFactory.createEmptyBorder());
			mTxtId.setEditable(false);
			mTxtId.setFont(getFont());
			mTxtId.setOpaque(false);
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
	
	protected Email getModel()
	{
		return(mObjModel);
	}
	
	protected JTextComponent getSubjectField()
	{
		if(mTxtSubject == null)
			{
			mTxtSubject = new JTextField(30);
			
			mTxtSubject.setBorder(BorderFactory.createLoweredBevelBorder());
			mTxtSubject.setFont(getFont());
			mTxtSubject.setText(getModel().getSubject());
			
			getTextUtilities().setupUnRedoableMenuTextComponent(mTxtSubject, new EmailFieldChangeListener(getModel(), "Subject"));
			}
		
		return(mTxtSubject);
	}
	
	protected JLabel getSubjectLabel()
	{
		JLabel lblSubject = new JLabel("Subject:");
		
		lblSubject.setDisplayedMnemonic(KeyEvent.VK_S);
		lblSubject.setFont(getFont().deriveFont(Font.BOLD));
		lblSubject.setLabelFor(getFromField());
		lblSubject.setOpaque(false);
		
		return(lblSubject);
	}
	
	protected JTextComponent getToField()
	{
		if(mTxtTo == null)
			{
			mTxtTo = new JTextField(30);
			
			mTxtTo.setBorder(BorderFactory.createLoweredBevelBorder());
			mTxtTo.setFont(getFont());
			mTxtTo.setText(getModel().getTo());
			
			getTextUtilities().setupUnRedoableMenuTextComponent(mTxtTo, new EmailFieldChangeListener(getModel(), "To"));
			}
		
		return(mTxtTo);
	}
	
	protected JLabel getToLabel()
	{
		JLabel lblTo = new JLabel("To:");
		
		lblTo.setDisplayedMnemonic(KeyEvent.VK_T);
		lblTo.setFont(getFont().deriveFont(Font.BOLD));
		lblTo.setLabelFor(getToField());
		lblTo.setOpaque(false);
		
		return(lblTo);
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
		objConstraints.gridwidth = objConstraints.gridheight = 1;
		objConstraints.anchor = GridBagConstraints.NORTHWEST;
		objConstraints.fill = GridBagConstraints.HORIZONTAL;
		objConstraints.insets = new Insets(5, 10, 2, 5);
		add(getIdLabel(), objConstraints);
		
		objConstraints.gridx++;
		objConstraints.insets = new Insets(5, 0, 2, 5);
		objConstraints.weightx = 1;
		add(getIdField(), objConstraints);
		
		objConstraints.gridx = 0;
		objConstraints.gridy++;
		objConstraints.insets = new Insets(3, 10, 2, 5);
		objConstraints.weightx = 0;
		add(getDateLabel(), objConstraints);
		
		objConstraints.gridx++;
		objConstraints.insets = new Insets(3, 0, 2, 5);
		objConstraints.weightx = 1;
		add(((JDatePickerImpl)(getDateField())), objConstraints);
		
		objConstraints.gridx++;
		objConstraints.insets = new Insets(3, 10, 2, 5);
		objConstraints.weightx = 0;
		add(getContentTypeLabel(), objConstraints);
		
		objConstraints.gridx++;
		objConstraints.insets = new Insets(3, 0, 2, 5);
		objConstraints.weightx = 1;
		add(getContentTypeField(), objConstraints);
		
		objConstraints.gridx = 0;
		objConstraints.gridy++;
		objConstraints.insets = new Insets(3, 10, 2, 5);
		objConstraints.weightx = 0;
		add(getFromLabel(), objConstraints);
		
		objConstraints.gridx++;
		objConstraints.insets = new Insets(3, 0, 2, 5);
		objConstraints.weightx = 1;
		add(getFromField(), objConstraints);
		
		objConstraints.gridx++;
		objConstraints.insets = new Insets(3, 10, 2, 5);
		objConstraints.weightx = 0;
		add(getCharacterSetLabel(), objConstraints);
		
		objConstraints.gridx++;
		objConstraints.insets = new Insets(3, 0, 2, 5);
		objConstraints.weightx = 1;
		add(getCharacterSetField(), objConstraints);
		
		objConstraints.gridx = 0;
		objConstraints.gridy++;
		objConstraints.insets = new Insets(3, 10, 2, 5);
		objConstraints.weightx = 0;
		add(getToLabel(), objConstraints);
		
		objConstraints.gridx++;
		objConstraints.insets = new Insets(3, 0, 2, 5);
		objConstraints.weightx = 1;
		add(getToField(), objConstraints);
		
		objConstraints.gridx++;
		objConstraints.insets = new Insets(3, 10, 2, 5);
		objConstraints.weightx = 0;
		add(getContentTransferEncodingLabel(), objConstraints);
		
		objConstraints.gridx++;
		objConstraints.insets = new Insets(3, 0, 2, 5);
		objConstraints.weightx = 1;
		add(getContentTransferEncodingField(), objConstraints);
		
		objConstraints.gridx = 0;
		objConstraints.gridy++;
		objConstraints.insets = new Insets(3, 10, 2, 5);
		objConstraints.weightx = 0;
		add(getSubjectLabel(), objConstraints);
		
		objConstraints.fill = GridBagConstraints.HORIZONTAL;
		objConstraints.gridwidth = 3;
		objConstraints.gridx++;
		objConstraints.insets = new Insets(3, 0, 2, 5);
		objConstraints.weightx = 1;
		add(getSubjectField(), objConstraints);
		
		objConstraints.gridwidth = 5;
		objConstraints.gridx = 0;
		objConstraints.gridy++;
		objConstraints.insets = new Insets(3, 10, 2, 5);
		add(getBodyLabel(), objConstraints);
		
		objConstraints.fill = GridBagConstraints.BOTH;
		objConstraints.gridy++;
		objConstraints.insets = new Insets(3, 10, 5, 5);
		objConstraints.weighty = 1.0;
		add(getBodyScrollPane(), objConstraints);
	}
	
	protected void setModel(final Email objModel)
	{
		mObjModel = objModel;
	}
}