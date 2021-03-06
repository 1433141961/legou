    起止时间：2019.10.20---至今
    编写人：元志伟
    QQ：1433141961
    邮箱:1433141961@qq.com

**乐购商城的介绍**

    乐购商城是一个综合性的 B2乐购商城主要分为网站前台B2C 平台，类似京东商城、天猫商城。网站采用商家入驻的模式，
    商家入驻平台提交申请，有平台进行资质审核，审核通过后，商家拥有独立的管理后台录入商品信息。
    商品经过平台审核后即可发布。、运营商后台、商家管理后台三个子系统。
----------------------------------运营商后台主要功能模块说明-------------------------------

**品牌管理模块**

    功能：主要是品牌的分页查询、新增、删除、修改

**规格管理模块**

    功能：主要功能和品牌管理模块一样，主要的不同点是修改和删除操作涉及到两张表（规格表和规格项表）数据的依赖关系。
    **注意点：**
        1.修改：由于我们不清楚修改的规格项条数，未来避免繁琐的逻辑我们采用简单的方式，即先删除旧规格项数据然后再插入修改后的数据
        
        2.删除:规格项要随着规格一起删除，否则留着规格项无意义，相当于脏数据

**模板管理模块(重点是表结构)**  

    这个模块的操作涉及多对多的表关系，多对多通常是采用中间表作为桥梁实现，这样子效率会比较慢些。为了避免多表联合查询影响效率，我们采用冗余表的设计结构；
    ，也就是将设计多表联合查询的表数据集成到模块表中，将其作为一个字段存储到数据库中
    
    
    表结构：CREATE TABLE `tb_type_template` (
          `id` bigint(11) NOT NULL AUTO_INCREMENT,
          `name` varchar(80) DEFAULT NULL COMMENT '模板名称',
          `spec_ids` varchar(1000) DEFAULT NULL COMMENT '关联规格',
          `brand_ids` varchar(1000) DEFAULT NULL COMMENT '关联品牌',
          `custom_attribute_items` varchar(2000) DEFAULT NULL COMMENT '自定义属性',
          PRIMARY KEY (`id`)
        ) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;
        
        **注意点：**
            1.新增：在新增的时候由于涉及对关联品牌和规格的数据查询，我们要先查询到这两个的数据，然后对齐选择。这里涉及select2插件的使用
            
            2.注意json数据传递的格式
            
        其余的例如删除和修改等都比较简单，和之前的两个模块大同小异，就不多多操作了
        只要把前面两个模块给练还好了基本上对数据的增删改查就没问题了
        
**分类管理模块(重点是表结构)**

    分类管理模块风味三个级别1，2，3，所以涉及到多个表之间的操作，显然比较麻烦，也影响效率
    因此我们采用一表制的表设计结构，这个表设计结构和冗余表设计结构比较相似
    
    **表结构：**
    CREATE TABLE `tb_item_cat` (
      `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '类目ID',
      `parent_id` bigint(20) DEFAULT NULL COMMENT '父类目ID=0时，代表的是一级的类目',
      `name` varchar(50) DEFAULT NULL COMMENT '类目名称',
      `type_id` bigint(11) DEFAULT NULL COMMENT '类型id',
      PRIMARY KEY (`id`),
      KEY `parent_id` (`parent_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1213 DEFAULT CHARSET=utf8 COMMENT='商品类目';
    
    **注意点：**
          1.顶级分类的id是0，默认情况下点击分“分类管理”查询的是点击分类
          
          2. 分类查询和其他两个模块的最大差异在setGrade()和findByParentId()两个方法，代码如下：
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
 **运营商后台登录**     
 
    该模块使用的是spring security做的认证。和其他登录功能不同，这边没有访问数据库，,是在配置文件（springSecurity.xml）中配置的认证管理，
    配置如下：
        <!--认证管理器-->
            <authentication-manager>
                <authentication-provider>
                    <user-service>
                        <!--密码前面没加{noop} 这个域版本有关，5.0以前的版本不需要加，之后需要加-->
                        <user name="admin" password="123456"  authorities="ROLE_ADMIN"></user>
                    </user-service>
                </authentication-provider>
            </authentication-manager>
            **注意**
                根据版本问题是否在密码前面加{noop}。假如是5.0版本以上是需要加的，在5.0一下就不需要加
            
    后台获取用户名的方式，代码如下：        
     //创建security上下文所有者对象
                SecurityContextHolder sc = new SecurityContextHolder();
                //获得上下文对象
                SecurityContext context = sc.getContext();
                //获得name
                String userName = context.getAuthentication().getName();
                
                这里只是简单做了一个登录功能，后续功能继续跟上，如：扫码登录，验证功能,有想法可以自己添加，
                可以做二次开发
                              
                
----------------------------------------------------商家后台功能说明------------------------------------------------------
环境搭建时间：2019-11-18


**商家系统申请入驻功能**
   
        **首先我们来看看表结构**：如下：
            CREATE TABLE `tb_seller` (
              `seller_id` varchar(100) NOT NULL COMMENT '用户ID',
              `name` varchar(80) DEFAULT NULL COMMENT '公司名',
              `nick_name` varchar(50) DEFAULT NULL COMMENT '店铺名称',
              `password` varchar(60) DEFAULT NULL COMMENT '密码',
              `email` varchar(40) DEFAULT NULL COMMENT 'EMAIL',
              `mobile` varchar(11) DEFAULT NULL COMMENT '公司手机',
              `telephone` varchar(50) DEFAULT NULL COMMENT '公司电话',
              `status` varchar(1) DEFAULT '0' COMMENT '状态',
              `address_detail` varchar(100) DEFAULT NULL COMMENT '详细地址',
              `linkman_name` varchar(50) DEFAULT NULL COMMENT '联系人姓名',
              `linkman_qq` varchar(13) DEFAULT NULL COMMENT '联系人QQ',
              `linkman_mobile` varchar(11) DEFAULT NULL COMMENT '联系人电话',
              `linkman_email` varchar(40) DEFAULT NULL COMMENT '联系人EMAIL',
              `license_number` varchar(20) DEFAULT NULL COMMENT '营业执照号',
              `tax_number` varchar(20) DEFAULT NULL COMMENT '税务登记证号',
              `org_number` varchar(20) DEFAULT NULL COMMENT '组织机构代码',
              `address` bigint(20) DEFAULT NULL COMMENT '公司地址',
              `logo_pic` varchar(100) DEFAULT NULL COMMENT '公司LOGO图',
              `brief` varchar(2000) DEFAULT NULL COMMENT '简介',
              `create_time` datetime DEFAULT NULL COMMENT '创建日期',
              `legal_person` varchar(40) DEFAULT NULL COMMENT '法定代表人',
              `legal_person_card_id` varchar(25) DEFAULT NULL COMMENT '法定代表人身份证',
              `bank_user` varchar(50) DEFAULT NULL COMMENT '开户行账号名称',
              `bank_name` varchar(100) DEFAULT NULL COMMENT '开户行',
              PRIMARY KEY (`seller_id`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
            
            该表最重要的三个字段（seller_id,status,password）,既然重要，当然是后续是要操作的，seller_id表示商家名称也表示商家id（这个字段很重要，具体看代码）；
            stuts表示商家审核的状态；password密码，这里为了安全我们需要进行加密，采用spring security框架的加密算法进行加密

**商家入驻-->商家审批-->商家登录**
        
    入驻申请后需要审批通过才可登录，也就是将数据库的字段status的值改为‘1’（0表示审批通过，0表示未审核通过，1表示已经审核，2表示审核未通过），
    后期对于状态的操作也是对这个字段进行的。
    
    入驻：用程序的思想表达就是插入数据
    审批：就是修改数据
    登录：校验数据
        
**新增商品功能**
    
    使用到FastDFS作为文件服务器保存图片
    
    
 ----------------------------------------------------门户网站------------------------------------------------------
   
**搜索功能**
    
    重点在与solr的使用




















    