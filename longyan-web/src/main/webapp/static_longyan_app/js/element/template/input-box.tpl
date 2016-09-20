<div class="input-box g-hrz <#=data.fieldName#>">
	<#if(!data.no_label){#>
		<div class="label u-wrap"><#=data.text#></div>		
	<#}#>
	<input type="<#=data.type||'text'#>" class="u-full" placeholder="<#=data.placeholder#>"  />
	<div class="iconfont icon-longyanwarn label-warn"></div>
	<div class="iconfont icon-longyandel1 label-del"></div>
	<#if(data.label_right){#>
		<div class="label-right"><#=data.label_right#></div>
	<#}#>	
	<div class="clear-both"></div>
</div>