//定义控制器
app.controller("loginController",function($scope,$http){

    // 显示用户的名称
    $scope.findLoginname = function(){
        // 发送get请求
        $http.get("../login/findLoginname").success(function(resp){
            if(resp.success){
                // 数据
                $scope.loginname = resp.data;
            }else{
                // 表示失败
                alert(resp.message);
            }
        });
    };

});
/*
app.controller("loginController", function($scope,loginService) {

    //获取用户登录名
    $scope.loadLoginName = function(){

        //调用服务层方法
        loginService.loadLoginName().success(function(data){
                $scope.loginName = data.loginName;
            }
        );

    };

});*/

