package com.my.studio.springboot.controller;

import com.my.studio.springboot.property.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
// @RequestMapping("/c")
public class TestController {

    // 获取 application.peoperties 配置的参数
    @Value("${parameter.name}")
    private String pname;

    @Autowired
    private ApplicationProperties applicationProperties;

    /**
     * 标准URL，例：
     * RequestMapping("/hello") 或 @RequestMapping({"/hello", "/word"})
     * RequestMapping(value="/hello") 或 @RequestMapping(value={"/hello", "/word"})
     * @return
     */
    @RequestMapping(value = {"/hello", "/hi"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String hello() {
        return "Hello Spring Boot!" + " I`m " + pname + " and " + applicationProperties.toString();
    }

    /**
     * 占位符URL映射，结合@PathVariable("")注解将占位符中的值绑定到方法参数上
     * 如果占位符中的值是纯数字，可以根据自己的需求将方法参数类型设置为 Long、Integer、String
     * @param name
     * @param num
     * @return
     */
    @RequestMapping(value = {"/{name}/{num}/hello"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String something(@PathVariable("name") String name, @PathVariable("num") int num) {
        return "Hello " + name + " " + num;
    }

    /**
     * 通配符：? 匹配任何单字符；* 匹配任意数量的字符（含 0 个）；** 匹配任意数量的目录（含 0 个）
     * @return
     */
    @RequestMapping(value="/?/hello")
    public String ant1() {
        return "Hello ant1";
    }
    @RequestMapping(value="/*/hello")
    public String ant2() {
        return "Hello ant2";
    }
    @RequestMapping(value="/**/hello")
    public String ant3() {
        return "Hello ant3";
    }

    /**
     * path 与 value 作用相同
     * name 用于统一资源管理，生成地址用。类默认名称全部大写，方法默认名称方法名
     * headers 指定 request 中必须包含某些指定的 header 值，才能让该方法处理请求
     * params 指定 request 中必须包含某些参数值，才让该方法处理请求
     * consumes 指定处理请求的提交内容类型（Content-Type），例如application/json，text/html
     * produces 指定返回的内容类型，仅当 request 请求头中的（Accept）类型中包含该指定类型才返回
     * @return
     */
    @RequestMapping(value="/checkParam", name = "checkParams", params = "nx!=0", headers = "!hd")
    public String checkParams(@RequestParam("nx") String param) {
        return "Hello checkParams : " + param;
    }

}
