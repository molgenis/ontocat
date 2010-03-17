$(document).ready(function() {

	j$ = jQuery.noConflict();
	j$("div").addClass("a");
	
	
	addHelpVisualization();
	
//	j$("#submit_term").click(function() {
//		var submitted_terms1 = j$("#tokenize").val(); 
//		//alert($("#tokenize").val());	
//		//$("#dialog").dialog();
//	 
//	});

   clearContents();
   
   

	j$("#tokenize").tokenInput("http://localhost:8080/ontocat/rest/json/searchAll/",  function(data){      
		
		alert("Token Input data :  " + data.term[0].accession );
		alert("Token Input data :  " + data.term[0].label );
  	  //j$(data.getValues("label",null)).appendTo("body");
	  //j$(data.getValues("accession",null)).appendTo("body"); 
  },
  {
			hintText: "Enter a term",
            noResultsText: "No results available",
            searchingText: "Searching...",
            prePopulate: [{"label":"brca", "accession":"brca"},{"label":"glioblastoma","accession":"glioblastoma"},{"label":"Parahippocampal","accession":"Parahippocampal"}],
            classes: {
                tokenList: "token-input-list-facebook",
                token: "token-input-token-facebook",
                tokenDelete: "token-input-delete-token-facebook",
                selectedToken: "token-input-selected-token-facebook",
                highlightedToken: "token-input-highlighted-token-facebook",
                dropdown: "token-input-dropdown-facebook",
                dropdownItem: "token-input-dropdown-item-facebook",
                dropdownItem2: "token-input-dropdown-item2-facebook",
                selectedDropdownItem: "token-input-selected-dropdown-item-facebook",
                inputToken: "token-input-input-token-facebook"
            },
            
            focus: function(data) {alert("test");},
            
            getItemList: function (json, query) {
            	 return json.completions[query];
            },
            
            formatToken: function(row) {
             return row.value.length > 20 ? row.value.substr(0, 20) + '...' : row.value;
            },
            
            formatTokenTooltip: function(row) {
             return row.value;
            }      
	});
	j$("#help1").hide();
	j$("#help").hide();
 });


//
//$.ajax({
//	  dataType: 'jsonp',
//	  data: 'id=10',
//	  jsonp: 'jsonp_callback',
//	  url: 'http://localhost:8080/ontocat/rest/json/searchAll/brca',
//	  success: function (data) {
//	    alert("JSON Dataaaaaaaaaaaaaaaa : " + data.term[0].accession );
//	  }
//	});

$.getJSON("http://localhost:8080/ontocat/rest/json/searchAll/brca", 
        function(data){ 
		
		//j$("#json_results2").html("JSON Data : " + data.term[0].accession );
       	//j$("#json_results2").html( data.term[0].label );
		//j$("#json_results2").html("JSON Data 3:" +  data.term[1].accession); 
		//j$("#json_results2").html("JSON Data 4: " + data.term[0].label);
		//j$("#json_results2").html("JSON Data 5: " + data.term[0].ontologyAccession);
		//j$("#json_results2").html("JSON Data 1: " + data.term[0].accession);
        });

$.getJSON("http://localhost:8080/ontocat/rest/json/searchAll/brca",  function(json){
			// Parse JSON objects
			 jJSON["accession"] = (function() {
				response = {
					values: [],
					count: 0
				};
				j$.each(json.term ,function(i,term) {
						response.count++;
						response.values[i] = term.accession;
					
				});
				return response;
			})();

		jJSON["label"] = (function() {
				response = {
					values: [],
					count: 0
				};
				j$.each(json.term ,function(i,term) {
						response.count++;
						response.values[i] = term.label;
				});
				return response;
			})();

			 
			j$("<div id='json_results2'><p>From Second getJson call").appendTo("body");
			j$(jJSON.getValues("label",null)).appendTo("body");
			j$("<br/>").appendTo("body");
			j$(jJSON.getValues("accession",null)).appendTo("body");
			j$("<br/></div>").appendTo("body");

	});


var jJSON = {
    getValues: function(obj,num) {
        return jJSON[obj]["values"].slice(0,((num == null) ? jJSON[obj]["values"].length : num));
    },
    getCount: function(obj) {
        return jJSON[obj]["count"];
    },
    getRandomValues: function(obj,num) {
        var a = [];
        var b = jJSON[obj]["values"];
        var c = b.length;
        if (num != null && num < c) {
            c = num;
        }
        for (i = 0; i < c; i++) {
            var e = Math.floor(Math.random() * b.length);
            a[i] = b[e];
            b.splice(e,1);
        }
        return a;
    }
};

//Callback is a function that can be passed as an argument to another function and can be executed after the parent is executed. 

$.ajax({
  
   
   beforeSend: function(){    // Handle the beforeSend event is called before the request is sent, and is passed the XMLHttpRequest object as a parameter.
   },
   error: function() {        //is called if the request fails. It is passed the XMLHttpRequest object, a string indicating the error type, and an exception object if applicable.
   },
   dataFilter: function() {	  //is called on success. It is passed the returned data and the value of dataType, and must return the (possibly altered) data to pass on to success.
   },
   success: function (data) { //is called if the request succeeds. It is passed the returned data, a string containing the success code, and the XMLHttpRequest object.	
   },
   complete: function(){  // Handle the complete event  complete is called when the request finishes, whether in failure or success. It is passed the XMLHttpRequest object, as well as a string containing the success or error code.
	    var submitted_terms1 = $("#tokenize").val(); 
	
		//alert("Input:");
		//alert(submitted_terms1); 
   }
  
 });


function addHelpVisualization() {
	j$("a").click(function(event){ 
 		 //alert("As you can see, the link no longer took you to jquery.com");
		 event.preventDefault();  
  		 j$(this).hide("slow");
	 });

	j$("button").click(function(){
   		j$("p").css("background-color","yellow");
 	});

	j$("#help").click(function(event) {
	        j$("#help_text").slideDown();  // $("#help_text").show();  
	});

	j$("#help_text").click(function(event) {
		j$("#help_text").slideUp(1000);  // $("#help_text").hide();  
	});


	j$("#help1").click(function(event) {
	        j$("#help1_text").toggle();
	});
	
};


function clearContents() {
	//the contents of the input is not empty 
	/*if ($("#tokenize").length != 0) { 
      alert ("this is not empty "); 
	  delete $("#tokenize").value;
	}else {
	alert ("this is  empty")};*/
};




