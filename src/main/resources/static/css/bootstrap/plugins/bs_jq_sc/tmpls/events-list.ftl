<span id="cal-slide-tick" style="display: none"></span>
<div id="cal-slide-content" class="cal-event-list">
	<ul class="unstyled list-unstyled">
		<% _.each(events, function(event) { %>
			<li>
				订单号：<%= event.orderId %><br>
				计划单号：<%= event.scheduleNo %><br>
				计划名称：<%= event.scheduleName %><br>
				投放量：<%= event.totalTraffic %><br>
				投放周期：
				<% for(var i = 0; i < event.timeBean.length; i++){%>
                	<p><%= event.timeBean[i].beginTime %> 　-　 <%= event.timeBean[i].endTime %><p>
                <%}%>
			</li>
		<% }) %>
	</ul>
</div>
