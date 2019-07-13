app=angular.module('frontend');
app.controller('div1Controller',function($scope, productosService){
	$scope.titulo="Hola Mundo";
	$scope.numero=12;
	$scope.enblanco={};
	
	$scope.productos=productosService;
	
//	$scope.productos=productosService.data().productos;
	
	//$scope.sumaTotal=function(){
	//	return productosService.sumaTotal($scope.productos);
	//}
	$scope.eliminar=function(producto){
		productosService.eliminar(producto).then(   
				function(resp){
					$scope.productos.forEach(     
							function(o,i){     
								if(producto.id==o.id) {
									$scope.productos.splice(i,1);
									return false;
								}

							}            
					);
				},
				function(err){}    );
	}
	productosService.listar();
	
	/*productosService.listar().then(
			function(resp){
				$scope.productos=resp.data;
			},
			function(err){}
	);*/
	
	
	
	$scope.agregar=function(){
		productosService.agregar($scope.enblanco);
	}
	
	
	/*
	$scope.productos=[
		{id:1,descripcion:"Arroz",precio:50.58},		
		{id:2,descripcion:"Leche",precio:45.12},
		{id:3,descripcion:"Chupetín",precio:6},
		
	];
	$scope.agregar=function(){
		$scope.enblanco.id=$scope.productos.length+10;
		$scope.productos.push($scope.enblanco);
	}
	$scope.eliminar=function(producto){
		console.log(producto);
		$scope.productos.forEach(     
				function(o,i){     
					if(producto.id==o.id) {
						$scope.productos.splice(i,1);
						return false;
					}

				}            
		);
	}
	
	$scope.sumaTotal=function(){
		var total=0;
		//for(i=0; i<$scope.productos.length; i++) {
		//	total+=$scope.productos[i].precio;
		//}
		$scope.productos.forEach(     
				function(o,i){     
					total+=o.precio;
				}            
		);
		return total;
	}*/
	
});