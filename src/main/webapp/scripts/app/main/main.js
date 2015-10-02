'use strict';

angular.module('hipster1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('home', {
                parent: 'site',
                url: '/',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/main/main.html',
                        controller: 'MainController'
                    },
                    'sidenav@': {
                        templateUrl: 'scripts/app/entities/userDTO/userNav.html',
                        controller: 'MainController'
                    }
                },
                resolve: {
                    
                }
            });
    });
