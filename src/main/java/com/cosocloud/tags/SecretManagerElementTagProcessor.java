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
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecretManagerElementTagProcessor extends AbstractElementTagProcessor {
    private static final String TAG_NAME = "sm";
    private static final int PRECEDENCE = 2000;

    private static Logger logger = LoggerFactory.getLogger(SecretManagerElementTagProcessor.class);
    
    public SecretManagerElementTagProcessor(final String dialectPrefix) {
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
        final String secretName = (String)expression.execute(context);
        logger.debug(secretName);

        Region region = Region.US_EAST_1;
        SecretsManagerClient secretsClient = SecretsManagerClient.builder()
            .region(region)
            .credentialsProvider(ProfileCredentialsProvider.create("commercial"))
            .build();
        
        try {
            GetSecretValueRequest valueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

            GetSecretValueResponse valueResponse = secretsClient.getSecretValue(valueRequest);
            String secret = valueResponse.secretString();
            logger.debug(secret);
            structureHandler.replaceWith(secret, false);
        } catch (SecretsManagerException e) {
            logger.error(e.awsErrorDetails().errorMessage(), e);
        }
        secretsClient.close();
    }
}
