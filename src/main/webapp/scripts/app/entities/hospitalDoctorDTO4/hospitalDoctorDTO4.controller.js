'use strict';

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
