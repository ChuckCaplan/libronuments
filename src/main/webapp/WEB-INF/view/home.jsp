<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="UTF-8" /> 
		<title>Baltimore Libronuments</title>

		<!-- CSS and Javascript Includes -->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/app-resources/css/style.css"/>
		<link rel="styleSheet" type="text/css" href="${pageContext.request.contextPath}/app-resources/css/ui-grid.min.css"/>
		<script src="${pageContext.request.contextPath}/app-resources/js/lib/angular.min.js"></script>
		<script src="${pageContext.request.contextPath}/app-resources/js/lib/angular-resource.min.js"></script>
		<script src="${pageContext.request.contextPath}/app-resources/js/lib/Chart.min.js"></script>
		<script src="${pageContext.request.contextPath}/app-resources/js/lib/angular-chart.min.js"></script>
		<script src="${pageContext.request.contextPath}/app-resources/js/lib/ui-grid.min.js"></script>
		<script src="${pageContext.request.contextPath}/app-resources/js/app.js"></script>
	</head>

	<body ng-app="libronuments">

		<h1> Baltimore Libronuments </h1>
		
		<!--  Pie Chart -->       
		<div ng-controller="PieCtrl" ng-app="libronuments" style="width:400px;">
			<canvas id="pie" class="chart chart-pie" chart-data="data" chart-labels="labels" chart-options="options"></canvas>
		</div> 
		 
		<p />
		
		<!--  Grid Control -->
		<div ng-controller="GridCtrl">
		
			<!-- Form to add a row -->
			<form name="addForm">
			<fieldset>
			Type: <select name="Type" ng-model="Type" required ng-options="x for x in types" ng-focus="successMessage = ''"></select>
			Name: <input name="Name" ng-model="Name" required maxlength="50" size="20" ng-focus="successMessage = ''" />
			Neighborhood: <select name="Neighborhood" ng-model="Neighborhood" required ng-options="x for x in neighborhoods" ng-focus="successMessage = ''"> </select>
			<br />Location: <input name="Location" ng-model="Location" maxlength="100" size="30" required ng-click="successMessage = ''" ng-focus="successMessage = ''" />
			Zip Code: <input name="ZipCode" ng-model="ZipCode" maxlength="5" size="5" required ng-focus="successMessage = ''"/>
			<button type="button" class="btn btn-success" ng-click="addRow();">Add</button> 
			<!-- Various messages -->
			<br/><span class="msg-success">{{ successMessage }}</span><span class="msg-val">{{ failureMessage }}</span>
			</fieldset> 
			</form>

			<br />
			<button type="button" class="btn btn-success" ng-click="deleteSelected()">Delete Selected</button>
			<p />
			
			<!-- The main grid -->
			<div id="grid1" ui-grid="gridOptions" class="grid" ui-grid-edit ui-grid-selection ui-grid-validate></div>
		</div>
		
	</body>
</html>