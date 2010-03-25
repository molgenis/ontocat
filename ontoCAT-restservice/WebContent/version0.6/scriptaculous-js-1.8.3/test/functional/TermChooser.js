var the_object = {}; 
//Static script ID counter
JSONscriptRequest.scriptCounter = 1;

function getTerms(jData) {
	  if (jData == null) {
	    // There was a problem parsing search results
	    return;
	  }
}
// save place array in 'the_object' to make it accessible from mouse event handlers


////////////////////////////////////////////////////////////////////////////////
if ((the_object.term)) {
	
	if (the_object.term.length > 1) {

		alert ("the object is not null");
	document.getElementById('suggestBoxElement').style.visibility = 'visible';
    var suggestBoxHTML  = '';
    // iterate over places and build suggest box content
    for (i=0; i<the_object.term.length-1; i++) {
      // for every Term record we create a html div   // each div gets an id using the array index for later retrieval        // define mouse event handlers to highlight places on mouseover     // and to select a place on click     // all events receive the postalcode array index as input parameter
      suggestBoxHTML += "<div class='suggestions' id=pcId" + i + " onmousedown='suggestBoxMouseDown(" + i +")' onmouseover='suggestBoxMouseOver(" +  i +")' onmouseout='suggestBoxMouseOut(" + i +")'> " + the_object.term[i].label + ' ' + the_object.term[0].accession  +'</div>';
    }
    // display suggest box
    document.getElementById('suggestBoxElement').innerHTML = suggestBoxHTML;
  
    if (the_object.term.length == 1) {
      // exactly one place for term
      // directly fill the form, no suggest box required 
      var placeInput = document.getElementById("placeInput");
      placeInput.value = the_object.term[0].label;
    }
    closeSuggestBox();
  }
}



function TermLabelLookup() {
	var termLabel = document.getElementById("ac4").value;
	
	
    request = 'http://localhost:8080/ontocat/rest/json/searchAll/' + termLabel;

	//alert("still in TermLabelLookup");
	
	// Create a new script object
	  aObj = new JSONscriptRequest(request);
	  // Build the script tag
	  aObj.buildScriptTag();
	  // Execute (add) the script tag
	  //aObj.addScriptTag();
}


function displayJsonTerms() {

	//alert("json data");
}

function JSONscriptRequest(fullUrl) {
	//alert ("Inside JSONscriptRequest");
    // REST request path
    this.fullUrl = fullUrl; 
    // Keep IE from caching requests
    this.noCacheIE = '&noCacheIE=' + (new Date()).getTime();
    // Get the DOM location to put the script tag
    this.headLoc = document.getElementsByTagName("head").item(0);
    // Generate a unique script tag id
    this.scriptId = 'YJscriptId' + JSONscriptRequest.scriptCounter++;
}


function closeSuggestBox() {
	  document.getElementById('suggestBoxElement').innerHTML = '';
	  document.getElementById('suggestBoxElement').style.visibility = 'hidden';
	}

//remove highlight on mouse out event
function suggestBoxMouseOut(obj) {
  document.getElementById('pcId'+ obj).className = 'suggestions';
}

//the user has selected a place name from the suggest box
function suggestBoxMouseDown(obj) {
  closeSuggestBox();
  var placeInput = document.getElementById("placeInput");
  placeInput.value = the_object.term[obj].label ;
}

//function to highlight places on mouse over event
function suggestBoxMouseOver(obj) {
  document.getElementById('pcId'+ obj).className = 'suggestionMouseOver';
}

// buildScriptTag method
//
JSONscriptRequest.prototype.buildScriptTag = function () {

	var termLabel = document.getElementById("ac4").value;
	
	if (!(termLabel) == "") {
		var fullUrl = 'http://localhost:8080/ontocat/rest/json/searchAll/' + termLabel;
	
		//alert("JSONscriptRequest");
		//alert(fullUrl);
		//alert(this.fullUrl);
	
		var http_request ;//= new XMLHttpRequest();	
		if (window.XMLHttpRequest) { // Mozilla, Safari, ...
		    http_request = new XMLHttpRequest();
		} else if (window.ActiveXObject) { // IE
		    http_request = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		http_request.open( "GET", this.fullUrl, true );
		
		http_request.onreadystatechange = function () {
		    if ( http_request.readyState == 4 && http_request.status == 200 ) {
		            the_object = JSON.parse( http_request.responseText );
		            
		            var tok;	            
		            for (i=0; i<the_object.term.length-1; i++) {
		            	tok = the_object.term[i].label + the_object.term[0].accession;
		            	Termtokens[i] = tok;
		            }
		            //termtokens is filled with json data
		            document.getElementById('suggestBoxElement').style.visibility = 'visible';
		            var suggestBoxHTML  = '';
		            // iterate over places and build suggest box content
		            for (i=0; i<the_object.term.length-1; i++) {
		              // for every Term record we create a html div   // each div gets an id using the array index for later retrieval        // define mouse event handlers to highlight places on mouseover     // and to select a place on click     // all events receive the postalcode array index as input parameter
		              suggestBoxHTML += "<div class='suggestions' id=pcId" + i + " onmousedown='suggestBoxMouseDown(" + i +")' onmouseover='suggestBoxMouseOver(" +  i +")' onmouseout='suggestBoxMouseOut(" + i +")'> " + the_object.term[i].label + ' ' + the_object.term[0].accession  +'</div>';
		            }
		            // display suggest box
		            document.getElementById('suggestBoxElement').innerHTML = suggestBoxHTML;
		          
		            if (the_object.term.length == 1) {
		              // exactly one place for term
		              // directly fill the form, no suggest box required 
		              var placeInput = document.getElementById("placeInput");
		              placeInput.value = the_object.term[0].label;
		            }
		            closeSuggestBox();
		          

		        }
		};
		http_request.send(null);
	
	//alert(http_request.responseText);
	//document.write(http_request.responseText);
	
	//alert("Status OK");
	
	/////////////////////////////////////////////////////////////////////////////////////
    // Create the script tag
    this.scriptObj = document.createElement("script");
    
    // Add script object attributes
    this.scriptObj.setAttribute("type", "text/javascript");
    this.scriptObj.setAttribute("src", this.fullUrl + this.noCacheIE);
    this.scriptObj.setAttribute("term", this.scriptId);
	};
};
 

// removeScriptTag method
// 
JSONscriptRequest.prototype.removeScriptTag = function () {
    // Destroy the script tag
    this.headLoc.removeChild(this.scriptObj);  
};

// addScriptTag method
//
JSONscriptRequest.prototype.addScriptTag = function () {
    // Create the script tag
    this.headLoc.appendChild(this.scriptObj);
};



