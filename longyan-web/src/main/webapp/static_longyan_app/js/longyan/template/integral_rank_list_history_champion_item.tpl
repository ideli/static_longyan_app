	<div class="integral-history-item-box g-hrz" data-key="<#=data.id#>" >
		<div class="top u-wrap">
			<#=data.month#>月
		</div>
		<div class="left u-full bottom-border">
			<div class="touxiang"><!-- <#=data.touxiang#> --> <img class="integral-image" src="<#=window.resource.image#>/defaultusertouxiang.png" /></div>		
			<div class="xingMing"><#=data.employeeXingMing#></div>
			<div class="others">			
				<div class="location"><#=data.mallName#> <#=data.organizationName#></div>
				<div class="clear-both"></div>
			</div>
		</div>
		<div class="right integral bottom-border">
			<#if(data.scoreRank==null){   #>
				<span>0</span>
				<#}else{#>
			<span class="top-highlight"><#=data.scoreAmount#></span>
					<#}#>
				积分</div>
		<div class="clear-both"></div>
	</div>
</div>