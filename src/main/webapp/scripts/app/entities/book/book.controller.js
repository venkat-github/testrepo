'use strict';

angular.module('hipster1App')
    .controller('bookController', function ($scope, DoctorVisitDTO, ParseLinks) {
        $scope.page = 1;
        
        
        $scope.clear = function () {
            $scope.doctorVisitDTO = {userId: null, doctorId: null, hospitalId: null, date: null, slotNo: null, id: null};
        };
    });
