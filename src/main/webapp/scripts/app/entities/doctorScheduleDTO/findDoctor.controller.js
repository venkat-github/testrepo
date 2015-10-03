'use strict';

angular.module('hipster1App').controller('FindDoctorController',
    ['$scope', '$stateParams', '$state', 'entity', 'ParseLinks', 'DoctorScheduleDTO',
    'UserDTO',
        function($scope, $stateParams, $state, entity, ParseLinks, DoctorScheduleDTO, UserDTO) {

        $scope.doctorName = '';

        $scope.load = function(id) {
            DoctorScheduleDTO.get({id : id}, function(result) {
                $scope.doctorScheduleDTO = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('hipster1App:doctorScheduleDTOUpdate', result);
            //$modalInstance.close(result);
            $state.go('doctorScheduleDTO');
        };

        $scope.addDoctorToHospitalPage = function (item) {
            $state.go('doctorScheduleDTO.new',{data:item});
        }

        $scope.search = function () {
            UserDTO.query({page: $scope.page, per_page: 5, name: $scope.doctorName}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.userDTOs = result;
            });
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
