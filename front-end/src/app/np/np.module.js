(function() {

    angular.module('app.np',['app.np.invoiceProduct']).config(config);

    function config($stateProvider) {

        $stateProvider.state({
            name: 'app.np',
            url: '/np/',
            abstract: true
        });

    }

})();