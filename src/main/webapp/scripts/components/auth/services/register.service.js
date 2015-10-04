'use strict';

angular.module('hipster1App')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });

angular.module('hipster1App')
    .factory('SubmitDoctorRegistration', function ($resource) {
        return $resource('api/register/doctor', {}, {
        });
    });

angular.module('hipster1App')
    .factory('SubmitHospitalRegistration', function ($resource) {
        return $resource('api/register/hospital', {}, {
        });
    });



