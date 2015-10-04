'use strict';

angular.module('hipster1App').controller('UserDTODialogController',
    ['$scope', '$state', '$stateParams', 'entity', 'UserDTO',
        function($scope, $state, $stateParams, entity, UserDTO) {
    	
    	$scope.userDTO = entity;
        $scope.load = function(id) {
            UserDTO.get({id : id}, function(result) {
                $scope.userDTO = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('hipster1App:userDTOUpdate', result);
            $state.go('userDTO.detail',{id : $scope.userDTO.id});
        };

        $scope.save = function () {
        	if ($scope.userDTO.id != null) {
                UserDTO.update($scope.userDTO, onSaveFinished);
            } else {
                UserDTO.save($scope.userDTO, onSaveFinished);
            }
        };

        $scope.clear = function() {
            //$modalInstance.dismiss('cancel');
        };
}]);
