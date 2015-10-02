'use strict';

angular.module('hipster1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hospitalDTO', {
                parent: 'home',
                url: '/hospitalDTOs',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'HospitalDTOs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/hospitalDTO/hospitalDTOs.html',
                        controller: 'HospitalDTOController'
                    }
                },
                resolve: {
                }
            })
            .state('hospitalDTO.detail', {
                parent: 'hospitalDTO',
                url: '/hospitalDTO/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'HospitalDTO'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/hospitalDTO/hospitalDTO-detail.html',
                        controller: 'HospitalDTODetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'HospitalDTO', function($stateParams, HospitalDTO) {
                        return HospitalDTO.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hospitalDTO.new', {
            	parent: 'hospitalDTO',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/hospitalDTO/hospitalDTO-dialog.html',
                        controller: 'HospitalDTODialogController'
                    }
                },
                resolve: {
                    entity: ['$state', '$stateParams', 'HospitalDTO', function($state, $stateParams, HospitalDTO) {
                    	return {name: null, mobileNo: null, emailId: null, city: null, location: null, zipcode: null, id: null};
                    }]
                }
            })
            .state('hospitalDTO.edit', {
                parent: 'hospitalDTO',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/hospitalDTO/hospitalDTO-dialog.html',
                        controller: 'HospitalDTODialogController'
                    }
                },
                resolve: {
                    entity: ['$state', '$stateParams', 'HospitalDTO', function($state, $stateParams, HospitalDTO) {
                    	return HospitalDTO.get({id : $stateParams.id});
                    }]
                }
            });
    });
