package plugins.ontocatbrowser;

import java.util.HashMap;
import java.util.Map;

import org.molgenis.framework.db.Query;

import uk.ac.ebi.ontoapi.OntologyService;
import uk.ac.ebi.ontoapi.ols.OlsOntologyService;

public class Tests
{

	public Tests() throws Exception
	{
		// EBI service
		//Query qs = new QueryServiceLocator().getOntologyQuery();

		// OntoCat service
		OntologyService os = new OlsOntologyService();

		
		/** BEING TESTED */
		//HashMap<String, String> map = qs.getTermRelations("APO:0000002", "APO");
		//System.out.println(map);
		
		HashMap<String, String> map2 = new HashMap<String, String>();
		Map<String, String[]> os1 = os.getTerm("APO:0000002").getRelations();
		for(String key : os1.keySet()){
			String[] valArr = os1.get(key);
			String concatVal = "";
			for(String subVal : valArr){
				concatVal += subVal + ", ";
			}
			concatVal = concatVal.substring(0, concatVal.length()-2);
			map2.put(key, concatVal);
		}
		System.out.println(map2);
		
		/** CONFIRMED EQUAL
		  
		  
		  
		  
		  	HashMap<String, String> resultSet = qs.getPrefixedTermsByName("piet", false);
			System.out.println(resultSet);
			-- to --
			HashMap<String, String> resultSet2 = new HashMap<String, String>();
			List<OntologyTerm> on2 = os.searchAll("piet");
			for(OntologyTerm o : on2){
				resultSet.put(o.getAccession(), o.getLabel());
			}
			System.out.println(resultSet);
		  
		  
			HashMap<String, String> res = qs.getTermsByName("piet", "NEWT", false);
			System.out.println(res);
			-- to --
			HashMap<String, String> res2 = new HashMap<String, String>();
			List<OntologyTerm> on2 = os.searchOntology("NEWT", "piet");
			for(OntologyTerm o : on2){
				res2.put(o.getAccession(), o.getLabel());
			}
			System.out.println(res2);
		
		 
		 
			HashMap<String, String> map = qs.getTermMetadata("APO:0000002", "APO");
			System.out.println(map);
			-- to --
			HashMap<String, String> map2 = new HashMap<String, String>();
			Map<String, String[]> os1 = os.getTerm("APO:0000002").getAnnotations();
			for(String key : os1.keySet()){
				String[] valArr = os1.get(key);
				String concatVal = "";
				for(String subVal : valArr){
					concatVal += subVal + "";
				}
				map2.put(key, concatVal);
			}
			System.out.println(map2);




		  	LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(qs.getRootTerms("SPD"));
			System.out.println(map.toString());
			-- to --
			LinkedHashMap<String, String> map2 = new LinkedHashMap<String, String>();
			List<OntologyTerm> on2 = os.getRootTerms("SPD");
			for(OntologyTerm o : on2){
				map2.put(o.getAccession(), o.getLabel());
			}
			System.out.println(map2);
		  
		  
		  
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(qs.getTermChildren("PW:0000180", "PW", 1, new int[] { 2 }));
			System.out.println(map.toString());
			-- to --
			LinkedHashMap<String, String> map2 = new LinkedHashMap<String, String>();
			List<OntologyTerm> on2 = os.getTerm("PW:0000180").getChildren();
			for(OntologyTerm o : on2){
				map2.put(o.getAccession(), o.getLabel());
			}
			System.out.println(map2);
			
			
			
		  
			HashMap<String, String> map = qs.getTermParents("ZEA:0015162", "ZEA");
			System.out.println(map.toString());
			-- to --
			HashMap<String, String> map2 = new HashMap<String, String>();
			List<OntologyTerm> on2 = os.getTerm("ZEA:0015162").getParents();
			for(OntologyTerm o : on2){
			map2.put(o.getAccession(), o.getLabel());
			}
			System.out.println(map2);
		  
		  
		  
			HashMap<String, String> map = qs.getTermXrefs("ZEA:0015162", "ZEA");
			System.out.println(map.toString());
			-- CAN FIND MORE THAN ORIGINAL --
			HashMap<String, String> map2 = new HashMap<String, String>();
			Map<String, String[]> os1 = os.getTerm("ZEA:0015162").getRelations();
			for(String key : os1.keySet()){
					String[] valArr = os1.get(key);
					String concatVal = "";
					for(String subVal : valArr){
						concatVal += subVal + " ";
					}
					map2.put(key, concatVal);
			}
			System.out.println(map2.toString());
		  
		  
			String term = os.getTerm("TTO:1001979").getLabel();
			System.out.println(term);
			-- to --
			term = qs.getTermById("TTO:1001979", "TTO");
			System.out.println(term);
			
			
			
			HashMap on1 = qs.getOntologyNames();
			System.out.println(on1.toString());
			-- to --
			List<Ontology> on2 = os.getOntologies();
			HashMap<String, String> on3 = new HashMap<String, String>();
			for(Ontology o : on2){
				on3.put(o.getOntologyAccession(), o.getLabel());
			}
			System.out.println(on3.toString());
			
		
		
		*/
	}

	public static void main(String[] args) throws Exception {
		new Tests();
	}
}
