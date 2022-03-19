package com.r0ngsh3n.mdcheatsheets.parsers;

import com.google.common.base.CharMatcher;
import com.r0ngsh3n.mdcheatsheets.models.CheatSheet;
import com.r0ngsh3n.mdcheatsheets.utils.StringTools;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class MarkDownParser {

    public final String replaceText(String rawText){
        //first replace tab with 4 spaces
        rawText = CharMatcher.anyOf("\t").replaceFrom(rawText, "    ");
        return rawText;
    }

    public final CheatSheet parse(String rawText){
        //first line always Headline for the cheat sheet
        //if first line is not header line...we give default headline: Cheat Sheet
        List<String> rawTextList = StringTools.read2List(rawText);
        if(CollectionUtils.isEmpty(rawTextList)){
            return null;
        }

        CheatSheet cheatSheet = new CheatSheet();
        if(CharMatcher.is('#').matchesAnyOf(rawTextList.get(0))){
            cheatSheet.setHeadline(rawTextList.get(0));
            rawTextList.remove(0);
        }





        return null;

    }

}
