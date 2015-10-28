'use strict';

angular.module('hipster1App').controller('DoctorVisitDTODialogController',
    ['$scope', '$stateParams', '$state', '$modalInstance','entity', 'DoctorVisitDTO',
        function($scope, $stateParams, $state, $modalInstance, entity, DoctorVisitDTO) {

        $scope.doctorVisitDTO = entity;
        $scope.load = function(id) {
            DoctorVisitDTO.get({id : id}, function(result) {
                $scope.doctorVisitDTO = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('hipster1App:doctorVisitDTOUpdate', result);
            $modalInstance.close(result);
            //$state.go('doctorVisitDTO',{mobileno : '12345'});
        };

        $scope.save = function () {
            if ($scope.doctorVisitDTO.id != null) {
                DoctorVisitDTO.update($scope.doctorVisitDTO, onSaveFinished);
            } else {
                DoctorVisitDTO.save($scope.doctorVisitDTO, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);


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
                parent :'entity',
                url: '/doctorVisitDTO/{id}',
                data: {
                   roles: []
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


angular.module('hipster1App')
    .controller('DoctorVisitDTODetailController', function ($scope, $rootScope, $stateParams, entity, DoctorVisitDTO) {
        $scope.doctorVisitDTO = entity;
        $scope.load = function (id) {
            DoctorVisitDTO.get({id: id}, function(result) {
                $scope.doctorVisitDTO = result;
            });
        };
        $rootScope.$on('hipster1App:doctorVisitDTOUpdate', function(event, result) {
            $scope.doctorVisitDTO = result;
        });
    });


angular.module('hipster1App')
    .controller('DoctorVisitDTOController', function ($scope, DoctorVisitDTO, ParseLinks) {
        $scope.doctorVisitDTOs = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            DoctorVisitDTO.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.doctorVisitDTOs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            DoctorVisitDTO.get({id: id}, function(result) {
                $scope.doctorVisitDTO = result;
                $('#deleteDoctorVisitDTOConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            DoctorVisitDTO.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDoctorVisitDTOConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.doctorVisitDTO = {userId: null, doctorId: null, hospitalId: null, date: null, slotNo: null, id: null};
        };
    });
