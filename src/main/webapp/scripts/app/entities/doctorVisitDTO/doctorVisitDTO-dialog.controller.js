'use strict';

angular.module('hipster1App').controller('DoctorVisitDTODialogController',
    ['$scope', '$stateParams', '$state', '$modalInstance','entity', 'DoctorVisitDTO',
        function($scope, $stateParams, $state, $modalInstance, entity, DoctorVisitDTO) {

        $scope.doctorVisitDTO = entity;
        $scope.load = function(id) {
            DoctorVisitDTO.get({id : id}, function(result) {
                $scope.doctorVisitDTO = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('hipster1App:doctorVisitDTOUpdate', result);
            $modalInstance.close(result);
            //$state.go('doctorVisitDTO',{mobileno : '12345'});
        };

        $scope.save = function () {
            if ($scope.doctorVisitDTO.id != null) {
                DoctorVisitDTO.update($scope.doctorVisitDTO, onSaveFinished);
            } else {
                DoctorVisitDTO.save($scope.doctorVisitDTO, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
