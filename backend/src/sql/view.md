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