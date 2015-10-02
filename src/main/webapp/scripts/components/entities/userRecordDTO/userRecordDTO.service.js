'use strict';

angular.module('hipster1App')
    .factory('UserRecordDTO', function ($resource, DateUtils) {
        return $resource('api/userRecords/:id', {}, {
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
