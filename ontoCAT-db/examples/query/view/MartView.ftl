<!--Date:        May 10, 2009
 * Template:	PluginScreenFTLTemplateGen.ftl.ftl
 * generator:   org.molgenis.generators.screen.PluginScreenFTLTemplateGen 3.3.0-testing
 * 
 * THIS FILE IS A TEMPLATE. PLEASE EDIT :-)
-->
<#macro plugin_query_view_MartView screen>
<!-- normally you make one big form for the whole plugin-->
<form method="post" enctype="multipart/form-data" name="${screen.name}">
	<!--needed in every form: to redirect the request to the right screen-->
	<input type="hidden" name="__target" value="${screen.name}"" />
	<!--needed in every form: to define the action. This can be set by the submit button-->
	<input type="hidden" name="__action" />
	
<!-- this shows a title and border -->
	<div class="formscreen">
		<div class="form_header" id="${screen.getName()}">
		${screen.label}
		</div>
		 
		<#--optional: mechanism to show messages-->
		<#list screen.getMessages() as message>
			<#if message.success>
		<p class="successmessage">${message.text}</p>
			<#else>
		<p class="errormessage">${message.text}</p>
			</#if>
		</#list>
		
		<div class="screenbody">
			<div class="screenpadding">	
<script>



</script>			
			
			
<#--begin your plugin-->
Current query:
<#if screen.sql?exists>${screen.sql}<#else>NONE</#if>
<br/>
<#list screen.entities as entity>
${entity.name}<br/>
<#list entity.fields as field>
&nbsp;&nbsp;<input type="checkbox" name="select_field" VALUE="${entity.name}.${field.name}">${field.name}<br/>
</#list>
</#list>
<input type="submit" onclick="__action.value='update_selection';return true;" value="update_selection"/>

<#--<table>
<#list screen.inputs as input>
<tr><td><label>${input.label}</label></td><td>${input.html}</td></tr>
</#list>
<table>
<#list screen.actions as action>${action.html}</#list-->
	
<#--end of your plugin-->	
			</div>
		</div>
	</div>
</form>
</#macro>
