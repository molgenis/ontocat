
/////////////////////////////////////////////////////////////////////

function RunSparql() {


	// http://www.thefigtrees.net/lee/blog/2006/04/sparql_calendar_demo_a_sparql.html
	var sparqler = new SPARQL.Service("http://sparql.org/sparql");
	// graphs and prefixes defined here
	// are inherited by all future queries
	sparqler.addDefaultGraph("http://thefigtrees.net/lee/ldf-card");
	sparqler.addNamedGraph("http://torrez.us/elias/foaf.rdf");
	sparqler.setPrefix("foaf", "http://xmlns.com/foaf/0.1/"); 
	sparqler.setPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");

	// "json" is the default output format
	sparqler.setOutput("json");
	var query = sparqler.createQuery();

	// these settings are for this query only
	//TODO : fill in settings
	//query.addDefaultGraph(...);
	//query.addNamedGraph(...);
	//query.setPrefix(...);
	
	// query wrappers:

	// passes standard JSON results object to success callback
//	query.setPrefix("ldf", "http://thefigtrees.net/lee/ldf-card#");
	query.setPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
	query.setPrefix("foaf", "http://xmlns.com/foaf/0.1/");
	//TODO : FILL IN query 
	//query.query(
	//		  "SELECT ?who ?mbox WHERE { ldf:LDF foaf:knows ?who . ?who foaf:mbox ?mbox }",
	//		  {failure: onFailure, success: function(json) { for (var x in json.head.vars) { ... } ...}}
	//		);

	query.query  ("SELECT DISTINCT ?name WHERE {?x rdf:type foaf:Person .  ?x foaf:name ?name }	LIMIT 10");
	
	// passes boolean value to success callback
	/*query.ask(
	  "ASK ?person WHERE { ?person foaf:knows [ foaf:name "Dan Connolly" ] }",
	  {failure: onFailure, success: function(bool) { if (bool) ... }}
	);*/ 

	// passes a single vector (array) of values 
	// representing a single column of results 
	// to success callback

	query.setPrefix("ldf", "http://thefigtrees.net/lee/ldf-card#");
	/*var addresses = query.selectValues(
	  "SELECT ?mbox WHERE { _:someone foaf:mbox ?mbox }",
	  {failure: onFailure, success: function(values) { for (var i = 0; i < values.length; i++) { ... values[i] ...} } }
	);*/ 

	// passes a single value representing a single 
	// row of a single column (variable) to success callback
	alert(" sparql lib");
	
    query.setPrefix("ldf", "http://thefigtrees.net/lee/ldf-card#");
	var myAddress = query.selectSingleValue(
	  "SELECT ?mbox WHERE {ldf:LDF foaf:mbox ?mbox }",
	  {failure: onFailure, success: function(value) { alert("value is: " + value); } }
	); 
		
	// shortcuts for all of the above 
	// (w/o ability to set any query-specific graphs or prefixes)
	/*sparqler.query(...);
	sparqler.ask(...);
	sparqler.selectValues(...);
	sparqler.selectSingleValue(...);
*/

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
