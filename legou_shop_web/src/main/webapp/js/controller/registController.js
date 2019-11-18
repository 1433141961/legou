// 商家注册控制器
app.controller("registController", function($scope,$http) {
	
	// 商家注册
	$scope.regist = function(){
		// 发送请求，完成商家注册功能
        $http.post("../seller/save",$scope.entity).success(function(resp){
            if(resp.success){
                // 给出提示
				alert("正在审批，3个工作日内根据审批结果登录系统！！");
				// 跳转到登陆页面
				location.href = "shoplogin.html";
            }else{
                alert(resp.message);
            }
        });
	};
	
});