'use strict';

angular.module('hipster1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hospitalDoctorDTO4', {
                parent: 'entity',
                url: '/hospitalDoctorDTO4s',
                data: {
                    pageTitle: 'HospitalDoctorDTO4s'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/hospitalDoctorDTO4/hospitalDoctorDTO4s.html',
                        controller: 'HospitalDoctorDTO4Controller'
                    }
                },
                resolve: {
                }
            })
            .state('hospitalDoctorDTO4.book', {
                parent: 'hospitalDoctorDTO4',
                url: '/hospitalDoctorDTO4sbook/:id',
                data: {
                    pageTitle: 'HospitalDoctorDTO4s'
                },
                onEnter: ['$stateParams', '$state','$modal',  
                          function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hospitalDoctorDTO4/doctorVisitDTO-dialog.html',
                        controller: 'HospitalDoctorDTO4DialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['HospitalDoctorDTO4', function(HospitalDoctorDTO4) {
                            	//$scope.hospitalDoctorDTO4 = HospitalDoctorDTO4.get({id : $stateParams.id}); 
                                return HospitalDoctorDTO4.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hospitalDoctorDTO4', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
 
                
            })
            .state('hospitalDoctorDTO4.detail', {
                parent: 'entity',
                url: '/hospitalDoctorDTO4/{id}',
                data: {
                    pageTitle: 'HospitalDoctorDTO4'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/hospitalDoctorDTO4/hospitalDoctorDTO4-detail.html',
                        controller: 'HospitalDoctorDTO4DetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'HospitalDoctorDTO4', function($stateParams, HospitalDoctorDTO4) {
                        return HospitalDoctorDTO4.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hospitalDoctorDTO4.new', {
                parent: 'hospitalDoctorDTO4',
                url: '/new',
                data: {
                    
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hospitalDoctorDTO4/hospitalDoctorDTO4-dialog.html',
                        controller: 'HospitalDoctorDTO4DialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {doctorName: null, speciality: null, experience: null, date: null, freeSlots: null, occupiedSlots: null, degree: null, doctorId: null, hospitalId: null, location: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('hospitalDoctorDTO4', null, { reload: true });
                    }, function() {
                        $state.go('hospitalDoctorDTO4');
                    })
                }]
            })
            .state('hospitalDoctorDTO4.edit', {
                parent: 'hospitalDoctorDTO4',
                url: '/{id}/edit',
                data: {
                    
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hospitalDoctorDTO4/hospitalDoctorDTO4-dialog.html',
                        controller: 'HospitalDoctorDTO4DialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['HospitalDoctorDTO4', function(HospitalDoctorDTO4) {
                                return HospitalDoctorDTO4.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hospitalDoctorDTO4', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
