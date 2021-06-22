(function() {

    angular.module('app.wr.warehouse').controller('warehouseViewController', warehouseViewController);

    function warehouseViewController($scope, $state, $translate, toitsuToasterService, warehouseService, rackService, productService, invoiceService) {

        var rackViewState = 'app.rc.rack.view';
        var productViewState = 'app.pr.product.view';

        $scope.warehouse = {};
        $scope.warehouseDataIsOpen = true;
        
        // Για την προσθήκη νέων ραφιών
        $scope.racks = [];
        $scope.products = [];
        $scope.invoices = [];
        $scope.inputs = [];
        $scope.moreInputs = function(){
            $scope.inputs.push(0);   
        }  

        // Για ταξινόμηση των στηλών του πίνακα
        $scope.sortBy = 'id';
        $scope.reverse = false;
        $scope.doSort = function(propName) {
           $scope.sortBy = propName;
           $scope.reverse = !$scope.reverse;
        };

        init();

        function init() {
            if ($state.params.id) {
                getWarehouse();
                $scope.isEditable = true;
                $scope.isDisabled = true;
            }else {
                $scope.warehouse.racks = [];
                $scope.isEditable = false;
                $scope.isDisabled = false;
            }
            
        }

        function getWarehouse() {
            warehouseService.getWarehouse($state.params.id).$promise
                .then(function(response) {
                    $scope.warehouse = response;
                    // TODO: Πρέπει να κάνω ένα dto που να επιστρέφει τα πάντα, ή να κάνω διαφορετικές κλήσεις;
                    getProducts();
                    getInvoices();
                })
                .catch(function(response) {
                    toitsuToasterService.apiValidationErrors(response);
                })
                .finally(function() {
                    App.unblockUI();
                });
        }

        function getProducts() {
            productService.indexProduct({warehouseId: $state.params.id}).$promise
                .then(function(response) {
                    $scope.products = response;
                })
                .catch(function(response) {
                    toitsuToasterService.apiValidationErrors(response);
                })
                .finally(function() {
                    App.unblockUI();
                });
        }

        function getInvoices() {
            invoiceService.indexInvoice({warehouseId: $state.params.id}).$promise
                .then(function(response) {
                    $scope.invoices = response;
                })
                .catch(function(response) {
                    toitsuToasterService.apiValidationErrors(response);
                })
                .finally(function() {
                    App.unblockUI();
                });
        }

        $scope.saveWarehouse = function() {
            for (rc of $scope.racks) {
                $scope.warehouse.racks.push(rc);
            }

            warehouseService.saveWarehouse($scope.warehouse).$promise
                .then(function(response) {
                    $state.go($state.current, {id: response.id}, {reload: true})
                        .then(function() {
                            toitsuToasterService.showSuccess($translate.instant('wr.warehouse.saveSuccess'));
                        });
                })
                .catch(function(response) {
                    toitsuToasterService.apiValidationErrors(response);
                })
                .finally(function() {
                    App.unblockUI();
                });
        }

        $scope.getRack = function(id) {
            $state.go(rackViewState, {id: id});
        }

        $scope.deleteRack = function(id) {
            var result = confirm($translate.instant('global.deleteConfirmationMessage'));
            if (result) {
                rackService.deleteRack(id).$promise
                    .then(function() {
                        $state.go($state.current, {id: $scope.warehouse.id}, {reload: true})
                            .then(function(){
                                toitsuToasterService.showSuccess($translate.instant('rc.rack.deleteSuccess'));
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

        $scope.getProduct = function(id) {
            $state.go(productViewState, {id: id});
        }

        $scope.getInvoice = function(id) {
            $state.go($state.current, {id: id});
        }

    }

})();