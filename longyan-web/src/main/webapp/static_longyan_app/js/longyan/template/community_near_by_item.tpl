<div class="community-near-by-list-item">
	<div class="item-col-1">
		<div class="item-base-info">
			<div class="item-name">
				<#=data.name#>
			</div>
			<div class="item-mall-name">
				<#=data.mallName?data.mallName:''#>
			</div>
			<div class="clear-both"></div>
		</div>
		<div class="item-address">
			<#=data.address#>
		</div>
	</div>	
	<div class="item-col-3">
		<div class="item-status">			
			<#=data.status==0?'编辑':''#>
			<#=data.status==1?'可认领':''#>
			<#=data.status==2?'可抢':''#>
		</div>
		<div class="item-distance">
			<#=data.distance#>
		</div>
	</div>	
	<div class="clear-both"></div>
</div>