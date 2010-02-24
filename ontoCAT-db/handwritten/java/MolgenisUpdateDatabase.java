import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.molgenis.Molgenis;
import org.molgenis.MolgenisOptions;

import org.molgenis.util.cmdline.CmdLineException;

public class MolgenisUpdateDatabase
{
	public static void main(String[] args) throws Exception
	{
		new Molgenis().updateDb();
	}
}
