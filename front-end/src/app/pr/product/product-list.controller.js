(function() {

    angular.module('app.pr.product').controller('productListController', productListController);

    function productListController($scope, $state, $translate, toitsuToasterService, productService) {

        var viewState = 'app.pr.product.view';

        $scope.products = [];
        $scope.productArgs = {};

        $scope.sortBy = 'id';
        $scope.reverse = false;
        $scope.doSort = function(propName) {
           $scope.sortBy = propName;
           $scope.reverse = !$scope.reverse;
        };

        init();

        function init() {
            getProducts();
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

        $scope.newProduct = function() {
            $state.go(viewState, {id: null});
        }
        
        $scope.retrieveData = function(){
            productService.indexProduct($scope.productArgs).$promise
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

        $scope.restoreData = getProducts;

        $scope.getProduct = function(id) {
            $state.go(viewState, {id: id});
        }

        $scope.deleteProduct = function(id) {
            var result = confirm($translate.instant('global.deleteConfirmationMessage'));
            if (result) {
                productService.deleteProduct(id).$promise
                    .then(function(){
                        $state.go($state.current, {}, {reload: true})
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

    }

})();