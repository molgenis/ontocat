/* Date:        February 12, 2009
 * Template:	PluginScreenJavaTemplateGen.java.ftl
 * generator:   org.molgenis.generate.screen.PluginScreenJavaTemplateGen 3.2.0-testing
 * 
 * THIS FILE IS A TEMPLATE. PLEASE EDIT :-)
 */

package plugins.examples.switchmacro;

import org.molgenis.framework.db.Database;
import org.molgenis.framework.ui.PluginModel;
import org.molgenis.framework.ui.ScreenModel;
import org.molgenis.util.Tuple;
 
/**
 * This plugin shows how you can use the "getViewName" function to use different
 * layout macros.
 */
public class SwitchPlugin extends PluginModel
{
	boolean toggle = true;

	public SwitchPlugin(String name, ScreenModel parent)
	{
		super(name, parent);
	}

	@Override
	public String getViewName()
	{
		if (toggle) return "plugins_test_SwitchPlugin1";
		else
			return "plugins_test_SwitchPlugin2";
	}

	@Override
	public String getViewTemplate()
	{
		return "plugins/examples/test/SwitchPlugin.ftl";
	}

	@Override
	public void handleRequest(Database db, Tuple request)
	{
		String action = request.getAction();
		if (action.equals("do_switch"))
		{
			toggle = !toggle;
		}
	}

	@Override
	public void reload(Database db)
	{
		// try
		// {
		// Database db = this.getDatabase();
		// Query q = db.query(Experiment.class);
		// q.like("name", "test");
		// List<Experiment> recentExperiments = q.find();
		//			
		// //do something
		// }
		// catch(Exception e)
		// {
		// //...
		// }
	}
}
