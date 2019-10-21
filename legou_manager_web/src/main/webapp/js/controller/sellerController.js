app.controller("sellerController",function($scope,$controller,$http){
	
	// controller的继承，本质就是共用一个$scope
	$controller('baseController',{$scope:$scope});

	// 定义查询实体类，status=0查询未审核的所有商家
    $scope.searchEntity = {"status":"0"};

    // 定义reloadList方法
    $scope.reloadList = function(){
        // 分页的条件查询
        $http.post("../seller/search/"+$scope.paginationConf.currentPage+"/"+$scope.paginationConf.itemsPerPage,$scope.searchEntity).success(function(resp){
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

    // 通过主键查询
    $scope.findOne = function(sellerId){
        // 分页的条件查询
        $http.post("../seller/findOne/"+sellerId).success(function(resp){
            if(resp.success){
                $scope.entity = resp.data;
            }else{
                // 表示失败
                alert(resp.message);
            }
        });
    };

    // 审核商家 商家id和状态码
    $scope.auditing = function(sellerId,status){
        // 分页的条件查询
        $http.post("../seller/auditing/"+sellerId+"/"+status).success(function(resp){
            if(resp.success){
                // 提成成功
                alert(resp.message);
                // 刷新列表
                $scope.reloadList();
            }else{
                // 表示失败
                alert(resp.message);
            }
        });
    };


});