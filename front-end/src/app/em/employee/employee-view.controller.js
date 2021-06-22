(function() {

    angular.module('app.em.employee').controller('employeeViewController', employeeViewController);

    function employeeViewController($scope, $state, $translate, toitsuToasterService, employeeService, employee, lists) {

        var invoiceViewState = 'app.nv.invoice.view';

        $scope.employee = {};
        $scope.invoices = [];
        $scope.inputs = [];
        $scope.moreInputs = function(){
            $scope.inputs.push(0);
        }

        $scope.sortBy = 'id';
        $scope.reverse = false;
        $scope.doSort = function(propName) {
            $scope.sortBy = propName;
            $scope.reverse = !$scope.reverse;
        }

        init();

        function init() {
            console.log($scope.employee);
            console.log(employee);
            $scope.employee = employee;
            console.log($scope.employee);
            if ($state.params.id) {
                //getEmployee();
                $scope.isEditable = true;
                $scope.isDisabled = true;
            }else {
                $scope.employee.invoices = [];
                $scope.isEditable = false;
                $scope.isDisabled = false;
            }
        }

        function getEmployee() {
            employeeService.getEmployee($state.params.id).$promise
                .then(function(response) {
                    $scope.employee = response;
                })
                .catch(function(response) {
                    toitsuToasterService.apiValidationErrors(response);
                })
                .finally(function() {
                    App.unblockUI();
                });
        }

        $scope.saveEmployee = function() {
            employeeService.saveEmployee($scope.employee).$promise
                .then(function(response) {
                    $state.go($state.current, {id: response.id}, {reload: true})
                        .then(function() {
                            toitsuToasterService.showSuccess($translate.instant('em.employee.saveSuccess'));
                        });
                })
                .catch(function(response) {
                    toitsuToasterService.apiValidationErrors(response);
                })
                .finally(function() {
                    App.unblockUI();
                });
        }

        $scope.getInvoice = function(id) {
            $state.go(invoiceViewState, {id: id}, {reload: true});
        }

        $scope.deleteInvoice = function(id) {
            var result = confirm($translate.instant('global.deleteConfirmationMessage'));
            if (result) {
                employeeService.deleteInvoice(id).$promise
                .then(function() {
                    $state.go($state.current, {id: $scope.employee.id}, {reload: true})
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