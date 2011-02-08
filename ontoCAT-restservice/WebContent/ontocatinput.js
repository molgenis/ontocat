//Ontocat search widget
//use: <input type=""

function showOntocatInput(input){
	showOntocatInput(input);
}

//decorates the select box with the OntocatInput logic
function showOntocatInput(input) {
	var id = input.id;

	//hide previously open div
	if (window.OntocatInputDiv && window.OntocatInputDiv.id != id)
	{
		//alert("hiding "+window.OntocatInputDiv.id);
		window.OntocatInputDiv.style.display = "none";
	}

	//create if not exists
	if (document.getElementById(id) == null) {
		//alert("create"); 
		var myInput = new OntocatInput(input);
		window.OntocatInputDiv = myInput.ontocatDiv;	
		myInput.searchInput.focus();
	} 
	else
	{
		window.OntocatInputDiv = document.getElementById(id);
			
		//hide if shown,
		if (window.OntocatInputDiv.style.display == "block")
		{
			window.OntocatInputDiv.style.display = "none";
			input.focus();
			input.blur();
		}
		// else show 
		else
		{
			window.OntocatInputDiv.style.display = "block";
			input.focus();
			input.blur();			
			OntocatInputDiv.searchInput.focus();		
	}
	}
	return myInput;
}

//constructor
function OntocatInput(input) {
	//alert("constructor"); 
	return this.init(input);
}

//define OntocatInput class prototype
//OntocatInput decorates the select input with mechanisms to automatically populate itself
OntocatInput.prototype = {		
//constructor		
init : function(input) {
	input.blur();
	this.input = input;

	//create the xref <div class="OntocatInput" style="block" id="input.id+'_'+input.form.name">
	this.ontocatDiv = document.createElement("div");
	this.ontocatDiv.className = "OntocatInput";
	this.ontocatDiv.style.display = "block";
	this.ontocatDiv.id = input.id;
	
	//add the div as child of the input
	this.input.parentNode.insertBefore(this.ontocatDiv, this.input.nextSibling);
	this.ontocatDiv.setAttribute("OntocatInputObject", this); //???

	//add the search box <label>search:</label><input /><br>
	this.searchLabel = document.createElement("label");
	this.searchLabel.appendChild(document.createTextNode("search:"));
	this.ontocatDiv.appendChild(this.searchLabel);
	this.searchInput = document.createElement("input");
	this.ontocatDiv.appendChild(this.searchInput);
	this.ontocatDiv.appendChild(document.createElement("br"));

	//initialize with current value (i.e. the first option that is set) 
	this.ontocatDiv.value = this.input.value;
	if(this.input.options.length > 0)
	{
		this.searchInput.value = this.input.options[0].text;
	} 

	//add a handler to the search box to update select when changed 
	var _this = this;
	this.addEvent(this.searchInput, "keyup", function(e) {
		if(_this.searchInput.value.length > 0)
		{
			_this.reload();
		}
		
		//if only one option auto-select
		if(_this.selectInput.options.length == 1)
		{
			_this.selectInput.options[0].style.background = "blue";
			_this.selectInput.options[0].style.color = "white";
		}
		
		//on 'enter' and if only one option, choose that
		if( (e.which == 13 || e.keyCode == 13) && _this.selectInput.options.length == 1)
		{
			_this.selectInput.selectedIndex = 0;
			_this.select(_this);
			return;
		}
		//on 'esc' close dialog
		if(e.which == 27)
		{
			_this.ontocatDiv.style.display = "none";
		}
	});

	//add the select box 
	this.selectInput = document.createElement("select");	
	//this.selectInput.multiple = "true";
	
	this.selectInput.style.width = "100%";
	this.ontocatDiv.appendChild(this.selectInput);

	//add handler so the input is updated when clicking one select option
	var _this = this;	
	this.addEvent(this.selectInput, "change", function(e) {
		_this.select(_this);
	});	
	
	if(this.searchInput.value.length > 0)
	{
		this.reload();
	}
	window.focus();
	this.input.blur();
	this.searchInput.focus();
},
/*Copy the current selected item to 'input' and close dialog*/
select : function(_this)
{
	//alert('clicked');
	if(_this.selectInput.options.length > 0)
	{
		//alert("clicked option "+ _this.selectInput.options[_this.selectInput.selectedIndex].value);
		
		//remove existing options this.selectInput.options; 
		for (i = this.input.options.length - 1; i >=0; i--) 
		{
			this.input.removeChild(this.input.options[i]);
		}
		
		//create a new option with selected value
		var option = document.createElement("option");
		option.value = _this.selectInput.options[_this.selectInput.selectedIndex].value;
		option.text = _this.selectInput.options[_this.selectInput.selectedIndex].text;
		option.selected = true;
		this.input.appendChild(option);
		
		//hide the search box
		_this.ontocatDiv.style.display = "none";
	}	
},
/* reload function*/
reload : function() {
	//alert("reload"); 

	//load the select box contents via AJAX 
	//xref_label instead of xref_labels for downward compatability
	var url = "rest/json/searchAll/"+this.searchInput.value;
	//alert(url);

	// branch for native XMLHttpRequest object
	var _this = this;

	req = false;
	if (window.XMLHttpRequest  && !(window.ActiveXObject)) //NOT IE
	{
		req = new XMLHttpRequest();
	}
	else if (window.ActiveXObject) {
		req = new ActiveXObject("Microsoft.XMLHTTP");
	}

	if (req) {
		req.onreadystatechange = function(e) {
			// only if req shows "complete" 
			if (req.readyState == 4) {
				// only if "OK" 
			if (req.status == 200) {
				// ...processing statements go here...
				var options = eval('(' + req.responseText + ')');
				//delegate handling to redrawOptions
				_this.redrawOptions(options['term']);
			} else {
				alert("There was a problem retrieving the XML data:\n"
						+ req.statusText);
			}
		}
		};
		req.open("GET", url, true);
		req.send("");
	}
},
redrawOptions : function(options) {
	//remove existing options this.selectInput.options; 
	for (i = this.selectInput.options.length - 1; i >=0; i--) 
	{
		this.selectInput.removeChild(this.selectInput.options[i]);
	}

	//add empty option to set 'null' when search is empty
	if(this.searchInput.value == "")
	{
		this.selectInput.appendChild(document.createElement("option"));
	}	
	
	//add the current options
	for ( var i in options) {
		var o = options[i];
		var option = document.createElement("option");

		//add the value
		option.value = o['accession'];
		
		option.text = o['label']+'('+o['accession']+')';	

		//add option to select box
		this.selectInput.appendChild(option);
	}
	
	//resize select to fit
	this.selectInput.size = options.length <= 10 ? options.length : 10;
},
///* TODO: this method is unused? */
//handleAjax: function(e) {
//	//alert("render");
//		// only if req shows "complete" 
//		if (req.readyState == 4) 
//		{
//			// only if "OK" 
//			if (req.status == 200) 
//			{
//				var options = eval('(' + req.responseText + ')');
//				this.redrawOptions(options);
//			} else 
//			{
//			alert("There was a problem retrieving the XML data:\n"
//					+ req.statusText);
//			}
//		}
//	},
/*helper function to add events for both IE and FF in one call
@obj = the oject to add the event ont
@eventname = name of the event minus the 'on', e.g. 'click' means 'onclick'
@func = the function to be called if this event happens
 */
addEvent : function(obj, eventname, func) {
	//alert(eventname);
	if (navigator.userAgent.match(/MSIE/)) {
		obj.attachEvent("on" + eventname, func);
	} else {
		obj.addEventListener(eventname, func, true);
	}
}
}