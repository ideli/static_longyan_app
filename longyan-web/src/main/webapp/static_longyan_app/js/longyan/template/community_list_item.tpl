<div class="community-item-box g-hrz" data-key="<#=data.id#>" onClick="location.href='<#=data.url#>'">
	<div class="u-full">
		<div class="u-full g-hrz">
			<div class="left name u-wrap"><#=data.name#></div>
			<div class="right u-full createDate"><#=data.createDate?data.createDate.substring(0,10):'';#></div>
			<div class="clear-both"></div>
		</div>
		<div class="u-full g-hrz">
			<div class="left u-full">
				<div class="others">
					<!-- <div class="lu-icon">录</div> -->
					<!-- <div>录入率<#=data.inputRate*100#>%</div> -->
					<!-- <div class="l2">未录入 <#=data.roomMount-data.alreadyInputAmount<0?0:data.roomMount-data.alreadyInputAmount#></div> -->
				</div>

				<div class="others">
					<!-- <div class="icon iconfont icon-longyanroad"></div> -->
					<div class="location"><!-- <#=data.province#>  --><#=data.city#> <!-- <#=data.area#> --> <#=data.address#></div>
				</div>
				<!-- <div class="others">
					<img width="28" src="<#=window.resource.image#>/pie_icon.png" />
					<div>录入率<#=data.inputRate*100#>%</div>
					<div class="l1">录入率 <#=data.inputRate>100?'100':data.inputRate#>%</div>
				</div> -->
			</div>
			<!-- <div class="right next-button u-wrap">
				<i class="icon iconfont icon-lylrightarrow"></i>
			</div>	 -->
		</div>

	</div>
	<!-- <div class="right">
		>
	</div> -->
	<div class="clear-both">

	</div>
</div>