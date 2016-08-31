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
	
	private Action mActBrowseFiles/*, mActFirstPage, mActLastPage*/;
	private Action /*mActNextPage, mActPreviousPage, */mActImportJobs, mActQuit;
//	private JComboBox<Integer> mCboPageSize;
	private JList<File> mLstFiles;
	/*private JSpinner mSpnCurrentPage;
	private JTable mTblIndividuals;
	private JTextField mTxtTotalPages;*/
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
	
	/*protected JSpinner getCurrentPageField()
	{
		if(mSpnCurrentPage == null)
			{
			mSpnCurrentPage = new JSpinner(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), ((Integer)(getPageSizeField().getSelectedItem())), Integer.valueOf(1)));
			
			mSpnCurrentPage.setBorder(BorderFactory.createLoweredBevelBorder());
			mSpnCurrentPage.setFont(getFont());
			}
		
		return(mSpnCurrentPage);
	}*/
	
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
	
	/*protected Action getFirstPageAction()
	{
		if(mActFirstPage == null)
			mActFirstPage = new FirstPage();
		
		return(mActFirstPage);
	}
	
	protected JButton getFirstPageButton()
	{
		JButton btnFirstPage = new JButton(getFirstPageAction());
		
		btnFirstPage.setFont(getFont().deriveFont(Font.BOLD));
		
		return(btnFirstPage);
	}*/
	
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
	
	/*protected JPanel getIndividualsPagingPanel()
	{
		JPanel pnlPaging = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		pnlPaging.add(getFirstPageButton());
		pnlPaging.add(getPreviousPageButton());
		pnlPaging.add(getPageLabel());
		pnlPaging.add(getCurrentPageField());
		pnlPaging.add(getOfLabel());
		pnlPaging.add(getTotalPagesField());
		pnlPaging.add(getNextPageButton());
		pnlPaging.add(getLastPageButton());
		pnlPaging.add(new JLabel("     "));
		pnlPaging.add(getPageSizeLabel());
		pnlPaging.add(getPageSizeField());
		
		return(pnlPaging);
	}*/
	
	protected JPanel getIndividualsPanel()
	{
		/*JPanel pnlIndividuals = new JPanel(new BorderLayout(0, 5));
		
		pnlIndividuals.add(getIndividualsScrollPanel(), BorderLayout.CENTER);
		pnlIndividuals.add(getIndividualsPagingPanel(), BorderLayout.SOUTH);*/
		
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
	
	/*protected JScrollPane getIndividualsScrollPanel()
	{
		JScrollPane pnlIndividuals = new JScrollPane(getIndividualsTable(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		return(pnlIndividuals);
	}
	
	protected JTable getIndividualsTable()
	{
		if(mTblIndividuals == null)
			{
			IndividualTableModel objTableModel = new IndividualTableModel(getModel().getTableData());
			
			new Thread(new IndividualsTableLoader(getModel())).start();
			
			getModel().addObserver(new TableDataLoaderObserver(objTableModel));
			
			mTblIndividuals = new JTable(objTableModel);
			mTblIndividuals.addMouseListener(new IndividualTableMouseListener(this, new IndividualManager(getModel())));
			mTblIndividuals.setColumnSelectionAllowed(false);
			mTblIndividuals.setRowSelectionAllowed(true);
			mTblIndividuals.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			}
		
		return(mTblIndividuals);
	}*/
	
	/*protected Action getLastPageAction()
	{
		if(mActLastPage == null)
			mActLastPage = new LastPage();
		
		return(mActLastPage);
	}
	
	protected JButton getLastPageButton()
	{
		JButton btnLastPage = new JButton(getLastPageAction());
		
		btnLastPage.setFont(getFont().deriveFont(Font.BOLD));
		
		return(btnLastPage);
	}*/
	
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
	
	/*protected Action getNextPageAction()
	{
		if(mActNextPage == null)
			mActNextPage = new NextPage();
		
		return(mActNextPage);
	}
	
	protected JButton getNextPageButton()
	{
		JButton btnNextPage = new JButton(getNextPageAction());
		
		btnNextPage.setFont(getFont().deriveFont(Font.BOLD));
		
		return(btnNextPage);
	}*/
	
	/*protected JLabel getOfLabel()
	{
		JLabel lblOf = new JLabel("of");
		
		lblOf.setFont(getFont());
		lblOf.setOpaque(false);
		
		return(lblOf);
	}
	
	protected JLabel getPageLabel()
	{
		JLabel lblPage = new JLabel("Page:");
		
		lblPage.setFont(getFont().deriveFont(Font.BOLD));
		lblPage.setLabelFor(getCurrentPageField());
		lblPage.setOpaque(false);
		
		return(lblPage);
	}
	
	protected JComboBox<Integer> getPageSizeField()
	{
		if(mCboPageSize == null)
			{
			mCboPageSize = new JComboBox<Integer>(new Integer[] {5, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 150, 200, 250, 300, 350, 400, 500, 600, 700, 800, 900, 1000});
			
			mCboPageSize.setBorder(BorderFactory.createLoweredBevelBorder());
			mCboPageSize.setFont(getFont());
			}
		
		return(mCboPageSize);
	}
	
	protected JLabel getPageSizeLabel()
	{
		JLabel lblPageSize = new JLabel("Page Size:");
		
		lblPageSize.setFont(getFont().deriveFont(Font.BOLD));
		lblPageSize.setLabelFor(getPageSizeField());
		lblPageSize.setOpaque(false);
		
		return(lblPageSize);
	}
	
	protected Action getPreviousPageAction()
	{
		if(mActPreviousPage == null)
			mActPreviousPage = new PreviousPage();
		
		return(mActPreviousPage);
	}
	
	protected JButton getPreviousPageButton()
	{
		JButton btnPreviousPage = new JButton(getPreviousPageAction());
		
		btnPreviousPage.setFont(getFont().deriveFont(Font.BOLD));
		
		return(btnPreviousPage);
	}*/
	
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
	
	/*protected JTextField getTotalPagesField()
	{
		if(mTxtTotalPages == null)
			{
			mTxtTotalPages = new JTextField(5);
			
			mTxtTotalPages.setBackground(getBackground());
			mTxtTotalPages.setBorder(BorderFactory.createEmptyBorder());
			mTxtTotalPages.setEditable(false);
			mTxtTotalPages.setFont(getFont());
			mTxtTotalPages.setHorizontalAlignment(JTextField.CENTER);
			mTxtTotalPages.setOpaque(false);
			mTxtTotalPages.setText("0");
			}
		
		return(mTxtTotalPages);
	}*/
	
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