<% _.each(events, function(event){ %>
<div class="cal-row-fluid">
	<div class="cal-cell<%= event.days%> cal-offset<%= event.start_day %> day-highlight dh-<%= event['class'] %>" data-event-class="<%= event['class'] %>">
        <div class="progress progress-striped active" style="width: 110px; height: 20px;">
            <div class="progress-bar <%= event['classStyle'] %>" role="progressbar" data-transitiongoal="<%= event['percent'] %>"></div>
        </div>
	</div>
</div>

<% }); %>
