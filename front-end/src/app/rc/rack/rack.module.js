(function() {

    angular.module('app.rc.rack',[]).config(config);

    function config($stateProvider) {

        $stateProvider.state({
            name: 'app.rc.rack',
            url: 'rack',
            abstract: true
        });

        $stateProvider.state({
            name: 'app.rc.rack.list',
            url: '/list',
            templateUrl: './src/app/rc/rack/rack-list.html',
            controller: 'rackListController',
            data: {
                isArray: true
            },
            params: {

            },
            resolve: {
                dependencies: function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        files: [
                            './src/app/rc/rack/rack-list.controller.js'
                        ]
                    }]);
                },
                activation: function($rootScope) {
                    $rootScope.breadcrumbs =[{'state': 'app.rc.rack.list', 'title': 'menu.rc.list'}];
                }
            }
        });

        $stateProvider.state({
            name: 'app.rc.rack.view',
            url: '/view/:id',
            templateUrl: './src/app/rc/rack/rack-view.html',
            controller: 'rackViewController',
            data: {

            },
            params: {
                id: {dynamic:true}
            },
            resolve: {
                dependencies: function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        files: [
                            './src/app/rc/rack/rack-view.controller.js'
                        ]
                    }]);
                },
                activation: function($rootScope) {
                    $rootScope.breadcrumbs =[{'state': 'app.rc.rack.view', 'title': 'menu.rc.view'}];
                }
            }
        });

    }

})();