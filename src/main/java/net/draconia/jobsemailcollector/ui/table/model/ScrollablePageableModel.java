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
	
	private Boolean mbPageable;
	private Class<? extends JDialog> mClsDetailDialog;
	private Class<?> mClsCollectionSizeType, mClsIdType;
	private Class<?> mClsRowDataType, mClsRowKeyType;
	private Integer miCurrentPage, miPageRowCount, miPageSize;
	private List<Column> mLstColumns;
	private List<Integer> mLstPageSizes;
	private Object mObjCollection, mObjCollectionModel, mObjCollectionSizeModel;
	private Object mObjManager, mObjRowModel;
	private String msGetCollectionSizeName, msGetDataCollectionName, msGetRowByIndexName;
	
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
		Number nCollectionSize;
		
		try
			{
			nCollectionSize = ((Number)(getCollectionSize()));
			}
		catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
			{
			objException.printStackTrace(System.err);
			
			nCollectionSize = Integer.valueOf(0);
			}
		
		if(nCollectionSize.longValue() > 0)
			setCurrentPage(1);
		else
			setCurrentPage(0);
	}
	
	public Object getCollection() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		if(mObjCollection == null)
			{
			Method funcGetCollection = getCollectionModel().getClass().getDeclaredMethod(getGetDataCollectionName(), new Class<?>[0]);
			
			if(!funcGetCollection.isAccessible())
				funcGetCollection.setAccessible(true);
			
			mObjCollection = funcGetCollection.invoke(getCollectionModel(), new Object[0]);
			}
		
		return(mObjCollection);
	}
	
	public Object getCollectionModel()
	{
		return(mObjCollectionModel);
	}
	
	public Object getCollectionSize() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		Object objCollectionSizeModel = getCollectionSizeModel(), objSize;
		Method funcSize;
		
		funcSize = objCollectionSizeModel.getClass().getDeclaredMethod(getGetCollectionSizeName(), new Class<?>[0]);
		
		if(!funcSize.isAccessible())
			funcSize.setAccessible(true);
		
		objSize = funcSize.invoke(objCollectionSizeModel, new Object[0]);
		
		return(objSize);
	}
	
	public Object getCollectionSizeModel()
	{
		if(mObjCollectionSizeModel == null)
			mObjCollectionSizeModel = getManager();
		
		return(mObjCollectionSizeModel);
	}
	
	public Class<?> getCollectionSizeType()
	{
		if(mClsCollectionSizeType == null)
			mClsCollectionSizeType = Long.class;
		
		return(mClsCollectionSizeType);
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
				if(((Number)(getCollectionSize())).longValue() > 0)
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
	
	public String getGetCollectionSizeName()
	{
		if(msGetCollectionSizeName == null)
			msGetCollectionSizeName = "";
		
		return(msGetCollectionSizeName);
	}
	
	public String getGetDataCollectionName()
	{
		if(msGetDataCollectionName == null)
			msGetDataCollectionName = "";
		
		return(msGetDataCollectionName);
	}
	
	public String getGetRowByIndexName()
	{
		if(msGetRowByIndexName == null)
			msGetRowByIndexName = "";
		
		return(msGetRowByIndexName);
	}
	
	public Class<?> getIdType()
	{
		if(mClsIdType == null)
			mClsIdType = Integer.class;
		
		return(mClsIdType);
	}
	
	public Object getManager()
	{
		return(mObjManager);
	}
	
	public Integer getPageQuantity() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		long lSize = ((Number)(getCollectionSize())).longValue();
		
		if(getPageSize() > 0)
			return(Double.valueOf(Math.ceil(lSize / Double.valueOf(getPageSize()))).intValue());
		else
			return(1);
	}
	
	public int getPageRowCount()
	{
		return(getPageRowCount(getCurrentPage()));
	}
	
	public int getPageRowCount(final Integer iPage)
	{
		if(miPageRowCount == null)
			{
			int iCollectionSize;
			
			try
				{
				iCollectionSize = ((Number)(getCollectionSize())).intValue();
				}
			catch(IllegalAccessException  | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
				{
				objException.printStackTrace();
				
				iCollectionSize = 0;
				}
			
			if(iPage == null)
				return(iCollectionSize);
			else
				{
				int iPageSize = getPageSize(), iRecordCountCurrentPage = iPageSize * iPage;
				
				if(iPage > 1)
					{
					int iRecordCountPageMinusOne = (iPageSize * (iPage - 1));
					
					if(iRecordCountCurrentPage > iCollectionSize)
						miPageRowCount = (iCollectionSize - iRecordCountPageMinusOne);
					else
						miPageRowCount = (iRecordCountCurrentPage - iRecordCountPageMinusOne);
					}
				else if(iRecordCountCurrentPage > iCollectionSize)
					miPageRowCount = (iCollectionSize - iRecordCountCurrentPage);
				else
					miPageRowCount = iRecordCountCurrentPage;
				}
			}
		
		return(miPageRowCount);
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
		Method funcGet = getRowModel().getClass().getDeclaredMethod(getGetRowByIndexName(), new Class<?>[] {getRowKeyType()});
		
		if(!funcGet.isAccessible())
			funcGet.setAccessible(true);
		
		return(funcGet.invoke(getCollection(), new Object[] {iRowIndex}));
	}
	
	public Class<?> getRowDataType()
	{
		return(mClsRowDataType);
	}
	
	public Class<?> getRowKeyType()
	{
		return(mClsRowKeyType);
	}
	
	public Object getRowModel()
	{
		if(mObjRowModel == null)
			try
				{
				mObjRowModel = getCollection();
				}
			catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
				{
				objException.printStackTrace(System.err);
				}
		
		return(mObjRowModel);
	}
	
	public boolean isPageable()
	{
		if(mbPageable == null)
			mbPageable = false;
		
		return(mbPageable);
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
	
	protected void resetPageRowCount()
	{
		miPageRowCount = null;
	}
	
	/**
	 * Method to provide a way to update this model when the data changes 
	 * even though I'm accumulating the model's data through getCollection()
	 * 
	 * @param objCollection {@link Object} Could be List or Map - if I stabilize it I may specialize the type but I can't specialize it to Collection because Maps aren't Collections
	 */
	public void setCollection(final Object objCollection)
	{
		if(objCollection != null)
			mObjCollection = objCollection;
		
		setChanged();
		notifyObservers();
			
	}
	
	public void setCollectionModel(final Object objCollectionModel)
	{
		mObjCollectionModel = objCollectionModel;
		
		setChanged();
		notifyObservers();
	}
	
	public void setCollectionSizeModel(final Object objCollectionSizeModel)
	{
		if(objCollectionSizeModel == null)
			mObjCollectionSizeModel = getManager();
		else
			mObjCollectionSizeModel = objCollectionSizeModel;
		
		setChanged();
		notifyObservers();
	}
	
	public void setCollectionSizeType(final Class<?> clsCollectionSizeType)
	{
		if(clsCollectionSizeType == null)
			mClsCollectionSizeType = Long.class;
		else
			mClsCollectionSizeType = clsCollectionSizeType;
		
		setChanged();
		notifyObservers();
	}
	
	public void setCurrentPage(final Integer iCurrentPage)
	{
		if(iCurrentPage == null)
			miCurrentPage = 0;
		else
			miCurrentPage = iCurrentPage;
		
		resetPageRowCount();
		
		setChanged();
		notifyObservers();
	}
	
	public void setDetailDialogClass(final Class<? extends JDialog> clsDetailDialog)
	{
		mClsDetailDialog = clsDetailDialog;
		
		setChanged();
		notifyObservers();
	}
	
	public void setGetCollectionSizeName(final String sGetCollectionsSizeName)
	{
		if(sGetCollectionsSizeName == null)
			msGetCollectionSizeName = "getIndividualCount";
		else
			msGetCollectionSizeName = sGetCollectionsSizeName;
		
		setChanged();
		notifyObservers();
	}
	
	public void setGetDataCollectionName(final String sGetDataCollectionName)
	{
		if(sGetDataCollectionName == null)
			msGetDataCollectionName = "getTableData";
		else
			msGetDataCollectionName = sGetDataCollectionName;
		
		setChanged();
		notifyObservers();
	}
	
	public void setGetRowByIndexName(final String sGetRowByIndexName)
	{
		if(sGetRowByIndexName == null)
			msGetRowByIndexName = "get";
		else
			msGetRowByIndexName = sGetRowByIndexName;
		
		setChanged();
		notifyObservers();
	}
	
	public void setIdType(final Class<?> clsIdType)
	{
		mClsIdType = clsIdType;
		
		setChanged();
		notifyObservers();
	}
	
	public void setManager(final Object objManager)
	{
		mObjManager = objManager;
		
		setChanged();
		notifyObservers();
	}
	
	public void setPageable(final Boolean bPageable)
	{
		if(bPageable == null)
			mbPageable = false;
		else
			mbPageable = true;
		
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
	
	public void setRowKeyType(final Class<?> clsRowKeyType)
	{
		mClsRowKeyType = clsRowKeyType;
		
		setChanged();
		notifyObservers();
	}
	
	public void setRowModel(final Object objRowModel)
	{
		if(objRowModel == null)
			try
				{
				mObjRowModel = getCollection();
				}
			catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
				{
				objException.printStackTrace(System.err);
				}
		else
			mObjRowModel = objRowModel;
		
		setChanged();
		notifyObservers();
	}
}