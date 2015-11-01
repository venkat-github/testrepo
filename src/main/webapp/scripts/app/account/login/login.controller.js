'use strict';


angular.module('hipster1App')
    .controller('LoginController', function ($rootScope, $scope, $state, $timeout,Account, Auth) {
        $scope.user = {};
        $scope.errors = {};
        alert('login controller loaded');
        $scope.rememberMe = true;
        $timeout(function (){angular.element('[ng-model="username"]').focus();});
        $scope.login = function (event) {
            event.preventDefault();
            alert('logging in ');
            Auth.login({
                username: $scope.username,
                password: $scope.password,
                rememberMe: $scope.rememberMe
            }).then(function () {
                alert('logged in');
                $scope.authenticationError = false;
                Account.get().$promise.then(function(user) {
                    $rootScope.user = user.data;
                });
                if ($rootScope.previousStateName === 'register') {
                    $state.go('home');
                } else {
                    //$rootScope.back();
                    $state.go("userRecordDTO");
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
