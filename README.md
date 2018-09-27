# mybatis_generator_plugin
mybatis generator plugin,auto generator service interface and impl class


#### Usage：
first you need to checkout project,run 

`mvn:install`

then add dependency in the mybatis generator plugin in your project

```xml
<plugin>
        <groupId>org.mybatis.generator</groupId>
        <artifactId>mybatis-generator-maven-plugin</artifactId>
        <version>1.3.5</version>
        <configuration>
            <configurationFile>src/main/resources/generatorConfig.xml</configurationFile>
            <verbose>true</verbose>
            <overwrite>true</overwrite>
        </configuration>
        <dependencies>
            <dependency>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-core</artifactId>
                <version>1.3.5</version>
            </dependency>
          <dependency>
            <groupId>mybatis_generator_plugin</groupId>
            <artifactId>core</artifactId>
            <version>1.5-SNAPSHOT</version>
          </dependency>
        </dependencies>
    </plugin>
```
then add this plugin in the generator config file

```xml
<context id="XXX_mysql_tables" targetRuntime="MyBatis3Simple">
    <plugin type="com.mybatisPlugin.ServicePlugin" />
    ....
</context>
```