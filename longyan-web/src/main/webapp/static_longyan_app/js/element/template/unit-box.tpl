<div class="unit-box <#=data.fieldName#> <#=data.clazz#> g-hrz clearfix">
	<section class="unit-box-title g-hrz">
		<div class="text u-full"><#=data.text#></div>
		<div class="unit-box-title-right u-wrap">19户</div>
	</section>
	<section class="unit-box-content clearfix g-hrz">
		<!-- <div class="unit-item ">
			<input class="readonly" type="text" readonly value="123"/>
		</div>
		<div class="unit-item ">
			<input class="readonly" type="text" readonly value="123"/>
		</div>
		<div class="unit-item "><input class="readonly" type="text" readonly value="123"/></div>
		<div class="unit-item "><input class="readonly" type="text" readonly value="123"/></div>
		<div class="unit-item "><input class="readonly" type="text" readonly value="123"/></div>
		<div class="unit-item "><input class="readonly" type="text" readonly value="123"/></div> -->
		<#if(data.datas&&data.datas.length>0){#>

			<#for(var i=0;i<data.datas.length;i++){#>
				<#var item = data.datas[i];#>
				<div class="unit-item "><input class="readonly" type="text" readonly value="<#=item.value#>"/></div>
			<#}#>
		<#}#>

		<div class="unit-item add-input"><input type="text" placeholder="输入房号" /></div>
		<div class="unit-item add-btn"><i class="iconfont icon-longyanjiahao"></i>添加</div>
	</section>
	
</div>