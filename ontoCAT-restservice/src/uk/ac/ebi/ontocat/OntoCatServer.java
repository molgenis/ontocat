package uk.ac.ebi.ontocat;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

import uk.ac.ebi.ontocat.ols.OlsOntologyService;

/**
 * This class demonstrates how to serve an OntoCAT service via REST.
 * For this to work, it requires both ontoCAT and ontoCAT-restservice libraries.
 */
public class OntoCatServer
{
	// See README
	public static void main(String[] args) throws OntologyServiceException
	{
		// setup ontocat
		OntologyService os = new OlsOntologyService();

		// set the path
		String url = "http://localhost:8080/ontocat/rest";

		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
		sf.setResourceClasses(OntocatService.class);
		sf.setResourceProvider(OntocatService.class, new SingletonResourceProvider(new OntocatService(os)));
		sf.setAddress(url);

		sf.create();

	}
}
