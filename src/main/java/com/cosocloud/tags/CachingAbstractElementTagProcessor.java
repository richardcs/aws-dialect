package com.cosocloud.tags;

import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.Map;
import java.util.HashMap;

public abstract class CachingAbstractElementTagProcessor extends AbstractElementTagProcessor {
    protected Map<String, String> cache;
    
    public CachingAbstractElementTagProcessor(TemplateMode templateMode, String dialectPrefix, String tagName, boolean prefix, String attributeName, boolean applyPrefix, int precedence) {
        super(templateMode, dialectPrefix, tagName, prefix, attributeName, applyPrefix, precedence);
        this.cache = new HashMap<String, String>();
    }
}
        
