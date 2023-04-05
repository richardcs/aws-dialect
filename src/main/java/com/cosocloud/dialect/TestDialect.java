package com.cosocloud.dialect;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.templatemode.TemplateMode;

import com.cosocloud.tags.ElementTagProcessor;

import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.standard.processor.StandardXmlNsTagProcessor;

public class TestDialect extends AbstractProcessorDialect {

    private static final String DIALECT_NAME = "Test Dialect";

    public TestDialect() {
        // We will set this dialect the same "dialect processor" precedence as
        // the Standard Dialect, so that processor executions can interleave.
        super(DIALECT_NAME, "test", StandardDialect.PROCESSOR_PRECEDENCE);
    }

    public Set<IProcessor> getProcessors(final String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        processors.add(new ElementTagProcessor(dialectPrefix));
        return processors;
    }


}
