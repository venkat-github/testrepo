'use strict';

angular.module('hipster1App')
    .factory('DoctorScheduleDTO', function ($resource, DateUtils) {
        return $resource('api/doctorSchedules/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.startDate = DateUtils.convertLocaleDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocaleDateToServer(data.endDate);
                    alert(data.startDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.startDate = DateUtils.convertLocaleDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocaleDateToServer(data.endDate);
                    alert(data.startDate);
                    return angular.toJson(data);
                }
            }
            
        });
    });
