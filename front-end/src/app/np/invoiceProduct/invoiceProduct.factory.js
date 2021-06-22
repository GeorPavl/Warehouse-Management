(function() {

    angular.module('app.np.invoiceProduct').factory('invoiceProductFactory', invoiceProductFactory);

    function invoiceProductFactory($resource, $rootScope, headerService, invoiceProductConsts) {

        return $resource(null, null, {
            'list': {
                url: $rootScope.baseUrl + invoiceProductConsts.list,
                method: 'GET',
                isArray: true,
                header: headerService.getHeadersWithAuth()
            },
            'get': {
                url: $rootScope.baseUrl + invoiceProductConsts.get,
                method: 'GET',
                header: headerService.getHeadersWithAuth()
            },
            'save': {
                url: $rootScope.baseUrl + invoiceProductConsts.save,
                method: 'POST',
                header: headerService.getHeadersWithAuth()
            },
            'delete': {
                url: $rootScope.baseUrl + invoiceProductConsts.delete,
                method: 'DELETE',
                header: headerService.getHeadersWithAuth()
            },
            'index': {
                url: $rootScope.baseUrl + invoiceProductConsts.index,
                method: 'POST',
                isArray: true,
                header: headerService.getHeadersWithAuth()
            }
        });

    }

})();