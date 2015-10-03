'use strict';

angular.module('hipster1App').controller('bookDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', '$modalInstance','entity', 'DoctorVisitDTO',
        function($scope, $rootScope, $stateParams, $state, $modalInstance, entity, DoctorVisitDTO) {

        $scope.doctorVisitDTO2 = entity;
        $rootScope.doctorVisitDTO2 = entity;
        
        $scope.save = function () {
            
            $modalInstance.close();
            DoctorVisitDTO.save({
                    'id':null,
                    'slot':$rootScope.doctorVisitDTO2.slot,
                    'consultId':$rootScope.doctorVisitDTO2.consultId,
                    'userId':1,
                    'doctorId':$rootScope.doctorVisitDTO2.consultId,
                    'hospitalId':$rootScope.doctorVisitDTO2.hospitalId,
                    'slotNo':1,
                    'date': $rootScope.doctorVisitDTO2.date}, 
                    function(result) {
                        $state.go('doctorVisitDTO.detail',{id:result.id});
                    } , function() {
                    } );
            
        };

        $scope.clear = function() {
            alert('cancelling 5');
            $modalInstance.close();
            $state.go('home');
        };
}]);
