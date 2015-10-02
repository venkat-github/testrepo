 'use strict';

angular.module('hipster1App')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-hipster1App-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-hipster1App-params')});
                }
                return response;
            },
        };
    });