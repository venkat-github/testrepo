'use strict';


angular.module('hipster1App')
    .factory('UserRecordDTO', function ($resource, DateUtils) {
        return $resource('api/userRecords/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });

angular.module('hipster1App')
    .controller('UserRecordDTODetailController', function ($scope, $rootScope, $stateParams, entity, UserRecordDTO) {
        $scope.userRecordDTO = entity;
        $scope.load = function (id) {
            UserRecordDTO.get({id: id}, function(result) {
                $scope.userRecordDTO = result;
            });
        };
        $rootScope.$on('hipster1App:userRecordDTOUpdate', function(event, result) {
            $scope.userRecordDTO = result;
        });
    });

angular.module('hipster1App').controller('UserRecordDTODialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'UserRecordDTO',
        function($scope, $stateParams, $state, entity, UserRecordDTO) {

        $scope.userRecordDTO = entity;
        $scope.load = function(id) {
            UserRecordDTO.get({id : id}, function(result) {
                $scope.userRecordDTO = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('hipster1App:userRecordDTOUpdate', result);
            //$modalInstance.close(result);
            $state.go('doctorVisitDTO');
        };

        $scope.save = function () {
            if ($scope.userRecordDTO.id != null) {
                UserRecordDTO.update($scope.userRecordDTO, onSaveFinished);
            } else {
                UserRecordDTO.save($scope.userRecordDTO, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);


angular.module('hipster1App')
    .controller('UserRecordDTOController', function ($scope, UserRecordDTO, ParseLinks) {
        $scope.userRecordDTOs = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            UserRecordDTO.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.userRecordDTOs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            UserRecordDTO.get({id: id}, function(result) {
                $scope.userRecordDTO = result;
                $('#deleteUserRecordDTOConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            UserRecordDTO.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteUserRecordDTOConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.userRecordDTO = {userId: null, visitId: null, symptoms: null, diagnosis: null, prescription: null, tests: null, id: null};
        };
    });

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

