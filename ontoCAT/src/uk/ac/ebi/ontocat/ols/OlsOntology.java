package uk.ac.ebi.ontocat.ols;


import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.Ontology;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;

public class OlsOntology implements Ontology
{
	//handle to the service for lazy loading
	private OlsOntologyService ols;
	
	//properties
	private String abbreviation;
	private String dateReleased;
	private String label;
	private String ontologyAccession;
	private String synonymSlot;
	private static String versionNumber;
	//OLS doesn't have versions per ontology, hence static
	private String description;

	/**
	 * 
	 * @param ols OntologyLookupService
	 * @param ontologyAccession
	 */
	public OlsOntology(OlsOntologyService ols, String ontologyAccession)
	{
		this.ols = ols;
		this.ontologyAccession = ontologyAccession;
	}

	@Override
	public String getAbbreviation()
	{
		return abbreviation;
	}

	protected void setAbbreviation(String abbreviation)
	{
		this.abbreviation = abbreviation;
	}

	@Override
	public String getDateReleased() throws OntologyServiceException
	{
		if(dateReleased == null)
		{
			try
			{
				dateReleased = ols.getQuery().getOntologyLoadDate(this.getOntologyAccession());
			}
			catch (RemoteException e)
			{
				throw new OntologyServiceException(e);
			}
		}
		return dateReleased;
	}
	
	@Override
	public String getLabel() throws OntologyServiceException
	{
		if(label == null)
		{
			try
			{
				label = ols.getQuery().getTermById(getOntologyAccession(), getOntologyAccession());
			}
			catch (RemoteException e)
			{
				throw new OntologyServiceException(e);
			}
		}
		return label;
	}

	protected void setLabel(String displayLabel)
	{
		this.label = displayLabel;
	}

	@Override
	public String getOntologyAccession()
	{
		return ontologyAccession;
	}

	protected void setOntologyAccession(String ontologyAccession)
	{
		this.ontologyAccession = ontologyAccession;
	}

	@Override
	public String getSynonymSlot()
	{
		return synonymSlot;
	}

	protected void setSynonymSlot(String synonymSlot)
	{
		this.synonymSlot = synonymSlot;
	}

	@Override
	public String getVersionNumber() throws OntologyServiceException
	{
		if(versionNumber == null)
		{
//			try
//			{
//				versionNumber = ols.getQuery().getVersion();
//			}
//			catch (RemoteException e)
//			{
//				throw new OntologyServiceException(e);
//			}
		}
		return versionNumber;
	}

	@Override
	public String getDescription()
	{
		return description;
	}
	
	protected void setDescription(String description)
	{
		this.description = description;
	}

	@Override
	public List<OntologyTerm> getRootTerms() throws OntologyServiceException
	{
		try
		{
			return ols.convert(ols.getQuery().getRootTerms(this.getOntologyAccession()));
		}
		catch (RemoteException e)
		{
			throw new OntologyServiceException(e);
		}
	}
	
	public String toString()
	{
		try
		{
			return String
					.format(
							"Ontology(abbreviation=%s, label=%s, ontologyAccession=%s,  dateReleased=%s, synonymSlot=%s, versionNumber=%s, description=%s)",
							getAbbreviation(), getLabel(), getOntologyAccession(), getDateReleased(), getSynonymSlot(), getVersionNumber(), getDescription());
		}
		catch (OntologyServiceException e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
