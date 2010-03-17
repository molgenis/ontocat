$(document).ready(function() {

   addHelpVisualization();

   $("#tokenize2").tokenInput("http://localhost:8080/ontocat/rest/json/searchAll/",  
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
           
           keyup: function(data) {alert("test");}      
	});
   
   
   $("#tokenize2").keyup( function() {
	   alert("test");
   });
   

   
	$("#tokenize").tokenInput("http://localhost:8080/ontocat/rest/json/searchAll/",  function(data){      
		alert ("aewfaweF");

		alert ($(response));
		alert("Token Input data :  " + data.term[0].accession );
		alert("Token Input data :  " + data.term[0].label );
  	  //$(data.getValues("label",null)).appendTo("body");
	  //$(data.getValues("accession",null)).appendTo("body"); 
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
            
            click: function(data) {alert("test");},
            
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
	$("#help1").hide();
	$("#help").hide();
 });



$.getJSON("http://localhost:8080/ontocat/rest/json/searchAll/brca", 
        function(data){ 
		
		//$("#json_results2").html("JSON Data : " + data.term[0].accession );
       	//$("#json_results2").html( data.term[0].label );
		//$("#json_results2").html("JSON Data 3:" +  data.term[1].accession); 
		//$("#json_results2").html("JSON Data 4: " + data.term[0].label);
		//$("#json_results2").html("JSON Data 5: " + data.term[0].ontologyAccession);
		//$("#json_results2").html("JSON Data 1: " + data.term[0].accession);
        });

$.getJSON("http://localhost:8080/ontocat/rest/json/searchAll/brca",  function(json){
			// Parse JSON objects
			 jJSON["accession"] = (function() {
				response = {
					values: [],
					count: 0
				};
				$.each(json.term ,function(i,term) {
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
				$.each(json.term ,function(i,term) {
						response.count++;
						response.values[i] = term.label;
				});
				return response;
			})();

			 
			$("<div id='json_results2'><p>From Second getJson call").appendTo("body");
			$(jJSON.getValues("label",null)).appendTo("body");
			$("<br/>").appendTo("body");
			$(jJSON.getValues("accession",null)).appendTo("body");
			$("<br/></div>").appendTo("body");

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




function addHelpVisualization() {
	$("a").click(function(event){ 
 		 //alert("As you can see, the link no longer took you to jquery.com");
		 event.preventDefault();  
  		 $(this).hide("slow");
	 });

	$("button").click(function(){
   		$("p").css("background-color","yellow");
 	});

	$("#help").click(function(event) {
	        $("#help_text").slideDown();  // $("#help_text").show();  
	});

	$("#help_text").click(function(event) {
		$("#help_text").slideUp(1000);  // $("#help_text").hide();  
	});


	$("#help1").click(function(event) {
	        $("#help1_text").toggle();
	});
	
};





