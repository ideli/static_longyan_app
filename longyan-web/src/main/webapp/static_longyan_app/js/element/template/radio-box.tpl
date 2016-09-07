<div class="radio-box input-box g-hrz <#=data.fieldName#>">
	<div class="label u-wrap"><#=data.text#></div>		
	<div class="u-full">
	<!-- <input type="<#=data.type||'text'#>" class="u-full" placeholder="<#=data.placeholder#>" /> -->
	<#if(data.selections){#>
	<#for(var i=0;i<data.selections.length;i++){#>
		<div class="selection" index="<#=i#>" data-value="<#=data.selections[i].value#>">
			<div class="selection-button"><i class="icon iconfont selection-unselect">&#xe60e;</i></div>
			<div class="selection-text"><#=data.selections[i].text#></div>			
			<div class="clear-both"></div>
		</div>		
	<#}}#>
	</div>
	<div class="clear-both"></div>
	<input type="hidden"  />
</div>