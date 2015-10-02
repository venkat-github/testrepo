'use strict';

angular.module('hipster1App')
    .controller('DoctorVisitDTOController', function ($scope, DoctorVisitDTO, ParseLinks) {
        $scope.doctorVisitDTOs = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            DoctorVisitDTO.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.doctorVisitDTOs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            DoctorVisitDTO.get({id: id}, function(result) {
                $scope.doctorVisitDTO = result;
                $('#deleteDoctorVisitDTOConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            DoctorVisitDTO.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDoctorVisitDTOConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.doctorVisitDTO = {userId: null, doctorId: null, hospitalId: null, date: null, slotNo: null, id: null};
        };
    });
