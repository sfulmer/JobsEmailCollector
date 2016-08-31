package net.draconia.jobsemailcollector.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;

import java.io.File;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import net.draconia.jobsemailcollector.model.FileToImport;
import net.draconia.jobsemailcollector.ui.actions.CancelImport;
import net.draconia.jobsemailcollector.ui.observers.LocationProgressDialogObserver;
import net.draconia.jobsemailcollector.ui.observers.ProgressBarProgessDialogObserver;
import net.draconia.jobsemailcollector.ui.observers.StatisticsProgressDialogObserver;

public class ProgressDialog extends JDialog
{
	private static final long serialVersionUID = 1665237773289738425L;
	
	private Action mActCancel;
	private FileToImport mObjModel;
	private JProgressBar mBarProgress;
	private JTextField mTxtFileLocation, mTxtNumBytesRead, mTxtTotalNumberToRead;
	
	public ProgressDialog(final Window wndParent)
	{
		super(wndParent);
		
		initDialog();
	}
	
	public ProgressDialog(final Window wndParent, final FileToImport fileToImport)
	{
		super(wndParent);
		
		setModel(fileToImport);
		
		initDialog();
	}
	
	protected Action getCancelAction()
	{
		if(mActCancel == null)
			mActCancel = new CancelImport(getModel(), getOwner());
		
		return(mActCancel);
	}
	
	protected JButton getCancelButton()
	{
		return(new JButton(getCancelAction()));
	}
	
	protected JLabel getFileLabel()
	{
		JLabel lblFileLabel = new JLabel("File:");
		
		lblFileLabel.setFont(getFont().deriveFont(Font.BOLD));
		lblFileLabel.setOpaque(false);
		
		return(lblFileLabel);
	}
	
	protected JTextField getFileLocationText()
	{
		if(mTxtFileLocation == null)
			{
			File fileToParse = getModel().getFileToImport();
			String sPath;
			
			mTxtFileLocation = new JTextField(30);
			
			if(fileToParse != null)
				sPath = fileToParse.getAbsolutePath();
			else
				sPath = "";
			
			mTxtFileLocation.setBackground(getBackground());
			mTxtFileLocation.setBorder(BorderFactory.createEmptyBorder());
			mTxtFileLocation.setEditable(false);
			mTxtFileLocation.setFocusable(false);
			mTxtFileLocation.setFont(getFont().deriveFont(Font.BOLD));
			mTxtFileLocation.setText(sPath);
			
			getModel().addObserver(new LocationProgressDialogObserver(mTxtFileLocation));
			}
		
		return(mTxtFileLocation);
	}
	
	protected FileToImport getModel()
	{
		return(mObjModel);
	}
	
	protected JTextField getNumberBytesReadText()
	{
		if(mTxtNumBytesRead == null)
			{
			mTxtNumBytesRead = new JTextField(10);
			
			mTxtNumBytesRead.setBackground(getBackground());
			mTxtNumBytesRead.setBorder(BorderFactory.createEmptyBorder());
			mTxtNumBytesRead.setEditable(false);
			mTxtNumBytesRead.setFocusable(false);
			mTxtNumBytesRead.setFont(getFont().deriveFont(Font.BOLD));
			mTxtNumBytesRead.setHorizontalAlignment(JLabel.RIGHT);
			mTxtNumBytesRead.setText(String.valueOf(getModel().getNumberOfBytesRead()));
			}
		
		return(mTxtNumBytesRead);
	}
	
	protected JProgressBar getProgressBar()
	{
		if(mBarProgress == null)
			{
			mBarProgress = new JProgressBar();
			mBarProgress.setStringPainted(true);
			
			getModel().addObserver(new ProgressBarProgessDialogObserver(mBarProgress));
			}
		
		return(mBarProgress);
	}
	
	protected JLabel getSlashLabel()
	{
		JLabel lblSlash = new JLabel("/");

		lblSlash.setFocusable(false);
		lblSlash.setFont(getFont().deriveFont(Font.BOLD));
		lblSlash.setOpaque(false);
		
		return(lblSlash);
	}
	
	protected JPanel getStatisticsPanel()
	{
		JPanel pnl = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		pnl.add(getNumberBytesReadText());
		pnl.add(getSlashLabel());
		pnl.add(getTotalBytesToReadText());
		
		getModel().addObserver(new StatisticsProgressDialogObserver(getNumberBytesReadText(), getTotalBytesToReadText()));
		
		return(pnl);
	}
	
	protected JTextField getTotalBytesToReadText()
	{
		if(mTxtTotalNumberToRead == null)
			{
			mTxtTotalNumberToRead = new JTextField(10);
			
			mTxtTotalNumberToRead.setBackground(getBackground());
			mTxtTotalNumberToRead.setBorder(BorderFactory.createEmptyBorder());
			mTxtTotalNumberToRead.setEditable(false);
			mTxtTotalNumberToRead.setFocusable(false);
			mTxtTotalNumberToRead.setFont(getFont().deriveFont(Font.BOLD));
			mTxtTotalNumberToRead.setText(String.valueOf(getModel().getTotalNumberOfBytes()));
			}
		
		return(mTxtTotalNumberToRead);
	}
	
	protected void initControls()
	{
		GridBagConstraints objConstraints = new GridBagConstraints();
		
		objConstraints.gridx = objConstraints.gridy = 0;
		objConstraints.gridheight = objConstraints.gridwidth = 1;
		objConstraints.anchor = GridBagConstraints.CENTER;
		objConstraints.insets = new Insets(5, 5, 5, 5);
		add(getFileLabel(), objConstraints);
		
		objConstraints.gridx++;
		add(getFileLocationText(), objConstraints);
		
		objConstraints.gridy++;
		objConstraints.gridx = 0;
		objConstraints.gridwidth = 2;
		add(getProgressBar(), objConstraints);
		
		objConstraints.gridy++;
		objConstraints.fill = GridBagConstraints.BOTH;
		add(getStatisticsPanel(), objConstraints);
		
		objConstraints.gridy++;
		objConstraints.fill = GridBagConstraints.NONE;
		add(getCancelButton(), objConstraints);
	}
	
	protected void initDialog()
	{
		Dimension szScreen = getToolkit().getScreenSize();
		
		setAlwaysOnTop(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setFont(getParent().getFont());
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(350, 150));
		setResizable(false);
		setUndecorated(true);
		
		initControls();
		
		pack();
		setLocation((int)((szScreen.getWidth() - getWidth()) / 2), (int)((szScreen.getHeight() - getHeight()) / 2));
	}
	
	protected void setModel(final FileToImport fileToImport)
	{
		mObjModel = fileToImport;
	}
}