(function() {

    angular.module('app.rc.rack').service('rackService', rackService);

    function rackService(rackFactory) {

        return {
            getRacks: getRacks,
            getRack: getRack,
            saveRack: saveRack,
            deleteRack: deleteRack,
        }

        function getRacks() {
            return rackFactory.list({});
        }

        function getRack(id) {
            return rackFactory.get({id},null);
        }

        function saveRack(rack) {
            return rackFactory.save({},rack);
        }

        function deleteRack(id) {
            return rackFactory.delete({id},null);
        }

    }

})();