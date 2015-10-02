'use strict';

angular.module('hipster1App')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
