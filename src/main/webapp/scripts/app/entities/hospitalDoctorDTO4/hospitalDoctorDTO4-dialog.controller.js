'use strict';

angular.module('hipster1App').controller('HospitalDoctorDTO4DialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'HospitalDoctorDTO4',
        function($scope, $stateParams, $modalInstance, entity, HospitalDoctorDTO4) {

        $scope.hospitalDoctorDTO4 = entity;
        $scope.load = function(id) {
            HospitalDoctorDTO4.get({id : id}, function(result) {
                $scope.hospitalDoctorDTO4 = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('hipster1App:hospitalDoctorDTO4Update', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.hospitalDoctorDTO4.id != null) {
                HospitalDoctorDTO4.update($scope.hospitalDoctorDTO4, onSaveFinished);
            } else {
                HospitalDoctorDTO4.save($scope.hospitalDoctorDTO4, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
