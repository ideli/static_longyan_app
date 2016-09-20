<div class="rank-list-item-container">
	<div class="rank-list-item-box g-hrz" data-key="<#=data.id#>" <#if(data.url){#>onClick="window.location.href='<#=data.url#>'"<#}#>>
	<!-- <div class="rank-list-item-box g-hrz" data-key="<#=data.id#>" > -->
		<div class="top u-wrap <#if(data.index<4){#>top-highlight<#}#> ">
			<#=data.index#>
		</div>
		<div class="left u-full">
			<div class="xingMing"><#=data.name#></div>		
			<div class="others">			
				<div class="hu-icon basic-small-icon">户</div>
				<div class="label left-label">住宅 <#=data.inputMemberAmount#></div>						
				<div class="qu-icon basic-small-icon">区</div>
				<div class="label right-label">小区 <#=data.inputCommunityAmount#></div>	
				<div class="clear-both"></div>
			</div>
			<div class="others">			
				<div class="ren-icon basic-small-icon">人</div>
				<div class="label left-label">员工 <#=data.employeeCount#></div>						
				<img class="basic-small-icon" width="28" src="<#=window.resource.image#>/pie_icon.png" />
				<div class="label right-label">住宅录入率<#=data.inputMemberRate#>%</div>	
				<div class="clear-both"></div>
			</div>
		</div>
		<div class="right u-wrap">
			<i class="icon iconfont icon-lylrightarrow"></i>
		</div>
		<div class="clear-both"></div>
	</div>
	<#if(data.sub_area&&data.sub_area.length>0&&(data.id!=5&&data.id!=6&&data.id!=7)){#>
	<div class="sub-area-list-show-button">
		查看更多<i class="seemorm-icon"></i>
	</div>
	<div class="sub-area-list-container hidden">
		<#for(var i=0;i<data.sub_area.length;i++){#>
		<div class="rank-list-item-box sub-area-list-item g-hrz" data-key="<#=data.sub_area[i].id#>" onClick="window.location.href='#report_area_by_id/<#=data.sub_area[i].id#>'">
			<div class="top u-wrap">
				
			</div>
			<div class="left u-full">
				<div class="xingMing"><#=data.sub_area[i].name#></div>		
				<div class="others">			
					<div class="hu-icon basic-small-icon">户</div>
					<div class="label left-label">住宅 <#=data.sub_area[i].inputMemberAmount#></div>						
					<div class="qu-icon basic-small-icon">区</div>
					<div class="label right-label">小区 <#=data.sub_area[i].inputCommunityAmount#></div>	
					<div class="clear-both"></div>
				</div>
				<div class="others">			
					<div class="ren-icon basic-small-icon">人</div>
					<div class="label left-label">员工 <#=data.sub_area[i].employeeCount#></div>						
					<img class="basic-small-icon" width="28" src="<#=window.resource.image#>/pie_icon.png" />
					<div class="label right-label">住宅录入率<#=data.sub_area[i].inputMemberRate#>%</div>	
					<div class="clear-both"></div>
				</div>
			</div>
			<div class="right u-wrap">
				<i class="icon iconfont icon-lylrightarrow"></i>
			</div>
			<div class="clear-both"></div>
		</div>
		<#}#>
	<#}#>
	</div>
</div>