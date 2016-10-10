<div id="header-container" class="hidden">
</div>
<div id="my-owner-community-list-bar">
	<div class="item-box" index="1">
        <div class="bar-item" <#=config.status=="1"?'selected="true"':''#>>
            我完善的
        </div>
	</div>
    <div class="item-box" index="0">
        <div class="bar-item" <#=config.status=="0"?'selected="true"':''#>>
            我负责的
        </div>
    </div>
	<div class="clear-both"></div>
</div>
<div id="my-owner-community-list-view">
	<div id="my-owner-community-list-box">
	</div>
</div>

<div id="my-owner-community-list-view-sec">
    <div id="my-owner-community-list-box">
        <span>还没有创建或管理的小区</span>
        <span>赶快去创建吧!</span>
        <i class="createNewArea">创建小区</i>
    </div>
</div>