'use strict';

angular.module('hipster1App')
    .controller('NavbarController', function ($state, $rootScope, $scope, $http, $location, Auth, Principal) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;

        $scope.logout = function () {
            Auth.logout();
            $state.go('home');
        };
        
        $rootScope.specialities = ['DENTIST', 'GYNIC']
        $rootScope.cities = ['Alabama', 'Alaska', 'Arizona', 'Arkansas', 'California', 'Colorado', 'Connecticut', 'Delaware', 'Florida', 'Georgia', 'Hawaii', 'Idaho', 'Illinois', 'Indiana', 'Iowa', 'Kansas', 'Kentucky', 'Louisiana', 'Maine', 'Maryland', 'Massachusetts', 'Michigan', 'Minnesota', 'Mississippi', 'Missouri', 'Montana', 'Nebraska', 'Nevada', 'New Hampshire', 'New Jersey', 'New Mexico', 'New York', 'North Dakota', 'North Carolina', 'Ohio', 'Oklahoma', 'Oregon', 'Pennsylvania', 'Rhode Island', 'South Carolina', 'South Dakota', 'Tennessee', 'Texas', 'Utah', 'Vermont', 'Virginia', 'Washington', 'West Virginia', 'Wisconsin', 'Wyoming'];
        $rootScope.city = 'koramanagala';
        $rootScope.speciality = 'DENTIST';
        $scope.hospitalDoctorDTO4s = [];
        
        
        $rootScope.getCities = function(val) {
        	console.log('get city'+val)
        	//alert('get cities')
        	//alert('get city ' +val)
        	return $rootScope.getResp('/hipster1/api/cities',{city:val});
        }
        
        $rootScope.cityChanged= function(item, model, label){
        	console.log('newcity '+item)
        	$http.get('/hipster1/api/search', {
                params: {
                	location: item,
                	speciality : $rootScope.speciality
                }
              }).then(function(response){
              	console.log('doctors response '+response)
              	$state.go("hospitalDoctorDTO4")
              	angular.forEach(response.data, function(value, key) {
            		  console.log(key)
            		  console.log(value)
            		});
              });
        };

        $rootScope.fire = function(tostate, data) {
        	$state.go(tostate, {id:data.id});
        }
        
        $rootScope.getSpeciality = function(val) {
        	console.log('get city'+val)
        	//alert('get city ' +val)
        	return $rootScope.specialities;
        }
        
        $rootScope.specialityChanged = function(item, model, label){
        	console.log('newcity '+item)
        	//alert('specialityChanged '+item)
        	$state.go('hospitalDoctorDTO4');
        	
        	
        };

        $rootScope.getResp = function(url,param) {
        	console.log('get cities')
            return $http.get(url, {
              params: param
            }).then(function(res){
            	var resp = [];
            	angular.forEach(res.data, function(value, key) {
          		  resp.push(value)
          		});
            	return resp;
            });
          };
        
        $scope.hello = function() {
        	alert('hello');
        }
        
        $scope.makenew = function(item,slot, event) {
          	//alert('booking '+JSON.stringify(item));
          	//alert('booking '+event);
        	$state.go('hospitalDoctorDTO4',{id:item.id});
        	
        }  
        
      //alert('get cities user controller')
            });
