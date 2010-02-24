package commands;

import java.util.ArrayList;
import java.util.List;

import org.molgenis.framework.db.DatabaseException;
import org.molgenis.framework.ui.FormModel;
import org.molgenis.framework.ui.commands.SimpleCommand;
import org.molgenis.framework.ui.html.ActionInput;
import org.molgenis.framework.ui.html.HtmlInput;

public class MyCommand extends SimpleCommand
{
	@Override
	public List<HtmlInput> getActions()
	{
		List<HtmlInput> actions = new ArrayList<HtmlInput>();
		
		//ActionInput close = new ActionInput("close");
		///close.setIcon("path/to/image");
		//close.setLabel("My Command Example");
		
		return actions;
	}

	@Override
	public List<HtmlInput> getInputs() throws DatabaseException
	{
		//no inputs here
		return null;
	}

}
