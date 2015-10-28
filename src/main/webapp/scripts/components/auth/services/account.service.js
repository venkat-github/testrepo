'use strict';

angular.module('hipster1App')
    .factory('Account', function Account($resource) {
        return $resource('api/account', {}, {
            'get': { method: 'GET', params: {}, isArray: false,
                interceptor: {
                    response: function(response) {
                        // expose response
                        return response;
                    }
                }
            }
        });
    });


angular.module('hipster1App')
    .factory('Activate', function ($resource) {
        return $resource('api/activate', {}, {
            'get': { method: 'GET', params: {}, isArray: false}
        });
    });



angular.module('hipster1App')
    .factory('Password', function ($resource) {
        return $resource('api/account/change_password', {}, {
        });
    });

angular.module('hipster1App')
    .factory('PasswordResetInit', function ($resource) {
        return $resource('api/account/reset_password/init', {}, {
        })
    });

angular.module('hipster1App')
    .factory('PasswordResetFinish', function ($resource) {
        return $resource('api/account/reset_password/finish', {}, {
        })
    });

angular.module('hipster1App')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });

angular.module('hipster1App')
    .factory('SubmitDoctorRegistration', function ($resource) {
        return $resource('api/account/register/doctor', {}, {
        });
    });

angular.module('hipster1App')
    .factory('SubmitHospitalRegistration', function ($resource) {
        return $resource('api/account/register/hospital', {}, {
        });
    });

angular.module('hipster1App')
    .factory('RegisterOtp', function ($resource) {
        return $resource('api/account/register/otp', {}, 
            {
            'query': { method: 'GET', isArray: false}
            })
    });

angular.module('hipster1App')
    .factory('Sessions', function ($resource) {
        return $resource('api/account/sessions/:series', {}, {
            'getAll': { method: 'GET', isArray: true}
        });
    });





    
