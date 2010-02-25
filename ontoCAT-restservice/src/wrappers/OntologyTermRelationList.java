package wrappers;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="relations")
@XmlAccessorType(XmlAccessType.FIELD)
public class OntologyTermRelationList
{
	public OntologyTermRelationList()
	{
		
	}
	
	public OntologyTermRelationList(Map<String,List<String>> relationMap)
	{
		for(String key: relationMap.keySet())
		{
			for(String value: relationMap.get(key))
			{
				this.relations.add(new OntologyTermRelation(key, value));
			}
		}
	}

	@XmlElement(name="relationS")
	public List<OntologyTermRelation> relations = new ArrayList<OntologyTermRelation>();
	
}
