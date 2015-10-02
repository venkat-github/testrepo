'use strict';

angular.module('hipster1App')
    .controller('UserDTODetailController', function ($scope, $rootScope, $stateParams, entity, UserDTO) {
        $scope.userDTO = entity;
        $scope.load = function (id) {
            UserDTO.get({id: id}, function(result) {
                $scope.userDTO = result;
            });
        };
        $rootScope.$on('hipster1App:userDTOUpdate', function(event, result) {
            $scope.userDTO = result;
        });
    });
