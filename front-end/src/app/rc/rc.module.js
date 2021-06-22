(function() {

    angular.module('app.rc',['app.rc.rack']).config(config);

    function config($stateProvider) {

        $stateProvider.state({
            name: 'app.rc',
            url: '/rc/',
            abstract: true
        });

    }
    
})();