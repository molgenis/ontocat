function onSparqlFailure() {
	alert("problem with Sparql query");
}


function RunSparql() {


	// http://www.thefigtrees.net/lee/blog/2006/04/sparql_calendar_demo_a_sparql.html
	var sparqler = new SPARQL.Service("http://sparql.org/sparql");

	
	
	//query.addDefaultGraph(...); sparqler.addDefaultGraph("http://thefigtrees.net/lee/ldf-card");
	//query.addNamedGraph(...);   sparqler.addNamedGraph("http://torrez.us/elias/foaf.rdf");
	//query.setPrefix(...);
	// graphs and prefixes defined here are inherited by all future queries
	sparqler.setPrefix("foaf", "http://xmlns.com/foaf/0.1/");
	sparqler.setPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
	
	//sparqler.setPrefix("rdf", "")

	// "json" is the default output format
	sparqler.setOutput("json");
	var query = sparqler.createQuery();
	
	// query wrappers:

	// passes standard JSON results object to success callback
    //query.setPrefix("ldf", "http://thefigtrees.net/lee/ldf-card#");
	query.setPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
	query.setPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
	query.setPrefix("xsd", "http://www.w3.org/2001/XMLSchema#");
    
	//query.query(
	//		  "SELECT ?who ?mbox WHERE { ldf:LDF foaf:knows ?who . ?who foaf:mbox ?mbox }",
	//		  {failure: onFailure, success: function(json) { for (var x in json.head.vars) { ... } ...}}
	//		);

	//query.query  ("SELECT DISTINCT ?name WHERE {?x rdf:type foaf:Person .  ?x foaf:name ?name }	LIMIT 10");
	//query.query("SELECT ?x WHERE { ?x  <http://www.w3.org/2001/vcard-rdf/3.0#FN>  'John Smith' }" );
	
	
	query.setPrefix("dc", "http://purl.org/dc/elements/1.1/");
	query.query("SELECT ?book ?who WHERE { ?book dc:creator ?who }"); 
	var tmp1 = query.selectValues(
 		   "SELECT ?book ?who WHERE { ?book dc:creator ?who	}",
 		  {
 			   failure: onSparqlFailure, 
 			   success: function(values) { 
 			     alert("query 1");
 			   	 for (var i = 0; i < values.length; i++) { 
 			   		alert(values[i]);
 			   	 }
 			   	}
 		   }
 		); 
			
	alert(" sparql lib1");

	var vcard = query.selectSingleValue(
			  "SELECT ?x WHERE { ?x  <http://www.w3.org/2001/vcard-rdf/3.0#FN>  'John Smith' }",
			  {failure: onSparqlFailure,
			   success: function(value) { alert("value is: " + value); } }
			);
	
	var tmp = query.selectValues(
	   "SELECT ?x WHERE { ?x  <http://www.w3.org/2001/vcard-rdf/3.0#FN>  'John Smith' }",
	  {
		   failure: onSparqlFailure, 
		   success: function(values) { 
		   	 for (var i = 0; i < values.length; i++) { 
		   		alert(values[i]);
		   	 }
		   	}
	   }
	); 
	
	alert(" Running sparql query 3...");
	query.setPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");

	
	    query.setPrefix("d2r", "http://sites.wiwiss.fu-berlin.de/suhl/bizer/d2r-server/config.rdf#");
	    query.setPrefix("vcard", "http://www.w3.org/2001/vcard-rdf/3.0#");
	    query.setPrefix("iswc", "http://annotation.semanticweb.org/iswc/iswc.daml#");
	    query.setPrefix("xsd", "http://www.w3.org/2001/XMLSchema#");
	    query.setPrefix("doap", "http://usefulinc.com/ns/doap#");
	    query.setPrefix("foaf", "http://xmlns.com/foaf/0.1/");
	    query.setPrefix("owl", "http://www.w3.org/2002/07/owl#");
	    query.setPrefix("db1", "http://www.example.org/dbserver01/db01#");
	    query.setPrefix("swrc", "http://swrc.ontoware.org/ontology#");
	    query.setPrefix("dc", "http://purl.org/dc/elements/1.1/");
	    query.setPrefix("dctype", "http://purl.org/dc/dcmitype/");
	    query.setPrefix("admin", "http://webns.net/mvcb/");
	    query.setPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
	
	    var tmp2 = query.selectValues(
	    		   "SELECT DISTINCT * WHERE { ?s ?p ?o	} LIMIT 10",
	    		  {
	    			   failure: onSparqlFailure, 
	    			   success: function(values) { 
	    			   	 for (var i = 0; i < values.length; i++) { 
	    			   		alert(values[i]);
	    			   	 }
	    			   	}
	    		   }
	    		); 
	   
	alert(" sparql lib2");

	// passes boolean value to success callback
	/*query.ask(
	  "ASK ?person WHERE { ?person foaf:knows [ foaf:name "Dan Connolly" ] }",
	  {failure: onFailure, success: function(bool) { if (bool) ... }}
	);*/ 

	// passes a single vector (array) of values 
	// representing a single column of results 
	// to success callback

	//query.setPrefix("ldf", "http://thefigtrees.net/lee/ldf-card#");
	/*var addresses = query.selectValues(
	  "SELECT ?mbox WHERE { _:someone foaf:mbox ?mbox }",
	  {failure: onFailure, success: function(values) { for (var i = 0; i < values.length; i++) { ... values[i] ...} } }
	);*/ 

	alert(" sparql lib3");
	
    query.setPrefix("ldf", "http://thefigtrees.net/lee/ldf-card#");
	var myAddress = query.selectSingleValue(
	  "SELECT ?mbox WHERE {ldf:LDF foaf:mbox ?mbox }",
	  {failure: onSparqlFailure, success: function(value) { alert("value is: " + value); } }
	); 
		
	

	alert("end of sparql lib");

}

function TermLabelLookup() {
	var termLabel = document.getElementById("termLabelInput").value;
	
	alert(termLabel);
	
    request = 'http://localhost:8080/ontocat/rest/json/searchAll/' + termLabel;

	//alert(request);
	
	// Create a new script object
	  aObj = new JSONscriptRequest(request);
	  // Build the script tag
	  aObj.buildScriptTag();
	  // Execute (add) the script tag
	  //aObj.addScriptTag();
}



/////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////
