package net.draconia.jobsemailcollector.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Window;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

import net.draconia.jobsemailcollector.ui.actions.paging.*;

public class ScrollablePageableTable extends JPanel
{
	private static final long serialVersionUID = 2423017759548313902L;
	
	private Action mActFirstPage, mActLastPage, mActNextPage, mActPreviousPage;
	private JComboBox<Integer> mCboPageSize;
	private JSpinner mSpnCurrentPage;
	private JTable mTblTable;
	private JTextField mTxtTotalPages;
	private ScrollablePageableModel mObjModel;
	private TableModel mObjTableModel;
	
	public ScrollablePageableTable(final TableModel objTableModel)
	{
		super(new BorderLayout(0, 5));
		
		addAncestorListener(new AncestorListener()
		{
			public void ancestorRemoved(final AncestorEvent objAncestorEvent)
			{ }
			
			public void ancestorMoved(final AncestorEvent objAncestorEvent)
			{ }
			
			public void ancestorAdded(final AncestorEvent objAncestorEvent)
			{
				initPanel();
				
				((Window)(objAncestorEvent.getAncestor())).pack();
			}
		});
		
		setTableModel(objTableModel);
	}
	
	protected JSpinner getCurrentPageField()
	{
		if(mSpnCurrentPage == null)
			{
			mSpnCurrentPage = new JSpinner(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), ((Integer)(getPageSizeField().getSelectedItem())), Integer.valueOf(1)));
			
			mSpnCurrentPage.setBorder(BorderFactory.createLoweredBevelBorder());
			mSpnCurrentPage.setFont(getFont());
			
			getModel().addObserver(new ScrollablePageableCurrentPageObserver(mSpnCurrentPage));
			}
		
		return(mSpnCurrentPage);
	}
	
	protected Action getFirstPageAction()
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
	}
		
	protected Action getLastPageAction()
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
	}
	
	protected ScrollablePageableModel getModel()
	{
		if(mObjModel == null)
			mObjModel = new ScrollablePageableModel();
		
		return(mObjModel);
	}
	
	protected Action getNextPageAction()
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
	
	protected JPanel getPagingPanel()
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
	}
		
	protected JScrollPane getScrollPanel()
	{
		JScrollPane pnlScroll = new JScrollPane(getTable(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		return(pnlScroll);
	}
	
	protected JTable getTable()
	{
		if(mTblTable == null)
			{
			mTblTable = new JTable(getTableModel());
			mTblTable.addMouseListener(new ScrollablePageableTableMouseListener((Window)(getTopLevelAncestor()), getModel()));
			mTblTable.setColumnSelectionAllowed(false);
			mTblTable.setRowSelectionAllowed(true);
			mTblTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			}
		
		return(mTblTable);
	}
	
	protected TableModel getTableModel()
	{
		return(mObjTableModel);
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
			mTxtTotalPages.setText("0");
			}
		
		return(mTxtTotalPages);
	}
	
	protected void initPanel()
	{
		add(getScrollPanel(), BorderLayout.CENTER);
		add(getPagingPanel(), BorderLayout.SOUTH);
	}
	
	public void setTableModel(final TableModel objTableModel)
	{
		if(objTableModel != null)
			mObjTableModel = objTableModel;
		
		if(mTblTable != null)
			mTblTable.setModel(mObjTableModel);
	}
	
	protected class ScrollablePageableModel extends Observable implements Serializable
	{
		private static final long serialVersionUID = -4689051671448612855L;
		
		private Class<? extends JDialog> mClsDetailDialog;
		private Class<?> mClsIdType, mClsRowDataType;
		private Integer miCurrentPage, miPageSize;
		private Object mObjListModel, mObjRowModel, mObjManager;
		private String msGetDataByIdName, msGetDataListName;
		
		public ScrollablePageableModel()
		{ }
		
		protected Object convertIdToExpectedIdType(final Object objId) throws IllegalAccessException, IllegalArgumentException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException
		{
			if(!getIdType().equals(objId.getClass()))
				try
					{
					Constructor<?> objConstructor = getIdType().getDeclaredConstructor(new Class<?>[] {objId.getClass()});
					
					if(!objConstructor.isAccessible())
						objConstructor.setAccessible(true);
					
					return(objConstructor.newInstance(objId));
					}
				catch(NoSuchMethodException objException)
					{
					objException.printStackTrace(System.err);
					
					return(objId);
					}
			else
				return(objId);
		}
		
		public Integer getCurrentPage()
		{
			if(miCurrentPage == null)
				miCurrentPage = 0;
			
			return(miCurrentPage);
		}
		
		public Class<? extends JDialog> getDetailDialogClass()
		{
			return(mClsDetailDialog);
		}
		
		public String getGetDataByIdName()
		{
			if(msGetDataByIdName == null)
				msGetDataByIdName = "";
			
			return(msGetDataByIdName);
		}
		
		public String getGetDataListName()
		{
			if(msGetDataListName == null)
				msGetDataListName = "";
			
			return(msGetDataListName);
		}
		
		public Class<?> getIdType()
		{
			if(mClsIdType == null)
				mClsIdType = Integer.class;
			
			return(mClsIdType);
		}
		
		public List<?> getList() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
		{
			Method funcGetList = getListModel().getClass().getDeclaredMethod(getGetDataListName(), new Class<?>[0]);
			
			if(!funcGetList.isAccessible())
				funcGetList.setAccessible(true);
			
			return((List<?>)(funcGetList.invoke(getListModel(), new Object[0])));
		}
		
		public Object getListModel()
		{
			return(mObjListModel);
		}
		
		public Object getManager()
		{
			return(mObjManager);
		}
		
		public Integer getPageQuantity() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
		{
			return(Double.valueOf(Math.ceil(getList().size() / getPageSize())).intValue());
		}
		
		public Integer getPageSize()
		{
			if(miPageSize == null)
				miPageSize = 0;
			
			return(miPageSize);
		}
		
		public Object getRow(final Object objId) throws IllegalAccessException, IllegalArgumentException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException
		{
			Method funcGetRowById = getRowModel().getClass().getDeclaredMethod(getGetDataByIdName(), new Class<?>[] {getIdType()});
			
			if(!funcGetRowById.isAccessible())
				funcGetRowById.setAccessible(true);
			
			return(funcGetRowById.invoke(getRowModel(), new Object[] {convertIdToExpectedIdType(objId)}));
		}
		
		public Class<?> getRowDataType()
		{
			return(mClsRowDataType);
		}
		
		public Object getRowModel()
		{
			return(mObjRowModel);
		}
		
		public void setCurrentPage(final Integer iCurrentPage)
		{
			if(iCurrentPage == null)
				miCurrentPage = 0;
			else
				miCurrentPage = iCurrentPage;
			
			setChanged();
			notifyObservers();
		}
		
		public void setDetailDialogClass(final Class<? extends JDialog> clsDetailDialog)
		{
			mClsDetailDialog = clsDetailDialog;
			
			setChanged();
			notifyObservers();
		}
		
		public void setGetDataByIdName(final String sGetDataByIdName)
		{
			if(sGetDataByIdName == null)
				msGetDataByIdName = "";
			else
				msGetDataByIdName = sGetDataByIdName;
			
			setChanged();
			notifyObservers();
		}
		
		public void setGetDataListName(final String sGetDataListName)
		{
			if(sGetDataListName == null)
				msGetDataListName = "";
			else
				msGetDataListName = sGetDataListName;
			
			setChanged();
			notifyObservers();
		}
		
		public void setIdType(final Class<?> clsIdType)
		{
			mClsIdType = clsIdType;
			
			setChanged();
			notifyObservers();
		}
		
		public void setListModel(final Object objListModel)
		{
			mObjListModel = objListModel;
			
			setChanged();
			notifyObservers();
		}
		
		public void setManager(final Object objManager)
		{
			mObjManager = objManager;
			
			setChanged();
			notifyObservers();
		}
		
		public void setPageSize(final Integer iPageSize)
		{
			if(iPageSize == null)
				miPageSize = 0;
			else
				miPageSize = iPageSize;
			
			setChanged();
			notifyObservers();
		}
		
		public void setRowDataType(final Class<?> clsRowDataType)
		{
			mClsRowDataType = clsRowDataType;
			
			setChanged();
			notifyObservers();
		}
		
		public void setRowModel(final Object objRowModel)
		{
			mObjRowModel = objRowModel;
			
			setChanged();
			notifyObservers();
		}
	}
	
	protected class ScrollablePageableCurrentPageObserver implements Observer, Serializable
	{
		private static final long serialVersionUID = 7281127808347050692L;
		
		private JSpinner mSpnCurrentPage;
		
		public ScrollablePageableCurrentPageObserver(final JSpinner spnCurrentPage)
		{
			setCurrentPageSpinner(spnCurrentPage);
		}
		
		protected JSpinner getCurrentPageSpinner()
		{
			return(mSpnCurrentPage);
		}
		
		protected void setCurrentPageSpinner(final JSpinner spnCurrentPage)
		{
			mSpnCurrentPage = spnCurrentPage;
		}
		
		public void update(final Observable objObservable, final Object objArgument)
		{
			Integer iValue;
			ScrollablePageableModel objModel = ((ScrollablePageableModel)(objObservable));
			
			iValue = objModel.getCurrentPage();
			
			if(!getCurrentPageSpinner().getValue().equals(iValue))
				getCurrentPageSpinner().setValue(iValue);
		}
	}
	
	protected class ScrollablePageablePageSizeObserver implements Observer, Serializable
	{
		private static final long serialVersionUID = -3201722438345617743L;
		
		private JComboBox<Integer> mCboPageSize;
		private JTextComponent mTxtMaxPage;
		
		public ScrollablePageablePageSizeObserver(final JTextComponent txtMaxPage, final JComboBox<Integer> cboPageSize)
		{
			setMaxPageField(txtMaxPage);
			setPageSizeField(cboPageSize);
		}
		
		protected JTextComponent getMaxPageField()
		{
			return(mTxtMaxPage);
		}
		
		protected JComboBox<Integer> getPageSizeField()
		{
			return(mCboPageSize);
		}
		
		protected void setMaxPageField(final JTextComponent txtMaxPage)
		{
			mTxtMaxPage = txtMaxPage;
		}
		
		protected void setPageSizeField(final JComboBox<Integer> cboPageSize)
		{
			mCboPageSize = cboPageSize;
		}
		
		public void update(final Observable objObservable, final Object objArgument)
		{
			Integer iPageSize;
			ScrollablePageableModel objModel = ((ScrollablePageableModel)(objObservable));
			
			iPageSize = objModel.getPageSize();
			
			if(!getPageSizeField().getSelectedItem().equals(iPageSize))
				try
					{
					getPageSizeField().setSelectedItem(iPageSize);
					
					getMaxPageField().setText(objModel.getPageQuantity().toString());
					}
				catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
					{
					objException.printStackTrace(System.err);
					}
		}
	}
	
	protected class ScrollablePageableTableMouseListener extends MouseAdapter implements Serializable
	{
		private static final long serialVersionUID = -6595399234864558477L;
		
		private ScrollablePageableModel mObjModel;
		private Window mWndParent;
		
		public ScrollablePageableTableMouseListener(final Window wndParent, final ScrollablePageableModel objModel)
		{
			setParent(wndParent);
			setModel(objModel);
		}
		
		protected ScrollablePageableModel getModel()
		{
			return(mObjModel);
		}
		
		protected Window getParent()
		{
			return(mWndParent);
		}
		
		public void mousePressed(final MouseEvent objMouseEvent)
		{
			Object objRow = null;
			int iRow = -1;
			JTable tbl = ((JTable)(objMouseEvent.getComponent()));
			Point pt = objMouseEvent.getPoint();
			
			iRow = tbl.rowAtPoint(pt);
			
			if((iRow >= 0) && (objMouseEvent.getClickCount() == 2))
				try
					{
					int iId = ((int)(tbl.getValueAt(iRow, 0)));
					
					objRow = getModel().getRow(iId);
					}
				catch(IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException objException)
					{
					objRow = null;
					}
			
			if(objRow != null)
				try
					{
					Constructor<? extends JDialog> dlgConstructor;
					JDialog dlg;
					
					dlgConstructor = getModel().getDetailDialogClass().getDeclaredConstructor(new Class<?>[] {Window.class, objRow.getClass(), getModel().getManager().getClass()});
					
					if(!dlgConstructor.isAccessible())
						dlgConstructor.setAccessible(true);
					
					dlg = ((JDialog)(dlgConstructor.newInstance(new Object[] {getParent(), objRow, getModel().getManager()})));
					
					dlg.setVisible(true);
					}
				catch(IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException objException)
					{
					objException.printStackTrace(System.err);
					}
		}
		
		protected void setModel(final ScrollablePageableModel objModel)
		{
			mObjModel = objModel;
		}
		
		protected void setParent(final Window wndParent)
		{
			mWndParent = wndParent;
		}
	}
}