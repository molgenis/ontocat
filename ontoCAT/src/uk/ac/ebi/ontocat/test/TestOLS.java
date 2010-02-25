package uk.ac.ebi.ontocat.test;

import java.util.List;
import java.util.Map;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;

public class TestOLS
{
	public static void main(String[] args) throws OntologyServiceException
	{
		OntologyService ols = new OlsOntologyService();
		
		Map<String, List<String>> result = ols.getAnnotations("GO","GO:0003674");
		
		System.out.println("result"+result);
		for(String key: result.keySet())
		{
			for(String value: result.get(key))
			{
				System.out.println(key+"->"+value);
			}
		}
	}
}
