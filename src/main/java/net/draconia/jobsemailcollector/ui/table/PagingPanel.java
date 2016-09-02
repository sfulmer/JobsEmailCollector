package net.draconia.jobsemailcollector.ui.table;

import java.awt.FlowLayout;
import java.awt.Font;
import java.lang.reflect.InvocationTargetException;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import net.draconia.jobsemailcollector.ui.InitializablePanel;
import net.draconia.jobsemailcollector.ui.listeners.InitializablePanelAncestorListener;
import net.draconia.jobsemailcollector.ui.table.actions.FirstPage;
import net.draconia.jobsemailcollector.ui.table.actions.LastPage;
import net.draconia.jobsemailcollector.ui.table.actions.NextPage;
import net.draconia.jobsemailcollector.ui.table.actions.PreviousPage;
import net.draconia.jobsemailcollector.ui.table.listeners.ScrollablePageableCurrentPageChangeListener;
import net.draconia.jobsemailcollector.ui.table.listeners.ScrollablePageablePageSizeListener;
import net.draconia.jobsemailcollector.ui.table.model.ScrollablePageableModel;
import net.draconia.jobsemailcollector.ui.table.observers.ScrollablePageableCurrentPageObserver;

public class PagingPanel extends InitializablePanel
{
	private static final long serialVersionUID = 2553024281869599917L;
	
	private Action mActFirstPage, mActLastPage, mActNextPage, mActPreviousPage;
	private JComboBox<Integer> mCboPageSize;
	private JLabel mLblPageSize;
	private JSpinner mSpnCurrentPage;
	private JTextField mTxtTotalPages;
	private ScrollablePageableModel mObjModel;
		
	public PagingPanel(final ScrollablePageableModel objModel)
	{
		super(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		setModel(objModel);
		
		addAncestorListener(new InitializablePanelAncestorListener(this));
	}
	
	protected JSpinner getCurrentPageField()
	{
		if(mSpnCurrentPage == null)
			{
			Integer iMaximum = Integer.valueOf(getTotalPagesField().getText()), iMinimum, iValue;
			
			if(iMaximum > 0)
				iMinimum = iValue = 1;
			else
				iMinimum = iValue = 0;
			
			SpinnerNumberModel objSpinnerModel = new SpinnerNumberModel(iValue, iMinimum, iMaximum, Integer.valueOf(1));
			
			objSpinnerModel.addChangeListener(new ScrollablePageableCurrentPageChangeListener(getModel()));
			
			mSpnCurrentPage = new JSpinner(objSpinnerModel);
			
			mSpnCurrentPage.setBorder(BorderFactory.createLoweredBevelBorder());
			mSpnCurrentPage.setFont(getFont());
			
			getModel().addObserver(new ScrollablePageableCurrentPageObserver((SpinnerNumberModel)(mSpnCurrentPage.getModel()), getFirstPageAction(), getLastPageAction(), getNextPageAction(), getPreviousPageAction()));
			}
		
		return(mSpnCurrentPage);
	}
	
	protected Action getFirstPageAction()
	{
		if(mActFirstPage == null)
			{
			mActFirstPage = new FirstPage(getModel());
			
			mActFirstPage.setEnabled(false);
			}
		
		return(mActFirstPage);
	}
	
	protected JButton getFirstPageButton()
	{
		JButton btnFirstPage = new JButton(getFirstPageAction());
		
		btnFirstPage.setFont(getFont().deriveFont(Font.BOLD));
		
		return(btnFirstPage);
	}
		
	protected Action getLastPageAction()
	{
		if(mActLastPage == null)
			{
			Integer iCurrentPage, iPageQuantity;
			
			mActLastPage = new LastPage(getModel());
			
			iCurrentPage = getModel().getCurrentPage();
			
			try
				{
				iPageQuantity = getModel().getPageQuantity();
				}
			catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
				{
				objException.printStackTrace(System.err);
				
				iPageQuantity = 0;
				}
			
			mActLastPage.setEnabled(iPageQuantity > iCurrentPage);
			}
		
		return(mActLastPage);
	}
	
	protected JButton getLastPageButton()
	{
		JButton btnLastPage = new JButton(getLastPageAction());
		
		btnLastPage.setFont(getFont().deriveFont(Font.BOLD));
		
		return(btnLastPage);
	}
	
	public ScrollablePageableModel getModel()
	{
		return(mObjModel);
	}
	
	protected Action getNextPageAction()
	{
		if(mActNextPage == null)
			{
			Integer iCurrentPage, iPageQuantity;
			
			mActNextPage = new NextPage(getModel());
			
			iCurrentPage = getModel().getCurrentPage();
			
			try
				{
				iPageQuantity = getModel().getPageQuantity();
				}
			catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
				{
				objException.printStackTrace(System.err);
				
				iPageQuantity = 0;
				}
			
			mActNextPage.setEnabled(iPageQuantity > iCurrentPage);
			}
		
		return(mActNextPage);
	}
	
	protected JButton getNextPageButton()
	{
		JButton btnNextPage = new JButton(getNextPageAction());
		
		btnNextPage.setFont(getFont().deriveFont(Font.BOLD));
		
		return(btnNextPage);
	}
	
	protected JLabel getOfLabel()
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
			mCboPageSize = new JComboBox<Integer>(getModel().getPageSizes().toArray(new Integer[0]));
			
			mCboPageSize.setBorder(BorderFactory.createLoweredBevelBorder());
			mCboPageSize.setEditable(true);
			mCboPageSize.setFont(getFont());
			mCboPageSize.addActionListener(new ScrollablePageablePageSizeListener(getModel()));
			}
		
		return(mCboPageSize);
	}
	
	protected JLabel getPageSizeLabel()
	{
		if(mLblPageSize == null)
			{
			mLblPageSize = new JLabel("Page Size:");
			
			mLblPageSize.setFont(getFont().deriveFont(Font.BOLD));
			mLblPageSize.setLabelFor(getPageSizeField());
			mLblPageSize.setOpaque(false);
			
			try
				{
				if(getModel().getListSize() > 0)
					{
					mLblPageSize.setEnabled(true);
					mLblPageSize.getLabelFor().setEnabled(true);
					}
				else
					{
					mLblPageSize.setEnabled(false);
					mLblPageSize.getLabelFor().setEnabled(false);
					}
				}
			catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
				{
				objException.printStackTrace();
				}
			}
		
		return(mLblPageSize);
	}
	
	protected Action getPreviousPageAction()
	{
		if(mActPreviousPage == null)
			{
			mActPreviousPage = new PreviousPage(getModel());
			
			mActPreviousPage.setEnabled(false);
			}
		
		return(mActPreviousPage);
	}
	
	protected JButton getPreviousPageButton()
	{
		JButton btnPreviousPage = new JButton(getPreviousPageAction());
		
		btnPreviousPage.setFont(getFont().deriveFont(Font.BOLD));
		
		return(btnPreviousPage);
	}
	
	protected JTextField getTotalPagesField()
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
			
			try
				{
				mTxtTotalPages.setText(getModel().getPageQuantity().toString());
				}
			catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
				{
				objException.printStackTrace(System.err);
				
				mTxtTotalPages.setText("0");
				}
			}
		
		return(mTxtTotalPages);
	}
	
	protected void initPanel()
	{
		add(getFirstPageButton());
		add(getPreviousPageButton());
		add(getPageLabel());
		add(getCurrentPageField());
		add(getOfLabel());
		add(getTotalPagesField());
		add(getNextPageButton());
		add(getLastPageButton());
		add(new JLabel("     "));
		add(getPageSizeLabel());
		add(getPageSizeField());
	}
	
	protected void setModel(final ScrollablePageableModel objModel)
	{
		mObjModel = objModel;
	}
}