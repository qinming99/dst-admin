<div class="cal-row-fluid cal-row-head cal-week-box">
    <% _.each(days_name, function(name) { %>
    <div class="cal-cell1 <%= cal._getDayClass('week', start) %> " data-toggle="tooltip" title="<%= cal._getHolidayName(start) %>">
        <small><span data-cal-date="<%= start.getFullYear() %>-<%= start.getMonthFormatted() %>-<%= start.getDateFormatted() %>" data-cal-view="day"><%= cal.locale['ms' + start.getMonth()] %><%= start.getDate() %>日</span></small>
		<br>
        星期<%= name.substring(1) %>
    </div>
    <% start.setDate(start.getDate() + 1); %>
    <% }) %>
</div>

<div class="cal-week-box">
	<div class="cal-offset1 cal-column"></div>
	<div class="cal-offset2 cal-column"></div>
	<div class="cal-offset3 cal-column"></div>
	<div class="cal-offset4 cal-column"></div>
	<div class="cal-offset5 cal-column"></div>
	<div class="cal-offset6 cal-column"></div>
	<%= cal._week() %>
</div>

