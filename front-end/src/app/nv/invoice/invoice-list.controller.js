(function() {

    angular.module('app.nv.invoice').controller('invoiceListController', invoiceListController);

    function invoiceListController($scope, $state, $translate, $filter, toitsuToasterService, invoiceService) {

        var viewState = 'app.nv.invoice.view';

        $scope.invoices = [];
        $scope.invoiceArgs = {};

        $scope.sortBy = 'id',
        $scope.reverse = false;
        $scope.doSort = function(propName) {
            $scope.sortBy = propName;
            $scope.reverse = !$scope.reverse;
        }

        init();

        function init() {
            getInvoices();
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

        $scope.newInvoice = function() {
            $state.go(viewState, {id: null});
        }

        $scope.retrieveData = function() {
            console.log($scope.invoiceArgs);
            $scope.invoiceArgs.afterDate = $filter('date')($scope.invoiceArgs.afterDate, 'yyyy-MM-dd');
            $scope.invoiceArgs.beforeDate = $filter('date')($scope.invoiceArgs.beforeDate, 'yyyy-MM-dd');

            invoiceService.indexInvoice($scope.invoiceArgs).$promise
                .then(function(response) {
                    $scope.invoices = response;
                })
                .catch(function(response) {
                    toitsuToasterService.apiValidationErrors(response);
                })
                .finally(function() {
                    App.unblockUI();
                })
        }

        $scope.restoreData = getInvoices;

        $scope.getInvoice = function(id) {
            $state.go(viewState, {id: id});
        }

        $scope.deleteInvoice = function(id) {
            var result = confirm($translate.instant('global.deleteConfirmationMessage'));
            if (result) {
                invoiceService.deleteInvoice(id).$promise
                    .then(function() {
                        $state.go($state.current, {}, {reload: true})
                            .then(function() {
                                toitsuToasterService.showSuccess('nv.invoice.deleteSuccess');
                            })
                    })
                    .catch(function(response) {
                        toitsuToasterService.apiValidationErrors(response);
                    })
                    .finally(function() {
                        App.unblockUI();
                    })
            }
        }

    }

})();