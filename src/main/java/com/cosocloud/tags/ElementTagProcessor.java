package com.cosocloud.tags;

import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.IElementTagStructureHandler;

import org.unbescape.html.HtmlEscape;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElementTagProcessor extends AbstractElementTagProcessor {
    private static final String TAG_NAME = "element";
    private static final int PRECEDENCE = 10000;

    private static Logger logger = LoggerFactory.getLogger(ElementTagProcessor.class);
    
    public ElementTagProcessor(final String dialectPrefix) {
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
        //structureHandler.setBody("Hello, " + HtmlEscape.escapeHtml5(tag) + "!", false);
    }
}
