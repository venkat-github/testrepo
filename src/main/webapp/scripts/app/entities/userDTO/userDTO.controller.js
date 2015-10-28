'use strict';


angular.module('hipster1App')
    .controller('UserDTODetailController', function ($scope, $rootScope, $stateParams, entity, UserDTO) {
        $scope.userDTO = entity;
        $scope.load = function (id) {
            UserDTO.get({id: id}, function(result) {
                $scope.userDTO = result;
            });
        };
        $rootScope.$on('hipster1App:userDTOUpdate', function(event, result) {
            $scope.userDTO = result;
        });
    });



angular.module('hipster1App').controller('UserDTODialogController',
    ['$scope', '$state', '$stateParams', 'entity', 'UserDTO',
        function($scope, $state, $stateParams, entity, UserDTO) {
        
        $scope.userDTO = entity;
        $scope.load = function(id) {
            UserDTO.get({id : id}, function(result) {
                $scope.userDTO = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('hipster1App:userDTOUpdate', result);
            $state.go('userDTO.detail',{id : $scope.userDTO.id});
        };

        $scope.save = function () {
            if ($scope.userDTO.id != null) {
                UserDTO.update($scope.userDTO, onSaveFinished);
            } else {
                UserDTO.save($scope.userDTO, onSaveFinished);
            }
        };

        $scope.clear = function() {
            //$modalInstance.dismiss('cancel');
        };
}]);


angular.module('hipster1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('userDTO', {
                parent: 'entity',
                url: '/userDTOs',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'UserDTOs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userDTO/userDTOs.html',
                        controller: 'UserDTOController'
                    },
                    'sidenav@': {
                        templateUrl: 'scripts/app/entities/userDTO/userNav.html',
                        controller: 'MainController'
                    }
                },
                resolve: {
                }
            })
            .state('userDTO.detail', {
                parent: 'entity',
                url: '/userDTO/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'UserDTO'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userDTO/userDTO-detail.html',
                        controller: 'UserDTODetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', '$rootScope' ,'UserDTO', 
                    function($stateParams, $rootScope, UserDTO) {
                        //alert('check 1');
                        //alert(' user id is '+$rootScope.user.id);
                        //alert(' state param id id is '+$stateParams.id);
                        return UserDTO.get({id : $rootScope.user.id});

                    }]
                }
            })
            .state('userDTO.new', {
                parent: 'userDTO',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userDTO/userDTO-dialog.html',
                        controller: 'UserDTODialogController'
                    }
                },
                resolve: {
                    entity: ['$state', '$stateParams', 'UserDTO', function($state, $stateParams, UserDTO) {
                        return {name: null, mobileno: null, emailId: null, password: null, sex: null, age: null, dateOfBirth: null, bloodGroup: null, location: null, city: null, id: null};
                    }]
                }
                
            })
            .state('userDTO.edit', {
                parent: 'userDTO',
                url: '/userDTO/{id}',
                data: {
                    roles: ['ROLE_USER'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userDTO/userDTO-edit-dailog.html',
                        controller: 'UserDTODialogController'
                    }
                },
                resolve: {
                    entity: ['$state', '$stateParams', '$rootScope','UserDTO',
                     function($state, $stateParams, $rootScope, UserDTO) {
                        //return {name: null, mobileno: null, emailId: null, password: null, sex: null, age: null, dateOfBirth: null, bloodGroup: null, location: null, city: null, id: null};
                        //return UserDTO.get({id : $stateParams.id});
                        return UserDTO.get({id : $rootScope.user.id});
                    }]
                }
            });
    });

angular.module('hipster1App')
    .controller('UserDTOController', function ($rootScope, $http, $scope, UserDTO, ParseLinks) {
    	$scope.userDTOs = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            UserDTO.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.userDTOs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            UserDTO.get({id: id}, function(result) {
                $scope.userDTO = result;
                $('#deleteUserDTOConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            UserDTO.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteUserDTOConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.userDTO = {name: null, mobileno: null, emailId: null, password: null, sex: null, age: null, dateOfBirth: null, bloodGroup: null, location: null, city: null, id: null};
        };
                        
    });
