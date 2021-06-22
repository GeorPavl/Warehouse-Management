(function() {

    angular.module('app.nv.invoice').service('invoiceService', invoiceService);

    function invoiceService(invoiceFactory) {

        return {
            getInvoices: getInvoices,
            getInvoice: getInvoice,
            saveInvoice: saveInvoice,
            deleteInvoice: deleteInvoice,
            indexInvoice: indexInvoice
        }

        function getInvoices() {
            return invoiceFactory.list({});
        }

        function getInvoice(id) {
            return invoiceFactory.get({id}, null);
        }

        function saveInvoice(invoice) {
            return invoiceFactory.save({}, invoice);
        }

        function deleteInvoice(id) {
            return invoiceFactory.delete({id}, null);
        }

        function indexInvoice(search) {
            return invoiceFactory.index({},search);
        }

    }

})();