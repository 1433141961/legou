app.controller("typeTemplateController",function($scope,$controller,$http){
	
	// controller的继承，本质就是共用一个$scope
	$controller('baseController',{$scope:$scope});

	// 把json转换成数组，再解析数组，显示出名称
    $scope.arrayToString = function(jsonStr){
        // 使用,拼接后的字符串
        resultStr = "";
        // 把json字符串转换成数组
        var array = JSON.parse(jsonStr);
        // 遍历数组
        for(var i=0;i<array.length;i++){
            // 拼接内容
            if(i == 0){
                resultStr += array[i].text;
            }else{
                resultStr += ","+array[i].text;
            }
        }
        return resultStr;
    };

    // 定义reloadList方法
    $scope.reloadList = function(){
        // 发送请求，分页查询数据
        // 请求参数传当前页和每页显示的条数
        // 返回值返回json，格式如下：data = {total:xx,rows:[{},{}]}
        $http.get("../typeTemplate/findPage/"+$scope.paginationConf.currentPage+"/"+$scope.paginationConf.itemsPerPage).success(function(resp){
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
        var method = "add";
        // 判断当前是否再进行修改功能
        if($scope.entity.id != null){
            // 说明id有值，进行的是修改功能
            method = "update";
        }

        // 发送post请求，保存数据
        $http.post("../typeTemplate/"+method,$scope.entity).success(function(resp){
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
        $http.get("../typeTemplate/findOne/"+id).success(function(resp){
			// 把字符串解析json的数组
            resp.data.brandIds = JSON.parse(resp.data.brandIds);
            resp.data.specIds = JSON.parse(resp.data.specIds);
            resp.data.customAttributeItems = JSON.parse(resp.data.customAttributeItems);
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
            $http.get("../typeTemplate/delete?ids="+$scope.selectIds).success(function(resp){
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

    // 定义品牌数据
    // $scope.brandList = {data:[{id:1,text:'联想'},{id:2,text:'华为'},{id:3,text:'小米'}]};

    // 查询品牌数据，数据格式：{data:[{id:1,text:'联想'},{id:2,text:'华为'},{id:3,text:'小米'}]};
    $scope.findBrandList = function(){
        // 查询数据，赋值到brandList属性上
        $http.get("../brand/findBrandList").success(function(resp){
            if(resp.success){
                // 赋值操作
                $scope.brandList = {data:resp.data};
            }else{
                alert(resp.message);
            }
        });
    };

    // 查询规格数据
    $scope.findSpecificationList = function(){
        // 查询数据
        $http.get("../specification/findSpecificationList").success(function(resp){
            if(resp.success){
                // 赋值操作
                $scope.specificationList = {data:resp.data};
            }else{
                alert(resp.message);
            }
        });
    };

    // 初始化属性
    $scope.entity = {customAttributeItems:[]};

    // 添加扩展属性
    $scope.addCustomAttributeItems = function(){
        $scope.entity.customAttributeItems.push({});
    };

    // 删除扩展属性
    $scope.delCustomAttributeItems = function(index){
        $scope.entity.customAttributeItems.splice(index,1);
    };

});