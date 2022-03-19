package com.r0ngsh3n.mdcheatsheets.parsers;

import com.google.common.base.CharMatcher;

public class MarkDownParser {

    public String replaceText(String rawText){
        //first replace tab with 4 spaces
        rawText = CharMatcher.anyOf("\t").replaceFrom(rawText, "    ");
        return null;

    }

}
