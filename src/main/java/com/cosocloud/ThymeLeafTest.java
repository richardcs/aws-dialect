//package com.cosocloud;
//import org.springframework.boot.Banner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;
//import org.thymeleaf.templatemode.TemplateMode;
//import org.thymeleaf.templateresolver.FileTemplateResolver;
//
//import com.cosocloud.dialect.TestDialect;
//
//@SpringBootApplication
//public class ThymeLeafTest {
//    public static void main(String [] args) {
//    	ConfigurableApplicationContext ctx = new SpringApplicationBuilder(ThymeLeafTest.class).bannerMode(Banner.Mode.OFF).run(args);
//        TemplateEngine templateEngine = new TemplateEngine();
//        FileTemplateResolver fileTemplateResolver = new FileTemplateResolver();
//        fileTemplateResolver.setPrefix("./");
//        fileTemplateResolver.setSuffix(".txt");
//        fileTemplateResolver.setTemplateMode(TemplateMode.TEXT);
//        templateEngine.setTemplateResolver(fileTemplateResolver);
//        templateEngine.addDialect(new TestDialect());
//
//        Context context = new Context();
//        context.setVariable("name", System.getProperty("name"));
//        String output = templateEngine.process(args[0], context);
//        System.out.println(output);
//        SpringApplication.exit(ctx, ()-> 0);
//    }
//}
