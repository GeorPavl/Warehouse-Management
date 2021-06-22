(function() {

    angular.module('app.nv.invoice',[]).config(config);

    function config($stateProvider) {

        $stateProvider.state({
            name: 'app.nv.invoice',
            url: 'invoice',
            abstract: true
        });

        $stateProvider.state({
            name: 'app.nv.invoice.list',
            url: '/list',
            templateUrl: './src/app/nv/invoice/invoice-list.html',
            controller: 'invoiceListController',
            data: {
                isArray: true
            },
            params: {

            },
            resolve: {
                dependencies: function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        files: [
                            './src/app/nv/invoice/invoice-list.controller.js'
                        ]
                    }]);
                },
                activation: function($rootScope) {
                    $rootScope.breadcrumbs =[{'state': 'app.nv.invoice.list', 'title': 'menu.nv.list'}];
                }
            }
        });

        $stateProvider.state({
            name: 'app.nv.invoice.view',
            url: '/view/:id',
            templateUrl: './src/app/nv/invoice/invoice-view.html',
            controller: 'invoiceViewController',
            data: {

            },
            params: {
                id: {dynamic: true}
            },
            resolve: {
                dependencies: function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        files: [
                            './src/app/nv/invoice/invoice-view.controller.js'
                        ]
                    }]);
                },
                activation: function($rootScope) {
                    $rootScope.breadcrumbs =[{'state': 'app.nv.invoice.view', 'title': 'menu.nv.view'}];
                }
            }
        });

    }

})();