$(document).ready(function() {


	$("#submit_term").click(function() {
		var submitted_terms1 = $("#tokenize").val(); 
		//alert($("#tokenize").val());	
		//$("#dialog").dialog();
	 
	});

   
	//the contents of the input is not empty 
	/*if ($("#tokenize").length != 0) { 
	      alert ("this is not empty "); 
		  delete $("#tokenize").value;
	}else {
	alert ("this is  empty")};*/


	$("#tokenize").tokenInput("http://localhost:8080/ontocat/rest/json/searchAll/",  
	     {
			hintText: "Enter a term",
            noResultsText: "No results available",
            searchingText: "Searching...",
            prePopulate: [{"id":"brca", "name":"brca"},{"id":"glioblastoma","name":"glioblastoma"},{"id":"Parahippocampal","name":"Parahippocampal"}],
	    
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
	   onAddToken: function(args) {
           	 // alter tokenInput itself to use "new/url/to/call" instead of 
            	 // the original "url/to/call/"

	       return "http://localhost:8080/ontocat/rest/json/searchAll/";
           }
        
	});

	

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

	
 });


//	$.getJSON("http://localhost:9000/ontocat/json1/searchAll?q=brca2",function(json){
$.getJSON("http://localhost:8080/ontocat/rest/json/searchAll/brca", 
        function(data){      
	//the below work!
        	  //alert("JSON Data 1: " + data.term[0].accession );
        	  //alert("JSON Data:2 " + data.term[0].label );
        	  //alert("JSON Data 3:" +  data.term[1].accession); 
        	  //alert("JSON Data 4: " + data.term[0].label);
  			  //alert("JSON Data 5: " + data.term[0].ontologyAccession);
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

			 /* Return a number of values for a given object */
				//alert( jJSON.getValues("accession",null) );
				//alert( jJSON.getValues("label",null) );
				/* Return a count for a given object */
				// alert( jJSON.getCount("label") );
				/* Return a number of randomized values for a given object */
				//alert( jJSON.getRandomValues("accession",null) ); 
			
			$("<div id='json_results2'><p>").appendTo("body");
			$(jJSON.getValues("label",null)).appendTo("body");
			$("<br/>").appendTo("body");
			$(jJSON.getValues("accession",null)).appendTo("body");
			$("<br/></div>").appendTo("body");

			//send the above in autocomplete item
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
  
   
   beforeSend: function(){
          // Handle the beforeSend event is called before the request is sent, and is passed the XMLHttpRequest object as a parameter.
   },
   error: function() { 
	   //is called if the request fails. It is passed the XMLHttpRequest object, a string indicating the error type, and an exception object if applicable.
   },
   
   dataFilter: function() {
 	  //is called on success. It is passed the returned data and the value of dataType, and must return the (possibly altered) data to pass on to success.
   },

   success: function (data) {
 	//is called if the request succeeds. It is passed the returned data, a string containing the success code, and the XMLHttpRequest object.
    	
   },

   complete: function(){  // Handle the complete event  complete is called when the request finishes, whether in failure or success. It is passed the XMLHttpRequest object, as well as a string containing the success or error code.
		     var submitted_terms1 = $("#tokenize").val(); 
			
			//alert("Input:");
			//alert(submitted_terms1);
     
   }

  // do we have to define var xmlhttp? in order to retrieve data from xmlhttp.responseText 
 });







