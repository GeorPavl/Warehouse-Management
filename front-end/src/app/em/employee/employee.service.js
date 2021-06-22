(function() {

    angular.module('app.em.employee').service('employeeService', employeeService);

    function employeeService(employeeFactory) {
        return {
            getEmployees : getEmployees,
            getEmployee : getEmployee,
            saveEmployee : saveEmployee,
            deleteEmployee : deleteEmployee,
        }
        
        function getEmployees() {
            return employeeFactory.list({});
        }

        function getEmployee(id) {
            return employeeFactory.get({id},null);
        }

        function saveEmployee(Employee) {
            return employeeFactory.save({}, Employee);
        }

        function deleteEmployee(id) {
            return employeeFactory.delete({id},null);
        }
    }

})();