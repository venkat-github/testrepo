var vm = (function () {
    "use strict";

    var catalog2 = ko.observableArray([
        new Item(1, "T-Shirt",  20),
        new Item(2, "Trousers", 10),
        new Item(3, "Shirt", 20),
        new Item(4, "Shorts", 10)
    ]);

    var newProduct = new Item("","","","");

    return {
        catalog2: catalog2
    };
 
})();

ko.applyBindings(vm);