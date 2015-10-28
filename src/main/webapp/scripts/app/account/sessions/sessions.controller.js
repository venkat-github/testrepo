'use strict';

angular.module('hipster1App')
    .controller('SessionsController', function ($scope, Sessions, Principal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
        });

        $scope.success = null;
        $scope.error = null;
        $scope.sessions = Sessions.getAll();
        $scope.invalidate = function (series) {
            Sessions.delete({series: encodeURIComponent(series)},
                function () {
                    $scope.error = null;
                    $scope.success = 'OK';
                    $scope.sessions = Sessions.getAll();
                },
                function () {
                    $scope.success = null;
                    $scope.error = 'ERROR';
                });
        };
    });


angular.module('hipster1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('sessions', {
                parent: 'account',
                url: '/sessions',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Sessions'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/sessions/sessions.html',
                        controller: 'SessionsController'
                    }
                },
                resolve: {
                    
                }
            });
    });
