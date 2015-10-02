'use strict';

angular.module('hipster1App')
    .factory('HospitalDTO', function ($resource, DateUtils) {
        return $resource('api/hospitals/:id', {}, {
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
