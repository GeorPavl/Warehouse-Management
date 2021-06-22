(function() {

    angular.module('app.wr.warehouse').factory('warehouseFactory', warehouseFactory);

    function warehouseFactory($resource, $rootScope, headerService, warehouseConsts) {

        return $resource(null, null, {
            'list': {
                url: $rootScope.baseUrl + warehouseConsts.listUrl,
                method: 'GET',
                isArray: true,
                headers: headerService.getHeadersWithAuth()
            },
            'get': {
                url: $rootScope.baseUrl + warehouseConsts.getUrl,
                method: 'GET',
                headers: headerService.getHeadersWithAuth()
            },
            'save': {
                url: $rootScope.baseUrl + warehouseConsts.saveUrl,
                method: 'POST',
                headers: headerService.getHeadersWithAuth()
            },
            'delete': {
                url: $rootScope.baseUrl + warehouseConsts.deleteUrl,
                method: 'DELETE',
                headers: headerService.getHeadersWithAuth()
            },
            'index': {
                url: $rootScope.baseUrl + warehouseConsts.indexUrl,
                method: 'POST',
                isArray: true,
                headers: headerService.getHeadersWithAuth()
            }
        });

    }

})();