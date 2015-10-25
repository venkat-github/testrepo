'use strict';

angular.module('hipster1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('testBlob', {
                parent: 'entity',
                url: '/testBlobs',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'TestBlobs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/testBlob/testBlobs.html',
                        controller: 'TestBlobController'
                    }
                },
                resolve: {
                }
            })
            .state('testBlob.detail', {
                parent: 'entity',
                url: '/testBlob/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'TestBlob'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/testBlob/testBlob-detail.html',
                        controller: 'TestBlobDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'TestBlob', function($stateParams, TestBlob) {
                        return TestBlob.get({id : $stateParams.id});
                    }]
                }
            })
            .state('testBlob.new', {
                parent: 'testBlob',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/testBlob/testBlob-dialog.html',
                        controller: 'TestBlobDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {image: null, userId: null};
                                //return {file: null, image: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('testBlob', null, { reload: true });
                    }, function() {
                        $state.go('testBlob');
                    })
                }]
            })
            .state('testBlob.edit', {
                parent: 'testBlob',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/testBlob/testBlob-dialog.html',
                        controller: 'TestBlobDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TestBlob', function(TestBlob) {
                                return TestBlob.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('testBlob', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
