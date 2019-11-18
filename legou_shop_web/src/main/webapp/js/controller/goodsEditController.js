app.controller("goodsEditController",function($scope,$http){

    // 商品新增
    $scope.save=function(){
    	// 把富文本编辑器的值，设置到introduction属性上
        $scope.entity.tbGoodsDesc.introduction=editor.html();
        // 保存商品
        $http.post("../goods/save",$scope.entity).success(function(resp){
            if(resp.success){
                location.href = "goods.html";
            }else{
                alert(resp.message);
            }
        });
    };

	// 查询一级分类
	$scope.findCategory1List=function(){
		// 查询一级分类
        $http.get('../itemCat/findByParentId/0').success(function(resp){
			if(resp.success){
                $scope.category1List=resp.data;
			}else{
                alert(resp.message);
			}
		});
	};
	
	// 如果entity.tbGoods.category1Id一旦发生改变 触发查询二级分类的方法
	$scope.$watch("entity.tbGoods.category1Id",function( newValue,oldValue){
        // 查询一级分类
        $http.get('../itemCat/findByParentId/'+newValue).success(function(resp){
            if(resp.success){
                $scope.category2List=resp.data;
                // 清空三级分类列表数据
                $scope.category3List=[];
                // 清空模板id
                $scope.entity.tbGoods.typeTemplateId=null;
            }else{
                alert(resp.message);
            }
        });
	});
	
	// 如果entity.tbGoods.category2Id一旦发生改变 触发查询三级分类的方法
	$scope.$watch("entity.tbGoods.category2Id",function( newValue){
		// function可以有两个参数 newValue和oldValue 也可以有一个参数 newValue
        $http.get('../itemCat/findByParentId/'+newValue).success(function(resp){
			$scope.category3List=resp.data;
		})
	});
	
	//如果entity.tbGoods.category3Id一旦发生改变 触发查询分类方法
	$scope.$watch("entity.tbGoods.category3Id",function( newValue,oldValue){
		// function可以有两个参数 newValue和oldValue 也可以有一个参数 newValue
        $http.get('../itemCat/findOne/'+newValue).success(function(resp){
			$scope.entity.tbGoods.typeTemplateId=resp.data.typeId;
		})
	});
	
	//如果entity.tbGoods.typeTemplateId一旦发生改变 触发查询模板方法
	$scope.$watch("entity.tbGoods.typeTemplateId",function( newValue,oldValue){
		// function可以有两个参数 newValue和oldValue 也可以有一个参数 newValue
        $http.get('../typeTemplate/findOne/'+newValue).success(function(resp){
            // 模板
			$scope.brandList = JSON.parse(resp.data.brandIds);
			// 扩展属性
            $scope.entity.tbGoodsDesc.customAttributeItems = JSON.parse(resp.data.customAttributeItems);
		});

        // 查询规格集合
        $http.get('../typeTemplate/findSpecList/'+newValue).success(function(resp){
            // 规格集合
            $scope.specList = resp.data;
        });
    });

    // 初始化组合类对象
    $scope.entity={tbGoods:{isEnableSpec:"1"},tbGoodsDesc:{itemImages:[],specificationItems:[]}};

    // 文件上传
    $scope.uploadFile=function(){
        // 存储表单数据
        var formData=new FormData();
        // 向表单中添加file属性和值
        formData.append("file",file.files[0]);
        // 文件上传
        $http({
            method:'POST',
            url:"../upload",
            data: formData,
            headers: {'Content-Type':undefined},
            transformRequest: angular.identity}).
            then(function successCallback(resp) {
                // 请求成功执行代码
                if(resp.data.success){
                    $scope.image.url = resp.data.data;
                }else{
                    alert(resp.data.message);
                }

            });
        };

    // 动态添加图片
    $scope.addItemImages=function(){
        $scope.entity.tbGoodsDesc.itemImages.push($scope.image);
    };

    // 动态删除图片
    $scope.deleItemImages=function(index){
        $scope.entity.tbGoodsDesc.itemImages.splice(index,1);
    };

    // 出来规格选中不选中的效果
    $scope.updateSpecificationItems = function($event,key,value){
        // 判断是否勾选
        if($event.target.checked){
            // 记录是否找到已存在对象的标记 false代表没找到
            var flag = false;
            for (var i = 0; i < $scope.entity.tbGoodsDesc.specificationItems.length; i++) {
                if($scope.entity.tbGoodsDesc.specificationItems[i].attributeName==key){
                    $scope.entity.tbGoodsDesc.specificationItems[i].attributeValue.push(value);
                    flag=true;
                    break;
                }
            }
            if(!flag){ //代表没找到 需要追加新对象
                $scope.entity.tbGoodsDesc.specificationItems.push({"attributeName":key,"attributeValue":[value]});
            }
        }else{
            //取消勾选
            for (var i = 0; i < $scope.entity.tbGoodsDesc.specificationItems.length; i++) {
                if($scope.entity.tbGoodsDesc.specificationItems[i].attributeName==key){
                    var index = $scope.entity.tbGoodsDesc.specificationItems[i].attributeValue.indexOf(value);
                    $scope.entity.tbGoodsDesc.specificationItems[i].attributeValue.splice(index,1);
                    // 判断此对象中的attributeValue的内容是否为空
                    if($scope.entity.tbGoodsDesc.specificationItems[i].attributeValue.length==0){
                        // 应该把此对象从  $scope.entity.tbGoodsDesc.specificationItems中移除
                        $scope.entity.tbGoodsDesc.specificationItems.splice(i,1);
                    }
                    break;
                }
            }
        }

        // 构建sku列表
        createItemList();
    };

    // 构建sku列表
    function createItemList(){
        $scope.entity.itemList=[{spec:{},price:0,num:9999,status:'1',isDefault:'0'}];
        var specItems  = $scope.entity.tbGoodsDesc.specificationItems;
        for (var i = 0; i < specItems.length; i++) {
            $scope.entity.itemList = addColumn($scope.entity.itemList,specItems[i].attributeName,specItems[i].attributeValue)
        }
    };

    //	spec:{网络:移动3G}
    function addColumn(itemList,attributeName,attributeValue){
        var newItemList=[];
        for (var i = 0; i < itemList.length; i++) {
            for (var j = 0; j < attributeValue.length; j++) {
                var newRow = JSON.parse(JSON.stringify( itemList[i]));
                newRow.spec[attributeName]  = attributeValue[j];
                newItemList.push(newRow);
            }
        }
        return newItemList;
    };


});