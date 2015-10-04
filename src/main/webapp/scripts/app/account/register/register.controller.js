'use strict';

angular.module('hipster1App')
    .controller('RegisterController', function ($scope, $rootScope, $timeout, Auth) {
        $scope.success = null;
        $scope.error = null;
        $scope.showregister = true;
        $scope.showotp = false;
        $rootScope.email = '';
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
            return SubmitDoctorRegistration.save($rootScope.doctorDTO, function () {
                }, function (err) {
                }).$promise;
        }

        $scope.submitHospital = function () {
            return SubmitHospitalRegistration.save($rootScope.hospitalAdminDTO, function () {
                }, function (err) {
                }).$promise;
        }

        $scope.submitOtp = function () {
            Auth.registerOtp($rootScope.email, $scope.otp).then(function () {
                    alert('succesfully validated otp 2');
                    $scope.showotp = false;
                    
                    if ($rootScope.isDoctor) {
                        $rootScope.showDoctorForm = true;
                    } else if ($rootScope.isHospital) {
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
                $rootScope.isDoctor = $scope.registerAccount.doctor;
                $rootScope.isHospital = $scope.registerAccount.hospitalAdmin;
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
