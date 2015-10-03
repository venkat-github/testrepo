'use strict';

angular.module('hipster1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('doctorScheduleDTO', {
                parent: 'home',
                url: '/doctorScheduleDTOs',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'DoctorScheduleDTOs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/doctorScheduleDTO/doctorScheduleDTOs.html',
                        controller: 'DoctorScheduleDTOController'
                    }
                },
                resolve: {
                }
            })
            .state('doctorScheduleDTO.detail', {
                parent: 'doctorScheduleDTO',
                url: '/doctorScheduleDTO/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'DoctorScheduleDTO'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/doctorScheduleDTO/doctorScheduleDTO-detail.html',
                        controller: 'DoctorScheduleDTODetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'DoctorScheduleDTO', function($stateParams, DoctorScheduleDTO) {
                        return DoctorScheduleDTO.get({id : $stateParams.id});
                    }]
                }
            })
            .state('doctorScheduleDTO.new.start', {
            	parent: 'doctorScheduleDTO',
                url: '/new_start',
                params : { userId : null, visitId:null},
                data: {
                    roles: ['ROLE_USER'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/doctorScheduleDTO/findDoctor-dialog.html',
                        controller: 'FindDoctorController'
                    }
                },
                resolve: {
                    entity: ['$state', '$stateParams', 'DoctorScheduleDTO', function($state, $stateParams, DoctorScheduleDTO) {
                        return {doctorName:null}
                    }],
                }
            })

            .state('doctorScheduleDTO.new', {
            	parent: 'doctorScheduleDTO',
                url: '/new',
                params : { userId : null, visitId:null, data:null},
                data: {
                    roles: ['ROLE_USER'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/doctorScheduleDTO/doctorScheduleDTO-dialog.html',
                        controller: 'DoctorScheduleDTODialogController'
                    }
                },
                resolve: {
                    entity: ['$state', '$stateParams', 'DoctorScheduleDTO', function($state, $stateParams, DoctorScheduleDTO) {
                        
                    	return {doctorId: $stateParams.data.id, doctorName : $stateParams.data.name}
                    }],
                    

                    hospitals : ['$state', 'ParseLinks','$stateParams', 'HospitalDTO', function(
                        $state, ParseLinks, $stateParams, HospitalDTO) {
                        
                        return HospitalDTO.query({}, 
                            function(result, headers) {
                                result.links = ParseLinks.parse(headers('link'));
                                return result;
                            });
                    }]
                }
            })
            .state('doctorScheduleDTO.edit', {
                parent: 'doctorScheduleDTO',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/doctorScheduleDTO/doctorScheduleDTO-dialog.html',
                        controller: 'DoctorScheduleDTODialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DoctorScheduleDTO', function(DoctorScheduleDTO) {
                                return DoctorScheduleDTO.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('doctorScheduleDTO', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
