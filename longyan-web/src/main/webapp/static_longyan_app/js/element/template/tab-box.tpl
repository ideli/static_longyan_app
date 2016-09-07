<div class="tab-box g-hrz <#=data.fieldName#>">
	<div class="tab-container-box">
		<#for(var i=0;i<data.label.length;i++){#>
			<div class="tab-item-box" index="<#=i#>" style="width:<#=data.column_width#>">
				<#=data.label[i]#>
			</div>
		<#}#>
	</div>
</div>