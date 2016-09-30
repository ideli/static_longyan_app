<div id="header-container" class="hidden">
	
</div>
<div id="my-submit-list-bar">
	<div class="item-box" index="0">
        <div class="bar-item" <#=config.status=="0"?'selected="true"':''#>>
			待审核
		</div>
	</div>
	<div class="item-box" index="1">
        <div class="bar-item" <#=config.status=="1"?'selected="true"':''#>>
            通过
        </div>
	</div>
	<div class="item-box" index="2">
        <div class="bar-item" <#=config.status=="2"?'selected="true"':''#>>
            未通过
        </div>
	</div>
	<div class="clear-both"></div>
</div>
<div id="my-submit-list-view">
	<div id="my-submit-list-box">
		
	</div>
</div>