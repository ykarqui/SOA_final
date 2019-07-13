angular.module('frontend')
.constant('URL_API_BASE', '/api/v1/')
.constant('URL_BASE', '/')
.constant('URL_WS', '/api/v1/ws')
.run( function($rootScope,$location,$uibModal,coreService,$localStorage,$stomp){
	
	$rootScope.stomp=$stomp;
	$rootScope.loginOpen=false;
	//$rootScope.loginData=false;
	//$rootScope.loggedIn=false;
	
	$rootScope.loginData=$localStorage.userdata;
	$rootScope.loggedIn=$localStorage.logged;
	
	
	$rootScope.openLoginForm = function(size) {
		if (!$rootScope.loginOpen) {
			$rootScope.loginOpen = true;
			$uibModal.open({
				animation : true,
				backdrop : 'static',
				keyboard : false,
				templateUrl : 'views/loginForm.html',
				controller : 'LoginFormController',
				size : 'md'
			});
		}
	};
	
	$rootScope.cbauth=false;
	
	$rootScope.inRole=function(rol) {
	  if(!$localStorage.logged || !$localStorage.userdata.roles || $localStorage.userdata.roles.length==0)
	    return false;
	  var r=false;
	  $localStorage.userdata.roles.forEach(function(o,i){
	    if(o==rol) {
	      r=true;
	      return false;
	    }
	  });
	  return r;
	};
	$rootScope.authInfo=function(cb,rolif,cbrolif) {
		  //Si el usuario está en el rol indicado en rolif, se ejecuta la callback cbrolif
		  if(rolif && cbrolif && $rootScope.inRole('ROLE_'+rolif) )
		    cb=cbrolif;
		  if(cb)
				$rootScope.cbauth=cb;
		  if($rootScope.cbauth && $localStorage.logged)
				$rootScope.cbauth();
		}
	
	$rootScope.logout=function() {
		//$rootScope.loginData=false;
		//$rootScope.loggedIn=false;
		delete $localStorage.userdata;
		$localStorage.logged=false;
		$rootScope.loginOpen = false;
		coreService.logout().then(function(resp){
			//$rootScope.loginData=false;
			//$rootScope.loggedIn=true;	
			
		},function(){});
	};
	
	$rootScope.oldLoc=false;
	$rootScope.relocate=function(loc){
		$rootScope.oldLoc=$location.$$path;
		$location.path(loc);
	};
	$rootScope.backLocation=function() {
		if($rootScope.oldLoc)
			$location.path($rootScope.oldLoc);
	};
} 
);