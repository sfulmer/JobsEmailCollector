package net.draconia.jobsemailcollector.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.KeyEvent;

import java.io.File;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.draconia.jobsemailcollector.manager.IndividualManager;
import net.draconia.jobsemailcollector.manager.IndividualsTableLoader;

import net.draconia.jobsemailcollector.model.Individual;
import net.draconia.jobsemailcollector.model.Model;

import net.draconia.jobsemailcollector.observers.FileListImportJobsObserver;
import net.draconia.jobsemailcollector.observers.FileListObserver;

import net.draconia.jobsemailcollector.ui.actions.BrowseFiles;
import net.draconia.jobsemailcollector.ui.actions.ImportJobs;
import net.draconia.jobsemailcollector.ui.actions.Quit;

import net.draconia.jobsemailcollector.ui.model.FileListModel;
import net.draconia.jobsemailcollector.ui.model.IndividualTableModel;
import net.draconia.jobsemailcollector.ui.observers.TableDataLoaderObserver;

public class JobsEmailCollectorMainFrame extends JFrame
{
	private static final long serialVersionUID = 5691706816030821149L;
	
	private Action mActBrowseFiles, mActImportJobs, mActQuit;
	private JList<File> mLstFiles;
	private Model mObjModel;
	
	public JobsEmailCollectorMainFrame(final Model objModel)
	{
		super("Jobs Email Collector");
		
		setModel(objModel);
		
		initFrame();
	}
	
	protected Action getBrowseFilesAction()
	{
		if(mActBrowseFiles == null)
			mActBrowseFiles = new BrowseFiles(this, getModel());
		
		return(mActBrowseFiles);
	}
	
	protected JButton getBrowseFilesButton()
	{
		JButton btnBrowseFiles = new JButton(getBrowseFilesAction());
		
		btnBrowseFiles.setFont(getFont().deriveFont(Font.BOLD));
		
		return(btnBrowseFiles);
	}
	
	protected JLabel getFileLabel()
	{
		JLabel lblFiles = new JLabel("File:");
		
		lblFiles.setFont(getFont().deriveFont(Font.BOLD));
		lblFiles.setDisplayedMnemonic(KeyEvent.VK_F);
		lblFiles.setLabelFor(getFileListBox());
		lblFiles.setVerticalAlignment(JLabel.TOP);
		
		return(lblFiles);
	}
	
	protected JList<File> getFileListBox()
	{
		if(mLstFiles == null)
			{
			FileListModel objModel = new FileListModel(getModel());
			
			getModel().addObserver(new FileListObserver(objModel));
			
			mLstFiles = new JList<File>(objModel);
			
			mLstFiles.setBorder(BorderFactory.createLoweredBevelBorder());
			mLstFiles.setFont(getFont());
			}
		
		return(mLstFiles);
	}
	
	protected Action getImportAction()
	{
		if(mActImportJobs == null)
			{
			mActImportJobs = new ImportJobs(getModel(), this);
			
			mActImportJobs.setEnabled(false);
			
			getModel().addObserver(new FileListImportJobsObserver(mActImportJobs));
			}
			
		return(mActImportJobs);
	}
	
	protected JButton getImportButton()
	{
		JButton btnImport = new JButton(getImportAction());
		
		btnImport.setFont(getFont().deriveFont(Font.BOLD));
		
		return(btnImport);
	}
	
	protected JPanel getImportPanel()
	{
		GridBagConstraints objConstraints = new GridBagConstraints();
		JPanel pnlImport = new JPanel(new GridBagLayout());
		
		objConstraints.anchor = GridBagConstraints.NORTHWEST;
		objConstraints.fill = GridBagConstraints.BOTH;
		objConstraints.gridx = objConstraints.gridy = 0;
		objConstraints.gridwidth = objConstraints.gridheight = 1;
		objConstraints.insets = new Insets(5, 5, 5, 5);
		pnlImport.add(getFileLabel(), objConstraints);
		
		objConstraints.gridx++;
		pnlImport.add(new JScrollPane(getFileListBox(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), objConstraints);
		
		objConstraints.gridx++;
		objConstraints.fill = GridBagConstraints.NONE;
		pnlImport.add(getBrowseFilesButton(), objConstraints);
		
		objConstraints.anchor = GridBagConstraints.CENTER;
		objConstraints.fill = GridBagConstraints.VERTICAL;
		objConstraints.gridwidth = 2;
		objConstraints.gridx = 0;
		objConstraints.gridy++;
		pnlImport.add(getMainButtonPanel(), objConstraints);
		
		return(pnlImport);
	}
	
	protected JPanel getIndividualsPanel()
	{
		IndividualManager objIndividualManager = new IndividualManager(getModel());
		IndividualTableModel objTableModel = new IndividualTableModel(getModel().getTableData());
		JPanel pnlIndividuals;
		
		new Thread(new IndividualsTableLoader(getModel())).start();
		
		getModel().addObserver(new TableDataLoaderObserver(objTableModel));
		
		pnlIndividuals = new ScrollablePageableTable(objTableModel);
		ScrollablePageableTable.ScrollablePageableModel objModel = ((ScrollablePageableTable)(pnlIndividuals)).getModel();
		
		objModel.setListModel(getModel());
		objModel.setManager(objIndividualManager);
		objModel.setRowModel(objIndividualManager);
		objModel.setDetailDialogClass(IndividualDetailDialog.class);
		objModel.setGetDataByIdName("getIndividualById");
		objModel.setGetDataListName("getTableData");
		objModel.setIdType(Integer.class);
		objModel.setRowDataType(Individual.class);
		
		return(pnlIndividuals);
	}
	
	protected JPanel getMainButtonPanel()
	{
		JPanel pnl = new JPanel(new FlowLayout(5, 5, FlowLayout.CENTER));
		
		pnl.add(getImportButton());
		pnl.add(getQuitButton());
		
		return(pnl);
	}
	
	protected Model getModel()
	{
		return(mObjModel);
	}
	
	protected Action getQuitAction()
	{
		if(mActQuit == null)
			mActQuit = new Quit(this);
		
		return(mActQuit);
	}
	
	protected JButton getQuitButton()
	{
		JButton btnQuit = new JButton(getQuitAction());
		
		btnQuit.setFont(getFont().deriveFont(Font.BOLD));
		
		return(btnQuit);
	}
	
	protected void initControl()
	{
		setLayout(new BorderLayout(5, 5));
		
		add(getImportPanel(), BorderLayout.NORTH);
		add(getIndividualsPanel(), BorderLayout.CENTER);
	}
	
	protected void initFrame()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setFont(new Font("Tahana", Font.PLAIN, 10));
		
		initControl();
		
		pack();
	}
	
	protected void setModel(final Model objModel)
	{
		mObjModel = objModel;
	}
}