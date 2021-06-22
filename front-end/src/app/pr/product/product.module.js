(function() {

    angular.module('app.pr.product',[]).config(config);

    function config($stateProvider) {

        $stateProvider.state({
            name: 'app.pr.product',
            url: 'product',
            abstract: true
        });

        $stateProvider.state({
            name: 'app.pr.product.list',
            url: '/list',
            templateUrl: './src/app/pr/product/product-list.html',
            controller: 'productListController',
            data: {
                isArray: true
            },
            params: {

            },
            resolve: {
                dependencies: function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        files: [
                            './src/app/pr/product/product-list.controller.js'
                        ]
                    }]);
                },
                activation: function($rootScope) {
                    $rootScope.breadcrumbs =[{'state': 'app.pr.product.list', 'title': 'menu.pr.list'}];
                }
            }
        });

        $stateProvider.state({
            name: 'app.pr.product.view',
            url: '/view/:id',
            templateUrl: './src/app/pr/product/product-view.html',
            controller: 'productViewController',
            data: {
                
            },
            params: {
                id: {dynamic: true}
            },
            resolve: {
                dependencies: function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        files: [
                            './src/app/pr/product/product-view.controller.js'
                        ]
                    }]);
                },
                activation: function($rootScope) {
                    $rootScope.breadcrumbs =[{'state': 'app.pr.product.view', 'title': 'menu.pr.view'}];
                }
            }
        });

    }

})();