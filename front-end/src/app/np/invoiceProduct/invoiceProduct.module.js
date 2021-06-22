(function() {

    angular.module('app.np.invoiceProduct',[]).config(config);

    function config($stateProvider) {

        $stateProvider.state({
            name: 'app.np.invoiceProduct',
            url: 'invoiceProduct',
            abstract: true
        });

        $stateProvider.state({
            name: 'app.np.invoiceProduct.list',
            url: '/list',
            templateUrl: './src/app/np/invoiceProduct/invoiceProduct-list.html',
            controller: 'invoiceProductListController',
            data: {
                isArray: true 
            },
            params: {

            },
            resolve: {
                dependencies: function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        files: [
                            './src/app/np/invoiceProduct/invoiceProduct-list.controller.js'
                        ]
                    }]);
                },
                activation: function($rootScope) {
                    $rootScope.breadcrumbs =[{'state': 'app.np.invoiceProduct.list', 'title': 'menu.np.list'}];
                }
            }
        });

        $stateProvider.state({
            name: 'app.np.invoiceProduct.view',
            url: '/view/:id',
            templateUrl: './src/app/np/invoiceProduct/invoiceProduct-view.html',
            controller: 'invoiceProductViewController',
            data: {
                 
            },
            params: {
                id: {dynamic: true}
            },
            resolve: {
                dependencies: function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        files: [
                            './src/app/np/invoiceProduct/invoiceProduct-view.controller.js'
                        ]
                    }]);
                },
                activation: function($rootScope) {
                    $rootScope.breadcrumbs =[{'state': 'app.np.invoiceProduct.view', 'title': 'menu.np.view'}];
                }
            }
        });

    }
})();