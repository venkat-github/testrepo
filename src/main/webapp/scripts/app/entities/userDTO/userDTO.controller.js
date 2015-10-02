'use strict';

angular.module('hipster1App')
    .controller('UserDTOController', function ($rootScope, $http, $scope, UserDTO, ParseLinks) {
    	$scope.userDTOs = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            UserDTO.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.userDTOs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            UserDTO.get({id: id}, function(result) {
                $scope.userDTO = result;
                $('#deleteUserDTOConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            UserDTO.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteUserDTOConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.userDTO = {name: null, mobileno: null, emailId: null, password: null, sex: null, age: null, dateOfBirth: null, bloodGroup: null, location: null, city: null, id: null};
        };
                        
    });
