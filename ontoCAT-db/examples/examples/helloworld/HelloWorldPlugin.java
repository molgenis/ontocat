package plugins.examples.helloworld;

import org.molgenis.framework.db.Database;
import org.molgenis.framework.ui.PluginModel;
import org.molgenis.framework.ui.ScreenModel;
import org.molgenis.util.Tuple; 

public class HelloWorldPlugin extends PluginModel
{
	private static final long serialVersionUID = 5944394444180239960L;
	private HelloWorldModel screenModel = new HelloWorldModel();

	public HelloWorldPlugin(String name, ScreenModel parent)
	{
		super(name, parent);
	}

	@Override
	public String getViewName()
	{
		return "plugin_examples_helloworld_HelloWorldPlugin";
	}

	@Override
	public String getViewTemplate()
	{
		// TODO Auto-generated method stub
		return "plugins/examples/helloworld/HelloWorldPlugin.ftl";
	}

	@Override
	public void handleRequest(Database db, Tuple request)
	{
		this.logger.debug("handeling request in the plugin");

		String action = request.getString(INPUT_ACTION);
		if ("do_update_name".equals(action))
		{
			this.logger.debug("handeling update_name request");
			getModel().setName(request.getString("new_name"));
		}
	}

	@Override
	public void reload(Database db)
	{
		//nothing to do here, see ExperimentPlugin for example on database interaction.
	}

	/**
	 * Although not required, it is good practice to put the 'state' of the
	 * application in separate object. This separation between controller (in
	 * 'this') and the model (in the 'model') makes the code easier to
	 * understand. The third component of this M(odel)V(iew)C(ontroller) is in
	 * the template that holds the 'view', that is the layout of the model.
	 */
	public HelloWorldModel getModel()
	{
		return this.screenModel;
	}

}
