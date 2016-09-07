	<div class="integral-rank-item-box g-hrz" data-key="<#=data.id#>" >
		<#if(data.scoreRank==null){   #>
			<div class="top u-wrap">
				<hr class="fenge" style="width: 1rem;margin-top: 4rem;height: 0.15rem;background-color: #999">
			</div>
		<# }else if(data.scoreRank<4){#>
			<div class="top u-wrap top-highlight">
				<#=data.scoreRank#>
			</div>
		<#}else{#>
			<div class="top u-wrap">
				<#=data.scoreRank#>
			</div>
			<#}#>
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
			<#}else if(data.scoreRank<4){#>
				<span class="score"><#=data.scoreAmount#></span>
			<#}else{ #>
				<span><#=data.scoreAmount#></span>
	 		<#}#>
				积分</div>
		<div class="clear-both"></div>
	</div>
</div>