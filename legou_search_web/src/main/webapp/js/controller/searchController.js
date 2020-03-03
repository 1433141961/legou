app.controller("searchController",function($scope,$location,$http){

    // 定义对象 可以按品牌和分类查询
    $scope.paramMap = {"keyword":'',"brand":'',"category":'',"page":1};

    // 页面加载，初始化的方法
    $scope.initSearch=function(){
        // 获取url上的keyword
        $scope.paramMap.keyword=$location.search()["keyword"];
        // 调用查询的方法
        $scope.search();
    }

    // 查询
    $scope.search = function(){
        // 查询
        $http.post("./itemSearch/search",$scope.paramMap).success(function(resp){
            // 返回结果
            $scope.resultMap = resp.data;
            // 调用前端分页效果方法
            buildPageLabel();
        })
    }

    // 点击搜索按钮，进行查询操作
    $scope.searchByKeyword = function(){
        // 清空所有的查询条件
        $scope.paramMap.brand='';
        $scope.paramMap.category='';
        $scope.paramMap.page=1;
        // 新查询
        $scope.search();
    }

    // 向paramMap中添加查询条件，并且查询
    $scope.addParam = function(key,value){
        // 向paramMap中添加属性值
        $scope.paramMap[key] = value;
        // 查询
        $scope.search();
    }

    // 前端页面分页效果
    function buildPageLabel() {
        $scope.pageList = [];//新增分页栏属性
        var maxPageNo = $scope.resultMap.totalPages;//得到最后页码
        var firstPage = 1;//开始页码
        var lastPage = maxPageNo;//截止页码
        $scope.firstDot = true;//前面有点
        $scope.lastDot = true;//后边有点
        if ($scope.resultMap.totalPages > 5) { //如果总页数大于 5 页,显示部分页码
            if ($scope.paramMap.page <= 3) {//如果当前页小于等于 3
                lastPage = 5; //前 5 页
                $scope.firstDot = false;//前面没点
            } else if ($scope.paramMap.page >= lastPage - 2) {//如果当前页大于等于最大页码-2
                firstPage = maxPageNo - 4;  //后 5 页
                $scope.lastDot = false;//后边没点
            } else { //显示当前页为中心的 5 页
                firstPage = $scope.paramMap.page - 2;
                lastPage = $scope.paramMap.page + 2;
            }
        } else {
            $scope.firstDot = false;//前面无点
            $scope.lastDot = false;//后边无点
        }
        //循环产生页码标签
        for (var i = firstPage; i <= lastPage; i++) {
            $scope.pageList.push(i);
        }
    }

})