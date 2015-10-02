'use strict';

angular.module('hipster1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('doctorVisitDTO', {
                parent: 'home',
                url: '/doctorVisitDTOs',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'DoctorVisitDTOs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/doctorVisitDTO/doctorVisitDTOs.html',
                        controller: 'DoctorVisitDTOController'
                    }
                },
                resolve: {
                }
            })
            .state('doctorVisitDTO.fix', {
                parent: 'doctorVisitDTO',
                url: '/doctorVisitDTO.fix',
                params :{  
                	data:null
                	},
                data: {
                    roles: ['ROLE_USER','ROLE_ADMIN'],
                    pageTitle: 'DoctorVisitDTO'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/doctorVisitDTO/doctorVisitDTO-detail.html',
                        controller: 'DoctorVisitDTODetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'DoctorVisitDTO', '$rootScope', 'Principal',
                             function($stateParams, DoctorVisitDTO,$rootScope, Principal) {
                    	return Principal.identity(false).then(function(account) {
                            if (!angular.isDefined(account)) {
                            	account = {id:'1'};
                            }
                            $stateParams.data.userId = account.id;
	                    	$rootScope.doctorVisitDTO = {userId: null, doctorId: null, hospitalId: null, date: null, slotNo: null, id: null};
	                    		return DoctorVisitDTO.save($stateParams.data, function(){
	                    			//alert('saved successfully')
	                    	});
                    	});
                    	//return {userId: null, doctorId: null, hospitalId: null, date: null, slotNo: null, id: null};
                    }]
                }
            })
            .state('doctorVisitDTO.detail', {
                parent: 'doctorVisitDTO',
                url: '/doctorVisitDTO/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'DoctorVisitDTO'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/doctorVisitDTO/doctorVisitDTO-detail.html',
                        controller: 'DoctorVisitDTODetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'DoctorVisitDTO', function($stateParams, DoctorVisitDTO) {
                        return DoctorVisitDTO.get({id : $stateParams.id});
                    }]
                }
            })
            .state('doctorVisitDTO.new', {
                url: '/new',
                params: {userId :{}, doctorId : {}},
                data: {
                    roles: ['ROLE_USER'],
                },
                //return {userId: null, doctorId: null, hospitalId: null, date: null, slotNo: null, id: null};
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                	$modal.open({
                        templateUrl: 'scripts/app/entities/doctorVisitDTO/doctorVisitDTO-dialog.html',
                        controller: 'DoctorVisitDTODialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DoctorVisitDTO', function(DoctorVisitDTO) {
                            	return {userId: $stateParams.userId, doctorId: $stateParams.doctorId, hospitalId: null, date: null, slotNo: null, id: null};
                                //return DoctorVisitDTO.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        //$state.go('doctorVisitDTO', null, { reload: true });
                    }, function() {
                        //$state.go('^');
                    })
                }]

            })
            .state('doctorVisitDTO.edit', {
                parent: 'doctorVisitDTO',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/doctorVisitDTO/doctorVisitDTO-dialog.html',
                        controller: 'DoctorVisitDTODialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DoctorVisitDTO', function(DoctorVisitDTO) {
                                return DoctorVisitDTO.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('doctorVisitDTO', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
