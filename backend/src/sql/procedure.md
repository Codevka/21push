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

返回1表示 正常插入，目前没有其他返回值