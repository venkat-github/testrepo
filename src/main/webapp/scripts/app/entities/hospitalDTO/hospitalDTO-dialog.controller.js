'use strict';

angular.module('hipster1App').controller('HospitalDTODialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'HospitalDTO',
        function($scope, $stateParams, $state, entity, HospitalDTO) {

        $scope.hospitalDTO = entity;
        $scope.load = function(id) {
            HospitalDTO.get({id : id}, function(result) {
                $scope.hospitalDTO = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('hipster1App:hospitalDTOUpdate', result);
            //$modalInstance.close(result);
            $state.go('hospitalDTO',{mobileno : '12345'});
        };

        $scope.save = function () {
            if ($scope.hospitalDTO.id != null) {
                HospitalDTO.update($scope.hospitalDTO, onSaveFinished);
            } else {
                HospitalDTO.save($scope.hospitalDTO, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
