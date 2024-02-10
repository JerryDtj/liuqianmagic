# 项目说明
本项目是graalVM native-image的一个项目,主要是用于graalVM的练手.题材取自于刘谦的2024春晚魔术:守岁共此时
# 涉及技术点
## pom.xml修改
在build下添加如下内容:

```xml
<build>
    <plugins>
        <plugin>
            <configuration>
                <mainClass>com.jerry.Main</mainClass>
            </configuration>
            <groupId>org.graalvm.buildtools</groupId>
            <artifactId>native-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

## 打包清理命令

`mvn clean package -P graal`

## 打包命令

`mvn -Pnative native:compile`

### 注意点

在打包命令之前,还请先执行打包清理命令,否者会报错(笔者如此,至于为什么,暂时不知道.刚接触第一天,还不是很懂)
