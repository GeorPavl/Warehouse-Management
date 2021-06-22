(function() {

    angular.module('app.wr.warehouse').service('warehouseService', warehouseService);

    function warehouseService(warehouseFactory) {
        return {
            getWarehouses : getWarehouses,
            getWarehouse : getWarehouse,
            saveWarehouse : saveWarehouse,
            deleteWarehouse : deleteWarehouse,
            indexWarehouse : indexWarehouse
        }
        
        function getWarehouses() {
            return warehouseFactory.list({});
        }

        function getWarehouse(id) {
            return warehouseFactory.get({id},null);
        }

        function saveWarehouse(warehouse) {
            return warehouseFactory.save({}, warehouse);
        }

        function deleteWarehouse(id) {
            return warehouseFactory.delete({id},null);
        }

        function indexWarehouse(warehouseArgs) {
            return warehouseFactory.index({},warehouseArgs);
        }
    }

})();