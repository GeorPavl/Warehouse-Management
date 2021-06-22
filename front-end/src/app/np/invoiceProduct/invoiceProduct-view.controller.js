(function() {

    angular.module('app.np.invoiceProduct').controller('invoiceProductViewController', invoiceProductViewController);

    function invoiceProductViewController($scope, $state, $translate, toitsuToasterService, 
        invoiceProductService, invoiceService, productService, rackService, employeeService) {

        $scope.invoiceProduct = {};

        $scope.invoices = [];
        $scope.products = [];
        $scope.racks = [];
        $scope.employees = [];

        init();

        function init() {
            if ($state.params.id) {
                getInvoiceProduct();
                $scope.isEditable = true;
                $scope.isDisabled = true;
            }else {
                $scope.isEditable = false;
                $scope.isDisabled = false;
            }
            getInvoices();
            getProducts();
            getRacks();
            getEmployees();
        }

        function getInvoiceProduct() {
            invoiceProductService.indexInvoiceProduct({id: $state.params.id}).$promise
                .then(function(response) {
                    // Επειδή η indexInvoiceProduct επιστρέφει array, συγκεκριμενοποιώ το reponse
                    $scope.invoiceProduct = response[0];    
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

        $scope.saveInvoiceProduct = function() {
            invoiceProductService.saveInvoiceProduct($scope.invoiceProduct).$promise
                .then(function(response) {
                    $state.go($state.current, {id: response.id}, {reload: true})
                        .then(function(){
                            toitsuToasterService.showSuccess($translate.instant('np.invoiceProduct.saveSuccess'));
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

})();