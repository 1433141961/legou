//控制层 
app.controller('sellerController',
		function($scope, sellerService) {
			// 读取列表数据绑定到表单中
			$scope.findAll = function() {
				sellerService.findAll().success(function(response) {
					$scope.list = response;
				});
			}

			// 分页
			$scope.findPage = function(page, rows) {
				sellerService.findPage(page, rows).success(function(response) {
					$scope.list = response.rows;
					$scope.paginationConf.totalItems = response.total;// 更新总记录数
				});
			}

			// 查询实体
			$scope.findOne = function(id) {
				sellerService.findOne(id).success(function(response) {
					$scope.entity = response;
				});
			}

			// 注册
			$scope.register = function() {
				//商家注册
				sellerService.add($scope.entity).success(function(response) {
					if (response.success) {
						// 跳转到商品登录页面
						alert("平台会在1-5个工作日完成您的入驻申请");
						location.href = "shoplogin.html";
					} else {
						alert(response.message);
					}
				});
			}

			// 批量删除
			$scope.dele = function() {
				// 获取选中的复选框
				sellerService.dele($scope.selectIds).success(
						function(response) {
							if (response.success) {
								$scope.reloadList();// 刷新列表
								$scope.selectIds = [];
							}
						});
			}

			$scope.searchEntity = {};// 定义搜索对象

			// 搜索
			$scope.search = function(page, rows) {
				sellerService.search(page, rows, $scope.searchEntity).success(
						function(response) {
							$scope.list = response.rows;
							$scope.paginationConf.totalItems = response.total;// 更新总记录数
						});
			};

		});
