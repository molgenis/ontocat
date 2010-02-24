import org.molgenis.Molgenis;
import org.molgenis.generators.doc.DotDocMinimalGen;
import org.molgenis.generators.doc.ObjectModelDocGen;

/**
 * Generates the MOLGENIS application from the *db.xml and *ui.xml as set in
 * molgenis.properties
 */
public class MolgenisGenerate
{
	public static void main(String[] args) throws Exception
	{
		new Molgenis().generate();
	}
}
