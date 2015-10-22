'use strict';

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



