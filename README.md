# wed-redis-parent

### 1.0.0.4新增功能
- 新增redis机器监控API


### 1.0.0.3新增功能
- 引入swallow，添加清理内存缓存功能


### 1.0.0.2新增功能
- 添加对hashmap类型的批量操作的接口


### 1.0.0.1新增功能
- 对key操作后台管理


### 1.0.0.0版本api提供如下功能
- 对key进行统一管理，未在系统中录入的key无法使用
- 对redis各种数据类型（string，set，hashmap，sortedset）提供基本的接口支持
- 容灾考虑，redis机器配置化，随时可以切换机器