	<div class="integral-explain-item-box g-hrz" data-key="<#=data.id#>" >
		<div class="left u-full">
			<div class="name"><#=data.ruleName#></div>
			<div class="others">
				<div class="createDate"><#=data.createDate?data.createDate.substring(0,10):'';#></div>
				<div class="clear-both"></div>
			</div>
		</div>
		<div class="right  integral"><#if(data.deltaValue<0){#><span class="reduce">- <#=Math.abs(data.deltaValue)#></span><#}#><#if(data.deltaValue>0){#><span class="increase">+ <#=data.deltaValue#></span><#}#></div>

		<div class="clear-both"></div>
	</div>
</div>