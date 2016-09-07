<div class="tool-bar-box  <#=data.fieldName#>">
	<div class="bar-box-group">

		<ul class="g-hrz">

			<#if(data.item&&data.item.length>0){#>
				<#for(var i=0;i<data.item.length;i++){#>
					<li num="<#=i#>" class="bar-box-group-item" style="width:<#=data.item[i].width#>;">
						<i class="bar-box-group-item-icon <#=data.item[i].icon_class#>"></i>
						<span class="bar-box-group-item-text"><#=data.item[i].text#></span>
					</li>
				<#}#>
			<#}#>
			
		</ul>

	</div>	
</div>