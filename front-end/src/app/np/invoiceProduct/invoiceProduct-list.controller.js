(function() {

    angular.module('app.np.invoiceProduct').controller('invoiceProductListController', invoiceProductListController);

    function invoiceProductListController($scope, $state, $translate, $filter, toitsuToasterService, 
        invoiceProductService, invoiceService, productService, rackService, employeeService) {

        var viewState = 'app.np.invoiceProduct.view';

        $scope.invoiceProducts = [];
        $scope.invoiceProductArgs = {};

        $scope.invoices = [];
        $scope.products = [];
        $scope.racks = [];
        $scope.employees = [];

        $scope.sortBy = 'id';
        $scope.reverse = false;
        $scope.doSort = function(propName) {
            $scope.sortBy = propName;
            $scope.reverse = !$scope.reverse;
        }

        init();

        function init() {
            getInvoiceProducts();
            getInvoices();
            getProducts();
            getRacks();
            getEmployees();
        }

        function getInvoiceProducts() {
            invoiceProductService.getInvoiceProducts().$promise
                .then(function(response) {
                    $scope.invoiceProducts = response;
                })
                .catch(function(response) {
                    toitsuToasterService.apiValidationErrors(response);
                })
                .finally(function() {
                    App.unblockUI();
                });
        }

        $scope.newInvoiceProduct = function() {
            $state.go(viewState, {id:null});
        }

        $scope.restoreData = getInvoiceProducts;

        $scope.retrieveData = function() {
            $scope.invoiceProductArgs.afterDate = $filter('date')($scope.invoiceProductArgs.afterDate, 'yyyy-MM-dd');
            $scope.invoiceProductArgs.beforeDate = $filter('date')($scope.invoiceProductArgs.beforeDate, 'yyyy-MM-dd');            invoiceProductService.indexInvoiceProduct($scope.invoiceProductArgs).$promise 
                .then(function(response) {
                    $scope.invoiceProducts = response;
                })
                .catch(function(response) {
                    toitsuToasterService.apiValidationErrors(response);
                    console.log(response);
                })
                .finally(function() {
                    App.unblockUI();
                });
        }

        $scope.getInvoiceProduct = function(id) {
            $state.go(viewState, {id: id});
        }

        $scope.deleteInvoiceProduct = function(id) {
            var result = confirm($translate.instant('global.deleteConfirmationMessage'));
            if (result) {
                invoiceProductService.deleteInvoiceProduct(id).$promise
                    .then(function() {
                        $state.go($state.current, {}, {reload: true})
                            .then(function(){
                                toitsuToasterService.showSuccess('np.invoiceProduct.deleteSuccess');
                            })
                    })
                    .catch(function(response) {
                        toitsuToasterService.apiValidationErrors(response);
                    })
                    .finally(function() {
                        App.unblockUI();
                    });
            }
        }


        function getRacks() {
            rackService.getRacks().$promise
                .then(function(response) {
                    $scope.racks = response;
                })
        }

        function getProducts() {
            productService.getProducts().$promise
                .then(function(response) {
                    $scope.products = response;
                });
        }

        function getInvoices() {
            invoiceService.getInvoices().$promise
                .then(function(response) {
                    $scope.invoices = response;
                });
        }

        function getEmployees() {
            employeeService.getEmployees().$promise
                .then(function(response) {
                    $scope.employees = response;
                });
        }
    }

})();