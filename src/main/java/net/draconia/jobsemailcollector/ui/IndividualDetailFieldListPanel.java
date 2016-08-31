package net.draconia.jobsemailcollector.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import javax.swing.border.TitledBorder;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import net.draconia.jobsemailcollector.model.Individual;

import net.draconia.jobsemailcollector.ui.actions.IndividualDetailFieldListAdd;
import net.draconia.jobsemailcollector.ui.actions.IndividualDetailFieldListEdit;
import net.draconia.jobsemailcollector.ui.actions.IndividualDetailFieldListRemove;

import net.draconia.jobsemailcollector.ui.listeners.IndividualDetailFieldListSelectionListener;

import net.draconia.jobsemailcollector.ui.model.IndividualDetailListModel;

import net.draconia.jobsemailcollector.ui.utilities.MouseUtilities;

public class IndividualDetailFieldListPanel extends JPanel
{
	private static final long serialVersionUID = -4629729844367369226L;
	
	private Action mActAdd, mActEdit, mActRemove;
	private Individual mObjModel;
	private JList<String> mLstValues;
	private JPopupMenu mMnuList;
	private String msField, msTitle;
		
	public IndividualDetailFieldListPanel(final Individual objModel, final String sField, final String sTitle)
	{
		super();
		
		setFont(new Font("Tahana", Font.PLAIN, 10));
		
		setField(sField);
		setModel(objModel);
		setTitle(sTitle);
		
		addAncestorListener(new AncestorListener()
		{
			public void ancestorRemoved(final AncestorEvent objAncestorEvent)
			{ }
			
			public void ancestorMoved(final AncestorEvent objAncestorEvent)
			{ }
			
			public void ancestorAdded(final AncestorEvent objAncestorEvent)
			{
				initPanel();
			}
		});
		//initPanel();
	}
	
	private Action getAddAction()
	{
		if(mActAdd == null)
			mActAdd = new IndividualDetailFieldListAdd(getOwnerWindow(), getModel(), getField());
		
		return(mActAdd);
	}
	
	private JButton getAddButton()
	{
		JButton btnAdd = new JButton(getAddAction());
		
		btnAdd.setFont(getFont().deriveFont(Font.BOLD));
		
		return(btnAdd);
	}
	
	private JPanel getButtonPanel()
	{
		GridBagConstraints objConstraints = new GridBagConstraints();
		JPanel pnlButtons = new JPanel(new GridBagLayout());
		
		objConstraints.anchor = GridBagConstraints.CENTER;
		objConstraints.fill = GridBagConstraints.HORIZONTAL;
		objConstraints.gridx = objConstraints.gridy = 0;
		objConstraints.gridwidth = objConstraints.gridheight = 1;
		objConstraints.insets = new Insets(5, 5, 5, 5);
		objConstraints.weightx = objConstraints.weighty = 1;
		pnlButtons.add(getAddButton(), objConstraints);
		
		objConstraints.gridy++;
		pnlButtons.add(getEditButton(), objConstraints);
		
		objConstraints.gridy++;
		pnlButtons.add(getRemoveButton(), objConstraints);
		
		return(pnlButtons);
	}
	
	private Action getEditAction()
	{
		if(mActEdit == null)
			{
			mActEdit = new IndividualDetailFieldListEdit(getOwnerWindow(), getModel(), getField(), getFieldList());
			
			mActEdit.setEnabled(false);
			}
		
		return(mActEdit);
	}
	
	private JButton getEditButton()
	{
		JButton btnEdit = new JButton(getEditAction());
		
		btnEdit.setFont(getFont().deriveFont(Font.BOLD));
		
		return(btnEdit);
	}
	
	protected String getField()
	{
		return(msField);
	}
	
	protected JList<String> getFieldList()
	{
		if(mLstValues == null)
			{
			mLstValues = new JList<String>(new IndividualDetailListModel(getModel(), getField()));
			
			mLstValues.setBorder(BorderFactory.createLoweredBevelBorder());
			mLstValues.setFont(getFont());
			mLstValues.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			
			mLstValues.addListSelectionListener(new IndividualDetailFieldListSelectionListener(getEditAction(), getRemoveAction()));
			mLstValues.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(final MouseEvent objMouseEvent)
				{
					if(objMouseEvent.getClickCount() == 2)
						getEditButton().doClick();
				}
				
				@SuppressWarnings("unchecked")
				public void mousePressed(MouseEvent objMouseEvent)
				{
					if(SwingUtilities.isRightMouseButton(objMouseEvent))
						{
						int iRow;
						JList<String> lstValues = ((JList<String>)(objMouseEvent.getSource()));
						
						iRow = lstValues.locationToIndex(objMouseEvent.getPoint());
						lstValues.setSelectedIndex(iRow);
						}
				}
			});
			new MouseUtilities().installPopupListener(mLstValues, getListPopupMenu());
			}
		
		return(mLstValues);
	}
	
	protected JPopupMenu getListPopupMenu()
	{
		if(mMnuList == null)
			{
			mMnuList = new JPopupMenu();
			
			mMnuList.add(getAddAction());
			mMnuList.add(getEditAction());
			mMnuList.add(getRemoveAction());
			}
		
		return(mMnuList);
	}
	
	protected Individual getModel()
	{
		return(mObjModel);
	}
	
	private Window getOwnerWindow()
	{
		return(SwingUtilities.getWindowAncestor(this));
	}
	
	private Action getRemoveAction()
	{
		if(mActRemove == null)
			{
			mActRemove = new IndividualDetailFieldListRemove(getOwnerWindow(), getModel(), getField(), getFieldList());
			
			mActRemove.setEnabled(false);
			}
		
		return(mActRemove);
	}
	
	private JButton getRemoveButton()
	{
		JButton btnRemove = new JButton(getRemoveAction());
		
		btnRemove.setFont(getFont().deriveFont(Font.BOLD));
		
		return(btnRemove);
	}
	
	private JScrollPane getScrollPane()
	{
		JScrollPane scr = new JScrollPane(getFieldList(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		scr.setBorder(BorderFactory.createLoweredBevelBorder());
		
		return(scr);
	}
	
	protected String getTitle()
	{
		return(msTitle);
	}
	
	protected void initPanel()
	{
		TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), getTitle() + ":", TitledBorder.LEADING, TitledBorder.TOP, getFont().deriveFont(Font.BOLD));
		
		setBorder(border);
		setLayout(new BorderLayout(0, 5));
		setOpaque(false);
		
		add(getScrollPane(), BorderLayout.CENTER);
		add(getButtonPanel(), BorderLayout.EAST);
	}
	
	protected void setField(final String sField)
	{
		msField = sField;
	}
	
	protected void setModel(final Individual objModel)
	{
		mObjModel = objModel;
	}
	
	protected void setTitle(final String sTitle)
	{
		msTitle = sTitle;
	}
}