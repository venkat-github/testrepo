'use strict';

angular.module('hipster1App')
    .factory('HospitalDoctorDTO4Check', function ($resource, DateUtils) {
        return $resource('api/consultation/:id', {}, {
            'query': { method: 'GET', isArray: false}});
    });



angular.module('hipster1App').controller('HospitalDoctorDTO4DialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'HospitalDoctorDTO4',
        function($scope, $stateParams, $modalInstance, entity, HospitalDoctorDTO4) {

        $scope.hospitalDoctorDTO4 = entity;
        $scope.load = function(id) {
            HospitalDoctorDTO4.get({id : id}, function(result) {
                $scope.hospitalDoctorDTO4 = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('hipster1App:hospitalDoctorDTO4Update', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.hospitalDoctorDTO4.id != null) {
                HospitalDoctorDTO4.update($scope.hospitalDoctorDTO4, onSaveFinished);
            } else {
                HospitalDoctorDTO4.save($scope.hospitalDoctorDTO4, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);


angular.module('hipster1App')
    .controller('HospitalDoctorDTO4DetailController', function ($scope, $rootScope, $stateParams, entity, HospitalDoctorDTO4) {
        $scope.hospitalDoctorDTO4 = entity;
        $scope.load = function (id) {
            HospitalDoctorDTO4.get({id: id}, function(result) {
                $scope.hospitalDoctorDTO4 = result;
            });
        };
        $rootScope.$on('hipster1App:hospitalDoctorDTO4Update', function(event, result) {
            $scope.hospitalDoctorDTO4 = result;
        });
    });

angular.module('hipster1App')
    .controller('HospitalDoctorDTO4Controller',
    		['Auth','$scope','DateUtils', 'HospitalDoctorDTO4Check','$stateParams', '$state','$http', '$rootScope', 'HospitalDoctorDTO4','Principal','ParseLinks',
    		 function (Auth,$scope, DateUtils, HospitalDoctorDTO4Check, $stateParams,  $state, $http, $rootScope, HospitalDoctorDTO4,Principal, ParseLinks) {
        $scope.page = 1;
        $scope.loadAll = function(speciality) {
        	if (!angular.isDefined(speciality)) {
        		speciality = $rootScope.speciality;
        	}
            //date:'8/1/2015',
            HospitalDoctorDTO4.query({page: $scope.page, per_page: 5,
            	location: $rootScope.city, date: DateUtils.convertLocaleDateParamToServer(new Date()),
                speciality : $rootScope.speciality }, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.hospitalDoctorDTO4s = result;
                $scope.hospitalDoctorDTO4check = result[0];
            });
            
        };
        
                              
        $scope.today = function() {
            $scope.startDate = new Date();
        };
        $scope.today();

        $scope.changedt = function () {
            HospitalDoctorDTO4Check.query({
                doctorId: $rootScope.hospitalDoctorDTO4check.doctorId,
                date: DateUtils.convertLocaleDateParamToServer($scope.startDate),
                speciality : $rootScope.speciality }, function(result, headers) {
                    $scope.hospitalDoctorDTO4check = result;
            });
        }

        $scope.cleardt = function () {
            $scope.startDate = null;
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
                          
        $rootScope.fixAppointment = function(slot , hospitalDoctorDTO4) {
        	$rootScope.hospitalDoctorDTO4 = hospitalDoctorDTO4;
        	$rootScope.slot = slot;
        	$scope.clear();
        	
        	Principal.identity(true).then(function(account) {
                $scope.account = account;
                if (!angular.isDefined(account) || account == null) {
                    
                    $('#mymodal').modal('hide');

                    $state.go('book',{ 
                    'id':null,
                    'slot':slot,
                    'consultId':hospitalDoctorDTO4.id,
                    'doctorId':hospitalDoctorDTO4.doctorId,
                    'hospitalId':hospitalDoctorDTO4.hospitalId,
                    'slotNo':1,
                    'date': hospitalDoctorDTO4.date});
                } else {
                    
                    $state.go('doctorVisitDTO.fix',{ 'data':{
            		'id':null,
            		'slot':slot,
            		'consultId':hospitalDoctorDTO4.id,
            		'userId':account.id,
            		'doctorId':hospitalDoctorDTO4.doctorId,
            		'hospitalId':hospitalDoctorDTO4.hospitalId,
            		'slotNo':1,
            		'date': hospitalDoctorDTO4.date}});
                }
            
            });
        }

        
        $scope.bookAppointment = function(item, event) {
          	//alert('booking '+JSON.stringify(item));
          	//alert('booking '+event);mymodal
        	$scope.hospitalDoctorDTO4check = item;
        	$rootScope.hospitalDoctorDTO4check = item;
          	//$state.go('hospitalDoctorDTO4.book',{id:item.id});
        	$('#mymodal').modal('show');
        	
        }
        
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            HospitalDoctorDTO4.get({id: id}, function(result) {
                $scope.hospitalDoctorDTO4 = result;
                $('#deleteHospitalDoctorDTO4Confirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            HospitalDoctorDTO4.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteHospitalDoctorDTO4Confirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
        	$('#mymodal').modal('hide');
            $scope.hospitalDoctorDTO4 = {doctorName: null, speciality: null, experience: null, date: null, freeSlots: null, occupiedSlots: null, degree: null, doctorId: null, hospitalId: null, location: null, id: null};
        };
        

    }]);


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

