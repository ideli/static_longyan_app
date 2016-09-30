<div id="header-container" class="hidden"  style="display: none;">
	
</div>
<div id="community-near-by-list-bar" style="margin-top:0;">
	<div class="item-box" index="0">
		<div class="bar-item"<#=config.status=="0"?'selected="true"':''#>>
			全部
		</div>
	</div>	
	<div class="item-box" index="1">
		<div class="bar-item" <#=config.status=="1"?'selected="true"':''#>>
			预分配小区	
		</div>

	</div>
	<div class="item-box" index="2">
		<div class="bar-item" <#=config.status=="2"?'selected="true"':''#>>
			可抢小区
		</div>
	</div>	
	<div class="clear-both"></div>
</div>
<div id="community-near-by-list-view"  style="margin-top:3.91489362rem;">
	<div id="community-near-by-list-box">
		
	</div>
</div>