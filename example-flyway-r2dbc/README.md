# FlywayR2dbc

flyway 是一个数据库迁移工具，但其需要使用 jdbc，而在 reactive 程序中，通常会使用 r2dbc.   
如需要同时使用需分别引入 jdbc 和 r2dbc 驱动; 为减少配置量，通过配置类解析 r2dbc 配置，并以此创建 Flyway Bean  

```shell
git clone https://github.com/fordes123/spring-boot-examples
cd flyway-r2dbc
docker-compose up -d
gradle :flyway-r2dbc:bootRun
```