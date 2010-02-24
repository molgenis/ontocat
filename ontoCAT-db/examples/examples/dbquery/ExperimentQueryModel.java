package plugins.examples.dbquery;

import java.util.ArrayList;
import java.util.List;

import example.Experiment;

public class ExperimentQueryModel 
{
	List<Experiment> experiments = new ArrayList<Experiment>();
	String errorMessage = "";
	List<String> filters = new ArrayList<String>();
	
	//helper function to list search terms
	public String getSearchTerms()
	{
		StringBuffer result = new StringBuffer();
		for(String filter: filters)
		{
			result.append(filter + " ");
		}
		return result.toString();
	}

	public List<String> getFilters()
	{
		return filters;
	}

	public void setFilters(List<String> filters)
	{
		this.filters = filters;
	}

	public List<Experiment> getExperiments()
	{
		return experiments;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public void setExperiments(List<Experiment> experiments)
	{
		this.experiments = experiments;
	}
}
