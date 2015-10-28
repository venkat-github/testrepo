'use strict';

angular.module('hipster1App')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });

angular.module('hipster1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('logout', {
                parent: 'account',
                url: '/logout',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/main/main.html',
                        controller: 'LogoutController'
                    }
                }
            });
    });

