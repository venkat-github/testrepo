'use strict';


angular.module('hipster1App')
    .controller('LoginController', function ($rootScope,$http, $scope, $state, $timeout,Account,Principal, Auth) {
        $scope.user = {};
        $scope.errors = {};
        $scope.rememberMe = true;
        $timeout(function (){angular.element('[ng-model="username"]').focus();});
        $scope.login = function () {
            //event.preventDefault();
            Auth.login({
                username: $scope.username,
                password: $scope.password,
                rememberMe: $scope.rememberMe
            }).then(function () {
//                $scope.authenticationError = false;
                Account.get().$promise.then(function(user) {
                    $rootScope.user = user.data;
                });
                if ($rootScope.previousStateName === 'register') {
                    $state.go('home');
                } else {
                	//$rootScope.back();
                	console.log('login succeded');
                	if (Principal.isInRole('ROLE_USER')) {
                		console.log('user role');
                		$state.go("userRecordDTO");
                	} else if(Principal.isInRole('ROLE_DOCTOR')) {
                		console.log('doc role');
                	} else if(Principal.isInRole('ROLE_ADMIN')) {
                		console.log('admin role');
                	} else {
                		console.log('normal role');
                	}
                    $state.go('home');
                	//alert('firing http '+$http);
                	//$http.get("api/refresh");
                	//$location.path("/index.htnml");
                }
            }).catch(function () {
                alert('auth error ');
                $scope.authenticationError = true;
            });
        };
    });

angular.module('hipster1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('login', {
                parent: 'account',
                url: '/login',
                data: {
                    roles: [], 
                    pageTitle: 'Authentication'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/login/login.html',
                        controller: 'LoginController'
                    }
                },
                resolve: {
                    
                }
            });
    });
