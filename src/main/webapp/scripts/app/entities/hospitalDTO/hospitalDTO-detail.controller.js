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
