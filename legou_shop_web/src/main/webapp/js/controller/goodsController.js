app.controller("goodsController",function($scope,$controller,$http){

    // controller的继承，本质就是共用一个$scope
    $controller('baseController',{$scope:$scope});

    // 定义商品的状态
    $scope.status=["未上架","已上架","已下架"];

    // 分页
    $scope.reloadList = function(){
        $http.get("../goods/findPage/"+$scope.paginationConf.currentPage+"/"+$scope.paginationConf.itemsPerPage).success(function(resp){
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

    // 上架或者下架
    $scope.updateGoods = function(val,msg){
        // 判断是否选择了元素
        if($scope.selectIds.length == 0){
            return;
        }
        // 确认框
        if(window.confirm("确定"+msg+"操作吗")){
            // 发送请求
            $http.get("../goods/updateGoods/"+val+"/"+$scope.selectIds).success(function(resp){
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