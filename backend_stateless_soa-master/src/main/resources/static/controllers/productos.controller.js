app=angular.module('frontend');
app.controller('ProductosController',function($scope,productosService,$log,wsService){
	
	productosService.listar();
	
	 wsService.initStompClient('/backend/numeros', function(payload, headers, res) {
         $log.log(payload)
         //$scope.$apply();
       });
	
	
});