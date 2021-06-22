(function(){

    angular.module('app.em', ['app.em.employee']).config(config);

    function config($stateProvider) {

        $stateProvider.state({
            name: 'app.em',
            url: "/em/",
            abstract: true
        });
        
    }

})();