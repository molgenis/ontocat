/* Date:        August 6, 2009
 * Template:	PluginScreenJavaTemplateGen.java.ftl
 * generator:   org.molgenis.generators.screen.PluginScreenJavaTemplateGen 3.3.0-testing
 * 
 * THIS FILE IS A TEMPLATE. PLEASE EDIT :-)
 */

package plugins.examples.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.molgenis.framework.db.Database;
import org.molgenis.framework.ui.PluginModel;
import org.molgenis.framework.ui.ScreenMessage;
import org.molgenis.framework.ui.ScreenModel;
import org.molgenis.util.FileLink;
import org.molgenis.util.Tuple;

public class FileServingPlugin extends PluginModel
{
	String hyperlink;
	String localfile;
	
	public String getLocalfile()
	{
		return localfile;
	}

	public void setLocalfile(String localfile)
	{
		this.localfile = localfile;
	}

	public String getHyperlink()
	{
		return hyperlink;
	}

	public void setHyperlink(String hyperlink)
	{
		this.hyperlink = hyperlink;
	}

	public FileServingPlugin(String name, ScreenModel parent)
	{
		super(name, parent);
	}

	@Override
	public String getViewName()
	{
		return "plugin_examples_file_FileServingPlugin";
	}

	@Override
	public String getViewTemplate()
	{
		return "plugins/examples/file/FileServingPlugin.ftl";
	}

	@Override
	public void handleRequest(Database db, Tuple request)
	{
		//replace example below with yours
//		try
//		{
//		Database db = this.getDatabase();
//		String action = request.getString("__action");
//		
//		if( action.equals("do_add") )
//		{
//			Experiment e = new Experiment();
//			e.set(request);
//			db.add(e);
//		}
//		} catch(Exception e)
//		{
//			//e.g. show a message in your form
//		}
	}

	@Override
	public void reload(Database db)
	{
		//generate a random file
		try
		{
			//todo: enable setting of extension.
			FileLink link = this.getTempFile();
			
			BufferedWriter out = new BufferedWriter(new FileWriter(link.getLocalpath()+".html"));
			out.write("This");
			out.newLine();
			out.write("is a");
			out.newLine();
			out.write("test");
			out.close();
			
			//set the link.
			this.setLocalfile(link.getLocalpath()+".html");
			this.setHyperlink(link.getLink()+".html");
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			this.setMessages(new ScreenMessage(e.getMessage(),false));
			e.printStackTrace();
		}
		
		
		
//		try
//		{
//			Database db = this.getDatabase();
//			Query q = db.query(Experiment.class);
//			q.like("name", "test");
//			List<Experiment> recentExperiments = q.find();
//			
//			//do something
//		}
//		catch(Exception e)
//		{
//			//...
//		}
	}
	
	@Override
	public boolean isVisible()
	{
		//you can use this to hide this plugin, e.g. based on user rights.
		//e.g.
		//if(!this.getLogin().hasEditPermission(myEntity)) return false;
		return true;
	}
}
