package net.draconia.jobsemailcollector.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class FilterList implements Serializable
{
	private static final long serialVersionUID = -307243632286766790L;
	
	private List<Filter> mLstFilters;
	
	public FilterList()
	{ }
	
	public boolean addFilter(final Filter objFilter)
	{
		Boolean bReturnValue = getFiltersInternal().add(objFilter);
		
		return(bReturnValue);
	}
	
	public List<Filter> getFilters()
	{
		return(Collections.unmodifiableList(getFiltersInternal()));
	}
	
	protected List<Filter> getFiltersInternal()
	{
		if(mLstFilters == null)
			mLstFilters = Collections.synchronizedList(new ArrayList<Filter>());
		
		return(mLstFilters);
	}
	
	public boolean removeFilter(final Filter objFilter)
	{
		Boolean bReturnValue = getFiltersInternal().remove(objFilter);
		
		return(bReturnValue);
	}
	
	public String toString()
	{
		String sFilters = "where ";
		
		sFilters = sFilters.concat(StringUtils.join(getFilters().toArray(new Filter[0]), " and "));
		
		return(sFilters.substring(0, sFilters.length() - 1));
	}
}