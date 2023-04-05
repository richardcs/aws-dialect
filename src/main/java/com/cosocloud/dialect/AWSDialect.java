package com.cosocloud.dialect;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.templatemode.TemplateMode;

import com.cosocloud.tags.ParameterStoreElementTagProcessor;
import com.cosocloud.tags.SecretManagerElementTagProcessor;

import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.standard.processor.StandardXmlNsTagProcessor;

public class AWSDialect extends AbstractProcessorDialect {

    private static final String DIALECT_NAME = "AWS Dialect";

    public AWSDialect() {
        // We will set this dialect the same "dialect processor" precedence as
        // the Standard Dialect, so that processor executions can interleave.
        super(DIALECT_NAME, "aws", 100);
    }

    /*
     * Two attribute processors are declared: 'classforposition' and
     * 'remarkforposition'. Also one element processor: the 'headlines'
     * tag.
     */
    public Set<IProcessor> getProcessors(final String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<IProcessor>();

        processors.add(new SecretManagerElementTagProcessor(dialectPrefix));
        processors.add(new ParameterStoreElementTagProcessor(dialectPrefix));
        // This will remove the xmlns:score attributes we might add for IDE validation
        //processors.add(new StandardXmlNsTagProcessor(TemplateMode.HTML, dialectPrefix));
        return processors;
    }


}
