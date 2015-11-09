'use strict';

angular.module('hipster1App')
    .factory('Principal', function Principal($q, Account) {
        var _identity,
            _authenticated = false;

        return {
            isIdentityResolved: function () {
                return angular.isDefined(_identity);
            },
            isAuthenticated: function () {
                return _authenticated;
            },
            isInRole: function (role, cb) {
                if (!_authenticated) {
                	return false;
               }

               if (angular.isDefined(_identity) && _identity != null) {
            	   if (_identity.roles && _identity.roles.indexOf(role) !== -1) {
            		   if (angular.isDefined(cb)) {
            			   cb();
            		   }
            	   }
                   return _identity.roles && _identity.roles.indexOf(role) !== -1;
               } else {
            	   return false;
               }    
            },
            isInAnyRole: function (roles, cb) {
            	if (!_authenticated || !_identity || !_identity.roles) {
                	return false;
                }

                for (var i = 0; i < roles.length; i++) {
                	if (this.isInRole(roles[i], cb) ) {
                		return true;
                	}
                	
                }
                return true;
            },
            authenticate: function (identity) {
                _identity = identity;
                _authenticated = identity !== null;
            },
            identity: function (force) {
                var deferred = $q.defer();

                if (force === true) {
                    _identity = undefined;
                }

                // check and see if we have retrieved the identity data from the server.
                // if we have, reuse it by immediately resolving
                if (angular.isDefined(_identity)) {
                    deferred.resolve(_identity);

                    return deferred.promise;
                }

                // retrieve the identity data from the server, update the identity object, and then resolve.
                Account.get().$promise
                    .then(function (account) {
                    	_identity = account.data;
                        _authenticated = true;
                        deferred.resolve(_identity);
                    })
                    .catch(function() {
                        _identity = null;
                        _authenticated = false;
                        deferred.resolve(_identity);
                    });
                return deferred.promise;
            }
        };
    });
