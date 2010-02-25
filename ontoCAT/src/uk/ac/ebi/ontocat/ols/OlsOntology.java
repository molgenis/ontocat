package uk.ac.ebi.ontocat.ols;


import java.rmi.RemoteException;

import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyServiceException;

public class OlsOntology extends Ontology
{
	//handle to the service for lazy loading
	private OlsOntologyService ols;

	/**
	 * 
	 * @param ols OntologyLookupService
	 * @param ontologyAccession
	 */
	public OlsOntology(OlsOntologyService ols, String ontologyAccession)
	{
		super(ontologyAccession);
		this.ols = ols;
	}

	@Override
	public String getDateReleased() throws OntologyServiceException
	{
		//lazy loading
		if(super.getDateReleased() == null)
		{
			try
			{
				this.setDateReleased(ols.getQuery().getOntologyLoadDate(this.getOntologyAccession()));
			}
			catch (RemoteException e)
			{
				throw new OntologyServiceException(e);
			}
		}
		return super.getDateReleased();
	}
	
	@Override
	public String getLabel() throws OntologyServiceException
	{
		//lazy loading
		if(super.getLabel() == null)
		{
			try
			{
				this.setLabel( ols.getQuery().getTermById(getOntologyAccession(), getOntologyAccession()));
			}
			catch (RemoteException e)
			{
				throw new OntologyServiceException(e);
			}
		}
		return super.getLabel();
	}

	@Override
	public String getVersionNumber() throws OntologyServiceException
	{
		//lazy loading
		if(super.getVersionNumber() == null)
		{
			try
			{
				this.setVersionNumber( ols.getQuery().getVersion() );
			}
			catch (RemoteException e)
			{
				throw new OntologyServiceException(e);
			}
		}
		return super.getVersionNumber();
	}
}
