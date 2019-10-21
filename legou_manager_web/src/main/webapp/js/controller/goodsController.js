app.controller("goodsController",function($scope,$controller,$http){
	
	// controller的继承，本质就是共用一个$scope
	$controller('baseController',{$scope:$scope});

    // 定义搜索对象
    $scope.searchEntity={};

    //0：未审核  1：已审核  2:驳回
    $scope.status=["未审核","已审核","驳回"];

    // 加载数据的方法
    $scope.reloadList = function(){
        // 分页查询
        $http.post("../goods/search/"+$scope.paginationConf.currentPage+"/"+$scope.paginationConf.itemsPerPage,$scope.searchEntity).success(function(resp){
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

    // 定义对象
    $scope.itemCatList = {};

    // 查询分类数据
    $scope.findAllItemCat = function(){
        // 查询数据
        $http.get("../itemCat/findAllItemCat").success(function(resp){
            if(resp.success){
                // 进行组装数据
                for (var i = 0; i < resp.data.length; i++) {
                    $scope.itemCatList[resp.data[i].id]=resp.data[i].name;
                }
            }else{
                // 表示失败
                alert(resp.message);
            }
        });
    };

    // 审核通过/驳回功能
    $scope.updateAuditStatus = function(status){
        // 询问
        if(window.confirm("确定该操作吗")){
            // 审核通过或者驳回功能
            $http.post("../goods/updateAuditStatus/"+status+"/"+$scope.selectIds).success(function(resp){
                if(resp.success){
                    $scope.reloadList();
                    $scope.selectIds=[];
                }else{
                    // 表示失败
                    alert(resp.message);
                }
            });
        };
    };

});