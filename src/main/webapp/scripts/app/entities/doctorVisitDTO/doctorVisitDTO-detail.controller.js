'use strict';

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
