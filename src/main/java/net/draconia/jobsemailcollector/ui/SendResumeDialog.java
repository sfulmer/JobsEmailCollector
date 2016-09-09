package net.draconia.jobsemailcollector.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Window;

import java.awt.event.KeyEvent;

import java.io.File;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import javax.swing.border.TitledBorder;

import net.draconia.jobsemailcollector.domain.Individual;
import net.draconia.jobsemailcollector.ui.actions.Browse;
import net.draconia.jobsemailcollector.ui.actions.Close;
import net.draconia.jobsemailcollector.ui.actions.RemoveAttachment;
import net.draconia.jobsemailcollector.ui.actions.SendResume;
import net.draconia.jobsemailcollector.ui.actions.popup.TextCopy;
import net.draconia.jobsemailcollector.ui.actions.popup.TextCut;
import net.draconia.jobsemailcollector.ui.actions.popup.TextDelete;
import net.draconia.jobsemailcollector.ui.actions.popup.TextPaste;
import net.draconia.jobsemailcollector.ui.actions.popup.TextSelectAll;
import net.draconia.jobsemailcollector.ui.listeners.SendResumeDialogActionListener;

import net.draconia.jobsemailcollector.ui.model.SendResumeAttachmentsTableModel;
import net.draconia.jobsemailcollector.ui.model.SendResumeDialogModel;
import net.draconia.jobsemailcollector.ui.model.SendResumeToComboBoxModel;
import net.draconia.jobsemailcollector.ui.observers.SendResumeAttachmentTableModelObserver;
import net.draconia.jobsemailcollector.ui.observers.SendResumeAttachmentTableModelSelectObserver;
import net.draconia.jobsemailcollector.ui.observers.SendResumeAttachmentsRemoveObserver;
import net.draconia.jobsemailcollector.ui.observers.SendResumeDialogTextObserver;
import net.draconia.jobsemailcollector.ui.observers.SendResumeDialogToObserver;
import net.draconia.jobsemailcollector.ui.utilities.TextUtilities;
import net.draconia.jobsemailcollector.ui.utilities.MouseUtilities;

import org.apache.commons.lang3.tuple.Pair;

public class SendResumeDialog extends JDialog
{
	private static final long serialVersionUID = -7537174381543235983L;
	
	private Action mActBrowse, mActClose, mActRemove, mActSendResume;
	private TextUtilities mObjTextUtilities;
	private Individual mObjIndividual;
	private JComboBox<String> mCboTo;
	private JEditorPane mTxtBody;
	private JPopupMenu mMnuAttachments, mMnuText;
	private JTable mTblAttachments;
	private JTextField mTxtSubject;
	private MouseUtilities mObjMouseUtilities;
	private SendResumeDialogModel mObjModel;
	
	public SendResumeDialog(final Window wndParent, final Individual objIndividual)
	{
		super(wndParent, "Send Resume");
		
		setIndividual(objIndividual);
		
		initDialog();
	}
	
	protected JPanel getAttachmentsPanel()
	{
		GridBagConstraints objConstraints = new GridBagConstraints();
		JPanel pnlAttachments = new JPanel(new GridBagLayout());
		
		pnlAttachments.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Attachments:", TitledBorder.LEADING, TitledBorder.TOP, getFont().deriveFont(Font.BOLD)));
		
		objConstraints.anchor = GridBagConstraints.NORTHWEST;
		objConstraints.fill = GridBagConstraints.NONE;
		objConstraints.gridheight = 2;
		objConstraints.gridwidth = 1;
		objConstraints.gridx = objConstraints.gridy = 0;
		objConstraints.insets = new Insets(5, 5, 5, 5);
		objConstraints.weightx = objConstraints.weighty = 1;
		pnlAttachments.add(getAttachmentsScrollPanel(), objConstraints);
		
		objConstraints.gridheight = 1;
		objConstraints.gridx++;
		objConstraints.weightx = objConstraints.weighty = 0;
		pnlAttachments.add(getBrowseButton(), objConstraints);
		
		objConstraints.gridy++;
		pnlAttachments.add(getRemoveButton(), objConstraints);
		
		return(pnlAttachments);
	}
	
	protected JScrollPane getAttachmentsScrollPanel()
	{
		final JScrollPane scrAttachments = new JScrollPane(getAttachmentsTable(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		scrAttachments.setBorder(BorderFactory.createLoweredBevelBorder());
		scrAttachments.setPreferredSize(new Dimension(scrAttachments.getPreferredSize().width, getAttachmentsTable().getRowHeight() * 7));
		
		getMouseUtilities().installPopupListener(scrAttachments, getAttachmentsPopup());
		
		return(scrAttachments);
	}
	
	protected JPopupMenu getAttachmentsPopup()
	{
		if(mMnuAttachments == null)
			{
			mMnuAttachments = new JPopupMenu();
			
			mMnuAttachments.add(getBrowseAction());
			mMnuAttachments.addSeparator();
			mMnuAttachments.add(getRemoveAction());
			
			mMnuAttachments.setFont(getFont());
			}
		
		return(mMnuAttachments);
	}
	
	protected JTable getAttachmentsTable()
	{
		if(mTblAttachments == null)
			{
			SendResumeAttachmentsTableModel objModel = new SendResumeAttachmentsTableModel(getModel());
			
			getModel().addObserver(new SendResumeAttachmentTableModelObserver(objModel));
			
			mTblAttachments = new JTable(objModel);
			
			mTblAttachments.setBorder(BorderFactory.createLoweredBevelBorder());
			mTblAttachments.setFont(getFont());
			mTblAttachments.setRowSelectionAllowed(false);
			
			getMouseUtilities().installPopupListener(mTblAttachments, getAttachmentsPopup());
			
			mTblAttachments.getColumnModel().getColumn(0).setPreferredWidth(((int)(mTblAttachments.getPreferredScrollableViewportSize().getWidth() * .95)));
			mTblAttachments.getTableHeader().getColumnModel().getColumn(1).setHeaderRenderer(new SendResumeAttachmentTableHeaderRenderer());
			
			getModel().addObserver(new SendResumeAttachmentTableModelSelectObserver(mTblAttachments));
			getModel().addObserver(new SendResumeAttachmentsRemoveObserver(getRemoveAction()));
			}
		
		return(mTblAttachments);
	}
	
	protected JEditorPane getBodyField()
	{
		if(mTxtBody == null)
			{
			mTxtBody = new JEditorPane();
			mTxtBody.setBorder(BorderFactory.createLoweredBevelBorder());
			mTxtBody.setFont(getFont());
			mTxtBody.setPreferredSize(new Dimension(mTxtBody.getPreferredScrollableViewportSize().width, mTxtBody.getPreferredScrollableViewportSize().height * 5));
			mTxtBody.setText(getModel().getBody());
			
			//getTextUtilities().addChangeListener(mTxtBody, new SendResumeDialogChangeListener(getModel(), "Body"));
			getMouseUtilities().installPopupListener(mTxtBody, getTextPopup());
			
			getModel().addObserver(new SendResumeDialogTextObserver(mTxtBody, "Body"));
			}
		
		return(mTxtBody);
	}
	
	protected JLabel getBodyLabel()
	{
		JLabel lblBody = new JLabel("Body:");
		
		lblBody.setDisplayedMnemonic(KeyEvent.VK_S);
		lblBody.setFont(getFont().deriveFont(Font.BOLD));
		lblBody.setLabelFor(getBodyField());
		lblBody.setOpaque(false);
		
		return(lblBody);
	}
	
	protected JScrollPane getBodyScrollPanel()
	{
		JScrollPane pnlBody = new JScrollPane(getBodyField(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		return(pnlBody);
	}
	
	protected Action getBrowseAction()
	{
		if(mActBrowse == null)
			mActBrowse = new Browse(this, getModel());
		
		return(mActBrowse);
	}
	
	protected JButton getBrowseButton()
	{
		JButton btnBrowse = new JButton(getBrowseAction());
		
		btnBrowse.setFont(getFont().deriveFont(Font.BOLD));
		
		return(btnBrowse);
	}
	
	protected JPanel getButtonPanel()
	{
		JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		pnlButtons.add(getSendResumeButton());
		pnlButtons.add(getCloseButton());
		
		return(pnlButtons);
	}
	
	protected Action getCloseAction()
	{
		if(mActClose == null)
			{
			mActClose = new Close(this);
			
			getRootPane().getActionMap().put("CANCEL", mActClose);
			getRootPane().getInputMap(JRootPane.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "CANCEL");
			}
		
		return(mActClose);
	}
	
	protected JButton getCloseButton()
	{
		JButton btnClose = new JButton(getCloseAction());
		
		btnClose.setFont(getFont().deriveFont(Font.BOLD));
		
		return(btnClose);
	}
	
	protected Individual getIndividual()
	{
		return(mObjIndividual);
	}
	
	protected JPanel getMainPanel()
	{
		GridBagConstraints objConstraints = new GridBagConstraints();
		JPanel pnlMain = new JPanel(new GridBagLayout());
		
		objConstraints.anchor = GridBagConstraints.WEST;
		objConstraints.fill = GridBagConstraints.NONE;
		objConstraints.gridheight = objConstraints.gridwidth = 1;
		objConstraints.gridx = objConstraints.gridy = 0;
		objConstraints.insets = new Insets(5, 5, 2, 5);
		objConstraints.weightx = 0;
		pnlMain.add(getToLabel(), objConstraints);
		
		objConstraints.gridx++;
		objConstraints.weightx = 1;
		objConstraints.insets = new Insets(5, 0, 2, 5);
		pnlMain.add(getToField(), objConstraints);
		
		objConstraints.gridx = 0;
		objConstraints.gridy++;
		objConstraints.weightx = 0;
		objConstraints.insets = new Insets(3, 5, 2, 5);
		pnlMain.add(getSubjectLabel(), objConstraints);
		
		objConstraints.fill = GridBagConstraints.HORIZONTAL;
		objConstraints.gridx++;
		objConstraints.weightx = 1;
		objConstraints.insets = new Insets(5, 0, 2, 5);
		pnlMain.add(getSubjectField(), objConstraints);
		
		objConstraints.gridwidth = 2;
		objConstraints.gridx = 0;
		objConstraints.gridy++;
		objConstraints.weightx = 0;
		objConstraints.insets = new Insets(3, 5, 2, 5);
		pnlMain.add(getBodyLabel(), objConstraints);
		
		objConstraints.fill = GridBagConstraints.BOTH;
		objConstraints.gridwidth = 1;
		objConstraints.gridx++;
		objConstraints.insets = new Insets(5, 0, 2, 5);
		objConstraints.weightx = objConstraints.weighty = 1;
		pnlMain.add(getBodyScrollPanel(), objConstraints);
		
		objConstraints.gridwidth = 2;
		objConstraints.gridx = 0;
		objConstraints.gridy++;
		pnlMain.add(getAttachmentsPanel(), objConstraints);
		
		return(pnlMain);
	}
	
	protected SendResumeDialogModel getModel()
	{
		if(mObjModel == null)
			mObjModel = new SendResumeDialogModel(getIndividual());
		
		return(mObjModel);
	}
	
	protected MouseUtilities getMouseUtilities()
	{
		if(mObjMouseUtilities == null)
			mObjMouseUtilities = new MouseUtilities();
		
		return(mObjMouseUtilities);
	}
	
	protected Action getRemoveAction()
	{
		if(mActRemove == null)
			{
			int iSelectedCount = 0;
			
			for(Pair<File, Boolean> pairFile : getModel().getAttachmentList())
				iSelectedCount += pairFile.getValue() ? 1 : 0;
			
			mActRemove = new RemoveAttachment(getAttachmentsTable(), getModel());
			
			mActRemove.setEnabled(iSelectedCount > 0);
			}
		
		return(mActRemove);
	}
	
	protected JButton getRemoveButton()
	{
		JButton btnRemove = new JButton(getRemoveAction());
		
		btnRemove.setFont(getFont().deriveFont(Font.BOLD));
		
		return(btnRemove);
	}
	
	protected Action getSendResumeAction()
	{
		if(mActSendResume == null)
			mActSendResume = new SendResume(getModel(), this);
		
		return(mActSendResume);
	}
	
	protected JButton getSendResumeButton()
	{
		JButton btnSendResume = new JButton(getSendResumeAction());
		
		btnSendResume.setFont(getFont().deriveFont(Font.BOLD));
		
		return(btnSendResume);
	}
	
	protected JTextField getSubjectField()
	{
		if(mTxtSubject == null)
			{
			mTxtSubject = new JTextField(30);
			
			mTxtSubject.setBorder(BorderFactory.createLoweredBevelBorder());
			mTxtSubject.setFont(getFont());
			mTxtSubject.setText(getModel().getSubject());
			
			//getTextUtilities().addChangeListener(mTxtSubject, new SendResumeDialogChangeListener(getModel(), "Subject"));
			getMouseUtilities().installPopupListener(mTxtSubject, getTextPopup());
			
			getModel().addObserver(new SendResumeDialogTextObserver(mTxtSubject, "Subject"));
			}
		
		return(mTxtSubject);
	}
	
	protected JLabel getSubjectLabel()
	{
		JLabel lblSubject = new JLabel("Subject:");
		
		lblSubject.setDisplayedMnemonic(KeyEvent.VK_S);
		lblSubject.setFont(getFont().deriveFont(Font.BOLD));
		lblSubject.setLabelFor(getSubjectField());
		lblSubject.setOpaque(false);
		
		return(lblSubject);
	}
	
	protected JPopupMenu getTextPopup()
	{
		if(mMnuText == null)
			{
			mMnuText = new JPopupMenu();
			
			mMnuText.add(new TextCut());
			mMnuText.add(new TextCopy());
			mMnuText.add(new TextPaste());
			mMnuText.add(new TextDelete());
			mMnuText.addSeparator();
			mMnuText.add(new TextSelectAll());
			}
		
		return(mMnuText);
	}
	
	protected JComboBox<String> getToField()
	{
		if(mCboTo == null)
			{
			mCboTo = new JComboBox<String>(new SendResumeToComboBoxModel(getIndividual()));
			mCboTo.setBorder(BorderFactory.createLoweredBevelBorder());
			mCboTo.setEditable(true);
			mCboTo.setFont(getFont());
			mCboTo.setSelectedItem(getModel().getTo());
			
			mCboTo.addActionListener(new SendResumeDialogActionListener(getModel(), "To"));
			getModel().addObserver(new SendResumeDialogToObserver(mCboTo));
			}
		
		return(mCboTo);
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
	
	protected void initControls()
	{
		add(getMainPanel(), BorderLayout.CENTER);
		add(getButtonPanel(), BorderLayout.SOUTH);
	}
	
	protected void initDialog()
	{
		Dimension szScreen = getToolkit().getScreenSize();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setFont(getOwner().getFont());
		setLayout(new BorderLayout(5, 5));
		
		initControls();
		
		pack();
		setLocation(new Point((szScreen.width - getWidth())/2, (szScreen.height - getHeight())/2));
	}
	
	protected void setIndividual(final Individual objIndividual)
	{
		mObjIndividual = objIndividual;
	}
}