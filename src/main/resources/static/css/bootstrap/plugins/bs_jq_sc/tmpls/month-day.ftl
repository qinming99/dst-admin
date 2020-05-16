<div class="cal-month-day <%= cls %>">
	<span class="pull-right" data-cal-date="<%= data_day %>" data-cal-view="day" data-toggle="tooltip" title="<%= tooltip %>"><%= day %></span>
	<% if (events.length > 0) { %>
		<div class="events-list" data-cal-start="<%= start %>" data-cal-end="<%= end %>" style="max-height: 64px;">
			<% _.each(events, function(event) { %>
            <div class="progress progress-striped active" style="width: 110px; height: 20px;">
                <div class="progress-bar <%= event['classStyle'] %>" role="progressbar" data-transitiongoal="<%= event['percent'] %>"></div>
            </div>
            <% }); %>
		</div>
	<% } %>
</div>
