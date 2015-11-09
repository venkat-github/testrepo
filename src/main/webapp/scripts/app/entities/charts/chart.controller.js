'use strict';


angular.module('hipster1App')
    .controller('ChartController', function ($scope, $rootScope, $stateParams) {
        console.log('loaded charts');
    });



angular.module('hipster1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('chart1', {
                parent: 'home',
                url: '/chart1',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ChartController'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/charts/chart1.html',
                        controller: 'ChartController'
                    }
                },
                resolve: {
                }
            });
        });

