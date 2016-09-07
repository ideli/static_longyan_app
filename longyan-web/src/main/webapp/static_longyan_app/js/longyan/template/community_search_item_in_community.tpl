<div class="search-box-item 
<#if((!data.ownerId&&data._is_mall_employee)){#>g-hrz<#}#>
" data-value="<#=data._value#>" data-text="<#=data._text#>" owner-id="<#=data.ownerId#>">
	<div class="label u-full"><#=data._textDisplay#></div>
	<#if(!data.createEmployeeId&&!data.ownerId){#>
	<!--未创建的小区,可以在这个基础上创建-->
		<!-- <a class="button" href="#community_update_exist/<#=data.id#>"><img src="<#=window.resource.image#>/search_add_community.png"/></a> -->		
	<#}else{#>
	<!--已创建的小区-->
	<!--非商场员工-->
		<img class="button no-create-button" style="width:75px;" src="<#=window.resource.image#>/search_is_create.png"/>
	<#}#>
	<div class="clear-both"></div>
</div>
	<div class="address u-full"><#=data.city#> <!-- <#=data.area#> --> <#=data.address#></div>

<div class = "search-box-item-line"></div>