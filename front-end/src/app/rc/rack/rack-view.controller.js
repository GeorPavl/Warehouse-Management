(function() {

    angular.module('app.rc.rack').controller('rackViewController', rackViewController);

    function rackViewController($scope, $state, $translate, toitsuToasterService, 
        rackService, warehouseService, productService, invoiceService, invoiceProductService) {

        var invoiceViewState = 'app.nv.invoice.view';
        var productViewState = 'app.pr.product.view';
        var invoiceProductViewState = 'app.np.invoiceProduct.view';

        $scope.rack = {}
        
        $scope.rackProducts = [];
        $scope.rackInvoices = [];

        // Λίστες για dropdown
        $scope.warehouses = [];
        $scope.products = [];
        $scope.invoices = [];

        // Για προσθήκη ενέργειας απόδειξης
        $scope.invoiceProducts = [];
        $scope.inputs = [];
        $scope.moreInputs = function(){
            $scope.inputs.push(0);   
        }  
        
        // Για ταξινόμηση και αναζήτηση
        $scope.sortBy = 'id';
        $scope.reverse = false;
        $scope.doSort = function(propName) {
           $scope.sortBy = propName;
           $scope.reverse = !$scope.reverse;
        };

        init();

        function init() {
            if ($state.params.id) {
                getRack();
                getRackProducts();
                getRackInvoices();
                $scope.isEditable = true;
                $scope.isDisabled = true;
            }else {
                $scope.rack.invoices = [];
                $scope.isEditable = false;
                $scope.isDisabled = false;
            }
            getWarehouses();
            getProducts();
            getInvoices();
        }

        function getRack() {
            rackService.getRack($state.params.id).$promise
                .then(function(response) {
                    $scope.rack = response;
                })
                .catch(function(response) {
                    toitsuToasterService.apiValidationErrors(response);
                })
                .finally(function() {
                    App.unblockUI();
                });
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

        function getProducts() {
            productService.getProducts().$promise
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
            invoiceService.getInvoices().$promise
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

        function getRackProducts() {
            productService.indexProduct({rackId: $state.params.id}).$promise
                .then(function(response) {
                    $scope.rackProducts = response;
                })
                .catch(function(response) {
                    toitsuToasterService.apiValidationErrors(response);
                })
                .finally(function() {
                    App.unblockUI();
                });
        }

        function getRackInvoices() {
            invoiceService.indexInvoice({rackId: $state.params.id}).$promise
            .then(function(response) {
                $scope.rackInvoices = response;
            })
            .catch(function(response) {
                toitsuToasterService.apiValidationErrors(response);
            })
            .finally(function() {
                App.unblockUI();
            });
        }

        $scope.saveRack = function() {
            for (np of $scope.invoiceProducts) {
                $scope.rack.invoices.push(np);
            }

            rackService.saveRack($scope.rack).$promise
                .then(function(response) {
                    $state.go($state.current, {id: response.id}, {reload: true})
                        .then(function() {
                            toitsuToasterService.showSuccess($translate.instant('rc.rack.saveSuccess'));
                        });
                })
                .catch(function(response) {
                    toitsuToasterService.apiValidationErrors(response);
                })
                .finally(function() {
                    App.unblockUI();
                });
        }

        $scope.getInvoiceProduct = function(id) {
            $state.go(invoiceProductViewState, {id: id});
        }

        $scope.getInvoice = function(id) {
            $state.go(invoiceViewState, {id: id});
        } 

        $scope.getProduct = function(id) {
            $state.go(productViewState, {id: id});
        }

        $scope.deleteInvoiceProduct = function(id) {
            var result = confirm($translate.instant('global.deleteConfirmationMessage'));
            if (result) {
                invoiceProductService.deleteInvoiceProduct(id).$promise
                    .then(function() {
                        $state.go($state.current, {id: $scope.rack.id}, {reload: true})
                            .then(function() {
                                toitsuToasterService.showSuccess($translate.instant('np.invoiceProduct.deleteSuccess'));
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