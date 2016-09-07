<div class="group-button-box <#=data.fieldName#> <#=data.clazz#>">
	<#if(data.btns && data.btns.length>0){#>
		<#for(var i=0;i<data.btns.length;i++){#>
			<#var btn = data.btns[i];
				if(btn.text){
			#>
			<div num="<#=i#>" class="group-button-item <#=btn.clazz#>">
				<div class="text"><#=btn.text#></div>
			</div>
			<#}#>
		<#}#>
	<#}#>
	
</div>