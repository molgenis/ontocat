import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;

@Path("/ontocat/")
@Produces("application/xml")
public class OntocatService
{
	OntologyService s;
	
	public OntocatService(OntologyService service)
	{
		this.s = service;
	}
	
	@GET
	@Path("/ontologies/")
	//TODO: support JSON, XML and TSV formats
	public String getOntologyList() throws OntologyServiceException
	{				
		String result = "";

		for (Ontology o: s.getOntologies())
			result += clean(o.getAbbreviation()) +"\t" + clean(o.getLabel()) +"\t" + clean(o.getDescription()) + "\n";

		return result;
	}
	
	private String clean(Object o)
	{
		if(o == null) return "";
		//TODO: cleaning!
		else return o.toString();
	}
}
