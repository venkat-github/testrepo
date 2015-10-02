'use strict';

angular.module('hipster1App')
    .controller('HospitalDTOController', function ($scope, HospitalDTO, ParseLinks) {
        $scope.hospitalDTOs = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            HospitalDTO.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.hospitalDTOs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            HospitalDTO.get({id: id}, function(result) {
                $scope.hospitalDTO = result;
                $('#deleteHospitalDTOConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            HospitalDTO.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteHospitalDTOConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.hospitalDTO = {name: null, mobileNo: null, emailId: null, city: null, location: null, zipcode: null, id: null};
        };
    });
