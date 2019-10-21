// 定义公共的Controller，把公共的代码抽取出来，存放到该controller中，其他Controller可以继承该Controller
app.controller("baseController",function($scope){

    // 定义分页插件对象，设置分页的属性
    $scope.paginationConf = {
        // 当前页
        currentPage: 1,
        // 总记录数
        totalItems: 10,
        // 每页显示的条数
        itemsPerPage: 10,
        // 每页可以显示的条数
        perPageOptions: [10, 20, 30, 40, 50],
        // 数据改变数据，当点击上一页、下一页等触发该方法
        onChange: function(){
            // 重新加载，获取数据的方法
            $scope.reloadList();
        }
    };

    // 定义空数组
    $scope.selectIds = [];

    // 删除是批量删除，先把勾选的id值存入到一个数组中
    $scope.updateSelection = function(e,id){
        // 判断，当前是否勾选
        if(e.target.checked){
            $scope.selectIds.push(id);
        }else{
            // 获取id在数组中的下标值
            var index = $scope.selectIds.indexOf(id);
            // 移除指定的元素，index元素下标值 1移除的数量
            $scope.selectIds.splice(index,1);
        }
    };

});