(function() {

    angular.module('app.wr.warehouse').controller('warehouseListController', warehouseListController);

    function warehouseListController($scope, $state, $translate, toitsuToasterService, 
        warehouseService, rackService) {

        var viewState = 'app.wr.warehouse.view';

        $scope.warehouses = [];
        $scope.warehouseArgs = {};
        $scope.warehouseArgsIsOpen = true;
        $scope.racks = [];

        $scope.sortBy = 'id';
        $scope.reverse = false;
        $scope.doSort = function(propName) {
           $scope.sortBy = propName;
           $scope.reverse = !$scope.reverse;
        };

        init();

        function init() {
            getWarehouses();
            getRacks();
        }

        function getWarehouses() {
            warehouseService.getWarehouses().$promise
                .then(function(response) {
                    $scope.warehouses = response;
                })
                .catch(function(response) {
                    toitsuToasterService.apiValidationErrors(response);
                })
                .finally(function() {
                    App.unblockUI();
                });
        }

        function getRacks() {
            rackService.getRacks().$promise
                .then(function(response) {
                    $scope.racks = response;
                })
                .catch(function(response) {
                    toitsuToasterService.apiValidationErrors(response);
                })
                .finally(function() {
                    App.unblockUI();
                })
        }

        $scope.newWarehouse = function() {
            $state.go(viewState, {id: null});
        }

        $scope.restoreData = getWarehouses;

        $scope.retrieveData = function() {
            warehouseService.indexWarehouse($scope.warehouseArgs).$promise
                .then(function(response) {
                    $scope.warehouses = response;
                })
                .catch(function(response) {
                    toitsuToasterService.apiValidationErrors(response);
                })
                .finally(function() {
                    App.unblockUI();
                });
        }

        $scope.getWarehouse = function(id) {
            $state.go(viewState, {id: id});
        }

        $scope.deleteWarehouse = function(id) {
            var result = confirm($translate.instant('global.deleteConfirmationMessage'));
            if (result) {
                warehouseService.deleteWarehouse(id).$promise
                .then(function() {
                    $state.go($state.current, {},{reload: true})
                        .then(function() {
                            toitsuToasterService.showSuccess($translate.instant('wr.warehouse.deleteSuccess'));
                        });
                })
                .catch(function(response) {
                    toitsuToasterService.apiValidationErrors(response);
                })
                .finally(function() {
                    App.unblockUI();
                });
            }
        }
        
    }

})();