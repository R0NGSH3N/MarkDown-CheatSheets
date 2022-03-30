package com.r0ngsh3n.mdcheatsheets.parsers;

import org.junit.jupiter.api.Test;
import org.apache.commons.io.IOUtils;

public class TestMarkDownParser {

    @Test
    public void testParse() throws Exception{
        MarkDownParser markDownParser = new MarkDownParser();
        String inputFile = "/testMarkDown.MD";
        String inputStr = IOUtils.toString(this.getClass().getResource("/testMarkDown.MD"),"UTF-8");
        markDownParser.parse(inputStr);

    }
}
