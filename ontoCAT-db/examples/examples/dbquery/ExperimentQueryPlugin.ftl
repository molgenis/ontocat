<#--this you need in every plugin. 
Make sure the macro name is changed to match your plugin.--> 
<#macro ExperimentQuery screen>
<#assign model = screen.model>
<!-- normally you make one big form for the whole plugin-->
<form method="post" enctype="multipart/form-data" name="${screen.name}">
	<!--needed in every form: to redirect the request to the right screen-->
	<input type="hidden" name="__target" value="${screen.name}"" />
	<!--needed in every form: to define the action. This can be set by the submit button-->
	<input type="hidden" name="__action" />
 
<#--begin your plugin-->

<!--show error message if needed-->	
<#if model.errorMessage != ""><span style="color:red">Error: ${model.getErrorMessage()}!</span></#if>
<br/>

<!-- list of experiments-->
Below the list of known experiments:
<br/>

<!-- this plugin has a search option -->
<label>Search:</label><input name="search_terms" value="${model.searchTerms}"/>
<input type="submit" value="Search" onclick="__action.value='do_update_search';return true;"/>
<br/>

<!-- the list of experiments found-->
<ul>
<#list model.experiments as experiment>
<li>${experiment.name}</li>
</#list>
</ul>
<br/>
	

<#--end of your plugin-->	

</form>
</#macro>