package fudan.se.lab2.config;
import fudan.se.lab2.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    Logger logger = LoggerFactory.getLogger(FileService.class);
    /**
     * 资源映射配置
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
       logger.debug("start mapping");
        /**
         *
         *原理
        将所有/static/** 访问都映射到classpath:/static/ 目录下  访问：http://localhost:8080/static/c.jpg 能正常访问static目录下的c.jpg图片资源
         本地  ：                          映射                                         实际

      registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        云端
        registry.addResourceHandler("/**")
          .addResourceLocations("file:/usr/local/18302010059/static/")
       registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");*/
      //云端
        registry.addResourceHandler("/**")
                .addResourceLocations("file:/usr/local/18302010059/static/") ;

    }

}

