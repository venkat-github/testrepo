'use strict';

angular.module('hipster1App')
    .controller('UserRecordDTOController', function ($scope, UserRecordDTO, ParseLinks) {
        $scope.userRecordDTOs = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            UserRecordDTO.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.userRecordDTOs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            UserRecordDTO.get({id: id}, function(result) {
                $scope.userRecordDTO = result;
                $('#deleteUserRecordDTOConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            UserRecordDTO.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteUserRecordDTOConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.userRecordDTO = {userId: null, visitId: null, symptoms: null, diagnosis: null, prescription: null, tests: null, id: null};
        };
    });
