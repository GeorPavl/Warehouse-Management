(function() {

    angular.module('app.nv.invoice').factory('invoiceFactory', invoiceFactory);

    function invoiceFactory($resource, $rootScope, headerService, invoiceConsts) {

        return $resource(null, null, {
            'list': {
                url: $rootScope.baseUrl + invoiceConsts.listUrl,
                method: 'GET',
                isArray: true,
                headers: headerService.getHeadersWithAuth()
            },
            'get': {
                url: $rootScope.baseUrl + invoiceConsts.getUrl,
                method: 'GET',
                headers: headerService.getHeadersWithAuth()
            },
            'save': {
                url: $rootScope.baseUrl + invoiceConsts.saveUrl,
                method: 'POST',
                headers: headerService.getHeadersWithAuth()
            },
            'delete': {
                url: $rootScope.baseUrl + invoiceConsts.deleteUrl,
                method: 'DELETE',
                headers: headerService.getHeadersWithAuth()
            },
            'index': {
                url: $rootScope.baseUrl + invoiceConsts.indexUrl,
                method: 'POST',
                isArray: true,
                headers: headerService.getHeadersWithAuth()
            }
        });
    }

})();