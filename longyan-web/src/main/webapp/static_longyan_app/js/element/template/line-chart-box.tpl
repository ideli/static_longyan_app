<div class="line-chart-box <#=data.fieldName#>" >
	<#if(data.text){#>
	<div class="text"><#=data.text#></div>
	<#}#>
	<div class="chart-box">
		<canvas id="myChart-<#=data.fieldName#>"></canvas>		
	</div>
	
</div>