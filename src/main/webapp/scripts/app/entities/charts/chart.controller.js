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
            })
            .state('line1', {
                parent: 'home',
                url: '/line1',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ChartController'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/charts/line.html',
                        controller: 'ChartController'
                    }
                },
                resolve: {
                }
            })
            .state('pie1', {
                parent: 'home',
                url: '/pie1',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ChartController'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/charts/pie.html',
                        controller: 'ChartController'
                    }
                },
                resolve: {
                }
            })
            .state('stack1', {
                parent: 'home',
                url: '/stack1',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ChartController'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/charts/stacked.bar.html',
                        controller: 'ChartController'
                    }
                },
                resolve: {
                }
            });
        });

