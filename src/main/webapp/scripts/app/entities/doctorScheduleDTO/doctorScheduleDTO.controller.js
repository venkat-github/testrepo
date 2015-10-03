'use strict';

angular.module('hipster1App')
    .controller('DoctorScheduleDTOController', function ($scope, DoctorScheduleDTO, ParseLinks) {
        $scope.doctorScheduleDTOs = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            DoctorScheduleDTO.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.doctorScheduleDTOs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            DoctorScheduleDTO.get({id: id}, function(result) {
                $scope.doctorScheduleDTO = result;
                $('#deleteDoctorScheduleDTOConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            DoctorScheduleDTO.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDoctorScheduleDTOConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

      $scope.clear = function () {
            $scope.doctorScheduleDTO = {userId: null, visitId: null, symptoms: null, diagnosis: null, prescription: null, tests: null, id: null};
        };


    });
