## 存储过程设计

### 函数命名规则

1. 大小写规则按照小驼峰规则
2. 每个存储过程的具体命名顺序为 <权限><方法><作用对象><作用域>
3. 权限即不同的操作人员 **理论上** 能否调用该存储过程，分为 admin, user, fixer，all, server (server为业务逻辑代码范围定义内) 四个权限
4. 方法即该存储过程的功能简略描述，如 update ， delete ， get 等
5. 作用对象即该存储过程具体处理的对象，如 house ，user ， order等
6. 作用域命名方式：为所用到的表的头个字母的大写，如果用到多个表，则按照字典序将表头字母一一列出（表名首字母目前无冲突）
7. 伪语言均为 MySQL 语言语法
8. update 存储过程的存储按照软件设计说明书/功能模块设计中的页面设计存储。



| 更新日期 | 更新内容                           |
| -------- | ---------------------------------- |
| 5/23     |                                    |
| 5/29     | 更改存储的方式，添加了几个存储过程 |
|          |                                    |

初步计划按照用例模块设计来实现各存储过程

**update**: 

1. 按照软件设计书中功能模块设计的方法来设计存储过程
2. 因为文档交了，所以就简化写了，大概是这样的，第一行名字加上要的参数，第二行功能和状态码

###  登录页面：login

```mysql
allGetPasswordAndTypeByID(out status int, IN ID int)
```

功能：通过ID返回密码

返回值为 -1 表示帐号不存在，为1表示正常取出而且返回了密码



```mysql
allGetPasswordAndTypeByTel(out status int, IN Te int)
```

功能:通过电话返回密码

返回值为 -1 表示帐号不存在，为1表示正常取出而且返回了密码

这两个的调用情况可以用帐号长度来判断，我设置了username的长度是9位，而电话的长度是11位

### 注册页面：register

```mysql
serverInsertNewAccount(OUT status int, IN Passwor varchar(30), IN userTyp int,IN Nam varchar(30), IN Te char(11), IN Are varchar(30),     IN IdNumbe char(18), IN Provinc varchar(20),IN Cit varchar(20), IN emai varchar(64))
```

功能：插入帐号，并且在数据库内部判断电话是否重复了

返回-1表示电话重复，返回1表示正常插入

### 个人信息页面：info

```mysql
create procedure allGetSelfInfo(out status int ,in ID int)
```

功能：返回个人信息（返回了连密码在内的所有信息）

返回1表示 正常插入，目前没有其他返回值

```mysql
allUpdateSelfInfo(OUT status int, in ID int, IN Passwor varchar(30), IN Te char(11),IN Emai varchar(64), IN Nam varchar(30), IN Provinc varchar(20),IN Cit varchar(20), IN Are varchar(30))
```

功能：修改自身信息

返回1表示 正常插入，目前没有其他返回值断删除

### 用户管理页面：userManage

```mysql
procedure adminExportRepairer(OUT status int, IN Passwor varchar(30),IN Nam varchar(30), IN Te char(11), IN Are varchar(30),IN IdNumbe char(18), IN Provinc varchar(20),N Cit varchar(20))
```

功能 ：单个插入师傅信息，并返回帐号

返回1表示正常插入并且返回了帐号，返回-1表示该手机号已有人使用了

```mysql
adminSelectUserInfoByID (out status int, IN ID int)
```

功能：查询用户，并返回除了密码，身份证之外的信息

返回： 1 正常返回

​			 -1 查无此人

```mysql
adminSelectUserInfoByTel (out status int, IN Te  varchar(11))
```

功能：查询用户，并返回除了密码，身份证之外的信息

返回： 1 正常返回

​			 -1 查无此人

```mysql
 rootExportAdmin(OUT status int,IN Passwor varchar(30), IN Nam varchar(30),IN Te char(11), IN Are varchar(30), IN IdNumbe char(18),IN Provinc varchar(20), IN Cit varchar(20))
```

功能：root用户插入admin权限的账户

返回-1表示电话重复，返回1表示正常插入

（ps：这里是不是该做个鉴权机制啊？）

### 租客个人信息页面：renterInfo

```mysql
adminChangeUserInfo (OUT status int, in ID int, IN Te char(11),
IN Emai varchar(64), IN Nam varchar(30), IN Provinc varchar(20),IN Cit varchar(20), IN Are varchar(30))
```

功能：管理员修改用户信息，不能修改密码和主键

//todo 

客服确认租客缴纳租金
租客退回租金

（这两个没对线，我不知道到底是怎么个意思）

### 租房页面：rent

```mysql
userSearchHouseByKey(out status int ,in  KeyW varchar(64))
```

功能：根据关键词返回未租满的房子

我这里照的是main.js里面的 searchHouses 函数写的，不知道为啥不用上地址

这里不用的话感觉没地方用了啊

具体实现是在intro里面进行模糊搜索

### 房源管理页面：houseManage

```mysql
adminSearchHouseByKey(OUT status int, IN KeyW varchar(64))
```

功能：根据关键词返回所有房子

返回 1 表示正常

```mysql
adminImportNewHouse(out status int ,in provinc varchar(20),cit varchar(20),are varchar(30),addres varchar(50),rentTyp int,houseTyp int,pi varchar(50),intr text,ownerTe varchar(11), pric int)
```

功能：管理员导入单个新增房子

返回1表示正常

（这里应该不用做返回房间ID吧？）

### ,房源详情页面：houseDetail

```mysql
userApplyToRent (out status int ,in UID int ,in HID int,in rentTim date ,in contractDuratio int ,pric int ,commen text)
```

功能:用户提交租房申请（！！！！！   这里存的是申请租用的时长，而不是结束的日期！！！！！！！！    ！！！！！！！！！！）

而且这里会把目标房子的状态暂时设定为以租出（不然会出现很多奇怪的逻辑错误）

返回1表示成功申请

返回-1表示该房已满

```mysql
,adminChangeHouseInfo(out status int ,in HID int ,in provinc varchar (20),cit varchar(20),are varchar(30),addres varchar(50),rentTyp int ,houseTyp int ,pi varchar(50),intr text,ownerTe varchar(11),pric int ,houseStatu int)
```

功能：管理员修改房源信息（实现逻辑是只有主键不能修改）

```mysql
adminCloseHouse(out status int ,in HID int )
```

功能：暂停出租

 -- 返回1 说明正常关闭

 --返回-1 说明该房子已经租出了

```mysql
 adminOpenHouse(out status int ,in HID int )
```

功能：开启出租状态

返回1表示开启成功

返回-1表示该房子并未被关闭

### 订单查询页面：order

似乎你写了

### 订单管理页面：orderManage

```mysql
adminSearchOrderByUser(out status int ,in UID int )
```

功能：根据UID查询订单

返回-1表示这个用户没得订单

```mysql
adminSearchOrderByID(out status int ,in OID int )
```

功能：根据订单ID查询订单

返回-1表示订单不存在

### 申请详情页面：applicationDetail

```mysql
adminCheckRentApply(out status int ,in OID int ,OrderStatu int)
```

功能：处理申请（同意或者拒绝）

返回1表示成功拒绝

返回2表示成功同意

```mysql
serverGetHouseInfoByOrder(out status int ,in OID int )
```

功能：根据订单返回房子信息

```
serverGetUserInfoByOrder(out status int ,in OID int )
```

功能：根据订单返回用户信息

### 订单详情页面：orderDetail

感觉这里好像没我啥事i

### 报修页面：repair

```mysql
userCommitNewRepair(out status int ,in  usernam int,in houseI int ,in conten text,in pi text)
```

功能：用户提交报修

提交报修申请的人必须已经支付租金，并且提交的房屋号要存在对应的订单，否则返回-1

```mysql
 userCheckSelfAllRepair(out status int,in usernam int) 
```

功能：用户查看自己的所有保修

### 报修管理页面：repairManage

```mysql
adminSelectAllUnhandledRepair(out status int)
```

功能：选择所有没处理的报修

### 报修详情页面：repairDetail

```mysql
create procedure userCheckWorkerOrder(out status int,in usernam int)
```

功能：用户查询和自己有关的工单

```mysql
procedure adminGenerateWorkOrder(out status int, in adminID int,in RID int,in fixerID int)
```

功能：admin手动生成工单,并在repair表里面加上admin的主键

```mysql
adminChooseFixer(out status int)
```

功能：admin查看所有的师傅

```mysql
adminRefuseRepair(OUT status int, in adminI int,IN RID int)
```

功能：客服拒绝报修请求，并在repair表里面加上admin的主键

```mysql
userCommentRepair(out status int, in RID int, in commen text, in scor int)
```

功能：用户评价可以评价的（repair中status=2）的报修

返回：1,评价完毕

​			-1：目标订单无法评价或是评价出错。

### 维修任务页面：repairTask

```mysql
procedure fixerCheckSelfTask(out status int ,in UID int)
```

功能：维修师傅查看自己的工单。

### 工单详情页面：workorderDetail

```mysql
procedure fixerFinishWork(out status int, in workerOrderID int, callbac text)
```

-- func 将工单和保修设置为完成状态

--返回 -1 说明该工单已经终止或是正常结束

```mysql
fixerShowOrderInfo(out status int, in workerOrderID int)
```

--为fixer展示租客提交的报修房源编号、报修描述和报修图片,租客联系方式

```mysql
procedure userShowOrderInfo(out status int, in workerOrderID int)
```

--为用户展示，房源编号、报修描述和报修图片，维修师傅联系方式。

###  投诉页面：complaint

```mysql
userCheckSelfComplaintAll(out status int, in usernam int)
```

用户查看和自己有关的所有complaint

```mysql
userCommitNewComplaint(out status int, in usernam int ,  houseI int,  conten text, pi text)
```

用户提交新的投诉

### complaintManage

```mysql
adminCheckAllCom(out status int)
```

客服查看所有的投诉

### 投诉详情页面：complaintDetail

```mysql
 procedure adminReplyCom(out status int,in CID int,in con text,in adminI int)
```

客服回复投诉，并修改投诉的状态为已回复未评价

```mysql
procedure getUserTelByCom(out status int,in CID int)
```

通过complaint获得对应用户的电话，用于显示给管理员

```mysql
showAdminTelByCom (out status int,in CID int,in adminI int )
```

通过complaint获得对应管理员的电话，用于显示给用户

```mysql
userGiveEvaluation(OUT status int, IN CID int, IN con text, IN scor int)
```

用户对complaint进行评价并打分，并修改投诉状态。