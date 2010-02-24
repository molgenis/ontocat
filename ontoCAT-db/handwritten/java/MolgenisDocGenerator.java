import org.molgenis.Molgenis;
import org.molgenis.generators.doc.TextUmlGen;

/**
 * Generates the MOLGENIS application from the *db.xml and *ui.xml as set in
 * molgenis.properties
 */
public class MolgenisDocGenerator
{
	public static void main(String[] args) throws Exception
	{
		new Molgenis(TextUmlGen.class).generate();
	}
}
