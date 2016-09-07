<div class="select-box g-hrz <#=data.fieldName#>">
	<div class="label u-wrap"><#=data.text#></div>		
	<!-- <input type="text" class="u-full" placeholder="<#=data.placeholder#>" readonly="readonly"/> -->
	<div class="select u-full">
		<select>
			<option >请选择</option>
		  	<#for(var i=0;i<data.selections.length;i++){#>			
				<option value ="<#=data.selections[i].value#>"><#=data.selections[i].text#></option>
			<#}#>
		</select>
	</div>			
	<div class="clear-both"></div>
</div>