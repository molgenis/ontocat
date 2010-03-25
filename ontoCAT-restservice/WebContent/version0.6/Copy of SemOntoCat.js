function TermLabelLookup() {
	var termLabel = document.getElementById("termLabelInput").value;
	
	//alert(termLabel);
	
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
// postalcodes is filled by the JSON callback and used by the mouse event handlers of the suggest box
var postalcodes;

// this function will be called by our JSON callback
// the parameter jData will contain an array with postalcode objects
function getLocation(jData) {
  if (jData == null) {
    // There was a problem parsing search results
    return;
  }

  // save place array in 'postalcodes' to make it accessible from mouse event handlers
  postalcodes = jData.postalcodes;
    	
  if (postalcodes.length > 1) {
    // we got several places for the postalcode
    // make suggest box visible
    document.getElementById('suggestBoxElement').style.visibility = 'visible';
    var suggestBoxHTML  = '';
    // iterate over places and build suggest box content
    for (i=0;i< jData.postalcodes.length;i++) {
      // for every postalcode record we create a html div 
      // each div gets an id using the array index for later retrieval 
      // define mouse event handlers to highlight places on mouseover
      // and to select a place on click
      // all events receive the postalcode array index as input parameter
      suggestBoxHTML += "<div class='suggestions' id=pcId" + i + " onmousedown='suggestBoxMouseDown(" + i +")' onmouseover='suggestBoxMouseOver(" +  i +")' onmouseout='suggestBoxMouseOut(" + i +")'> " + postalcodes[i].countryCode + ' ' + postalcodes[i].postalcode + '    ' + postalcodes[i].placeName  +'</div>';
    }
    // display suggest box
    document.getElementById('suggestBoxElement').innerHTML = suggestBoxHTML;
  } else {
    if (postalcodes.length == 1) {
      // exactly one place for postalcode
      // directly fill the form, no suggest box required 
      var placeInput = document.getElementById("placeInput");
      placeInput.value = postalcodes[0].placeName;
    }
    closeSuggestBox();
  }
}


function closeSuggestBox() {
  document.getElementById('suggestBoxElement').innerHTML = '';
  document.getElementById('suggestBoxElement').style.visibility = 'hidden';
}


// remove highlight on mouse out event
function suggestBoxMouseOut(obj) {
  document.getElementById('pcId'+ obj).className = 'suggestions';
}

// the user has selected a place name from the suggest box
function suggestBoxMouseDown(obj) {
  closeSuggestBox();
  var placeInput = document.getElementById("placeInput");
  placeInput.value = postalcodes[obj].placeName;
}

// function to highlight places on mouse over event
function suggestBoxMouseOver(obj) {
  document.getElementById('pcId'+ obj).className = 'suggestionMouseOver';
}


// this function is called when the user leaves the postal code input field
// it call the geonames.org JSON webservice to fetch an array of places 
// for the given postal code 
function postalCodeLookup() {

  var country = document.getElementById("countrySelect").value;

  if (geonamesPostalCodeCountries.toString().search(country) == -1) {
     return; // selected country not supported by geonames
  }
  // display 'loading' in suggest box
  document.getElementById('suggestBoxElement').style.visibility = 'visible';
  document.getElementById('suggestBoxElement').innerHTML = '<small><i>loading ...</i></small>';

  var postalcode = document.getElementById("postalcodeInput").value;

  //request = 'http://www.geonames.org/postalCodeLookupJSON?postalcode=' + postalcode  + '&country=' + country  + '&callback=getLocation';

  // Create a new script object
  aObj = new JSONscriptRequest(request);
  // Build the script tag
  aObj.buildScriptTag();
  // Execute (add) the script tag
  aObj.addScriptTag();
}

// set the country of the user's ip (included in geonamesData.js) as selected country 
// in the country select box of the address form
function setDefaultCountry() {
  var countrySelect = document.getElementById("countrySelect");
  for (i=0;i< countrySelect.length;i++) {
    // the javascript geonamesData.js contains the countrycode
    // of the userIp in the variable 'geonamesUserIpCountryCode'
    if (countrySelect[i].value == geonamesUserIpCountryCode) {
      // set the country selectionfield
      countrySelect.selectedIndex = i;
    }
  }
  countrySelect.selectedIndex = 3;

}