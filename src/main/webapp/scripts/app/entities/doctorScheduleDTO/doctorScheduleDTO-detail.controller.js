'use strict';

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
