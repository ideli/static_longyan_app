<div class="community-item-box g-hrz">
	<div class="left u-full">
		<div class="name"><#=data.name#></div>
		<div class="location"><#=data.province#> <#=data.city#> <#=data.area#></div>
		<div class="others">
			<!-- <img width="28" src="<#=window.resource.image#>/button/luButton.png" /> -->
			<div class="lu-icon">录</div>
			<div>录入率<#=data.InputRate*100#>%</div>
			<#if(data.occupancyRate&&data.occupancyRate>0){#>
			<!-- <img width="28" src="<#=window.resource.image#>/button/zhuButton.png" /> -->
			<div class="zhu-icon">住</div>
			<div>入住率<#=data.occupancyRate*100#>%</div>
			<#}#>
		</div>
	</div>
	<div class="right u-wrap">
		>
	</div>
	<div class="clear-both">

	</div>
</div>