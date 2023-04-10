package com.cosocloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import com.cosocloud.dialect.AWSDialect;
import com.cosocloud.dialect.TestDialect;

@SpringBootApplication
public class AwsDialectApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(AwsDialectApplication.class, args);
		 TemplateEngine templateEngine = new TemplateEngine();
	        FileTemplateResolver fileTemplateResolver = new FileTemplateResolver();
	        // uncomment these to run in local
//	        fileTemplateResolver.setPrefix("./");
//	        fileTemplateResolver.setSuffix(".txt");
	        fileTemplateResolver.setTemplateMode(TemplateMode.TEXT);
	        templateEngine.setTemplateResolver(fileTemplateResolver);
	        templateEngine.addDialect(new AWSDialect());

	        Context context = new Context();
	        context.setVariable("name", System.getProperty("name"));
	        
	        for (int i = 0; i < args.length; i++) {
	        	String output = templateEngine.process(args[i], context);
	        	System.out.println("=============================");
		        System.out.println("Output for File:"+(i+1));
		        System.out.println("=============================");
	        	System.out.println(output);
	        	System.out.println("\n\n\n\n");
			}
	        
	        
	        SpringApplication.exit(ctx, ()-> 0);
	        }

}
