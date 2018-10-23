// include the angular modules we need
var app = angular.module('libronuments', ['ngResource', 'chart.js', 'ui.grid',
	'ui.grid.edit', 'ui.grid.selection', 'ui.grid.validate']);

// create the service factories
app.factory('Library', [ '$resource', function($resource) {
    return $resource('library/:id', {id : '@id'}, {
	updateLibrary : {
	    method : 'PUT'
	}
    });
} ]);

app.factory('Monument', [ '$resource', function($resource) {
    return $resource('monument/:id', {
	id : '@id'
    }, {
	updateMonument : {
	    method : 'PUT'
	}
    });
} ]);

app.factory('Neighborhood', [ '$resource', function($resource) {
    return $resource('neighborhood/:id', {
	id : '@id'
    });
} ]);

// create the controller for the pie chart
var chartScopeHolder;
app.controller("PieCtrl", function($scope) {
    $scope.labels = [ "Libraries", "Monuments"];
    $scope.data = [ 0, 0];
    $scope.colors = ['#6C86FF', '#FF6459'];    
    $scope.options = {
	legend : {
	    display : true
	}
    };
    chartScopeHolder = $scope;
});

// create the grid controller
app
	.controller(
		'GridCtrl',
		[
			'$scope', 'Library', 'Monument', 'Neighborhood', 'uiGridConstants', 'uiGridValidateService',
			function($scope, Library, Monument, Neighborhood, uiGridConstants, uiGridValidateService) {
			    // make success and error messages visibile;
				document.querySelector(".msg-val").style.visibility = 'visible';
				document.querySelector(".msg-success").style.visibility = 'visible';
				
				$scope.gridData = [];
			    $scope.types = ["Library", "Monument"];
			    $scope.neighborhoods = [];
			    $scope.gridOptions = {
				enableFiltering : true,
			    enableRowSelection: true,
			    enableSelectAll: true,
			    showGridFooter:true,
				data : $scope.gridData,
				columnDefs : [
					{
					    field : 'Type', width: '10%',
					    enableCellEdit : false,
					    filter : {
						type : uiGridConstants.filter.SELECT,
						selectOptions : [ {
						    value : 'Library',
						    label : 'Library'
						}, {
						    value : 'Monument',
						    label : 'Monument'
						} ]
					    }
					},
					{
					    field : 'Name',  width: '30%',
					    validators: {required: true, maxLength: 50}, cellTemplate: 'ui-grid/cellTitleValidator'
					},
					{
					    field : 'Neighborhood', width: '20%',
					    editableCellTemplate : 'ui-grid/dropdownEditor',
					    editDropdownValueLabel : 'Neighborhood',
					    editDropdownOptionsArray : [],
					    filter : {
						type : uiGridConstants.filter.SELECT,
						selectOptions : []
					    },
					    validators: {required: true}, cellTemplate: 'ui-grid/cellTitleValidator'
					}, {
					    field : 'Location', width: '30%',
					    validators: {required: true, maxLength: 100}, cellTemplate: 'ui-grid/cellTitleValidator'
					}, {
					    field : 'ZipCode', width: '10%',
					    validators: {required: true, zipcode: true}, cellTemplate: 'ui-grid/cellTitleValidator'
					} , {
					    field : 'Id',
					    type : 'number',
					    visible: false
					}]
			    };
			    
			    // custom validator to ensure a value is a valid Zipcode
			    uiGridValidateService.setValidator('zipcode',
				    function(argument) {
				      return function(oldValue, newValue, rowEntity, colDef) {
				        if (!newValue) {
				          return true; // We should not test for existence here
				        } else {
				          return isValidZipcode(newValue);
				        }
				      };
				    },
				    function(argument) {
				      return 'A valid 5-digit Baltimore Zipcode is required.';
				    }
				  );			    
			    
			    $scope.selectAll = function() {
			        $scope.gridApi.selection.selectAllRows();
			      };
			   
			      $scope.clearAll = function() {
			        $scope.gridApi.selection.clearSelectedRows();
			      };

			    // function is called when Delete button in UI is clicked
			    $scope.deleteSelected = function() {
					var count = 0;
					angular.forEach($scope.gridApi.selection
						.getSelectedRows(), function(data,
						index) {
					    if (data.Type == 'Library') {
							// call the service method
							ob.deleteLibrary(data.Id);
					    } else if (data.Type == 'Monument') {
							// call the service method
							ob.deleteMonument(data.Id);
					    }
					    // remove the row from the queue
					    $scope.gridOptions.data.splice(
						    $scope.gridOptions.data
							    .lastIndexOf(data), 1);
					    count++;
					});
					if (count == 0) {
					    alert('You must select rows to delete from the grid before clicking this button.');
					}
			    }

			    // function is called when Add button is clicked in UI
			    $scope.addRow = function() {
					if($scope.addForm.$valid) {
					    // check if valid zipcode
					    if (!isValidZipcode($scope.ZipCode)) {
						    $scope.successMessage = '';
						    $scope.failureMessage = 'A valid 5-digit Baltimore Zipcode is required.';
						    return;
					    }
					    if ($scope.Type == 'Library') {
							ob.library.name = $scope.Name;
							for (i=0; i<ob.neighborhoods.length; i++) {
							    if (ob.neighborhoods[i].neighborhood == $scope.Neighborhood) {
								ob.library.neighborhood = ob.neighborhoods[i];
							    }
							}
							ob.library.location = $scope.Location.toUpperCase();
							ob.library.zipCode = $scope.ZipCode;
							ob.addLibrary();
					    } else if ($scope.Type == 'Monument') {
					    	ob.monument.name = $scope.Name;
							for (i=0; i<ob.neighborhoods.length; i++) {
							    if (ob.neighborhoods[i].neighborhood == $scope.Neighborhood) {
								ob.monument.neighborhood = ob.neighborhoods[i];
							    }
							}
							ob.monument.location = $scope.Location.toUpperCase();
							ob.monument.zipCode = $scope.ZipCode;
							ob.addMonument();						    
					    } else {
					    	console.log('ERROR - trying to add something that is neither a library or a monument - ' + $scope.Type);
					    }	
					} else {
					    $scope.successMessage = '';
					    $scope.failureMessage = 'All fields are required.';
					}
			    }

			    // using ui grid api for cell editing
			    $scope.gridOptions.onRegisterApi = function(gridApi) {
					// set gridApi on scope
					$scope.gridApi = gridApi;
	
					// this event fires whenever a row is changed.
					// Update the DB.
					$scope.gridApi.edit.on.afterCellEdit($scope,
						function(rowEntity, colDef, newValue,
							oldValue) {
						    if (newValue != oldValue) {
							if (rowEntity.Type == 'Library') {
							    ob.library.id = Number(rowEntity.Id);
							    ob.library.name = rowEntity.Name;
							    for (i=0; i<ob.neighborhoods.length; i++) {
									if (ob.neighborhoods[i].neighborhood == rowEntity.Neighborhood) {
									    ob.library.neighborhood = ob.neighborhoods[i];
									}
							    }
							    rowEntity.Location = rowEntity.Location.toUpperCase();
							    ob.library.location = rowEntity.Location;
							    ob.library.zipCode = rowEntity.ZipCode;
							    // I have no choice but to call my own manual
								// validation
							    // because the ui grid validation will always try to
								// update
							    if (isValid(ob.library)) {
									// call the service method to update
									ob.updateLibraryDetail();
							    }						    
							} else if (rowEntity.Type = 'Monument') {
							    ob.monument.id = Number(rowEntity.Id);
							    ob.monument.name = rowEntity.Name;
							    for (i=0; i<ob.neighborhoods.length; i++) {
									if (ob.neighborhoods[i].neighborhood == rowEntity.Neighborhood) {
									    ob.monument.neighborhood = ob.neighborhoods[i];
									}
							    }
							    rowEntity.Location = rowEntity.Location.toUpperCase();
							    ob.monument.location = rowEntity.Location;
							    ob.monument.zipCode = rowEntity.ZipCode;
							    // I have no choice but to call my own manual
								// validation
							    // because the ui grid validation will always try to
								// update
							    if (isValid(ob.monument)) {
									// call the service method to update
									ob.updateMonumentDetail();
							    }							    
							} else {
							    console.log('ERROR - trying to update something that is neither a library or a monument - ' + rowEntity);
							}
						    }
						})
			    };

			    // ***** SERVICE METHODS AND VARIABLE HOLDERS *****
			    var ob = this;
			    // load all the data
			    ob.libraries = [];
			    ob.library = new Library();
			    ob.fetchAllLibraries = function() {
				ob.libraries = Library
					.query(function() {
					    // update the chart with the latest data
					    chartScopeHolder.data[0] = ob.libraries.length;
					    // remove all existing libraries
					    for (i = 0; i < $scope.gridData.length; i++) {
						if (typeof $scope.gridData[i] != 'undefined' && $scope.gridData[i] != null) {
						    if ($scope.gridData[i].Type == 'Library') {
							$scope.gridData
							.splice(i, 1);
						    }
						}
					    }

					    // add all new libraries. work from
					    // the end and add to the beginning
					    // so other items don't get messed
					    // up
					    librariesRemaining = ob.libraries.length;
					    for (i = ob.libraries.length -1; i >= 0; i--) {
							$scope.gridData.unshift({
							    "Type" : "Library",
							    "Name" : ob.libraries[i].name,
							    "Neighborhood" : ob.libraries[i].neighborhood.neighborhood,
							    "Location" : ob.libraries[i].location
								    .toUpperCase(),
							    "ZipCode" : ob.libraries[i].zipCode,
							    "Id" : Number(ob.libraries[i].id)
							});
							mapScopeHolder.addMarker(ob.libraries[i].id, ob.libraries[i].location + ' ' + ob.libraries[i].zipCode, ob.libraries[i].name, 'Library', ob.libraries[i].neighborhood.neighborhood);
					    }
					});
			    };
			    ob.fetchAllLibraries();

			    // do the exact same thing for monuments
			    ob.monuments = [];
			    ob.monument = new Monument();
			    ob.fetchAllMonuments = function() {
				ob.monuments = Monument
					.query(function() {
					    // update the chart with the latest data
					    chartScopeHolder.data[1] = ob.monuments.length;

					    // remove all existing monuments
					    for (i = 0; i < $scope.gridData.length; i++) {
						if (typeof $scope.gridData[i] != 'undefined' && $scope.gridData[i] != null) {
    							if ($scope.gridData[i].Type == 'Monument') {
    							    $scope.gridData
    							    	.splice(i, 1);
    							}
						}
					    }

					    // add all new monuments
					    monumentsRemaining = ob.monuments.length;
					    for (i = 0; i < ob.monuments.length; i++) {
						var position = i + $scope.gridData.length;
						$scope.gridData[position] = {
						    "Type" : "Monument",
						    "Name" : ob.monuments[i].name,
						    "Neighborhood" : ob.monuments[i].neighborhood.neighborhood,
						    "Location" : ob.monuments[i].location
							    .toUpperCase(),
						    "ZipCode" : Number(ob.monuments[i].zipCode),
						    "Id" : Number(ob.monuments[i].id)
						}	
						mapScopeHolder.addMarker(ob.monuments[i].id, ob.monuments[i].location + ' ' + ob.monuments[i].zipCode, ob.monuments[i].name, 'Monument', ob.monuments[i].neighborhood.neighborhood);
					    }
					    monumentsLoaded = true;
					});
			    };
			    ob.fetchAllMonuments();
			    
			    // do the exact same thing for neighborhoods
			    ob.neighborhoods = [];
			    ob.neighborhood = new Neighborhood();
			    ob.fetchAllNeighborhoods = function() {
				ob.neighborhoods = Neighborhood
					.query(function() {
					    // populate the neighborhood filter
					    // on the grid and the edit dropdown
					    for (i = 0; i < ob.neighborhoods.length; i++) {
						$scope.gridOptions.columnDefs[2].filter.selectOptions
							.push({
							    label : ob.neighborhoods[i].neighborhood,
							    value : ob.neighborhoods[i].neighborhood
							});
						$scope.gridOptions.columnDefs[2].editDropdownOptionsArray
							.push({
							    id : ob.neighborhoods[i].neighborhood,
							    Neighborhood : ob.neighborhoods[i].neighborhood
							});
						$scope.neighborhoods.push(ob.neighborhoods[i].neighborhood);
					    }
					    // sort the list
					    $scope.neighborhoods.sort();
					    // use a custom comparison because the neighborhoods are
						// in an array of objects
					    $scope.gridOptions.columnDefs[2].filter.selectOptions.sort(function(a,b) {return (a.label > b.label) ? 1 : ((b.label > a.label) ? -1 : 0);} );
					    $scope.gridOptions.columnDefs[2].editDropdownOptionsArray.sort(function(a,b) {return (a.Neighborhood > b.Neighborhood) ? 1 : ((b.Neighborhood > a.Neighborhood) ? -1 : 0);});
					});
			    };
			    ob.fetchAllNeighborhoods();
			    
			    // service calls
			    ob.addLibrary = function() {
				    ob.library.$save(function(dbLibrary) {
					// increment the chart
					chartScopeHolder.data[0] +=1;
					// add to map
					mapScopeHolder.addMarker(dbLibrary.id, dbLibrary.location + ' ' + dbLibrary.zipCode, dbLibrary.name, 'Library', dbLibrary.neighborhood.neighborhood);
					ob.reset();
					// add to grid
					$scope.gridOptions.data.unshift( {
					    "Type" : 'Library',
						"Name" : dbLibrary.name,
						"Neighborhood" : dbLibrary.neighborhood.neighborhood,
						"Location" : dbLibrary.location,
						"ZipCode" : Number(dbLibrary.zipCode),
						"Id" : Number(dbLibrary.id)
					});
					$scope.successMessage = 'Library successfully added.';
					$scope.failureMessage = '';
					$scope.resetForm();
					ob.reset();
				    }, function(err) {
					console.log(err.status);
					$scope.failureMessage = 'Library not added. There was an error or your library already exists - ' + err.status;
				    });
			    };
			    // add methods
			    ob.addMonument = function() {
				    ob.monument.$save(function(dbMonument) {
					// increment the chart
					chartScopeHolder.data[1] +=1;
					// add to map
					mapScopeHolder.addMarker(dbMonument.id, dbMonument.location + ' ' + dbMonument.zipCode, dbMonument.name, 'Monument', dbMonument.neighborhood.neighborhood);
					// add to grid
					$scope.gridOptions.data.unshift( {
					    "Type" : 'Monument',
						"Name" : dbMonument.name,
						"Neighborhood" : dbMonument.neighborhood.neighborhood,
						"Location" : dbMonument.location,
						"ZipCode" : Number(dbMonument.zipCode),
						"Id" : Number(dbMonument.id)
					});					
					$scope.successMessage = 'Monument successfully added.';
					$scope.failureMessage = '';
					$scope.resetForm();
					ob.reset();
				    }, function(err) {
					console.log(err.status);
					$scope.failureMessage = 'Monument not added. There was an error or your monument already exists - ' + err.status;
				    });
			    };

				// edit methods
				ob.editLibrary = function(id){
				ob.library = Library.get({ id: id}, function() {
					ob.reset();
				});
				};

				ob.editMonument = function(id){
				ob.monument = Monument.get({ id: id}, function() {
					ob.reset();
				});
				};

				// update methods
				ob.updateLibraryDetail = function(){
				ob.library.$updateLibrary(function(library){
					updateMarker(library.id, library.name, library.location, library.zipCode, library.neighborhood.neighborhood, 'Library');
					ob.reset();
				});
				};

				ob.updateMonumentDetail = function(){
				ob.monument.$updateMonument(function(monument){
					updateMarker(monument.id, monument.name, monument.location, monument.zipCode, monument.neighborhood.neighborhood, 'Monument');
					ob.reset();			    
				});
				};

				// delete methods
				ob.deleteLibrary = function(id){
				ob.library = Library.delete({ id: id}, function() {
				ob.reset();
				// decrement chart
				chartScopeHolder.data[0] -=1;
				// remove from map
				deleteMarker(getMarker(id+'Library'));
				});
				};

				ob.deleteMonument = function(id){
				ob.monument = Monument.delete({ id: id}, function() {
				ob.reset();
				// decrement chart
				chartScopeHolder.data[1] -=1;
				// remove from map
				deleteMarker(getMarker(id+'Monument'));
				});
				};

				// misc. methods
				ob.reset = function(){
				    	ob.library = new Library();
    					ob.monument = new Monument();
    					ob.neighborhood = new Neighborhood();
				};
				
				// clears the form. Need to use this instead of $setPristine
				// because of the way we are doing validation
				$scope.resetForm = function() {
				    $scope.Type = '';
				    $scope.Name = '';
				    $scope.Neighborhood = '';
				    $scope.Location = '';
				    $scope.ZipCode = '';
				}
			} ]);

// checks for a valid 5 digit zipcode
function isValidZipcode(zipcode) {
    return /(^\d{5}$)/.test(zipcode) && isBaltimoreZipcode(zipcode); 
}

// TODO this should ultimately be checked server-side as well
function isBaltimoreZipcode(zipcode) {
	// all baltimore zipCodes
	var zipcodes =[	'21201','21202','21205','21206','21207','21208','21209','21210','21211','21212',
		'21213','21214','21215','21216','21217','21218','21222','21223','21224','21225','21226',
		'21227','21228','21229','21230','21231','21234','21236','21237','21239','21251','21287',
		'21203','21233','21263','21264','21265','21270','21273','21274','21275','21278','21279',
		'21280','21281','21283','21288','21290','21297','21298',];
	// searching like this instead of with array.indexOf to support older browsers
    var count=zipcodes.length;
    for(var i=0; i<count; i++)
    {
        if(zipcodes[i]==zipcode){
        	return true;
        }
    }
    return false;
}

// checks to see if every field in a library or monument object is valid
function isValid(myObj) {
    if (myObj.name == null || myObj.name == '' || myObj.name.length > 50) {
		alert('Invalid name! Field is required and must be less than 50 characters.');
		return false;
    }
    if (myObj.location == null || myObj.location == '' || myObj.location.length > 100) {
		alert('Invalid location! Field is required and must be less than 100 characters.');
		return false;
    }
    if (!isValidZipcode(myObj.zipCode)) {
		alert('Invalid Zipcode! Field is required and must be a valid 5-digit Baltimore Zipcode.');
		return false;
    }
    return true;
}

var map;
var mapCanvas = document.getElementById("map");
var librariesRemaining = 0, monumentsRemaining = 0;
var baltimore;
// called when google's js library is loaded
function initMap() {
	  mapCanvas.style.visibility='hidden'; 
	  baltimore = new google.maps.LatLng(39.299236, -76.609383);  
	  var mapOptions = {
	    // baltimore
	    center: baltimore,
	    zoom: 11
	  }
	  map = new google.maps.Map(mapCanvas, mapOptions);
}

var mapInitialized = false;
// called once all markers are added
function showMap() {
	// remove the loading message div completely
	var element = document.getElementById("map_loading");
	element.parentNode.removeChild(element);
	map.setCenter(baltimore);
	map.setZoom(11);
	mapCanvas.style.visibility='visible';
	if (markerArray.length == 0) {
		alert('Error adding libraries and monuments to map! The geocoding service is most likely down.');
	}
	mapInitialized = true;
}

var prev_infowindow =false;
var markerArray = Array();

function getMarker(id) {
	for(i = 0 ; i< markerArray.length; i++) { 
		if (markerArray[i].id == id) {
			return markerArray[i];
		}
	}
	return null;
}

// deletes a marker from a map
function deleteMarker(marker) {
	// this method needs to be null-safe in case the geocoder didn't return anything and there is no marker.
	if (marker !== null) {
		marker.setMap(null);
		// remove from array
		for(i = 0 ; i< markerArray.length; i++) { 
			if (markerArray[i].id == marker.id) {
				markerArray.splice(i,1);
			}
		}
	}
}

function updateMarker(id, name, location, zipCode, neighborhood, type) {
	deleteMarker(getMarker(id+type));
	mapScopeHolder.addMarker(id, location + ' ' + zipCode, name, type, neighborhood);
}

var mapScopeHolder;
app.controller('MapCtrl', function($scope, $http) {
	$scope.addMarker = function(id, address, title, type, neighborhood) { 
		// if address is only a zipcode, return and do not add to map
		if (isValidZipcode(address.trim())) {
			updateCount(type);
			return;
		}
		var color = 'blue';
		if (type =='Monument') {
			color = 'red';
		}		
		address = address.replace(/\r?\n|\r/g, ', ');
    	$http({
    		url: 'https://geocoding.geo.census.gov/geocoder/locations/onelineaddress?benchmark=Public_AR_Current&format=jsonp&callback=JSON_CALLBACK&address='+address,
    		method: 'JSONP'
    	})
    	.success(function(response) {
    		try {
    			var position = new google.maps.LatLng( response.result.addressMatches[0].coordinates.y,response.result.addressMatches[0].coordinates.x);
    	        var marker = new google.maps.Marker({
    	            map: map,
    	            position: position,
    	            title: title,
    	            icon: 'http://maps.google.com/mapfiles/ms/icons/'+color+'-dot.png',
    	            id: id+type
    	        });
    	        var infowindow = new google.maps.InfoWindow({
    	            content: '<h2>'+type+' - '+title+'</h2>'+neighborhood+'<br/><br/>'+address
    	          });	        
    	        marker.addListener('click', function() {
    	        	if( prev_infowindow ) {
    	                prev_infowindow.close();
    	             }

    	             prev_infowindow = infowindow;        	
    	            infowindow.open(map, marker);
    	          });
    	        markerArray.push(marker);    
    	        map.setCenter(position);
    	        map.setZoom(15);
    	        // If we are manually adding a marker then show the info window
    	        if (mapInitialized) {
    	        	infowindow.open(map, marker);
    	        }
    		} catch(err) {
    			console.log(err+' - No coordinates for '+address);
    		}
    	}).error(function (data, status, headers, config) {
    		console.log('Error calling geocode URL - data:' + data + ' status: '+ status + ' headers: ' + headers + ' config: ' + config);
	    }).finally(function () {
	    	updateCount(type);
	    });
    }
    mapScopeHolder = $scope;
});

// update the counts of processed places and show the map if we are done
function updateCount(type)  {
	if (!mapInitialized) {
		// if last call then show the map
		if (type =='Monument') {
			monumentsRemaining--;
		} else {
			librariesRemaining--;
		}
		if (monumentsRemaining == 0 && librariesRemaining == 0) {
			showMap();
		}
	}
}