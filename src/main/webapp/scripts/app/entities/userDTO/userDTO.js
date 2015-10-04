'use strict';

angular.module('hipster1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('userDTO', {
                parent: 'entity',
                url: '/userDTOs',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'UserDTOs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userDTO/userDTOs.html',
                        controller: 'UserDTOController'
                    },
                    'sidenav@': {
                        templateUrl: 'scripts/app/entities/userDTO/userNav.html',
                        controller: 'MainController'
                    }
                },
                resolve: {
                }
            })
            .state('userDTO.detail', {
                parent: 'entity',
                url: '/userDTO/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'UserDTO'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userDTO/userDTO-detail.html',
                        controller: 'UserDTODetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', '$rootScope' ,'UserDTO', 
                    function($stateParams, $rootScope, UserDTO) {
                        //alert('check 1');
                        //alert(' user id is '+$rootScope.user.id);
                        //alert(' state param id id is '+$stateParams.id);
                        return UserDTO.get({id : $rootScope.user.id});

                    }]
                }
            })
            .state('userDTO.new', {
            	parent: 'userDTO',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userDTO/userDTO-dialog.html',
                        controller: 'UserDTODialogController'
                    }
                },
                resolve: {
                    entity: ['$state', '$stateParams', 'UserDTO', function($state, $stateParams, UserDTO) {
                    	return {name: null, mobileno: null, emailId: null, password: null, sex: null, age: null, dateOfBirth: null, bloodGroup: null, location: null, city: null, id: null};
                    }]
                }
                
            })
            .state('userDTO.edit', {
                parent: 'userDTO',
                url: '/userDTO/{id}',
                data: {
                    roles: ['ROLE_USER'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userDTO/userDTO-edit-dailog.html',
                        controller: 'UserDTODialogController'
                    }
                },
                resolve: {
                    entity: ['$state', '$stateParams', '$rootScope','UserDTO',
                     function($state, $stateParams, $rootScope, UserDTO) {
                    	//return {name: null, mobileno: null, emailId: null, password: null, sex: null, age: null, dateOfBirth: null, bloodGroup: null, location: null, city: null, id: null};
                    	//return UserDTO.get({id : $stateParams.id});
                        return UserDTO.get({id : $rootScope.user.id});
                    }]
                }
            });
    });
