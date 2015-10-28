'use strict';



angular.module('hipster1App')
    .controller('HospitalDTODetailController', function ($scope, $rootScope, $stateParams, entity, HospitalDTO) {
        $scope.hospitalDTO = entity;
        $scope.load = function (id) {
            HospitalDTO.get({id: id}, function(result) {
                $scope.hospitalDTO = result;
            });
        };
        $rootScope.$on('hipster1App:hospitalDTOUpdate', function(event, result) {
            $scope.hospitalDTO = result;
        });
    });


angular.module('hipster1App').controller('HospitalDTODialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'HospitalDTO',
        function($scope, $stateParams, $state, entity, HospitalDTO) {

        $scope.hospitalDTO = entity;
        $scope.load = function(id) {
            HospitalDTO.get({id : id}, function(result) {
                $scope.hospitalDTO = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('hipster1App:hospitalDTOUpdate', result);
            //$modalInstance.close(result);
            $state.go('hospitalDTO',{mobileno : '12345'});
        };

        $scope.save = function () {
            if ($scope.hospitalDTO.id != null) {
                HospitalDTO.update($scope.hospitalDTO, onSaveFinished);
            } else {
                HospitalDTO.save($scope.hospitalDTO, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);


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

angular.module('hipster1App')
    .controller('HospitalDTOController', function ($scope, HospitalDTO, ParseLinks) {
        $scope.hospitalDTOs = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            HospitalDTO.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.hospitalDTOs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            HospitalDTO.get({id: id}, function(result) {
                $scope.hospitalDTO = result;
                $('#deleteHospitalDTOConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            HospitalDTO.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteHospitalDTOConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.hospitalDTO = {name: null, mobileNo: null, emailId: null, city: null, location: null, zipcode: null, id: null};
        };
    });
