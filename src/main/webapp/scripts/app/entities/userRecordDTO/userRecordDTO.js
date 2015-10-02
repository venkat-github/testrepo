'use strict';

angular.module('hipster1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('userRecordDTO', {
                parent: 'home',
                url: '/userRecordDTOs',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'UserRecordDTOs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userRecordDTO/userRecordDTOs.html',
                        controller: 'UserRecordDTOController'
                    }
                },
                resolve: {
                }
            })
            .state('userRecordDTO.detail', {
                parent: 'userRecordDTO',
                url: '/userRecordDTO/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'UserRecordDTO'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userRecordDTO/userRecordDTO-detail.html',
                        controller: 'UserRecordDTODetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'UserRecordDTO', function($stateParams, UserRecordDTO) {
                        return UserRecordDTO.get({id : $stateParams.id});
                    }]
                }
            })
            .state('userRecordDTO.new', {
            	parent: 'userRecordDTO',
                url: '/new',
                params : { userId : null, visitId:null},
                data: {
                    roles: ['ROLE_USER'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userRecordDTO/userRecordDTO-dialog.html',
                        controller: 'UserRecordDTODialogController'
                    }
                },
                resolve: {
                    entity: ['$state', '$stateParams', 'UserRecordDTO', function($state, $stateParams, UserRecordDTO) {
                    	alert('new userid '+$stateParams.userId)
                    	return {userId: $stateParams.userId, visitId: $stateParams.visitId, symptoms: null, diagnosis: null, prescription: null, tests: null, id: null};
                    }]
                }
            })
            .state('userRecordDTO.edit', {
                parent: 'userRecordDTO',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/userRecordDTO/userRecordDTO-dialog.html',
                        controller: 'UserRecordDTODialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['UserRecordDTO', function(UserRecordDTO) {
                                return UserRecordDTO.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('userRecordDTO', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
