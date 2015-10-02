'use strict';

angular.module('hipster1App').controller('UserRecordDTODialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'UserRecordDTO',
        function($scope, $stateParams, $state, entity, UserRecordDTO) {

        $scope.userRecordDTO = entity;
        $scope.load = function(id) {
            UserRecordDTO.get({id : id}, function(result) {
                $scope.userRecordDTO = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('hipster1App:userRecordDTOUpdate', result);
            //$modalInstance.close(result);
            $state.go('doctorVisitDTO');
        };

        $scope.save = function () {
            if ($scope.userRecordDTO.id != null) {
                UserRecordDTO.update($scope.userRecordDTO, onSaveFinished);
            } else {
                UserRecordDTO.save($scope.userRecordDTO, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
