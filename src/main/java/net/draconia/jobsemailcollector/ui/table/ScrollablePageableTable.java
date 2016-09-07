package net.draconia.jobsemailcollector.ui.table;

import java.awt.BorderLayout;
import java.awt.Window;

import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import javax.swing.table.TableModel;

import net.draconia.jobsemailcollector.ui.InitializablePanel;

import net.draconia.jobsemailcollector.ui.listeners.InitializablePanelAncestorListener;

import net.draconia.jobsemailcollector.ui.table.listeners.ScrollablePageableTableMouseListener;

import net.draconia.jobsemailcollector.ui.table.model.Column;
import net.draconia.jobsemailcollector.ui.table.model.ScrollablePageableModel;
import net.draconia.jobsemailcollector.ui.table.model.ScrollablePageableableTableModel;

public class ScrollablePageableTable extends InitializablePanel
{
	private static final long serialVersionUID = 2423017759548313902L;
	
	private JTable mTblTable;
	private ScrollablePageableModel mObjModel;
	private TableModel mObjTableModel;
	
	public ScrollablePageableTable(final Column[] objArrColumns)
	{
		super(new BorderLayout(0, 5));
		
		addAncestorListener(new InitializablePanelAncestorListener(this));
		
		getModel().addColumns(Arrays.asList(objArrColumns));
	}
	
	public ScrollablePageableTable(final TableModel objTableModel)
	{
		super(new BorderLayout(0, 5));
		
		addAncestorListener(new InitializablePanelAncestorListener(this));
		
		setTableModel(objTableModel);
	}
	
	public ScrollablePageableModel getModel()
	{
		if(mObjModel == null)
			mObjModel = new ScrollablePageableModel();
		
		return(mObjModel);
	}
	
	protected JPanel getPagingPanel()
	{
		return(new PagingPanel(getModel()));
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
		if(mObjTableModel == null)
			mObjTableModel = new ScrollablePageableableTableModel(getModel());
		
		return(mObjTableModel);
	}
	
	protected void initPanel()
	{
		add(getScrollPanel(), BorderLayout.CENTER);
		
		if(getModel().isPageable())
			add(getPagingPanel(), BorderLayout.SOUTH);
	}
	
	public void setTableModel(final TableModel objTableModel)
	{
		if(objTableModel != null)
			mObjTableModel = objTableModel;
		
		if(mTblTable != null)
			mTblTable.setModel(mObjTableModel);
	}
}