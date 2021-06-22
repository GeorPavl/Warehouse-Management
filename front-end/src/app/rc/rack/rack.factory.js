(function() {

    angular.module('app.rc.rack').factory('rackFactory', rackFactory);

        function rackFactory($resource, $rootScope, headerService, rackConsts) {
            
            return $resource(null, null, {
                'list': {
                    url: $rootScope.baseUrl + rackConsts.listUrl,
                    method: 'GET',
                    isArray: true,
                    headers: headerService.getHeadersWithAuth()
                },
                'get': {
                    url: $rootScope.baseUrl + rackConsts.getUrl,
                    method: 'GET',
                    headers: headerService.getHeadersWithAuth()
                },
                'save': {
                    url: $rootScope.baseUrl + rackConsts.saveUrl,
                    method: 'POST',
                    headers: headerService.getHeadersWithAuth()
                },
                'delete': {
                    url: $rootScope.baseUrl + rackConsts.deleteUrl,
                    method: 'DELETE',
                    headers: headerService.getHeadersWithAuth()
                }
            });

        }

})();