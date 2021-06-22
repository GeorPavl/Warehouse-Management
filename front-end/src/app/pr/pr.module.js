(function() {

    angular.module('app.pr',['app.pr.product']).config(config);

    function config($stateProvider) {

        $stateProvider.state({
            name: 'app.pr',
            url: '/pr/',
            abstract: true
        });
        
    }

})();