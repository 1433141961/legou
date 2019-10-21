// 定义brancController
app.controller("brandController",function($scope,$controller,$http){
	
	// controller的继承，本质就是共用一个$scope
	$controller('baseController',{$scope:$scope});

    // 定义reloadList方法
    $scope.reloadList = function(){
        // 发送请求，分页查询数据
        // 请求参数传当前页和每页显示的条数
        // 返回值返回json，格式如下：data = {total:xx,rows:[{},{}]}
        $http.get("../brand/findPage/"+$scope.paginationConf.currentPage+"/"+$scope.paginationConf.itemsPerPage).success(function(resp){
            if(resp.success){
                // 分页数据
                $scope.list = resp.data;
                // 总记录数
                $scope.paginationConf.totalItems = resp.total;
            }else{
                // 表示失败
                alert(resp.message);
            }
        });
    };

    // 保存和修改的方法
    $scope.save = function(){

        // 定义变量名称
        var method = "save";
        // 判断当前是否再进行修改功能
        if($scope.entity.id != null){
            // 说明id有值，进行的是修改功能
            method = "update";
        }

        // 发送post请求，保存数据
        $http.post("../brand/"+method,$scope.entity).success(function(resp){
            if(resp.success){
                // 重新刷新数据
                $scope.reloadList();
                // 清空数据
                $scope.entity = {};
            }else{
                alert(resp.message);
            }
        });
    };

    // 通过id查询一个对象方法
    $scope.findOne = function(id){
        // 通过主键查询
        $http.get("../brand/findOne/"+id).success(function(resp){
            // 赋值给entity变量
            $scope.entity = resp.data;
        });
    };

    // 删除
    $scope.dele = function(){
        // 判断是否选择了元素
        if($scope.selectIds.length == 0){
            return;
        }

        // 确认框
        if(window.confirm("确定删除吗")){
            // 发送请求，删除数据
            $http.get("../brand/delete?ids="+$scope.selectIds).success(function(resp){
                if(resp.success){
                    // 重新刷新数据
                    $scope.reloadList();
                    // 清空数据
                    $scope.selectIds = [];
                }else{
                    alert(resp.message);
                }
            });
        }

    };

});