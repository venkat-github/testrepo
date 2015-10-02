'use strict';

angular.module('hipster1App')
    .controller('TestBlobDetailController', function ($scope, $rootScope, $stateParams, entity, TestBlob) {
        $scope.testBlob = entity;
        $scope.load = function (id) {
            TestBlob.get({id: id}, function(result) {
                $scope.testBlob = result;
            });
        };
        $rootScope.$on('hipster1App:testBlobUpdate', function(event, result) {
            $scope.testBlob = result;
        });
    });
