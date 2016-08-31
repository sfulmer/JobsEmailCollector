package net.draconia.jobsemailcollector.ui;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.UIManager;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class SendResumeAttachmentTableHeaderRenderer extends JCheckBox implements MouseListener, TableCellRenderer
{
	private static final long serialVersionUID = -10747578758594707L;
	
	private Boolean mbMousePressed;
	private int miColumn;
	
	public SendResumeAttachmentTableHeaderRenderer()
	{ }
	
	protected int getColumn()
	{
		return(miColumn);
	}
	
	public Component getTableCellRendererComponent(final JTable tbl, final Object objValue, final boolean bIsSelected, final boolean bHasFocus, final int iRow, final int iColumn)
	{
		if(tbl != null)
			{
			JTableHeader objTableHeader = tbl.getTableHeader();
			
			if(objTableHeader != null)
				{
				setBackground(objTableHeader.getBackground());
				setForeground(objTableHeader.getForeground());
				setFont(objTableHeader.getFont());
				setHorizontalAlignment(JCheckBox.CENTER);
				objTableHeader.addMouseListener(this);
				}
			
			addItemListener(new TableHeaderItemListener(tbl));
			}
		
		setColumn(iColumn);
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		
		return(this);
	}
	
	protected void handleClickEvent(final MouseEvent objMouseEvent)
	{
		if(isMousePressed())
			{
			JTable tblView;
			JTableHeader objTableHeader;
			int iColumn, iViewColumn;
			TableColumnModel objTableColumnModel;
			
			setMousePressed(false);
			
			objTableHeader = ((JTableHeader)(objMouseEvent.getSource()));
			tblView = objTableHeader.getTable();
			objTableColumnModel = tblView.getColumnModel();
			iViewColumn = objTableColumnModel.getColumnIndexAtX(objMouseEvent.getX());
			iColumn = tblView.convertColumnIndexToModel(iViewColumn);
			
			if((iViewColumn == getColumn()) && (objMouseEvent.getClickCount() == 1) && (iColumn != -1))
				doClick();
			}
	}
	
	protected boolean isMousePressed()
	{
		if(mbMousePressed == null)
			mbMousePressed = false;
		
		return(mbMousePressed);
	}
	
	public void mouseClicked(final MouseEvent objMouseEvent)
	{
		handleClickEvent(objMouseEvent);
		
		((JTableHeader)(objMouseEvent.getSource())).repaint();
	}
	
	public void mouseEntered(final MouseEvent objMouseEvent)
	{ }
	
	public void mouseExited(final MouseEvent objMouseEvent)
	{ }
	
	public void mousePressed(final MouseEvent objMouseEvent)
	{
		setMousePressed(true);
	}
	
	public void mouseReleased(final MouseEvent objMouseEvent)
	{ }
	
	protected void setColumn(final int iIndex)
	{
		miColumn = iIndex;
	}
	
	protected void setMousePressed(final Boolean bMousePressed)
	{
		if(bMousePressed == null)
			mbMousePressed = false;
		else
			mbMousePressed = bMousePressed;
	}
	
	protected class TableHeaderItemListener implements ItemListener, Serializable
	{
		private static final long serialVersionUID = 6173173216193242253L;
		
		private JTable mTblAttached;
		
		public TableHeaderItemListener(final JTable tblAttached)
		{
			setAttachedTable(tblAttached);
		}
		
		protected JTable getAttachedTable()
		{
			return(mTblAttached);
		}
		
		public void itemStateChanged(final ItemEvent objItemEvent)
		{
			Object objSource = objItemEvent.getSource();
			
			if(objSource instanceof AbstractButton)
				{
				boolean bChecked = objItemEvent.getStateChange() == ItemEvent.SELECTED;
				
				for(int iX = 0, iY = getAttachedTable().getRowCount(); iX < iY; iX++)
					getAttachedTable().setValueAt(new Boolean(bChecked), iX, 1);
				}
			
		}
		
		protected void setAttachedTable(final JTable tblAttached)
		{
			mTblAttached = tblAttached;
		}
	}
}