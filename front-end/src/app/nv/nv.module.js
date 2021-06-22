(function() {

    angular.module('app.nv',['app.nv.invoice']).config(config);

    function config($stateProvider) {

        $stateProvider.state({
            name: 'app.nv',
            url: '/nv/',
            abstract: true
        });

    }

})();