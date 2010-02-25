package uk.ac.ebi.ontocat.ols;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;

public class OlsOntologyTerm extends OntologyTerm
{
	Logger logger = Logger.getLogger(OlsOntologyTerm.class);

	// handle to the service to lazy load more properties
	private final OlsOntologyService ols;

	public OlsOntologyTerm(OlsOntologyService ols, String ontologyAccession, String termAccession, String termLabel) throws OntologyServiceException
	{
		if(ols == null) throw new OntologyServiceException("paramater ols is missing");
		if(ontologyAccession == null || ontologyAccession.equals("")) throw new OntologyServiceException("paramater ontologyAccession is missing");
		if(termAccession == null || termAccession.equals("")) throw new OntologyServiceException("paramater termAccession is missing");
		
		this.ols = ols;
		this.setOntologyAccession(ontologyAccession);
		this.setAccession(termAccession);
		this.setLabel(termLabel);
	}

	@Override
	public String getLabel() throws OntologyServiceException
	{
		// lazy load
		if (super.getLabel() == null)
		{
			logger.debug("retrieving label for " + this.getAccession());
			try
			{
				super.setLabel(ols.getQuery().getTermById(getAccession(), getOntologyAccession()));
			}
			catch (RemoteException e)
			{
				throw new OntologyServiceException(e);
			}
		}
		return super.getLabel();
	}	
}
