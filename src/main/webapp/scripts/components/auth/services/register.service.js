'use strict';

angular.module('hipster1App')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


