'use strict';

angular.module('hipster1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('entity', {
                abstract: true,
                parent: 'site',
                views : {
                    'sidenav@': {
                        templateUrl: 'scripts/app/entities/userDTO/userNav.html',
                        controller: 'MainController'
                    }
                }
            });
    });
