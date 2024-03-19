package com.daocao;

import cn.hutool.log.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class DaoCaoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DaoCaoApplication.class,args);
        log.info("项目启动成功");
    }
}
