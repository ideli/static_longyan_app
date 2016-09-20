<div class="card-select-box <#=data.fieldName#>">
	<div class="label"><#=data.text#></div>		
	<div class="u-full">	
		<#if(data.selections){#>
		<#for(var i=0;i<data.selections.length;i++){#>
			<div class="selection" index="<#=i#>" data-value="<#=data.selections[i].value#>">

				<div class="selection-text"><#=data.selections[i].text#></div>			
				<div class="clear-both"></div>
			</div>		
		<#}}#>
	</div>
	<div class="clear-both"></div>
	<input type="hidden"  />
</div>