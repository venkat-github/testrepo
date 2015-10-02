'use strict';

angular.module('hipster1App')
    .factory('TestBlob', function ($resource, DateUtils) {
        return $resource('api/testBlobs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
