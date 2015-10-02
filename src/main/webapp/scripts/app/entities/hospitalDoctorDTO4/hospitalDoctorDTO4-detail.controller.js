'use strict';

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
