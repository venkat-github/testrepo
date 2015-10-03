'use strict';

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
