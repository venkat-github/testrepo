'use strict';


angular.module('hipster1App')
    .controller('SearchController', function ($scope, $rootScope, $stateParams) {
        console.log('loaded search');
    });



angular.module('hipster1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('search', {
                parent: 'home',
                url: '/search',
                data: {
                    pageTitle: 'SearchController'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/search/search.html',
                        controller: 'SearchController'
                    }
                },
                resolve: {
                }
            })
            ;
        });

