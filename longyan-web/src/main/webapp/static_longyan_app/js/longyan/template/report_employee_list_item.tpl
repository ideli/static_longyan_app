<div class="report-employee-list-item-box g-hrz" data-key="<#=data.id#>" <#if(data.url){#>onClick="window.location.href='<#=data.url#>'"<#}#>>
	<div class="top u-wrap <#if(data.index<4){#>top-highlight<#}#>">
		<#=data.index#>
	</div>
	<div class="left u-full">
		<div class="xingMing"><#=data.name#></div>		
		<div class="others">			
			<div class="hu-icon basic-small-icon">户</div>
			<div class="label left-label">住宅 <#=data.inputMemberAmount#></div>						
			<div class="qu-icon basic-small-icon">区</div>
			<div class="label right-label">小区 <#=data.inputCommunityAmount#></div>	
			<div class="clear-both"></div>
		</div>
	</div>
	<div class="right u-wrap">
		<i class="icon iconfont icon-lylrightarrow"></i>
	</div>
	<div class="clear-both"></div>
</div>