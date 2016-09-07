<div class="member-build-edit-item-box g-hrz" data-key="<#=data.id#>" onClick="location.href='#member_info/<#=data.id#>'">
	<div class="u-full">
		<div class="u-full g-hrz">
			<div class="left name u-wrap"><#=data.building#>栋
				<#if(data.unit){#>
					<#=data.unit#>单元
				<#}#>
				<#=data.room#>室</div>
			<div class="right u-full createDate"><#=data.createDate?data.createDate.substring(0,10):'';#></div>
			<div class="clear-both"></div>
		</div>
		<div class="u-full g-hrz">
			<div class="left u-full">
				<div class="communityName "><#=data.communityName#></div>
				<div class="others">
					<div class="icon iconfont icon-longyanroad"></div>
					<div class="location"><#=data.province#> <#=data.city#> <#=data.area#> <#=data.communityAddress#></div>
				</div>
			</div>
		</div>
		
	</div>
	<div class="clear-both">

	</div>
</div>