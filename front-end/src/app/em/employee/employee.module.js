(function() {

    angular.module('app.em.employee',[]).config(config);

    function config($stateProvider) {

        $stateProvider.state({
            name: 'app.em.employee',
            url: 'employee',
            abstract: true
        });

        $stateProvider.state({
            name: 'app.em.employee.list',
            url: '/list',
            templateUrl: './src/app/em/employee/employee-list.html',
            controller: 'employeeListController',
            data: {
                isArray: true
            },
            params: {

            },
            resolve: {
                dependencies: function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        files: [
                            './src/app/em/employee/employee-list.controller.js'
                        ]
                    }]);
                },
                activation: function($rootScope) {
                    $rootScope.breadcrumbs =[{'state': 'app.em.employee.list', 'title': 'menu.em.list'}];
                }
            }
        });

        $stateProvider.state({
            name: 'app.em.employee.view',
            url: '/view/:id',
            templateUrl: './src/app/em/employee/employee-view.html',
            controller: 'employeeViewController',
            data: {

            },
            params: {
                id: {dynamic: true}
            },
            resolve: {
                dependencies: function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        files: [
                            './src/app/em/employee/employee-view.controller.js'
                        ]
                    }]);
                },
                activation: function($rootScope) {
                    $rootScope.breadcrumbs =[{'state': 'app.em.employee.view', 'title': 'menu.em.view'}];
                },
                employee: function($stateParams, employeeService) {
                    return !$stateParams.id ? {employee: {}} : employeeService.getEmployee($stateParams.id).$promise;
                },
                lists: function($q, warehouseService, rackService, invoiceService, productService) {
                    let lists = {};
                    
                    lists.warehouses = warehouseService.getWarehouses().$promise;
                    lists.racks = rackService.getRacks().$promise;
                    lists.products = productService.getProducts().$promise;
                    lists.invoices = invoiceService.getInvoices().$promise;

                    return $q.all(lists);
                }
            }
        })
        
    }

})();