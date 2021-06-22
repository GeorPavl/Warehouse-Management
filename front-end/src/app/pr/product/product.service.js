(function() {

    angular.module('app.pr.product').service('productService', productService);

    function productService(productFactory) {

        return{
            getProducts: getProducts,
            getProduct: getProduct,
            saveProduct: saveProduct,
            deleteProduct: deleteProduct,
            indexProduct: indexProduct,
            getProductStock: getProductStock
        }

        function getProducts() {
            return productFactory.list({});
        }

        function getProduct(id) {
            return productFactory.get({id}, null);
        }

        function saveProduct(product) {
            return productFactory.save({}, product);
        }

        function deleteProduct(id) {
            return productFactory.delete({id}, null);
        }

        function indexProduct(search) {
            return productFactory.index({},search);
        }

        function getProductStock(search) {
            return productFactory.stock({},search);
        }

    }

})();