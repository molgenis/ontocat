$(document).ready(function() {

	addHelpVisualization();
	   
	$("#help1").hide();
	$("#help").hide();

	$("#singleSuggest").autocomplete("http://localhost:8080/ontocat/rest/json/searchAll/brca", {
		matchContains: true,
		minChars: 0
	});
	

	$("#multipleSuggest").autocomplete(cities, {
		multiple: true,
		mustMatch: true,
		autoFill: true
	});

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







