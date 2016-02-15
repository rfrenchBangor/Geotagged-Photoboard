var map = L.map('map').setView([51.505, -0.09], 13);

L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
maxZoom: 18,
id: 'lemoncrap.oph51mg4',
accessToken: 'pk.eyJ1IjoibGVtb25jcmFwIiwiYSI6ImNpanNyZzd4cTAwYjF2eGx4cnBhdnEwNmQifQ.zFXLOtyev9icVLZPgANYTw'}).addTo(map);	
	
function plotPoint(image)
{
	var marker = L.marker([image.lat, image.lon]).addTo(map);
}


var geotagModule = angular.module('geotagApp', []);
geotagModule.controller('geotagController', function ($scope,$http) {
	 var urlBase="";
	 $http.defaults.headers.post["Content-Type"] = "application/json";
	    function findAllImages() 
	    {
	        $http.get(urlBase + '/images').
	            success(function (data) {
	                if (data._embedded != undefined) {
	                    $scope.images = data._embedded.images;
	                } else {
	                    $scope.images = [];
	                }
	                for (var i = 0; i < $scope.images.length; i++) {
	                        plotPoint($scope.images[i]);
	                }
	            });
	    }
	    findAllImages();
});