package com.kevin.startercreate;

import com.kevin.startercreate.processor.FormatProcessor;

public class FormatTemplate {
    private FormatProcessor formatProcessor;

    public FormatTemplate(FormatProcessor formatProcessor) {
        this.formatProcessor = formatProcessor;
    }

    public <T> String doFormat(T obj) {
        return formatProcessor.format(obj);
    }
}
