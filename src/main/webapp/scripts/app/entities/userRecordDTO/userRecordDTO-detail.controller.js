'use strict';

angular.module('hipster1App')
    .controller('UserRecordDTODetailController', function ($scope, $rootScope, $stateParams, entity, UserRecordDTO) {
        $scope.userRecordDTO = entity;
        $scope.load = function (id) {
            UserRecordDTO.get({id: id}, function(result) {
                $scope.userRecordDTO = result;
            });
        };
        $rootScope.$on('hipster1App:userRecordDTOUpdate', function(event, result) {
            $scope.userRecordDTO = result;
        });
    });
