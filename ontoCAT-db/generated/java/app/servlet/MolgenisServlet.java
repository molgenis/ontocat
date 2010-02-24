/*
 * Created by: org.molgenis.generators.server.MolgenisServletGen
 * Date: February 24, 2010
 */

package app.servlet;

import java.io.File;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.molgenis.framework.security.Login;

import org.molgenis.framework.db.Database;
import org.molgenis.framework.db.DatabaseException;
import org.molgenis.framework.ui.UserInterface;
import org.molgenis.framework.server.AbstractMolgenisServlet;

import org.molgenis.util.EmailService;
import org.molgenis.util.SimpleEmailService;

import org.molgenis.framework.db.jdbc.JndiDataSourceWrapper;

public class MolgenisServlet extends AbstractMolgenisServlet
{
	/** */
	private static final long serialVersionUID = 3141439968743510237L;
	/** */

	public Database getDatabase() throws DatabaseException, NamingException
	{
		//The datasource is created by the servletcontext!		
		DataSource dataSource = (DataSource)getServletContext().getAttribute("DataSource");
		return new app.JDBCDatabase(dataSource, new File("attachedfiles"));
		
		//TOMCAT
		//String jndiName = "java:comp/env/jdbc/molgenisdb";
		//JndiDataSourceWrapper source = new JndiDataSourceWrapper(jndiName);
		//return new app.JDBCDatabase(source, new File("attachedfiles"));
	
		//GLASSFISH
		//DataSource dataSource = (DataSource)getServletContext().getAttribute("DataSource");
		//return new app.JDBCDatabase(dataSource, new File("attachedfiles"));
	}

	public Login createLogin( Database db, HttpServletRequest request )
	{
		return new org.molgenis.framework.security.SimpleLogin();
	}

	public UserInterface createUserInterface( Login userLogin )
	{
		UserInterface app = new UserInterface( userLogin);
		app.setLabel("OntoCAT-DB");
		app.setVersion("3.3.2-testing");
		
		EmailService service = new SimpleEmailService();
		service.setSmtpProtocol("smtps");
		service.setSmtpHostname("smtp.gmail.com");
		service.setSmtpPort(465);
		service.setSmtpUser("molgenis");
		service.setSmtpPassword("molgenispass");	
		app.setEmailService(service);
		
		new app.ui.Molgenis_headerPlugin(app);
		new app.ui.MainMenu(app);
		return app;
	}
	
	public static String getMolgenisVariantID()
	{
		return "ontocatdb";
	}	
	
	@Override
	public Object getSoapImpl() throws DatabaseException, NamingException
	{
		return new app.servlet.SoapApi((Database)getDatabase());
	}
}
