package wrappers;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="relations")
@XmlAccessorType(XmlAccessType.FIELD)
public class OntologyTermRelation
{
	public OntologyTermRelation()
	{
		
	}
	
	public OntologyTermRelation(String type, String termAccession)
	{
		this.type = type;
		this.termAccession = termAccession;
	}
	
	public String type;
	public String termAccession;
}
