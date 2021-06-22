(function() {

    angular.module('app.pr.product').controller('productViewController', productViewController);

    function productViewController($scope, $state, $translate, $filter, toitsuToasterService, 
        productService, invoiceService, invoiceProductService, rackService) {

        var invoiceProductViewState = 'app.np.invoiceProduct.view';
        var invoiceViewState = 'app.nv.invoice.view';

        $scope.product = {};
        $scope.productInvoices = [];
        $scope.stockUntilNow = {};
        $scope.stock = {};

        $scope.invoices = [];
        $scope.racks = [];

        $scope.invoiceProducts = [];
        $scope.inputs = [];
        $scope.moreInputs = function() {
            $scope.inputs.push(0);
        }

        $scope.sortBy = 'id';
        $scope.reverse = false;
        $scope.doSort = function(propName) {
            $scope.sortBy = propName;
            $scope.reverse = !$scope.reverse;
        }   

        init();

        function init() {
            if ($state.params.id) {
                getProduct();
                getproductInvoices();
                getStockUntilNow();
                $scope.isEditable = true;
                $scope.isDisabled = true;
            }else {
                $scope.product.invoices = [];
                $scope.isEditable = false;
                $scope.isDisabled = false;
            }
            getInvoices();
            getRacks();
        }

        function getProduct() {
            productService.getProduct($state.params.id).$promise
                .then(function(response) {
                    $scope.product = response;
                })
                .catch(function(response) {
                    toitsuToasterService.apiValidationErrors(response);
                })
                .finally(function() {
                    App.unblockUI();
                });
        }

        function getStockUntilNow() {

            // Βρίσκω την τωρινή ημερομηνία και την μετατρέπω στην μορφή που θέλω
            var date = $filter('date')(new Date(), 'yyyy-MM-dd');
            
            productService.getProductStock({productId: $state.params.id, date: date}).$promise
                .then(function(response) {
                    $scope.stockUntilNow = response;
                })
                .catch(function(response) {
                    toitsuToasterService.apiValidationErrors(response);
                })
                .finally(function(){
                    App.unblockUI();
                })
        }

        function getproductInvoices() {
            invoiceService.indexInvoice({productId: $state.params.id}).$promise
                .then(function(response) {
                    $scope.productInvoices = response;
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

        function getInvoices() {
            invoiceService.getInvoices().$promise
                .then(function(response) {
                    $scope.invoices = response;
                })
        }

        $scope.getStock = function(stockArgs) {
            stockArgs.productId = $state.params.id;

            stockArgs.date = $filter('date')(stockArgs.date, 'yyyy-MM-dd');

            productService.getProductStock(stockArgs).$promise
                .then(function(response) {
                    $scope.stock = response;
                })
                .catch(function(response) {
                    toitsuToasterService.apiValidationErrors(response);
                })
                .finally(function() {
                    App.unblockUI();
                });
        }

        $scope.saveProduct = function() {
            for (np of $scope.invoiceProducts) {
                $scope.product.invoices.push(np);
            }
            console.log($scope.product);
            productService.saveProduct($scope.product).$promise
                .then(function(response) {
                    $state.go($state.current, {id: response.id}, {reload: true})
                        .then(function() {
                            toitsuToasterService.showSuccess($translate.instant('pr.product.saveSuccess'));
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

        $scope.deleteInvoiceProduct = function(id) {
            var result = confirm($translate.instant('global.deleteConfirmationMessage'));
            if (result) {
                invoiceProductService.deleteInvoiceProduct(id).$promise
                    .then(function() {
                        $state.go($state.current, {id: $scope.product.id}, {reload: true})
                            .then(function() {
                                toitsuToasterService.showSuccess($translate.instant('pr.product.deleteSuccess'));
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

        $scope.getInvoice = function(id) {
            $state.go(invoiceViewState, {id: id});
        }

    }

})();