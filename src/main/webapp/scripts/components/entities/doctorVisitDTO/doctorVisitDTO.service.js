'use strict';

angular.module('hipster1App')
    .factory('DoctorVisitDTO', function ($resource, DateUtils) {
        return $resource('api/patients/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.date = DateUtils.convertLocaleDateFromServer(data.date);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.date = DateUtils.convertLocaleDateToServer(data.date);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                	//alert('successfully booking for date '+data.date);
                    data.date = DateUtils.convertLocaleDateFromServer(data.date);
                    return angular.toJson(data);
                }
            }
        });
    });
