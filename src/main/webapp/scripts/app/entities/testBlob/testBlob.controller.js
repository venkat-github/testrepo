'use strict';


angular.module('hipster1App')
    .controller('TestBlobDetailController', function ($scope, $rootScope, $stateParams, entity, TestBlob) {
        $scope.testBlob = entity;
        $scope.load = function (id) {
            TestBlob.get({id: id}, function(result) {
                $scope.testBlob = result;
            });
        };
        $rootScope.$on('hipster1App:testBlobUpdate', function(event, result) {
            $scope.testBlob = result;
        });
    });



angular.module('hipster1App').controller('TestBlobDialogController',
    ['$scope', '$state', '$stateParams', 'entity', 'TestBlob',
        function($scope,$state, $stateParams, entity, TestBlob) {

        $scope.testBlob = entity;
        $scope.load = function(id) {
            TestBlob.get({id : id}, function(result) {
                $scope.testBlob = result;
            });
        };

        var onSaveFinished = function (result) {
            $state.go('userDTO.detail');
        };

        $scope.save = function () {
            TestBlob.update($scope.testBlob, onSaveFinished);
            /*
            if ($scope.testBlob.id != null) {
                TestBlob.update($scope.testBlob, onSaveFinished);
            } else {
                TestBlob.save($scope.testBlob, onSaveFinished);
            }
            */
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };

        $scope.abbreviate = function (text) {
            if (!angular.isString(text)) {
                return '';
            }
            if (text.length < 30) {
                return text;
            }
            return text ? (text.substring(0, 15) + '...' + text.slice(-10)) : '';
        };

        $scope.byteSize = function (base64String) {
            if (!angular.isString(base64String)) {
                return '';
            }
            function endsWith(suffix, str) {
                return str.indexOf(suffix, str.length - suffix.length) !== -1;
            }
            function paddingSize(base64String) {
                if (endsWith('==', base64String)) {
                    return 2;
                }
                if (endsWith('=', base64String)) {
                    return 1;
                }
                return 0;
            }
            function size(base64String) {
                return base64String.length / 4 * 3 - paddingSize(base64String);
            }
            function formatAsBytes(size) {
                return size.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ") + " bytes";
            }

            return formatAsBytes(size(base64String));
        };

        $scope.setFile = function ($files, testBlob) {
            if ($files[0]) {
                var file = $files[0];
                var fileReader = new FileReader();
                fileReader.readAsDataURL(file);
                fileReader.onload = function (e) {
                    var data = e.target.result;
                    var base64Data = data.substr(data.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        testBlob.file = base64Data;
                    });
                };
            }
        };

        $scope.setImage = function ($files, testBlob) {
            if ($files[0]) {
                var file = $files[0];
                var fileReader = new FileReader();
                fileReader.readAsDataURL(file);
                fileReader.onload = function (e) {
                    var data = e.target.result;
                    var base64Data = data.substr(data.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        testBlob.image = base64Data;
                    });
                };
            }
        };
}]);


angular.module('hipster1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('testBlob', {
                parent: 'entity',
                url: '/testBlobs',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'TestBlobs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/testBlob/testBlobs.html',
                        controller: 'TestBlobController'
                    }
                },
                resolve: {
                }
            })
            .state('testBlob.detail', {
                parent: 'entity',
                url: '/testBlob/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'TestBlob'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/testBlob/testBlob-detail.html',
                        controller: 'TestBlobDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'TestBlob', function($stateParams, TestBlob) {
                        return TestBlob.get({id : $stateParams.id});
                    }]
                }
            })
            .state('testBlob.new', {
                parent: 'testBlob',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/testBlob/testBlob-dialog.html',
                        controller: 'TestBlobDialogController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', '$rootScope' ,
                    function () {
                                return {image: null, userId: null};
                                //return {file: null, image: null, id: null};
                            }
                    ]
                }
                
            })
            .state('testBlob.edit', {
                parent: 'testBlob',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/testBlob/testBlob-dialog.html',
                        controller: 'TestBlobDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TestBlob', function(TestBlob) {
                                return TestBlob.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('testBlob', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

angular.module('hipster1App')
    .controller('TestBlobController', function ($scope, TestBlob, ParseLinks) {
        $scope.testBlobs = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            TestBlob.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.testBlobs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            TestBlob.get({id: id}, function(result) {
                $scope.testBlob = result;
                $('#deleteTestBlobConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            TestBlob.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTestBlobConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.testBlob = {file: null, image: null, id: null};
        };

        $scope.abbreviate = function (text) {
            if (!angular.isString(text)) {
                return '';
            }
            if (text.length < 30) {
                return text;
            }
            return text ? (text.substring(0, 15) + '...' + text.slice(-10)) : '';
        };

        $scope.byteSize = function (base64String) {
            if (!angular.isString(base64String)) {
                return '';
            }
            function endsWith(suffix, str) {
                return str.indexOf(suffix, str.length - suffix.length) !== -1;
            }
            function paddingSize(base64String) {
                if (endsWith('==', base64String)) {
                    return 2;
                }
                if (endsWith('=', base64String)) {
                    return 1;
                }
                return 0;
            }
            function size(base64String) {
                return base64String.length / 4 * 3 - paddingSize(base64String);
            }
            function formatAsBytes(size) {
                return size.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ") + " bytes";
            }

            return formatAsBytes(size(base64String));
        };
    });
