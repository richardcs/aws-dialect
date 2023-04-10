//package com.cosocloud;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;
//import org.thymeleaf.templatemode.TemplateMode;
//import org.thymeleaf.templateresolver.FileTemplateResolver;
//
//import com.cosocloud.dialect.TestDialect;
//
//public class ThymeLeafTest {
//    public static void main(String [] args) {
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
//        String output = templateEngine.process("hello", context);
//        System.out.println(output);
//    }
//}
