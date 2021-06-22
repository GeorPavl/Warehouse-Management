(function() {

    angular.module('app.rc.rack').controller('rackListController', rackListController);

    function rackListController($scope, $state, $translate, toitsuToasterService, rackService, warehouseService) {

        var viewState = 'app.rc.rack.view';

        $scope.racks = [];
        $scope.warehouses = [];

        $scope.sortBy = 'id';
        $scope.reverse = false;
        $scope.doSort = function(propName) {
           $scope.sortBy = propName;
           $scope.reverse = !$scope.reverse;
        };

        init();

        function init() {
            getRacks();
            getWarehouses();
        }

        function getRacks() {
            rackService.getRacks().$promise
                .then(function(response) {
                    $scope.racks = response;
                })
                .catch(function(response) {
                    toitsuToasterService.apiValidationErrors(response);
                })
                .finally(function() {
                    App.unblockUI();
                });
        }

        function getWarehouses() {
            warehouseService.getWarehouses().$promise
                .then(function(response) {
                    $scope.warehouses = response;
                    console.log(response);
                })
                .catch(function(response) {
                    console.log(response);
                    toitsuToasterService.apiValidationErrors(response);
                })
                .finally(function() {
                    App.unblockUI();
                });
        }

        $scope.newRack = function() {
            $state.go(viewState, {id: null});
        }

        $scope.getRack = function(id) {
            $state.go(viewState, {id: id});
        }

        $scope.deleteRack = function(id) {
            var result = confirm($translate.instant('global.deleteConfirmationMessage'));
            if (result) {
                rackService.deleteRack(id).$promise
                    .then(function(){
                        $state.go($state.current, {}, {reload: true})
                            .then(function() {
                                toitsuToasterService.showSuccess($translate.instant('rc.rack.deleteSuccess'));
                            })
                    })
                    .catch(function(response) {
                        toitsuToasterService.apiValidationErrors(response);
                    })
                    .finally(function() {
                        App.unblockUI();
                    });
            }
        }

    }

})();