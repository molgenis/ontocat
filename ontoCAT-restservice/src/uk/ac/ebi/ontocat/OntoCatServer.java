package uk.ac.ebi.ontocat;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

import uk.ac.ebi.ontocat.ols.OlsOntologyService;

public class OntoCatServer
{
	//See README
	public static void main(String[] args) throws OntologyServiceException
	{
		//setup ontocat
		OntologyService os = new OlsOntologyService();
		
		// set the path
		String url = "http://localhost:8080/ontocat/rest";

//		//configuration to use badgerfish
//		Map<String,Object> properties = new HashMap<String,Object>();
//		BadgerFishXMLInputFactory xif = new BadgerFishXMLInputFactory();
//		properties.put(XMLInputFactory.class.getName(), xif);
//		BadgerFishXMLOutputFactory xof = new BadgerFishXMLOutputFactory();
//		properties.put(XMLOutputFactory.class.getName(), xof);
//		
//		properties.put("Content-type", "text/plain");
//		
//		//setup cxf to serve it
//		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
//		sf.setProperties(properties);
//		sf.setResourceClasses(OntocatService.class);
//		sf.setResourceProvider(OntocatService.class, new SingletonResourceProvider(new OntocatService(os)));
//		sf.setAddress(url);
//				
//		BindingFactoryManager manager = sf.getBus().getExtension(BindingFactoryManager.class);
//		JAXRSBindingFactory factory = new JAXRSBindingFactory();
//		factory.setBus(sf.getBus());
//		manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID, factory);
//		
//		
//		sf.create();
		
//		System.out.println(sf.getDataBinding().getSupportedWriterFormats());
		
		
		
		
        // Build up the server factory bean
//        JaxWsServerFactoryBean sf = new JaxWsServerFactoryBean();
//        sf.setServiceClass(OntocatService.class);
//        // Use the HTTP Binding which understands the Java Rest Annotations
//        sf.setBindingId(HttpBindingFactory.HTTP_BINDING_ID);
//        sf.setAddress("http://localhost:9000/");
//        sf.getServiceFactory().setInvoker(new BeanInvoker(new OntocatService(os)));
//
//        // Turn the "wrapped" style off. This means that CXF won't generate
//        // wrapper JSON elements and we'll have prettier JSON text. This
//        // means that we need to stick to one request and one response
//        // parameter though.
//        sf.getServiceFactory().setWrapped(false);
//
//        // Tell CXF to use a different Content-Type for the JSON endpoint
//        // This should probably be application/json, but text/plain allows
//        // us to view easily in a web browser.
//        Map<String, Object> properties = new HashMap<String, Object>();
//        properties.put("Content-Type", "text/plain");
//
//        // Set up the JSON StAX implementation
////        Map<String, String> nstojns = new HashMap<String, String>();
////        //nstojns.put("http://demo.restful.server", "acme");
////
////        MappedXMLInputFactory xif = new MappedXMLInputFactory(nstojns);
////        properties.put(XMLInputFactory.class.getName(), xif);
////
////        MappedXMLOutputFactory xof = new MappedXMLOutputFactory(nstojns);
////        properties.put(XMLOutputFactory.class.getName(), xof);
//
//        sf.setProperties(properties);
//
//        sf.create();
		
	       JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
	        sf.setResourceClasses(OntocatService.class);
	        sf.setResourceProvider(OntocatService.class, 
	            new SingletonResourceProvider(new OntocatService(os)));
	        sf.setAddress(url);

	        sf.create();
		
	}
}
