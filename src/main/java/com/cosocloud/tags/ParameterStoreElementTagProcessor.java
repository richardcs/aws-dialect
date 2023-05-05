package com.cosocloud.tags;

import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.IElementTagStructureHandler;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.standard.expression.*;

import org.unbescape.html.HtmlEscape;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;
import software.amazon.awssdk.services.ssm.model.SsmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParameterStoreElementTagProcessor extends CachingAbstractElementTagProcessor {
    private static final String TAG_NAME = "ps";
    private static final int PRECEDENCE = 2000;

    private static Logger logger = LoggerFactory.getLogger(ParameterStoreElementTagProcessor.class);
    
    public ParameterStoreElementTagProcessor(final String dialectPrefix) {
        super(
            TemplateMode.TEXT, // This processor will apply only to TEXT mode
            dialectPrefix,     // Prefix to be applied to name for matching
            TAG_NAME,          // No tag name: match any tag name
            true,             // No prefix to be applied to tag name
            null,         // Name of the attribute that will be matched
            false,              // Apply dialect prefix to attribute name
            PRECEDENCE);        // Precedence (inside dialect's precedence)
        logger.debug(dialectPrefix);
    }

    @Override
    protected void doProcess(final ITemplateContext context, final IProcessableElementTag tag, final IElementTagStructureHandler structureHandler) {
        logger.debug(String.format("tag: %s", tag));

        final IEngineConfiguration configuration = context.getConfiguration();
        final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);
        final IStandardExpression expression = parser.parseExpression(context, tag.getAttributeValue("key"));
        final String paramName = (String)expression.execute(context);
        logger.debug(paramName);

        String cached = cache.get(paramName);
        if (cached != null) {
            logger.debug("got value from cache " + cached);
            structureHandler.replaceWith(cached, false);
        } else {
            Region region = Region.of(System.getenv("AWS_REGION"));
            SsmClient ssmClient = SsmClient.builder()
                .region(region)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();

            try {
                GetParameterRequest parameterRequest = GetParameterRequest.builder()
                    .name(paramName)
                    .withDecryption(true)
                    .build();

                GetParameterResponse parameterResponse = ssmClient.getParameter(parameterRequest);
                String parameter = parameterResponse.parameter().value();
                logger.debug("The parameter value is " + parameter);
                cache.put(paramName, parameter);
                structureHandler.replaceWith(parameter, false);
            } catch (SsmException e) {
                logger.error(e.getMessage(), e);
            }
            ssmClient.close();
        }
    }
}
