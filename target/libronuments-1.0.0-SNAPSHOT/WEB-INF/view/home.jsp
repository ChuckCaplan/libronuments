<!DOCTYPE html>
<%@page
	import="com.chuckcaplan.libronuments.controller.LibronumentsController"%>
<html lang="en-US">
<head>
	<meta charset="UTF-8" />
	<title>Baltimore Libronuments</title>
</head>

<body ng-app="libronuments">

	<h1>Baltimore Libronuments</h1>

	<!-- Tables are bad for layout, css should be used.
		However, the only way I could get both the pie chart canvas and map
		to align properly was with absolute positioning, and I worry that will not
		work the same on all browsers. HTML tables are a safer option.
		 -->
	<table align="center">
		<tr>
			<td>
				<!-- Google Map -->
				<div id="wrapper">
					<div id="map" ng-controller="MapCtrl"></div>
					<div id="map_loading">
						<br />
						<br />
						<br />
						<br />
						<h2>Generating map... Please wait.</h2>
					</div>
				</div>
			</td>
			<td>
				<!--  Pie Chart -->
				<div ng-controller="PieCtrl" ng-app="libronuments" id="chart">
					<canvas id="pie" class="chart chart-pie" chart-data="data"
						chart-labels="labels" chart-options="options"
						chart-colors="colors"></canvas>
				</div>
			</td>
		</tr>
	</table>
	<p />

	<!--  Grid Control -->
	<div ng-controller="GridCtrl" id="grid">

		<!-- Form to add a row -->
		<form name="addForm">
			<fieldset>
				<legend>Add New Library or Monument</legend>
				Type: <select name="Type" ng-model="Type" required
					ng-options="x for x in types" ng-focus="successMessage = ''"></select>
				Name: <input name="Name" ng-model="Name" required maxlength="50"
					size="20" ng-focus="successMessage = ''" /> Neighborhood: <select
					name="Neighborhood" ng-model="Neighborhood" required
					ng-options="x for x in neighborhoods"
					ng-focus="successMessage = ''">
				</select> <br />Location: <input name="Location" ng-model="Location"
					maxlength="100" size="30" required ng-click="successMessage = ''"
					ng-focus="successMessage = ''" /> Zip Code: <input name="ZipCode"
					ng-model="ZipCode" maxlength="5" size="5" required
					ng-focus="successMessage = ''" />
				<button type="button" class="btn btn-success" ng-click="addRow();">Add</button>
				<!-- Various messages -->
				<br /> <span class="msg-success">{{ successMessage }}</span><span
					class="msg-val">{{ failureMessage }}</span>
			</fieldset>
		</form>

		<br />
		<button type="button" class="btn btn-success" ng-click="selectAll()">Select
			All</button>
		<button type="button" class="btn btn-success" ng-click="clearAll()">Clear
			All</button>
		<button type="button" class="btn btn-success"
			ng-click="deleteSelected()">Delete Selected</button>
		<p />
		<i>Double-click grid data to edit in-line.</i> <br />

		<!-- The main grid -->
		<div id="grid1" ui-grid="gridOptions" class="grid" ui-grid-edit
			ui-grid-selection ui-grid-validate></div>
	</div>

	<!-- CSS and Javascript Includes -->
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/app-resources/css/style.css" />
	<link rel="styleSheet" type="text/css"
		href="${pageContext.request.contextPath}/app-resources/css/ui-grid.min.css" />
	<script
		src="${pageContext.request.contextPath}/app-resources/js/lib/angular.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/app-resources/js/lib/angular-resource.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/app-resources/js/lib/Chart.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/app-resources/js/lib/angular-chart.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/app-resources/js/lib/ui-grid.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/app-resources/js/app.js"></script>
	<script
		src="https://maps.google.com/maps/api/js?callback=initMap&key=<%=LibronumentsController.getApiKey("google.maps.api.key")%>"></script>
</body>
</html>