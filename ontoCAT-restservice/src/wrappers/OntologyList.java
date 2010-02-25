package wrappers;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import uk.ac.ebi.ontocat.Ontology;

@XmlRootElement(name="ontologies")
@XmlAccessorType(XmlAccessType.FIELD)
/** Root element for an ontology list */
public class OntologyList
{
	public OntologyList()
	{
	}
	
	public OntologyList(List<Ontology> ontologies)
	{
		this.ontologies = ontologies;
	}
	
	@XmlElement(name="ontology")
	public List<Ontology> ontologies = new ArrayList<Ontology>();
}
