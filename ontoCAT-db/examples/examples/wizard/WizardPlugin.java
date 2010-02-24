/* Date:        July 24, 2009
 * Template:	PluginScreenJavaTemplateGen.java.ftl
 * generator:   org.molgenis.generators.screen.PluginScreenJavaTemplateGen 3.3.0-testing
 * 
 * THIS FILE IS A TEMPLATE. PLEASE EDIT :-)
 */

package plugins.examples.wizard;

import java.util.ArrayList;
import java.util.List;

import org.molgenis.framework.db.Database;
import org.molgenis.framework.db.QueryRule;
import org.molgenis.framework.db.QueryRule.Operator;
import org.molgenis.framework.ui.PluginModel;
import org.molgenis.framework.ui.ScreenModel;
import org.molgenis.framework.ui.html.ActionInput;
import org.molgenis.framework.ui.html.HtmlInput;
import org.molgenis.framework.ui.html.MrefInput;
import org.molgenis.framework.ui.html.XrefInput;
import org.molgenis.util.Tuple;

import example.Experiment;
import example.Sample;

public class WizardPlugin extends PluginModel
{ 
	//remember current step of this wizard
	private int step = 1;
	//form currently to be shown
	private List<HtmlInput> inputs = new ArrayList<HtmlInput>();
	//actions to be shown
	private List<HtmlInput> actions = new ArrayList<HtmlInput>();
	
	//values to be collected
	Integer experimentId = null;
	
	
	public WizardPlugin(String name, ScreenModel parent)
	{
		super(name, parent);
	}

	@Override
	public String getViewName()
	{
		return "plugin_examples_wizard_WizardPlugin";
	}

	@Override
	public String getViewTemplate()
	{
		return "plugins/examples/wizard/WizardPlugin.ftl";
	}

	@Override
	public void handleRequest(Database db, Tuple request)
	{
		String action = request.getAction();
		if("goto_step2".equals(action))
		{
			//validate the input
			//...
			experimentId = request.getInt("experiment_id");
			
			//goto step 2
			step = 2;
		}
		else if("back".equals(action))
		{
			if(step > 1) step--;
		}
		else if("reset".equals(action))
		{
			experimentId = null;
			step = 1;
		}
	}

	@Override
	public void reload(Database db)
	{
		//clear wizard
		inputs.clear();
		actions.clear();
		
		//step 1: choose experiment
		if(step == 1)
		{	
			//add chooser for experiments
			XrefInput selectExperiment = new XrefInput("experiment_id",experimentId);
			selectExperiment.setXrefEntity(Experiment.class);
			selectExperiment.setXrefLabel("name");
			selectExperiment.setXrefField("id");
			selectExperiment.setTooltip("choose one experiment and click next");
			
			inputs.add(selectExperiment);
			
			//add action to go next
			ActionInput next = new ActionInput("goto_step2");
			next.setLabel("next");
			
			actions.add(next);
		}
		
		//step 2: choose samples from this experiment
		else if(step == 2)
		{
			MrefInput selectSample = new MrefInput("sample_ids",null);
			selectSample.setXrefEntity(Sample.class);
			//todo: automate the filling in of label and field
			//add therefore default xref_label to <entity xref_label = ...
			selectSample.setXrefLabel("name");
			selectSample.setXrefField("id");
			selectSample.setXrefFilters(new QueryRule("experiment",Operator.EQUALS, experimentId));
			selectSample.setTooltip("choose one or more samples and click next");
	
			inputs.add(selectSample);
			
			//add action to go next, and back
			ActionInput next = new ActionInput("goto_step2");
			next.setLabel("next");
			ActionInput back = new ActionInput("back");
			back.setLabel("back");
			
			actions.add(next);
			actions.add(back);			
		}
		
		//step 3: generate a datase
		
	}
	
	@Override
	public boolean isVisible()
	{
		//you can use this to hide this plugin, e.g. based on user rights.
		//e.g.
		//if(!this.getLogin().hasEditPermission(myEntity)) return false;
		return true;
	}

	public List<HtmlInput> getInputs()
	{
		return inputs;
	}

	public void setInputs(List<HtmlInput> inputs)
	{
		this.inputs = inputs;
	}

	public List<HtmlInput> getActions()
	{
		return actions;
	}

	public void setActions(List<HtmlInput> actions)
	{
		this.actions = actions;
	}
}
