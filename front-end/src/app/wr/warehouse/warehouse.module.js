(function() {

    angular.module('app.wr.warehouse',[]).config(config);

    function config($stateProvider) {

        $stateProvider.state({
            name: 'app.wr.warehouse',
            url: 'warehouse',
            abstract: true
        });

        $stateProvider.state({
            name: 'app.wr.warehouse.list',
            url: '/list',
            templateUrl: './src/app/wr/warehouse/warehouse-list.html',
            controller: 'warehouseListController',
            data: {
                isArray: true
            },
            params: {

            },
            resolve: {
                dependencies: function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        files: [
                            './src/app/wr/warehouse/warehouse-list.controller.js'
                        ]
                    }]);
                },
                activation: function($rootScope) {
                    $rootScope.breadcrumbs =[{'state': 'app.wr.warehouse.list', 'title': 'menu.wr.list'}];
                }
            }
        });

        $stateProvider.state({
            name: 'app.wr.warehouse.view',
            url: '/view/:id',
            templateUrl: './src/app/wr/warehouse/warehouse-view.html',
            controller: 'warehouseViewController',
            data: {

            },
            params: {
                id: {dynamic: true}
            },
            resolve: {
                dependencies: function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        files: [
                            './src/app/wr/warehouse/warehouse-view.controller.js'
                        ]
                    }]);
                },
                activation: function($rootScope) {
                    $rootScope.breadcrumbs =[{'state': 'app.wr.warehouse.view', 'title': 'menu.wr.view'}];
                }
            }
        })
        
    }

})();