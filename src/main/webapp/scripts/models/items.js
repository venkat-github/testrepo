var Item = function (id,name,age) {
    "use strict";
    var
        _id = ko.observable(id),
        _name = ko.observable(name),
        _age = ko.observable(age)
    ;

    return {
        id:_id,
        name:_name,
        age:_age
    };
};
