(function() {

    angular.module('app.nv.invoice').controller('invoiceViewController', invoiceViewController);

    function invoiceViewController($scope, $state, $translate, toitsuToasterService, invoiceService, invoiceProductService) {

        $scope.invoice = {}
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
                getInvoice();
                $scope.isEditable = true;
                $scope.isDisabled = true;
            }else {
                $scope.invoice.invoiceProducts = [];
                $scope.isEditable = false;
                $scope.isDisabled = false;
            }
        }

        function getInvoice() {
            invoiceService.getInvoice($state.params.id).$promise
                .then(function(response) {
                    $scope.invoice = response;
                })
                .catch(function(response) {
                    toitsuToasterService.apiValidationErrors(response);
                })
                .finally(function() {
                    App.unblockUI();
                });
        }

        $scope.saveInvoice = function() {
            for (np in $scope.invoiceProducts) {
                $scope.invoice.invoiceProducts.push(np);
            }
            
            invoiceService.saveInvoice($scope.invoice).$promise
                .then(function(response) {
                    $state.go($state.current, {id: response.id}, {reload: true})
                        .then(function() {
                            console.log(response);
                            toitsuToasterService.showSuccess($translate.instant('nv.invoice.saveSuccess'));
                        })
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
                .then(function(){
                    $state.go($state.current, {id: $scope.invoice.id}, {reload: true})
                        .then(function() {
                            toitsuToasterService.showSuccess('np.invoiceProduct.deleteSuccess')
                        });
                })
                .catch(function(response) {
                    toitsuToasterService.apiValidationErrors(response);
                })
                .finally(function() {
                    App.unblockUI();
                });
            }
        };

    }

})();