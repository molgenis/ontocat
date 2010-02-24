package plugins.examples.portal;

import org.molgenis.framework.db.Database;
import org.molgenis.framework.ui.PluginModel;
import org.molgenis.framework.ui.ScreenModel;
import org.molgenis.util.Tuple;

public class PortalIntegrationPlugin extends PluginModel 
{
	//edit this to accomodate the desired iframe portal
	private String url = "http://www.google.com";

	private String width = "100%";
	private String height = "400px";
	
	private static final long serialVersionUID = 5944394444180239960L;

	public PortalIntegrationPlugin(String name, ScreenModel parent)
	{
		super(name, parent);
	}

	@Override
	public String getViewName()
	{
		//this will call the macro in the template, see below
		return "plugin_examples_portal_PortalIntegrationPlugin";
	}

	@Override
	public String getViewTemplate()
	{
		//you need to edit this if your plugin path changes...
		return "plugins/examples/portal/PortalIntegrationPlugin.ftl";
	}

	@Override
	public void handleRequest(Database db, Tuple request)
	{
		//nothing to do here. 
	}

	@Override
	public void reload(Database db)
	{
		//nothing to do here
		//could ask parent form for parameter, e.g.
		
		//this is the path to the parent form you want to link from:
		//FormScreen<Experiment> parentForm = ((FormScreen<Experiment>)this.get(height)
		
		//this is the visible record:
		//Experiment e = parentForm.getRecords().get(0);
		
		//create an url from it:
		//this.url = "http://pathtomyscript?parameter="+e.getId();
		
		this.url = "http://www.google.com";
		
	}
	
//BORING GETTERS BELOW	
	public String getUrl()
	{
		return url;
	}

	public String getWidth()
	{
		return width;
	}

	public String getHeight()
	{
		return height;
	}

	
}
