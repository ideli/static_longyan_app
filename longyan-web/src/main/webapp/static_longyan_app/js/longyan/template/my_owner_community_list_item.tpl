<div class="my-owner-community-list-item" data-id="<#=data.id#>">
	<div class="item-col-1">
		<div class="item-base-info">
			<div class="item-name">
				<#=data.name#>
			</div>
			<div class="item-mall-name">
				<#=data.mallName#>
			</div>
			<div class="clear-both"></div>
		</div>
		<div class="item-address">
			<#=data.address#>
		</div>
	</div>
	<div class="item-col-3">
		<div class="item-status">			
			<#=data.haveComplate?'已完善':'待完善'#>
		</div>
		<div class="item-distance">
			<#=data.distance#>
		</div>
	</div>
	<div class="item-col-2">
		剩45天
	</div>
	<div class="clear-both"></div>
</div>