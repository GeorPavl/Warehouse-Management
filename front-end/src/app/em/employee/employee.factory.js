(function() {

    angular.module('app.em.employee').factory('employeeFactory', employeeFactory);

    function employeeFactory($resource, $rootScope, headerService, employeeConsts) {

        return $resource(null, null, {
            'list': {
                url: $rootScope.baseUrl + employeeConsts.listUrl,
                method: 'GET',
                isArray: true,
                headers: headerService.getHeadersWithAuth()
            },
            'get': {
                url: $rootScope.baseUrl + employeeConsts.getUrl,
                method: 'GET',
                headers: headerService.getHeadersWithAuth()
            },
            'save': {
                url: $rootScope.baseUrl + employeeConsts.saveUrl,
                method: 'POST',
                headers: headerService.getHeadersWithAuth()
            },
            'delete': {
                url: $rootScope.baseUrl + employeeConsts.deleteUrl,
                method: 'DELETE',
                headers: headerService.getHeadersWithAuth()
            }
        });

    }

})();