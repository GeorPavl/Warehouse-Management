(function() {

    angular.module('app.pr.product').factory('productFactory', productFactory);

    function productFactory($resource, $rootScope, headerService, productConsts) {

        return $resource(null, null, {
            'list': {
                url: $rootScope.baseUrl + productConsts.list,
                method: 'GET',
                isArray: true,
                headers: headerService.getHeadersWithAuth()
            },
            'get': {
                url: $rootScope.baseUrl + productConsts.get,
                method: 'GET',
                headers: headerService.getHeadersWithAuth()
            },
            'save': {
                url: $rootScope.baseUrl + productConsts.save,
                method: 'POST',
                headers: headerService.getHeadersWithAuth()
            },
            'delete': {
                url: $rootScope.baseUrl + productConsts.delete,
                method: 'DELETE',
                headers: headerService.getHeadersWithAuth()
            },
            'index': {
                url: $rootScope.baseUrl + productConsts.index,
                method: 'POST',
                isArray: true,
                headers: headerService.getHeadersWithAuth()
            },
            'stock': {
                url: $rootScope.baseUrl + productConsts.stock,
                method: 'POST',
                headers: headerService.getHeadersWithAuth()
            }            
        });

    }

})();