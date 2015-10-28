'use strict';

angular.module('hipster1App')
    .controller('bookController', function ($scope, DoctorVisitDTO, ParseLinks) {
        $scope.page = 1;
        
        
        $scope.clear = function () {
            $scope.doctorVisitDTO = {userId: null, doctorId: null, hospitalId: null, date: null, slotNo: null, id: null};
        };
    });

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


angular.module('hipster1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('book', {
                parent: 'entity',
                url: '/book',
                
                params: {slot :{},consultId :{},hospitalId :{},slotNo :{},date:{}, doctorId : {}},
                data: {
                    
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/book/book-dialog.html',
                        controller: 'bookDialogController',
                        size: 'lg',
                        resolve: {
                            entity: [function() {
                                //return {id : $stateParams.id});
                            //alert('returning slot '+$stateParams.slot)
                            return {
                                slot : $stateParams.slot,
                                consultId : $stateParams.consultId,
                                hospitalId : $stateParams.hospitalId,
                                slotNo : $stateParams.slotNo,
                                date : $stateParams.date,
                                email : '',
                                mobileno : ''
                             };
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('home');
                        //$state.go('doctorVisitDTO', null, { reload: true });
                    }, function() {
                        $state.go('home');
                    })
                }]
            });
    });

