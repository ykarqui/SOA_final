app=angular.module('frontend');
app.controller('MainController',function($scope, $rootScope, $log, graphService, wsService){
	$scope.graphOptions = {
			demo : {
				options : {},
				data : {}
			}
		};

		$scope.procesaDatosGraph=function(datos){
			var labels=[];
			var data=[];
			datos.forEach(function(o,i){
				labels.push(o.label);
				data.push(o.value);
			});
			$scope.graphOptions.demo.data={data:data,labels:labels}
		};
		$scope.iniciaWS = function() {
			$log.log("iniciandoWS");
			wsService.initStompClient('/backend/data/graph', function(payload,
					headers, res) {
				$log.log(payload);
				if(payload.type=='GRAPH_DATA') {
					$scope.procesaDatosGraph(payload.payload);
				}
				$scope.$apply();
			});
		}

		$scope.requestRefresh=function(){
			graphService.requestPushData();
		};
		$rootScope.authInfo($scope.iniciaWS());
		
		//$scope.iniciaWS();
		
		$scope.$on("$destroy", function() {
			 wsService.stopStompClient();
		});
	
});