package net.draconia.jobsemailcollector.ui.table.model;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import javax.swing.JDialog;

public class ScrollablePageableModel extends Observable implements Serializable
{
	private static final long serialVersionUID = -4689051671448612855L;
	
	private Class<? extends JDialog> mClsDetailDialog;
	private Class<?> mClsIdType, mClsRowDataType;
	private List<Column> mLstColumns;
	private Integer miCurrentPage, miPageSize;
	private List<Integer> mLstPageSizes;
	private Object mObjListModel, mObjRowModel, mObjManager;
	private String msGetDataByIdName, msGetDataListName;
	
	public ScrollablePageableModel()
	{ }
	
	public boolean addColumn(final String sName, final Class<?> clsType, final Class<?> clsRowType, final String sGetterName)
	{
		boolean bReturnValue = getColumnsInternal().add(new Column(sName, clsType, clsRowType, sGetterName));
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public boolean addColumn(final String sName, final Class<?> clsType, final Class<?> clsRowType, final String sGetterName, final String sSetterName)
	{
		boolean bReturnValue = getColumnsInternal().add(new Column(sName, clsType, clsRowType, sGetterName, sSetterName));
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public boolean addColumn(final String sName, final Class<?> clsType, final Class<?> clsRowType, final String sGetterName, final String sSetterName, final Boolean bEditable)
	{
		boolean bReturnValue = getColumnsInternal().add(new Column(sName, clsType, clsRowType, sGetterName, sSetterName, bEditable));
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public boolean addColumn(final Column objColumn)
	{
		boolean bReturnValue = getColumnsInternal().add(objColumn);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public boolean addColumns(final Collection<Column> collColumns)
	{
		boolean bReturnValue = getColumnsInternal().addAll(collColumns);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public boolean addPageSize(final Integer iPageSize)
	{
		boolean bReturnValue = getPageSizesInternal().add(iPageSize);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
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
	
	public void firstPage()
	{
		int iListSize;
		
		try
			{
			iListSize = getListSize();
			}
		catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
			{
			objException.printStackTrace(System.err);
			
			iListSize = 0;
			}
		
		if(iListSize > 0)
			setCurrentPage(1);
		else
			setCurrentPage(0);
	}
	
	public List<Column> getColumns()
	{
		return(Collections.unmodifiableList(getColumnsInternal()));
	}
	
	protected List<Column> getColumnsInternal()
	{
		if(mLstColumns == null)
			mLstColumns = Collections.synchronizedList(new ArrayList<Column>());
		
		return(mLstColumns);
	}
	
	public Integer getCurrentPage()
	{
		if(miCurrentPage == null)
			try
				{
				if(getListSize() > 0)
					miCurrentPage = 1;
				else
					miCurrentPage = 0;
				}
			catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
				{
				objException.printStackTrace(System.err);
				
				miCurrentPage = 0;
				}
		
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
	
	public Object getList() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		Method funcGetList = getListModel().getClass().getDeclaredMethod(getGetDataListName(), new Class<?>[0]);
		
		if(!funcGetList.isAccessible())
			funcGetList.setAccessible(true);
		
		return(funcGetList.invoke(getListModel(), new Object[0]));
	}
	
	public Object getListModel()
	{
		return(mObjListModel);
	}
	
	public int getListSize() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		int iSize = 0;
		Method funcSize;
		Object objList = getList();
		
		funcSize = objList.getClass().getDeclaredMethod("size", new Class<?>[0]);
		
		if(!funcSize.isAccessible())
			funcSize.setAccessible(true);
		
		iSize = ((int)(funcSize.invoke(objList, new Object[0])));
		
		return(iSize);
	}
	
	public Object getManager()
	{
		return(mObjManager);
	}
	
	public Integer getPageQuantity() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		int iSize = getListSize();
		
		if(getPageSize() > 0)
			return(Double.valueOf(Math.ceil(iSize / Double.valueOf(getPageSize()))).intValue());
		else
			return(1);
	}
	
	public Integer getPageSize()
	{
		if(miPageSize == null)
			if(getPageSizes().size() > 0)
				miPageSize = getPageSizes().get(0);
			else
				miPageSize = 0;
		
		return(miPageSize);
	}
	
	public List<Integer> getPageSizes()
	{
		return(Collections.unmodifiableList(getPageSizesInternal()));
	}
	
	protected List<Integer> getPageSizesInternal()
	{
		if(mLstPageSizes == null)
			{
			mLstPageSizes = Collections.synchronizedList(new ArrayList<Integer>());
			
			mLstPageSizes.addAll(Arrays.asList(new Integer[] {5, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 150, 200, 250, 300, 350, 400, 500, 600, 700, 800, 900, 1000}));
			}
		
		return(mLstPageSizes);
	}
	
	public Object getRow(final Integer iRowIndex) throws IllegalAccessException, IllegalArgumentException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		Method funcGet = getList().getClass().getDeclaredMethod("get", new Class<?>[] {Integer.class});
		
		if(!funcGet.isAccessible())
			funcGet.setAccessible(true);
		
		return(funcGet.invoke(getListModel(), new Object[] {iRowIndex}));
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
	
	public void lastPage()
	{
		int iPageQuantity;
		
		try
			{
			iPageQuantity = getPageQuantity();
			}
		catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
			{
			iPageQuantity = 0;
			}
		
		if(iPageQuantity > 0)
			setCurrentPage(iPageQuantity);
		else
			setCurrentPage(0);
	}
	
	public void nextPage()
	{
		int iCurrentPage, iPageQuantity;
		
		try
			{
			iPageQuantity = getPageQuantity();
			}
		catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
			{
			iPageQuantity = 0;
			}
		
		iCurrentPage = getCurrentPage();
		
		if(iPageQuantity > 0)
			if(iCurrentPage < iPageQuantity)
				setCurrentPage(iCurrentPage + 1);
			else
				setCurrentPage(iPageQuantity);
		else
			setCurrentPage(0);
	}
	
	public void previousPage()
	{
		int iCurrentPage, iPageQuantity;
		
		try
			{
			iPageQuantity = getPageQuantity();
			}
		catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
			{
			iPageQuantity = 0;
			}
		
		iCurrentPage = getCurrentPage();
		
		if(iPageQuantity > 0)
			if(iCurrentPage > 1)
				setCurrentPage(iCurrentPage - 1);
			else
				setCurrentPage(1);
		else
			setCurrentPage(0);
	}
	
	public boolean removeColumn(final Integer iColumnIndex)
	{
		boolean bReturnValue = getColumnsInternal().remove(iColumnIndex);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public boolean removeColumn(final Column objColumn)
	{
		boolean bReturnValue = getColumnsInternal().remove(objColumn);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public boolean removePageSize(final Integer iPageSize)
	{
		boolean bReturnValue = getPageSizesInternal().remove((Object)(iPageSize));
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
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
	
	/**
	 * Method to provide a way to update this model when the data changes 
	 * even though I'm accumulating the model's data through getList()
	 * 
	 * @param objList {@link Object} Could be List or Map - if I stabilize it I may specialize the type
	 */
	public void setList(final Object objList)
	{
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
			miPageSize = getPageSizes().get(0);
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