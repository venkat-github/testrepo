'use strict';

angular.module('hipster1App')
    .controller('MainController', ['$scope','$rootScope', 'Principal',
    	function ($scope, $rootScope, Principal) {
    	/*
    	        Account.get().$promise.then(function(user) {
                    alert('setting the user');
                    alert('setting the user '+user.data.name);
                	$rootScope.user = user.data;
                	
                });
		*/
        Principal.identity().then(function(account) {
			$rootScope.user = account;
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
    }]);
