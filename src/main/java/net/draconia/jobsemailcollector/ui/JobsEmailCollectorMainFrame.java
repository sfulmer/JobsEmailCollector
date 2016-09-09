package net.draconia.jobsemailcollector.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.KeyEvent;

import java.io.File;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.draconia.jobsemailcollector.domain.Individual;

import net.draconia.jobsemailcollector.model.Model;

import net.draconia.jobsemailcollector.observers.FileListImportJobsObserver;
import net.draconia.jobsemailcollector.observers.FileListObserver;
import net.draconia.jobsemailcollector.service.IndividualService;
import net.draconia.jobsemailcollector.ui.actions.BrowseFiles;
import net.draconia.jobsemailcollector.ui.actions.ImportJobs;
import net.draconia.jobsemailcollector.ui.actions.Quit;

import net.draconia.jobsemailcollector.ui.model.FileListModel;

import net.draconia.jobsemailcollector.ui.observers.IndividualTableModelObserver;

import net.draconia.jobsemailcollector.ui.table.ScrollablePageableTable;

import net.draconia.jobsemailcollector.ui.table.model.Column;
import net.draconia.jobsemailcollector.ui.table.model.ScrollablePageableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class JobsEmailCollectorMainFrame extends JFrame
{
	private static final long serialVersionUID = 5691706816030821149L;
	
	private Action mActBrowseFiles, mActImportJobs, mActQuit;
	
	@Autowired
	@Qualifier("individualService")
	private IndividualService mObjIndividualService;
	
	private JList<File> mLstFiles;
	
	@Autowired
	@Qualifier("mainModel")
	private Model mObjModel;
	
	public JobsEmailCollectorMainFrame()
	{
		super("Jobs Email Collector");
		
		//initFrame();
	}
	
	public JobsEmailCollectorMainFrame(final Model objModel)
	{
		super("Jobs Email Collector");
		
		setModel(objModel);
		
		//initFrame();
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
	
	protected IndividualService getIndividualService()
	{
		return(mObjIndividualService);
	}
	
	protected JPanel getIndividualsPanel()
	{
		JPanel pnlIndividuals;
		
		//new Thread(new IndividualsTableLoader(getModel())).start();
		
		//getModel().addObserver(new TableDataLoaderObserver(objTableModel));
		pnlIndividuals = new ScrollablePageableTable(new Column[]
		{	new Column("Id", Integer.class, Individual.class, "getId")
		,	new Column("Name", String.class, Individual.class, "getName")
		,	new Column("Last Email Contact", Date.class, Individual.class, "getDateOfLastEmail")
		});
		ScrollablePageableModel objModel = ((ScrollablePageableTable)(pnlIndividuals)).getModel();
		
		objModel.setCollectionModel(getModel());
		objModel.setService(getIndividualService());
		objModel.setDetailDialogClass(IndividualDetailDialog.class);
		objModel.setGetCollectionSizeName("getIndividualCount");
		objModel.setGetDataCollectionName("getIndividuals");
		objModel.setGetRowByIndexName("get");
		objModel.setIdType(Integer.class);
		objModel.setPageable(true);
		objModel.setRowDataType(Individual.class);
		objModel.setRowKeyType(Object.class);
		
		getModel().addObserver(new IndividualTableModelObserver(objModel));
		
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
	
	@PostConstruct
	protected void initFrame()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setFont(new Font("Tahana", Font.PLAIN, 10));
		
		initControl();
		
		pack();
	}
	
	protected void setIndividualService(final IndividualService objIndividualService)
	{
		mObjIndividualService = objIndividualService;
	}
	
	protected void setModel(final Model objModel)
	{
		mObjModel = objModel;
	}
}