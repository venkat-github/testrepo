'use strict';

angular.module('hipster1App')
    .directive('hasAnyRole', ['Principal', function (Principal) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
            	var setVisible = function () {
            			//alert('setting element visible');
                        element.removeClass('hidden');
                    },
                    setHidden = function () {
                        element.addClass('hidden');
                    },
                    defineVisibility = function (reset) {
                        var result;
                        if (reset) {
                            //setVisible();
                        }
                        setHidden();
                        result = Principal.isInAnyRole(roles, setVisible);
                        //alert(roles[0])
                        //alert(result)
                        if (result) {
                        	//alert('setting visible2')
                            setVisible();
                        } else {
                        	//alert('setting hidden')
                            //setHidden();
                        }
                    },
                    roles = attrs.hasAnyRole.replace(/\s+/g, '').split(',');
                if (roles.length > 0) {
                	//alert(attrs.hasAnyRole)
                	//alert(roles);
                	//alert(roles.length)
                    defineVisibility(true);
                }
            }
        };
    }])
    .directive('hasRole', ['Principal', function (Principal) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
            	//alert('test hasRole '+element.id)
                var setVisible = function () {
                        element.removeClass('hidden');
                    },
                    setHidden = function () {
                        element.addClass('hidden');
                    },
                    defineVisibility = function (reset) {

                        if (reset) {
                            setVisible();
                        }

                        Principal.isInRole(role)
                            .then(function(result) {
                                if (result) {
                                    setVisible();
                                } else {
                                    setHidden();
                                }
                            });
                    },
                    role = attrs.hasRole.replace(/\s+/g, '');

                if (role.length > 0) {
                    defineVisibility(true);
                }
            }
        };
    }]);
