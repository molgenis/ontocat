/* Date:        May 10, 2009
 * Template:	PluginScreenJavaTemplateGen.java.ftl
 * generator:   org.molgenis.generators.screen.PluginScreenJavaTemplateGen 3.3.0-testing
 * 
 * THIS FILE IS A TEMPLATE. PLEASE EDIT :-)
 */

package plugins.query.view;

import java.util.ArrayList;
import java.util.List;

import org.molgenis.framework.db.JoinQuery;
import org.molgenis.framework.db.Database;
import org.molgenis.framework.ui.PluginModel;
import org.molgenis.framework.ui.ScreenMessage;
import org.molgenis.framework.ui.ScreenModel;
import org.molgenis.model.elements.Entity;
import org.molgenis.model.elements.Model;
import org.molgenis.util.Tuple;

public class MartView extends PluginModel
{
	//layout: 
	//left a 'data model browser' with all entities and fields
	//right 'count/list preview' of the selected result
	//initially refresh action executes this as it may take some time
	
	//data model browser dialog:
	//selection to show all or selected only
	//all fields are shown in a tree with entities as nodes
	//mouse over or [?] shows annotation
	//a field can be ticked to be selected
	//a field can be ticked to create a filter rule
	//left join is assumed -> otherwise a rule can be set?
	
	//count/list preview:
	//headers show selected fields including joinpath in small
	//columns can be moved in the resultset to change column ordering
	//columns can be set to be sorted (asc/desc)
	//pagination is assumed and suitable limits set (e.g. 100)
	
	//filters dialog:
	//a ticked field can be enhanced with a filter
	//future: each filter has a name A or B and C
	//filter depends on the type 
	//-> enum/xrefs are listed with appropriate dialogs
	//-> equals filters can have multiple values seperated by spaces or ','
	//-> date shows date dialogue
	//inverse xrefs are shown as optional related elements (subnodes of tree)
	
	
	//step one: show a set of main nodes to choose from
	//only one root node can be opened at one time
	//from each node there can be an endless path (except circular references)
	
	
	//from one selected node, show a tree of related entities (nodes) and fields (leafs) 
	//each entity is shown closed but can be opened up for adding its fields
	//this depends whether the 
	//each action should send to server which nodes are opened
	
	//a query is a variant of this tree with selected fields
	
	private List<String> selectedFields = new ArrayList<String>();
	private List<Entity> entities = new ArrayList<Entity>();
	private String sql = "NONE";
	

	public List<Entity> getEntities()
	{
		return entities;
	}

	public void setEntities(List<Entity> entities)
	{
		this.entities = entities;
	}

	public MartView(String name, ScreenModel parent)
	{
		super(name, parent);
	} 

	@Override
	public String getViewName()
	{
		return "plugin_query_view_MartView";
	}

	@Override
	public String getViewTemplate()
	{
		return "plugins/query/view/MartView.ftl";
	}

	@Override
	public void handleRequest(Database db, Tuple request)
	{
		logger.debug("Handle request "+request);
		// set field selection
		if ("update_selection".equals(request.getAction()))
		{
			logger.debug("update selection");
			selectedFields.clear();
			if (request.getObject("select_field") != null)
			{
				logger.debug("add field");
				for (Object f : request.getList("select_field"))
					selectedFields.add((String) f);
			}
		}
		else if ("clear_selection".equals(request.getAction()))
		{
			selectedFields.clear();
		}

		// replace example below with yours
		// try
		// {
		// Database db = this.getDatabase();
		// String action = request.getString("__action");
		//		
		// if( action.equals("do_add") )
		// {
		// Experiment e = new Experiment();
		// e.set(request);
		// db.add(e);
		// }
		// } catch(Exception e)
		// {
		// //e.g. show a message in your form
		// }
	}

	@Override
	public void reload(Database db)
	{
		try
		{
			Model m = db.getMetaData();
			entities = m.getEntities();
			
			JoinQuery q = new JoinQuery(db,selectedFields);
			this.sql = q.toFindSql();

		}
		catch (Exception e)
		{
			this.setMessages(new ScreenMessage(e.getMessage(),false));
			this.sql="NONE";
			e.printStackTrace();
		}
		
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

	public String getSql()
	{
		return sql;
	}

	public void setSql(String sql)
	{
		this.sql = sql;
	}

	@Override
	public boolean isVisible()
	{
		// you can use this to hide this plugin, e.g. based on user rights.
		// e.g.
		// if(!this.getLogin().hasEditPermission(myEntity)) return false;
		return true;
	}

//	public List<HtmlInput> getInputs()
//	{
//		List<HtmlInput> inputs = new ArrayList<HtmlInput>();
//		for (EntityMetaData em : model.getEntities())
//		{
//			for (FieldMetaData fm : em.getAllFields())
//			{
//				String field = em.getName() + "." + fm.getName();
//				OnoffInput bi = new OnoffInput("field", selectedFields.contains(field));
//				bi.setOnValue(field);
//				bi.setLabel(field);
//				inputs.add(bi);
//			}
//		}
//
//		return inputs;
//	}
//
//	public List<HtmlInput> getActions()
//	{
//		List<HtmlInput> inputs = new ArrayList<HtmlInput>();
//		inputs.add(new ActionInput("add fields"));
//		inputs.add(new ActionInput("clear fields"));
//		return inputs;
//	}
//
//	public MolgenisMetaData getModel()
//	{
//		// load the metadata
//		Model m;
//		try
//		{
//			URL u = MartView.class.getResource("molgenis.properties");
//			// File f = new File(u.getFile());
//			// Properties p = new Properties();
//			// p.load(new FileInputStream(f));
//			m = MolgenisLanguage.parse(new MolgenisOptions(u.getFile()));
//			return new MolgenisMetaDataImp(m);
//		}
//		catch (Exception e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public String getSql()
//	{
//		try
//		{
//			CustomQuery q = new CustomQuery(model);
//			q.setFields(selectedFields.toArray(new String[selectedFields.size()]));
//			return q.toFindSql();
//		}
//		catch (Exception e)
//		{
//			//e.printStackTrace();
//			return e.getMessage();
//		}
//	}
}
