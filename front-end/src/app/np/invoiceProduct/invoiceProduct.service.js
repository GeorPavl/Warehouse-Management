(function() {

    angular.module('app.np.invoiceProduct').service('invoiceProductService', invoiceProductService);

    function invoiceProductService(invoiceProductFactory) {

        return {
            getInvoiceProducts: getInvoiceProducts,
            getInvoiceProduct: getInvoiceProduct,
            saveInvoiceProduct: saveInvoiceProduct,
            deleteInvoiceProduct: deleteInvoiceProduct,
            indexInvoiceProduct: indexInvoiceProduct
        }

        function getInvoiceProducts() {
            return invoiceProductFactory.list({});
        }

        function getInvoiceProduct(id) {
            return invoiceProductFactory.get({id}, null);
        }

        function saveInvoiceProduct(invoiceProduct) {
            return invoiceProductFactory.save({},invoiceProduct);
        }

        function deleteInvoiceProduct(id) {
            return invoiceProductFactory.delete({id}, null);
        }

        function indexInvoiceProduct(search) {
            return invoiceProductFactory.index({}, search);
        }

    }

})();