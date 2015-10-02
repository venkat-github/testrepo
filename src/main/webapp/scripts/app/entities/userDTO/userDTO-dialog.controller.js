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
            //$modalInstance.close(result);
            //userDTO.detail
            //$state.go('registrationOtp.new',{mobileno : '12345'});
            alert('saved')
          $state.go('userDTO.detail',{id : $scope.userDTO.id});
            
        };

        $scope.save = function () {
        	alert('save fired ')
        	if ($scope.userDTO.id != null) {
        		alert('updating')
                UserDTO.update($scope.userDTO, onSaveFinished);
            } else {
            	alert('oops not updating')
                UserDTO.save($scope.userDTO, onSaveFinished);
            }
        };

        $scope.clear = function() {
            //$modalInstance.dismiss('cancel');
        };
}]);
