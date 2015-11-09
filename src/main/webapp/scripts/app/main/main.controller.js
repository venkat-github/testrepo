'use strict';

angular.module('hipster1App')
    .controller('MainController', [ '$state', '$scope','$rootScope', 'Principal',
    	function ($state, $scope, $rootScope, Principal) {
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
            if ($scope.isAuthenticated()) {
            	//alert($scope.isAuthenticated());
            	//$state.go("userRecordDTO");
            }
        });
    }]);
