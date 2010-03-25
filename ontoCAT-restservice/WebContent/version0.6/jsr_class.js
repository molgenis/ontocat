function displayJsonTerms() {

	//alert("json data");
}


function addElement(the_object) {
	  var ni = document.getElementById('myDiv');
	  var numi = document.getElementById('theValue');
	  var num = (document.getElementById('theValue').value -1)+ 2;
	  numi.value = num;
	  var newdiv = document.createElement('div');
	  var divIdName = 'my'+num+'Div';
	  newdiv.setAttribute('id',divIdName);
	  
	  newdiv.innerHTML = "<ul><li> " + the_object.term.length + "Results" + "</li>";
	  for (i=0; i<the_object.term.length; i++) {
		  newdiv.innerHTML +=  "<li>" + the_object.term[i].label + "</li>";
	  }
	  ni.appendChild(newdiv);
	}

function JSONscriptRequest(fullUrl) {
	
    // REST request path
    this.fullUrl = fullUrl; 
    // Keep IE from caching requests
    this.noCacheIE = '&noCacheIE=' + (new Date()).getTime();
    // Get the DOM location to put the script tag
    this.headLoc = document.getElementsByTagName("head").item(0);
    // Generate a unique script tag id
    this.scriptId = 'YJscriptId' + JSONscriptRequest.scriptCounter++;
}

// Static script ID counter
JSONscriptRequest.scriptCounter = 1;

// buildScriptTag method
//
JSONscriptRequest.prototype.buildScriptTag = function () {

	//alert("inside JSONscriptRequest");
	//alert(this.fullUrl);

	var the_object = {}; 
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
	            
	            //alert(http_request.responseText);
	        	//set the result inside the dropdown menu 

	            // as long as you get the data .."open" the dropdown box 
	            addElement( the_object);
	            
	            var tok;
	            autoComplete.Set("mygroup4", new Array( 
	            	function () {
	            		for (i=0; i<the_object.term.length-1; i++) {
	            			tok = the_object.term[i].label + the_object.term[0].accession;
	            			return  tok;
	            			 
	          	   };},
	            		
	            		"twenty", "tweak", "tool", 
	            	    "two hundred", "testing", "two-thirds", "terran", "tomato", "tower", "twin", "task", 
	            	    "toolbar", "test")); 
	            //document.write(http_request.responseText);
	        	//document.getElementById("termLabelInput").setAttribute(name, http_request.responseText)
	        	//document.getElementById("termLabelInput").value = http_request.responseText;

	             //document.getElementById("termLabelSuggest").value = http_request.responseText;
	             document.getElementById("termLabelSuggest").value =  "<ul><li> " + the_object.term.length  + "</li>";
	             document.getElementById("termLabelSuggest").value +=  "<li>" + the_object.term[0].label + "</li>";
	             document.getElementById("termLabelSuggest").value +=  "<li>" + the_object.term[0].accession + "</li>";
	            
	             //format data 
	             //var jsonT = eval('('+ the_object.term +')');
	             

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
}
 

// removeScriptTag method
// 
JSONscriptRequest.prototype.removeScriptTag = function () {
    // Destroy the script tag
    this.headLoc.removeChild(this.scriptObj);  
}

// addScriptTag method
//
JSONscriptRequest.prototype.addScriptTag = function () {
    // Create the script tag
    this.headLoc.appendChild(this.scriptObj);
}



