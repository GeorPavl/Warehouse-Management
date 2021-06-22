(function(){

    angular.module('app.wr', ['app.wr.warehouse']).config(config);

    function config($stateProvider) {

        $stateProvider.state({
            name: 'app.wr',
            url: "/wr/",
            abstract: true
        });
        
    }

})();