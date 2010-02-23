import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.jaxrs.JAXRSBindingFactory;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.ols.OlsOntologyService;

public class OntoCatServer
{
	//See README
	public static void main(String[] args) throws OntologyServiceException
	{
		//setup ontocat
		OntologyService os = new OlsOntologyService();
		
		//setup cxf to serve it
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
		sf.setResourceClasses(OntocatService.class);
		sf.setResourceProvider(OntocatService.class, new SingletonResourceProvider(new OntocatService(os)));
		sf.setAddress("http://localhost:9000/");
		BindingFactoryManager manager = sf.getBus().getExtension(BindingFactoryManager.class);
		JAXRSBindingFactory factory = new JAXRSBindingFactory();
		factory.setBus(sf.getBus());
		manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID, factory);
		sf.create();
	}
}
