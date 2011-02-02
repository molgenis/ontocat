package uk.ac.ebi.ontocat;


import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.ols.OlsOntologyService;
import wrappers.OntologyList;
import wrappers.OntologyTermAnnotation;
import wrappers.OntologyTermAnnotationList;
import wrappers.OntologyTermDefinition;
import wrappers.OntologyTermList;
import wrappers.OntologyTermRelation;
import wrappers.OntologyTermRelationList;

@Path("/")
public class OntocatService
{
	Logger logger = Logger.getLogger(OntocatService.class);
	OntologyService os;

	public OntocatService() throws OntologyServiceException
	{
		this.os = new OlsOntologyService();
	}
	
	public OntocatService(OntologyService service)
	{
		this.os = service;
	}

//os.getOntologies()	
	@GET
	@Path("/xml/ontologies/")
	@Produces("application/xml")
	@WebMethod
	public OntologyList getOntologiesXML() throws OntologyServiceException
	{
		logger.debug("getOntologiesXML: begin");
		OntologyList result = new OntologyList(os.getOntologies());
		logger.debug("getOntologiesXML: done, returned "+result.ontologies.size());
		return result;
	}

	@GET
	@Path("/json/ontologies/")
	@Produces("application/json")
	@WebMethod
	public OntologyList getOntologiesJSON() throws OntologyServiceException
	{
		return getOntologiesXML();
	}

	
//	os.searchAll(keyword)	
	@GET
	@Path("/json1/searchAll/")
	@Produces("application/json")
	@WebMethod
	public List<OntologyTerm> searchAllJSON1(@PathParam("q") String keywords) throws OntologyServiceException
	{
		return this.searchAllXML(keywords).terms;
	}
	
//		os.searchAll(keyword)	
	@GET
	@Path("/json/searchAll/{keywords}")
	@Produces("application/json")
	@WebMethod
	public List<OntologyTerm> searchAllJSON(@PathParam("keywords") String keywords) throws OntologyServiceException
	{
		return this.searchAllXML(keywords).terms;
	}
	
	@GET
	@Path("/xml/searchAll/{keywords}")
	@Produces("application/xml")
	@WebMethod
	public OntologyTermList searchAllXML(@PathParam("keywords") String keywords) throws OntologyServiceException
	{
		try
		{
			return new OntologyTermList(os.searchAll(keywords));
		}
		catch (OntologyServiceException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
//os.searchOntology	
	@GET
	@Path("/json/search/{ontologyAccession}/{keywords}")
	@Produces("application/json")
	@WebMethod
	public List<OntologyTerm> searchOntologyJSON(@PathParam("ontologyAccession")String ontologyAccession, @PathParam("keywords") String keywords) throws OntologyServiceException 
	{
		return this.searchOntologyXML(ontologyAccession, keywords).terms;
	}
	
	@GET
	@Path("/xml/search/{ontologyAccession}/{keywords}")
	@Produces("application/xml")
	@WebMethod
	public OntologyTermList searchOntologyXML(@PathParam("ontologyAccession")String ontologyAccession, @PathParam("keywords") String keywords) throws OntologyServiceException 
	{
		logger.debug(ontologyAccession + ":"+keywords);
		try
		{
			return new OntologyTermList(os.searchOntology(ontologyAccession, keywords));
		}
		catch (OntologyServiceException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
//os.getParents	
	@GET
	@Path("/json/parents/{ontologyAccession}/{termAccession}")
	@Produces("application/json")
	@WebMethod
	public List<OntologyTerm> getParentsJSON(@PathParam("ontologyAccession")String ontologyAccession, @PathParam("termAccession") String termAccession)
	{
		return this.getParentsXML(ontologyAccession, termAccession).terms;
	}
	
	@GET
	@Path("/xml/parents/{ontologyAccession}/{termAccession}")
	@Produces("application/xml")
	@WebMethod
	public OntologyTermList getParentsXML(@PathParam("ontologyAccession")String ontologyAccession, @PathParam("termAccession") String termAccession)
	{
		logger.debug("test "+ontologyAccession + ":"+termAccession);
		try
		{
			return new OntologyTermList(os.getParents(ontologyAccession, termAccession));
		}
		catch (OntologyServiceException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			 throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
		}
	}	
	
//os.getChildren
	@GET
	@Path("/json/children/{ontologyAccession}/{termAccession}")
	@Produces("application/json")
	@WebMethod
	public List<OntologyTerm> getChildrenJSON(@PathParam("ontologyAccession")String ontologyAccession, @PathParam("termAccession") String termAccession)
	{
		return this.getChildrenXML(ontologyAccession, termAccession).terms;
	}	
	
	@GET
	@Path("/xml/children/{ontologyAccession}/{termAccession}")
	@Produces("application/xml")
	@WebMethod
	public OntologyTermList getChildrenXML(@PathParam("ontologyAccession")String ontologyAccession, @PathParam("termAccession") String termAccession)
	{
		logger.debug("test "+ontologyAccession + ":"+termAccession);
		try
		{
			return new OntologyTermList(os.getChildren(ontologyAccession, termAccession));
		}
		catch (OntologyServiceException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			 throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
		}
	}	

//os.getTerm
	@GET
	@Path("/json/term/{ontologyAccession}/{termAccession}")
	@Produces("application/json")
	@WebMethod
	public OntologyTerm getTermJSON(@PathParam("ontologyAccession")String ontologyAccession, @PathParam("termAccession") String termAccession)
	{
		return getTermXML(termAccession, termAccession);
	}
	
	@GET
	@Path("/xml/term/{ontologyAccession}/{termAccession}")
	@Produces("application/xml")
	@WebMethod
	public OntologyTerm getTermXML(@PathParam("ontologyAccession")String ontologyAccession, @PathParam("termAccession") String termAccession)
	{
		System.out.println("test "+ontologyAccession + ":"+termAccession);
		try
		{
			return os.getTerm(ontologyAccession, termAccession);
			//return new	OntologyTerm(String ontologyAccession, String termAccession, String label) 

		}
		catch (OntologyServiceException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			 throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
		}
	}	

//os.getRootTerms
	@GET
	@Path("/json/root/{ontologyAccession}")
	@Produces("application/json")
	@WebMethod
	public OntologyTermList getRootTermsJSON(@PathParam("ontologyAccession")String ontologyAccession)
	{
		return getRootTermsXML(ontologyAccession);
	}
	
	@GET
	@Path("/xml/root/{ontologyAccession}")
	@Produces("application/xml")
	@WebMethod
	public OntologyTermList getRootTermsXML(@PathParam("ontologyAccession")String ontologyAccession)
	{
		try
		{
			return new OntologyTermList(os.getRootTerms(ontologyAccession));
		}
		catch (OntologyServiceException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			 throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

//os.getTermPath
	@GET
	@Path("/json/path/{ontologyAccession}/{termAccession}")
	@Produces("application/json")
	@WebMethod
	public List<OntologyTerm> getTermPathJSON(@PathParam("ontologyAccession")String ontologyAccession, @PathParam("termAccession") String termAccession)
	{
		return getTermPathXML(termAccession, termAccession).terms;
	}
	
	@GET
	@Path("/xml/path/{ontologyAccession}/{termAccession}")
	@Produces("application/xml")
	@WebMethod
	public OntologyTermList getTermPathXML(@PathParam("ontologyAccession")String ontologyAccession, @PathParam("termAccession") String termAccession)
	{
		logger.debug("test "+ontologyAccession + ":"+termAccession);
		try
		{
			return new OntologyTermList(os.getTermPath(ontologyAccession, termAccession));
		}
		catch (OntologyServiceException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			 throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
		}
	}		
	
//os.getDefinitions	
	@GET
	@Path("/json/definition/{ontologyAccession}/{termAccession}")
	@Produces("application/json")
	@WebMethod
	public OntologyTermDefinition getDefinitionJSON(@PathParam("ontologyAccession")String ontologyAccession, @PathParam("termAccession") String termAccession)
	{
		return this.getDefinitionXML(ontologyAccession, termAccession);
	}
	
	@GET
	@Path("/xml/definition/{ontologyAccession}/{termAccession}")
	@Produces("application/xml")
	@WebMethod
	public OntologyTermDefinition getDefinitionXML(@PathParam("ontologyAccession")String ontologyAccession, @PathParam("termAccession") String termAccession)
	{
		logger.debug("definitions "+ontologyAccession + ":"+termAccession);
		try
		{
			return new OntologyTermDefinition(os.getDefinitions(ontologyAccession, termAccession));
		}
		catch (OntologyServiceException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			 throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
		}
	}	
	
//os.getRelations
	@GET
	@Path("/json/relations/{ontologyAccession}/{termAccession}")
	@Produces("application/json")
	@WebMethod
	public List<OntologyTermRelation> getRelationsJSON(@PathParam("ontologyAccession")String ontologyAccession, @PathParam("termAccession") String termAccession)
	{
		return this.getRelationsXML(ontologyAccession, termAccession).relations;
	}
	
	@GET
	@Path("/xml/relations/{ontologyAccession}/{termAccession}")
	@Produces("application/xml")
	@WebMethod
	public OntologyTermRelationList getRelationsXML(@PathParam("ontologyAccession")String ontologyAccession, @PathParam("termAccession") String termAccession)
	{
		try
		{
			return new OntologyTermRelationList(os.getRelations(ontologyAccession, termAccession));
		}
		catch (OntologyServiceException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			 throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
		}
	}		
	
//os.getSynonyms
	@GET
	@Path("/json/synonyms/{ontologyAccession}/{termAccession}")
	@Produces("application/json")
	@WebMethod
	public OntologyTermSynonymList getSynonymsJSON(@PathParam("ontologyAccession")String ontologyAccession, @PathParam("termAccession") String termAccession)
	{
		return getSynonymsXML(termAccession, termAccession);
	}
	
	@GET
	@Path("/xml/synonyms/{ontologyAccession}/{termAccession}")
	@Produces("application/xml")
	@WebMethod
	public OntologyTermSynonymList getSynonymsXML(@PathParam("ontologyAccession")String ontologyAccession, @PathParam("termAccession") String termAccession)
	{
		logger.debug("definitions "+ontologyAccession + ":"+termAccession);
		try
		{
			return new OntologyTermSynonymList(os.getSynonyms(ontologyAccession, termAccession));
		}
		catch (OntologyServiceException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			 throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
		}
	}
	
//os.getAnnotations
	@GET
	@Path("/json/annotations/{ontologyAccession}/{termAccession}")
	@Produces("application/json")
	@WebMethod
	public List<OntologyTermAnnotation> getAnnotationsJSON(@PathParam("ontologyAccession")String ontologyAccession, @PathParam("termAccession") String termAccession)
	{
		return getAnnotationsXML(ontologyAccession,termAccession).annotations;
	}
	
	@GET
	@Path("/xml/annotations/{ontologyAccession}/{termAccession}")
	@Produces("application/xml")
	@WebMethod
	public OntologyTermAnnotationList getAnnotationsXML(@PathParam("ontologyAccession")String ontologyAccession, @PathParam("termAccession") String termAccession)
	{
		try
		{
			return new OntologyTermAnnotationList(os.getAnnotations(ontologyAccession, termAccession));
		}
		catch (OntologyServiceException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			 throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
		}
	}	
	

	private String clean(Object o)
	{
		if (o == null)
			return "";
		// TODO: cleaning!
		else
			return o.toString();
	}
	
	@GET
	@Path("json/dummy/{keywords}")
	@Produces("application/json")
	//@HttpResource(location = "/json/dummy/{keywords}")
	public List<OntologyTerm> dummyJSON(@PathParam("keywords") String keywords) throws OntologyServiceException
	{
		List<OntologyTerm> tList = new ArrayList<OntologyTerm>();
		tList.add(new OntologyTerm(keywords,"test","test"));
		return tList;
	}
	
	@GET
	@Path("xml/dummy/{keywords}")
	@Produces("application/xml")
	//@HttpResource(location = "/json/dummy/{keywords}")
	public List<OntologyTerm> dummyXML(@PathParam("keywords") String keywords) throws OntologyServiceException
	{
		List<OntologyTerm> tList = new ArrayList<OntologyTerm>();
		tList.add(new OntologyTerm(keywords,"test","test"));
		return tList;
	}
}
