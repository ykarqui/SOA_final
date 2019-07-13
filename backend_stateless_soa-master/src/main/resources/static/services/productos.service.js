app=angular.module('frontend');

app.service('productosService',
function($http, URL_API_BASE){
	
	var servicio={
		datos:[],
		listar:function(){
			$http.get(URL_API_BASE+'productos').then(
					function(resp){
						this.datos=resp.data;
					},
					function(err){}
			);
		},
		agregar:function(producto) {
			return $http.post(URL_API_BASE+'productos',producto);
		},
		eliminar:function(producto){
			return $http.delete(URL_API_BASE+'productos/'+producto.id);
			
			
		},
		sumaTotal:function(){
			var total=0;
			this.datos.forEach(     
					function(o,i){     
						total+=o.precio;
					}            
			);
			return total;
		}
	};
	return servicio;
	
}
);