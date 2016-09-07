<div class="member-unit-list-item-box g-hrz unit-item " data-key="<#=data.id#>" onClick="">
	<div class="u-full">
		<div class="name"><#=data.buildingName#></div>
		<div class="bottom">
			<ul class="unit-list-left-ul clearfix">
				<li>
					<i class="iconfont icon-longyantick blue"></i>
					<span><#=data.floorAmount?data.floorAmount:0#>层</span>
				</li>
				<li>
					<i class="iconfont icon-longyantick green"></i>
					<span><#=data.unitAmount?data.unitAmount:0#>单元</span>
				</li>
				<li>
					<i class="iconfont icon-longyantick orage"></i>
					<span><#=data.roomAmount?data.roomAmount:0#>户</span>
				</li>
			</ul>
		</div>
	</div>
	<div class="unit-list-right-icon u-wrap"><i class="iconfont icon-longyantickhollow"></i></div>
</div>