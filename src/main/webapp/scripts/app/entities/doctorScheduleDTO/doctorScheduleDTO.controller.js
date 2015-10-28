'use strict';


angular.module('hipster1App')
    .factory('DoctorScheduleDTO', function ($resource, DateUtils) {
        return $resource('api/doctorSchedules/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.startDate = DateUtils.convertLocaleDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocaleDateToServer(data.endDate);
                    alert(data.startDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.startDate = DateUtils.convertLocaleDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocaleDateToServer(data.endDate);
                    alert(data.startDate);
                    return angular.toJson(data);
                }
            }
            
        });
    });

angular.module('hipster1App')
    .controller('DoctorScheduleDTODetailController', function ($scope, $rootScope, $stateParams, entity, DoctorScheduleDTO) {
        $scope.doctorScheduleDTO = entity;
        $scope.load = function (id) {
            DoctorScheduleDTO.get({id: id}, function(result) {
                $scope.doctorScheduleDTO = result;
            });
        };
        $rootScope.$on('hipster1App:doctorScheduleDTOUpdate', function(event, result) {
            $scope.doctorScheduleDTO = result;
        });
    });

angular.module('hipster1App')
    .controller('DoctorScheduleDTOController', function ($scope, DoctorScheduleDTO, ParseLinks) {
        $scope.doctorScheduleDTOs = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            DoctorScheduleDTO.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.doctorScheduleDTOs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            DoctorScheduleDTO.get({id: id}, function(result) {
                $scope.doctorScheduleDTO = result;
                $('#deleteDoctorScheduleDTOConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            DoctorScheduleDTO.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDoctorScheduleDTOConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

      $scope.clear = function () {
            $scope.doctorScheduleDTO = {userId: null, visitId: null, symptoms: null, diagnosis: null, prescription: null, tests: null, id: null};
        };


    });


angular.module('hipster1App').controller('DoctorScheduleDTODialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'hospitals', 'DoctorScheduleDTO',
        function($scope, $stateParams, $state, entity, hospitals, DoctorScheduleDTO) {

        $scope.doctorScheduleDTO = entity;
        
        angular.forEach(hospitals, function(value, key) {
                      alert(key);});

        $scope.hospitals2 = hospitals;
        
        $scope.times = [{name:"8:00"}, {name:"8:30"},{name:"9:00"}];
        $scope.load = function(id) {
            DoctorScheduleDTO.get({id : id}, function(result) {
                $scope.doctorScheduleDTO = result;

            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('hipster1App:doctorScheduleDTOUpdate', result);
            //$modalInstance.close(result);
            $state.go('doctorScheduleDTO');
        };

        $scope.save = function () {
            if ($scope.doctorScheduleDTO.id != null) {
                DoctorScheduleDTO.update($scope.doctorScheduleDTO, onSaveFinished);
            } else {
                DoctorScheduleDTO.save($scope.doctorScheduleDTO, onSaveFinished);
            }
        };
/*
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
*/

              $scope.today = function() {
            $scope.doctorScheduleDTO.startDate = new Date();
            $scope.doctorScheduleDTO.endDate = new Date();
        };
        $scope.today();

  $scope.clear = function () {
    $scope.dt = null;
  };

  
  $scope.open2 = function($event) {
    $scope.opened2 = true;
  };

$scope.open1 = function($event) {
    $scope.opened1 = true;
  };

  $scope.dateOptions = {
    formatYear: 'yy',
    startingDay: 1
  };

  $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
  $scope.format = $scope.formats[0];

  $scope.opened1 = false;
    $scope.opened2 = false;
  $scope.getDayClass = function(date, mode) {
    if (mode === 'day') {
      var dayToCheck = new Date(date).setHours(0,0,0,0);

      for (var i=0;i<$scope.events.length;i++){
        var currentDay = new Date($scope.events[i].date).setHours(0,0,0,0);

        if (dayToCheck === currentDay) {
          return $scope.events[i].status;
        }
      }
    }

    return '';
    };
  
}]);


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
