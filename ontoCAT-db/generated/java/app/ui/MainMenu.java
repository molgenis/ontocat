/* File:        Ontocatdb/screen/main.java
 * Copyright:   GBIC 2000-2,011, all rights reserved
 * Date:        February 8, 2011
 * 
 * generator:   org.molgenis.generators.ui.MenuScreenGen 3.3.2-testing
 *
 * 
 * THIS FILE HAS BEEN GENERATED, PLEASE DO NOT EDIT!
 */


package app.ui;

import org.molgenis.framework.ui.ScreenModel;
import org.molgenis.framework.ui.MenuModel;

/**
 *
 */
public class MainMenu extends MenuModel
{
	private static final long serialVersionUID = 1L;
	
	public MainMenu (ScreenModel parent)
	{
		super( "main", parent );
		this.setLabel("main");
		this.setPosition(Position.LEFT);
		
		//add ui elements
		new app.ui.InvestigationsForm(this);
		new app.ui.OntologiesForm(this);
		new app.ui.OntocatBrowserPlugin(this);
	}	
}


