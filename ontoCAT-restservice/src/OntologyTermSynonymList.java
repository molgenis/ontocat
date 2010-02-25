import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="synonyms")
@XmlAccessorType(XmlAccessType.FIELD)
public class OntologyTermSynonymList
{
	public OntologyTermSynonymList()
	{
		
	}
	
	public OntologyTermSynonymList(List<String> synonyms)
	{
		this.synonyms = synonyms;
	}
	
	@XmlElement(name="synonym")
	public List<String> synonyms = new ArrayList<String>();
}
