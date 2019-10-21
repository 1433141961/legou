app.controller("itemCatController",function($scope,$http){

    // 定义entity空对象，用来显示二级和三级分类
    $scope.entity1 = null;
    $scope.entity12 = null;

    // 定义级别属性
    $scope.grade = 1;

    // 父id
    $scope.parentId=0;

    // 提供一个设置级别的方法
    $scope.setGrade = function(val,en){
        $scope.grade = val;
        $scope.parentId=en.id;
        //列表显示的是一级分类
        if($scope.grade==1){
            $scope.entity1=null; //面包屑上显示的一级分类
            $scope.entity2=null; //面包屑上显示的二级分类
        }
        //列表显示的是二级分类    entity2 置成null
        if($scope.grade==2){
            $scope.entity1=en;
            $scope.entity2=null;
            $scope.parentId=en.id;
        }
        //列表显示的是三级分类
        if($scope.grade==3){
            $scope.entity2=en;
            $scope.parentId=en.id;
        }
    };

    // 查询分类数据
    $scope.findByParentId = function(parentId){
        // 清空二级和三级分类的名称
        if(parentId == 0){
            $scope.entity1=null;
            $scope.entity2=null;
            $scope.grade = 1;
            $scope.parentId=0;
        }

        $http.get("../itemCat/findByParentId/"+parentId).success(function(resp){
            if(resp.success){
                // 数据
                $scope.list = resp.data;
            }else{
                // 表示失败
                alert(resp.message);
            }
        });
    };

    // 查询所有的模板
    $scope.findTemplateAll = function(){
        $http.get("../typeTemplate/findAll").success(function(resp){
            if(resp.success){
                // 数据
                $scope.typeTemplateList = resp.data;
            }else{
                // 表示失败
                alert(resp.message);
            }
        });
    };

    // 保存和修改的方法
    $scope.save = function(){

        $scope.entity.parentId=$scope.parentId;

        // 定义变量名称
        var method = "add";
        // 判断当前是否再进行修改功能
        if($scope.entity.id != null){
            // 说明id有值，进行的是修改功能
            method = "update";
        }

        // 发送post请求，保存数据
        $http.post("../itemCat/"+method,$scope.entity).success(function(resp){
            if(resp.success){
                // 重新刷新数据
                $scope.findByParentId($scope.parentId);
            }else{
                alert(resp.message);
            }
        });
    };

});