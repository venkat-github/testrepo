'use strict';

angular.module('hipster1App')
    .controller('RegisterController', function ($scope, $rootScope, $timeout, Auth,
     SubmitHospitalRegistration,
     SubmitDoctorRegistration) {
        $scope.success = null;
        $scope.error = null;
        $scope.showregister = true;
        $scope.showotp = false;
        $rootScope.email = '';
        $rootScope.mobileno = '1234567890';
        $scope.otp = '';
        $scope.doNotMatch = null;
        $scope.errorUserExists = null;
        $scope.registerAccount = {};
        
        $rootScope.showDoctorForm = false;
        $rootScope.showHospitalForm = false;
        $rootScope.isDoctor = false;
        $rootScope.isHospital = false;

        $rootScope.doctorDTO = {};
        $rootScope.hospitalAdminDTO = {};


        $timeout(function (){angular.element('[ng-model="registerAccount.login"]').focus();});

        $scope.submitDoctor = function () {
            var val = angular.element("#degrees").val();
            $rootScope.doctorDTO.degrees = val;
            return SubmitDoctorRegistration.save($rootScope.doctorDTO, function () {
                    alert('succeeded 1');
                    $scope.success = true;
                    $rootScope.showDoctorForm = false;
                }, function (err) {
                    alert('failed 1');
                }).$promise;

        }

        $scope.submitHospital = function () {
            return SubmitHospitalRegistration.save($rootScope.hospitalAdminDTO, function () {
                    alert('succeeded 2');
                    $scope.success = true;
                    $rootScope.showHospitalForm = false;
                }, function (err) {
                    alert('failed 1');
                }).$promise;
        }

        $scope.submitOtp = function () {
        	alert('submitting mobile no '+$rootScope.mobileno);
            Auth.registerOtp($rootScope.mobileno, $scope.otp).then(function () {
                    alert('succesfully validated otp 2');
                    $scope.showotp = false;
                    
                    if ($rootScope.isDoctor) {
                        $rootScope.doctorDTO.email = $rootScope.email;
                        $rootScope.doctorDTO.mobileno = $rootScope.mobileno;
                        $rootScope.showDoctorForm = true;
                    } else if ($rootScope.isHospital) {
                        $rootScope.hospitalAdminDTO.mobileno = $rootScope.mobileno;
                        $rootScope.hospitalAdminDTO.email = $rootScope.email;
                        $rootScope.showHospitalForm = true;
                    } else {
                        $scope.success = true;
                    }
                }).catch(function (response) {
                    $scope.invalid_otp = true;
                });
        }

        $scope.register = function () {
            if ($scope.registerAccount.password !== $scope.confirmPassword) {
                $scope.doNotMatch = 'ERROR';
            } else {
                $scope.registerAccount.langKey =  'en' ;
                $scope.doNotMatch = null;
                $scope.error = null;

                $scope.errorUserExists = null;
                $scope.errorEmailExists = null;
                $rootScope.email = $scope.registerAccount.email;
                $rootScope.mobileno = $scope.registerAccount.mobileno;
                $scope.mobileno = $scope.registerAccount.mobileno;
                $rootScope.isDoctor = $scope.registerAccount.doctor;
                $rootScope.isHospital = $scope.registerAccount.hospitalAdmin;

                $scope.registerAccount.languages = ['TAMIL','TELUGU'];
                $scope.registerAccount.sex = 'MALE'
                $scope.registerAccount.bloodGroup = 'A_POSITIVE';
                $scope.registerAccount.communication = 'CALL_MOBILE';
                $scope.registerAccount.specialities = ['DENTIST','GYNIC'];

                Auth.createAccount($scope.registerAccount).then(function () {
                    
                    alert('showing otp form');
                    $scope.showregister = false;
                    $scope.showotp = true;
                }).catch(function (response) {
                    $scope.success = null;
                    if (response.status === 400 && response.data === 'login already in use') {
                        $scope.errorUserExists = 'ERROR';
                    } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                        $scope.errorEmailExists = 'ERROR';
                    } else {
                        $scope.error = 'ERROR';
                    }
                });
            }
        };
    });


angular.module('hipster1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('register', {
                parent: 'account',
                url: '/register',
                data: {
                    roles: [],
                    pageTitle: 'Registration'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/register/register.html',
                        controller: 'RegisterController'
                    }
                },
                resolve: {
                    
                }
            });
    });
