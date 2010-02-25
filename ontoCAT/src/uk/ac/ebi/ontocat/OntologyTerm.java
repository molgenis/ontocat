package uk.ac.ebi.ontocat;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "term")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class OntologyTerm
{
	private String accession;
	private String label;
	private String ontologyAccession;

	public OntologyTerm()
	{

	}

	/** Copy constructor 
	 * @throws OntologyServiceException */
	public OntologyTerm(OntologyTerm term) throws OntologyServiceException
	{
		this.setAccession(term.getAccession());
		this.setLabel(term.getLabel());
		this.setOntologyAccession(term.getOntologyAccession());
	}

	public String getAccession() throws OntologyServiceException
	{
		return accession;
	}

	public void setAccession(String accession)
	{
		this.accession = accession;
	}

	// public String[] getDefinitions() throws OntologyServiceException
	// {
	// return definitions;
	// }
	// public void setDefinitions(String[] definitions)
	// {
	// this.definitions = definitions;
	// }
	public String getLabel() throws OntologyServiceException
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public String getOntologyAccession() throws OntologyServiceException
	{
		return ontologyAccession;
	}

	public void setOntologyAccession(String ontologyAccession)
	{
		this.ontologyAccession = ontologyAccession;
	}

	// public String[] getSynonyms() throws OntologyServiceException
	// {
	// return synonyms;
	// }
	// public void setSynonyms(String[] synonyms)
	// {
	// this.synonyms = synonyms;
	// }
	// public Map<String, String[]> getAnnotations() throws
	// OntologyServiceException
	// {
	// return annotations;
	// }
	// public void setAnnotations(Map<String, String[]> annotations)
	// {
	// this.annotations = annotations;
	// }

	public String toString()
	{
		try
		{
			return String.format("OntologyTerm(accession=%s, label=%s, ontologyAccession=%s)", getAccession(),
					getLabel(), getOntologyAccession());
		}
		catch (OntologyServiceException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

//	private String toString(String[] array)
//	{
//		String result = null;
//		if (array != null)
//		{
//			result = "";
//			for (int i = 0; i < array.length; i++)
//			{
//				result += (i == 0 ? "" : ",") + array[i];
//			}
//		}
//		return result;
//	}
}
