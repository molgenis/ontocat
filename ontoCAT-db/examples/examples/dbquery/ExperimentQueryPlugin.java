package plugins.examples.dbquery;

import java.util.ArrayList;
import java.util.List;

import org.molgenis.framework.db.Database;
import org.molgenis.framework.db.Query;
import org.molgenis.framework.ui.PluginModel;
import org.molgenis.framework.ui.ScreenModel;
import org.molgenis.util.Tuple;

import example.Experiment;

public class ExperimentQueryPlugin extends PluginModel 
{
	private ExperimentQueryModel screenModel = new ExperimentQueryModel();
	
	public ExperimentQueryPlugin(String name, ScreenModel parent)
	{
		super(name, parent);
	}

	public ExperimentQueryModel getModel()
	{
		return screenModel;
	}

	@Override
	public String getViewName()
	{
		return "ExperimentQuery";
	}

	@Override
	public String getViewTemplate()
	{
		return "plugins/examples/dbquery/ExperimentQueryPlugin.ftl";
	}

	@Override
	public void handleRequest(Database db, Tuple request)
	{
		logger.debug("handeling request in the ExperimentQueryPlugin");

		String action = request.getString(INPUT_ACTION);
		if ("do_update_search".equals(action))
		{
			logger.debug("handeling do_update_search request");
			if(request.getObject("search_terms") != null)
			{
				//search terms separated by " "
				String[] filter_array = request.getString("search_terms").split(" ");
				List<String> filters =  new ArrayList<String>();
				for(String filter: filter_array)
				{
					//remove spaces using trim
					filters.add(filter.trim());
				}
				getModel().setFilters(filters);
			}
			else
			{
				//empty string
				getModel().setFilters(new ArrayList<String>());
			}
		}
		
	}

	@Override
	public void reload(Database db)
	{
		logger.debug("reloading the ExperimentQueryPlugin");
		
		//refresh query results, using filters if available
		try
		{						
			Query<Experiment> q = db.query(Experiment.class);
			
			
			for(String filter: this.getModel().getFilters())
			{
				q.like("Name",filter);
			}
			List<Experiment> experiments = q.find();
			
			this.getModel().setExperiments(experiments);
		}
		catch (Exception e)
		{
			getModel().setErrorMessage(e.getMessage());
		}		
	}

}
