import org.molgenis.framework.server.MolgenisServer;

/**
 * This program can be used to run MOLGENIS as standalone program
 * (so without Tomcat or Glassfish). 
 * <br>
 * Optionally, if you configure the database driver to hsqldb you have a standalone program!
 */
public class MolgenisRunStandalone
{
	public static void main(String[] args)
	{
		MolgenisServer.main(args);
	}
}
