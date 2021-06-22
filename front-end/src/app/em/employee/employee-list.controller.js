(function() {

    angular.module('app.em.employee').controller('employeeListController', employeeListController);

    function employeeListController($scope, $state, $translate, toitsuToasterService, employeeService) {

        var viewState = 'app.em.employee.view';

        $scope.employeeArgsIsOpen = true;
        $scope.deceasedDataIsOpen = true;

        $scope.employees = [];

        $scope.sortBy = 'id';
        $scope.reverse = false;
        $scope.doSort = function(propName) {
            $scope.sortBy = propName;
            $scope.reverse = !$scope.reverse;
        }

        init();

        function init() {
            getEmployees();
        }

        function getEmployees() {
            employeeService.getEmployees().$promise
                .then(function(response) {
                    $scope.employees = response;
                })
                .catch(function(response) {
                    toitsuToasterService.apiValidationErrors(response);
                })
                .finally(function() {
                    App.unblockUI();
                });
        }

        $scope.newEmployee = function() {
            $state.go(viewState, {id: null});
        }

        $scope.getEmployee = function(id) {
            $state.go(viewState, {id: id});
        }

        $scope.deleteEmployee = function(id) {
            var result = confirm($translate.instant('global.deleteConfirmationMessage'));
            if (result) {
                employeeService.deleteEmployee(id).$promise
                    .then(function(){
                        $state.go($state.current, {}, {reload: true})
                            .then(function() {
                                toitsuToasterService.showSuccess($translate.instant('em.employee.deleteSuccess'));
                            });
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