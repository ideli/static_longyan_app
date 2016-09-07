<div class="search-box-item 
<#if((!data.ownerId&&data._is_mall_employee)){#>g-hrz<#}#>
" data-value="<#=data._value#>" data-text="<#=data._text#>" owner-id="<#=data.ownerId#>">
	<div class="label u-full"><#=data._textDisplay#></div>
	<#if(!data.ownerId&&data._is_mall_employee){#>
	<!--商场员工权限-->
		<a class="button" href="#community_update_exist/<#=data.id#>">
			<!-- <img src="<#=window.resource.image#>/search_add_community.png"/> -->
			<button class="button_to_create">去创建</button>
		</a>		
	<#}else if(!data.createEmployeeId){#>
	<!--非商场员工-->
		<img class="button no-create-button" style="width:75px;" src="<#=window.resource.image#>/search_no_create.png"/>
	<#}#>
	<div class="clear-both"></div>
</div>
<div class = "search-box-item-line"></div>