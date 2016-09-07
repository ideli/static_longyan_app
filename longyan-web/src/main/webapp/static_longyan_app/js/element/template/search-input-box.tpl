<div  class="search-input-box input-box g-hrz picker-box <#=data.fieldName#>">
	<div class="label u-wrap"><#=data.text#></div>		
	<input type="<#=data.type||'text'#>" class="u-full location-picker-input" placeholder="<#=data.placeholder#>" readonly="readonly" action="text"/>
	<div class="iconfont icon-longyanwarn label-warn"></div>
	<input type="hidden"  action="value"/>
	<div class="clear-both"></div>
</div>
<div id="search-box-pop-view" class="search-input-box-pop-<#=data.fieldName#> pop-view" style="display:none;">
	<div id="header-container-<#=data.fieldName#>" class="hidden">
	
	</div>
	<div id="think-search-view-<#=data.fieldName#>">
		<div id="think-search-form-<#=data.fieldName#>" class="form-box">
				
		</div>
		<div class="search-box think-search-group">
		<!-- <div class="search-box-item">
			123
		</div>
		<div class="search-box-item-line"></div>
		<div class="search-box-item">
			ab<span class="search-box-item-key">c</span>
		</div>	
		<div class="search-box-item-line"></div> -->
		</div>
	</div>
</div>

