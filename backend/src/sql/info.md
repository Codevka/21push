# 表以及存储过程以及视图介绍

| 日期 | 更新号 | 更新内容                   |
| ---- | ------ | -------------------------- |
| 5/22 | 001    | 初步建立表，没有增加约束   |
| 5/23 | 002    | 完成 tables 信息的初步更新 |
| 5/28 | 003    | 修改了表的数据             |
|      |        |                            |
|      |        |                            |
|      |        |                            |



### 表关系图

### ![](./dgm.png)tables



| 表名      | 中文名 |
| --------- | ------ |
| Account   | 帐号   |
| Repair    | 报修   |
| House     | 房源   |
| Order     | 订单   |
| Complaint | 投诉   |
| WorkOrder | 工单   |



### Account

| 序号 | 英文字段名 | 中文字段名 | 数据类型    | 是否允许为空 | 主键/外键 |
| ---- | ---------- | ---------- | ----------- | ------------ | --------- |
| 1    | username   | 用户名     | int         | 否           | 主键      |
| 2    | password   | 密码       | varchar(30) | 否           | /         |
| 3    | userType   | 权限       | int         | 否           | /         |
| 4    | name       | 用户昵称   | varchar(30) | 否           | /         |
| 5    | tel        | 电话       | char(11)    | 是           | /         |
| 6    | email      | 邮箱       | varchar(64) | 是           | /         |
| 7    | provicnce  | 省         | varchar(20) | 否           | /         |
| 8    | city       | 市         | varchar(20) | 否           | /         |
| 9    | area       | 地区       | varchar(64) | 否           | /         |
| 10   | Idnumber   | 身份证     | char(18)    | 是           | /         |

| authority | 权限  |
| --------- | ----- |
| 1         | admin |
| 2         | user  |
| 3         | fixer |



### House	

| 序号 | 英文字段名  | 中文字段名 | 数据类型    | 是否允许为空 | 主键/外键 |
| ---- | ----------- | ---------- | ----------- | ------------ | --------- |
| 1    | houseId     | 房间编号   | int         | 否           | 主键      |
| 2    | province    | 省         | varchar(20) | 否           | /         |
| 3    | city        | 市         | varchar(20) | 否           | /         |
| 4    | area        | 地区       | varchar(30) | 否           | /         |
| 5    | address     | 具体地址   | varchar(50) | 否           | /         |
| 7    | rentType    | 租房形式   | int         | 否           | /8        |
| 8    | houseType   | 房间类型   | int         | 否           | /         |
| 9    | pic         | 图片       | varchar(50) | 是           | /         |
| 10   | intro       | 介绍       | text        | 是           | /         |
| 11   | ownerTel    | 户主电话   | varchar(11) | 否           | /         |
| 12   | price       | 价格       | int         | 否           | /         |
| 13   | houseStatus | 房源状态   | int         | 否           | /         |

| rentType | 类型     |
| -------- | -------- |
| 1        | 长租     |
| 2        | 短租     |
| 3        | 长短都可 |

| houseType | 类型   |
| --------- | ------ |
| 1         | 单人间 |
| 2         | 双人间 |
| 4         | 四人间 |

| Status                                                   | 租用状态 |
| -------------------------------------------------------- | -------- |
| -1                                                       | 已租出   |
| 这里还没定，目前想法是int值为几就是空着几个，但不能大于4 |          |
|                                                          |          |



### Orders

| 序号 | 英文字段名        | 中文字段名   | 数据类型 | 是否允许为空 | 主键/外键 |
| ---- | ----------------- | ------------ | -------- | ------------ | --------- |
| 1    | contractId        | 订单号       | int      | 否           | 主键      |
| 2    | username          | 用户名       | int      | 否           | 外键      |
| 3    | houseId           | 房源编号     | int      | 否           | 外键      |
| 4    | rentTime          | 租房时间     | date     | 否           | /         |
| 5    | orderGenerateTime | 订单生成时间 | date     | 否           | /         |
| 6    | contractDuration  | 订单持续时间 | int      | 否           | /         |
| 7    | status            | 订单状态     | int      | 否           | /         |
| 8    | price             | 订单价格     | int      | 否           | /         |
| 9    | comment           | 备注         | text     | 是           | /         |

| orderStatus | 状态create view adminShowUserInfoA<br/>as<br/>    select a.UID,a.location,a.telephone<br/>    from Account a where authority=2 |
| ----------- | ------------------------------------------------------------ |
| 0           | 申请但未处理                                                 |
| 1           | 生效中                                                       |
| -1          | 订单取消                                                     |
| -2          | 保留异常号                                                   |



### Complaint

| 序号 | 英文字段名    | 中文字段名 | 数据类型    | 是否允许为空 | 主键/外键 |
| ---- | ------------- | ---------- | ----------- | ------------ | --------- |
| 1    | complaintId   | 投诉编号   | int         | 否           | 主键      |
| 2    | username      | 用户名     | int         | 否           | 外键      |
| 3    | houseId       | 房源编号   | int         | 否           | 外键      |
| 4    | dealingStatus | 处理状态   | int         | 否           | /         |
| 5    | content       | 报修内容   | text        | 否           | /         |
| 6    | pic           | 图片       | varchar(50) | 否           | /         |
| 7    | reply         | 回复       | text        | 是           | /         |
| 8    | evaluation    | 评价       | text        | 是           | /         |
| 9    | score         | 打分       | int         | 是           | /         |
| 10   | adminId       | 客服用户名 | int         | 否           | 外键      |

 

| dealingStatus | 状态         |
| ------------- | ------------ |
| 0             | 申请但未处理 |
| 1             | 请求接受     |
| -1            | 请求驳回     |



### Repair

| 序号 | 英文字段名 | 中文字段名 | 数据类型    | 是否允许为空 | 主键/外键 |
| ---- | ---------- | ---------- | ----------- | ------------ | --------- |
| 1    | repairId   | 报修编号   | int         | 否           | 主键      |
| 2    | username   | 用户名     | int         | 否           | 外键      |
| 3    | houseId    | 房源编号   | int         | 否           | 外键      |
| 4    | status     | 处理状态   | int         | 否           | /         |
| 5    | content    | 报修内容   | text        | 否           | /         |
| 6    | pic        | 图片       | varchar(50) | 是           | /         |
| 7    | adminId    | 客服用户名 | int         | 是           | 外键      |
| 8    | evaluation | 评价       | text        | 是           | /         |
| 9    | score      | 打分       | int         | 否           | /         |

| dealingStatus | 状态         |
| ------------- | ------------ |
| 0             | 报修未被处理 |
| 1             | 报修被接受   |
| 2             | 修理完毕     |
| -1            | 报修被拒绝   |



### WorkOrder

| 序号 | 英文字段名   | 中文字段名 | 数据类型 | 是否允许为空 | 主键/外键 |
| ---- | ------------ | ---------- | -------- | ------------ | --------- |
| 1    | repairWorkId | 编号       | int      | 否           | 主键      |
| 2    | repairId     | 保修编号   | int      | 否           | 外键      |
| 3    | username     | 师傅用户名 | int      | 否           | 外键      |
| 4    | status       | 工单状态   | int      | 否           | /         |
| 5    | callback     | 维修反馈   | text     | 是           | /         |

| dealingStatus | 状态           |
| ------------- | -------------- |
| 0             | 工单未结束     |
| 1             | 工单正常结束   |
| -1            | 工单因异常终止 |

## 视图设计

### 视图命名规则

1. 大小写规则按照小驼峰规则
2. 每个视图的具体命名顺序为 <权限><作用><作用对象><作用域>
3. 权限即不同的操作人员 **理论上** 能否调用该视图，分为 admin, user, fixer，all, server (server为业务逻辑代码范围定义内) 四个权限
4. 方法即该视图的功能描述
5. 作用对象即该视图具体处理的对象，如 house ，user ， order等
6. 作用域命名方式：为所用到的表的头个字母的大写，如果用到多个表，则按照字典序将表头字母一一列出（表名首字母目前无冲突）update 由于表名更新,该条规则作废
7. 伪语言均为 MySQL 语言语法

(1)  adminShowHouseInfoH

功能:为客服展示所有房屋信息

脚本：

```mysql
create view adminShowHouseInfoH
as
    select  h.HID, location, datiledLocation, rentType, houseType, picture, introduction, ownerPhone, price, status   from House h

```

（2）adminShowUserInfoA

功能:为客服展示用户的非隐私信息

脚本:

```mysql
create view adminShowUserInfoA
as
    select a.UID,a.username,a.location,a.telephone
    from Account a where authority=2 (上传文档这里可以写成'user')
```

（3）adminShowFixerInfo

功能:为客服展示师傅的非隐私信息

脚本:

```mysql
create view adminShowFixerInfoA
as
    select a.UID,a.username,a.location,a.telephone
    from Account a where authority=3 (上传文档这里可以写成'fixer')
```

（4） adminShowRentableHouse

功能: 为客服展示所有未租完的房源

脚本:

```mysql
create view adminShowRentableHouseH
as
    select *from House h where h.status<>-1(可以改'rented')
```

（5）

功能:

脚本:

（）

功能:

脚本:

## 

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



| 更新日期 | 更新内容 |
| -------- | -------- |
| 5/23     |          |
|          |          |
|          |          |

初步计划按照用例模块设计来实现各存储过程

其中

#### user

（1） userQuerySelfHouseHD()

功能描述：根据特定用户查询该用户已租赁的房间信息

入口参数：UID

出口参数：指定房间的信息

伪语言实现：

```mysql
create procedure userQuerySelfHouseHO(IN UID int)
begin
    select h.HID,h.location,h.datiledLocation,h.rentType,h.houseType,h.price,h.status
    from House h,
         Orders o
    where o.HID =h.HID
      and o.UID = UID;
end;
```

（2）userQueryRentableHouse

功能描述：根据特定用户查看其可以租赁的房屋列表

入口参数：UID

出口参数：指定房间的信息

伪语言实现：

```mysql
create procedure userQueryRentableHouse (in UID int)
begin
    select *  from House h where h.location=(select  location from Account where Account.UID=UID);
end;
```

（）

功能描述：

入口参数：

出口参数：

伪语言实现：  definer = root@localhost

（）

功能描述：

入口参数：

出口参数：

#### admin

伪语言实现：adminRecordHouse()

功能描述：客服录入房源

入口参数：location，detailedLocation，rentType，houseType，picture，introduction,ownerPhone,price,status

出口参数：无

```mysql
create procedure adminRecordHouse(IN p_location varchar(30), IN p_detailedLocation varchar(50),
                                                        IN p_rentType int, IN p_houseType int, IN p_picture varchar(50),
                                                        IN p_introduction text, IN p_ownerPhone varchar(11),
                                                        IN p_price int)
begin
    insert into House(location, datiledLocation, rentType, houseType, picture, introduction, ownerPhone, price, status) values(p_location,p_detailedLocation,p_rentType,p_houseType,p_picture,p_introduction,p_ownerPhone,p_price,0);
end;


```



#### all

伪语言实现：allCheckSelfInfoA（）

功能描述：供给所有用户查询自己个人信息

入口参数：int UID

出口参数：指定用户信息

```mysql
create procedure allCheckSelfInfoA(IN UID int)
begin
    select *from   Account a where a.UID=@UID;
end;


```



#### fixer

伪语言实现：（）

功能描述：

入口参数：

出口参数：

#### server

伪语言实现： serverGenerateMessageAFMORW()

功能描述：爷还没写，等开会

入口参数：in  UID int,in messageType varchar(20),in tID int

出口参数：

伪语言实现：