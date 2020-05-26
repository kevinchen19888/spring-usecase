# 手动创建springboot starter 
1,创建进行json处理的工具类,工具类提供了两种可选配置(基于Gson或fastjson)并将其配置于 JsonFormatAutoConfiguration
配置文件中;  
2,在resource下创建 META-INF 文件夹,并在其下创建spring.factories并将JsonFormatAutoConfiguration配置为
EnableAutoConfiguration的value值;  
3,springboot项目加载时会加载所有META-INF目录下spring.factories文件,并读取其中的配置,从而完成初始化;