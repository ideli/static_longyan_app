<div class="message-item <#=data.isRead?'isRead':''#>">
	<div class="message-item-title">
		<div class="message-item-title-readmark">
			<#=data.isRead?'圆':''#>			
		</div>
		<div class="message-item-title-text">
			<#=data.message#>
		</div>
		<div class="clear-both"></div>
	</div>
	<div class="message-item-datetime">
		<div class="message-item-datetime-date">
			<#=data.dateStr#>
		</div>
		<div class="message-item-datetime-time">
			<#=data.timeStr#>
		</div>
		<div class="clear-both"></div>
	</div>
</div>