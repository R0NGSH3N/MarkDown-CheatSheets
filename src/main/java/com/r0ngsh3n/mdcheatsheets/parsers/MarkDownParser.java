package com.r0ngsh3n.mdcheatsheets.parsers;

import com.google.common.base.CharMatcher;
import com.r0ngsh3n.mdcheatsheets.models.Block;
import com.r0ngsh3n.mdcheatsheets.models.CheatSheet;
import com.r0ngsh3n.mdcheatsheets.models.MDTokens;
import com.r0ngsh3n.mdcheatsheets.utils.StringTools;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MarkDownParser {

    public final String replaceText(String rawText){
        //first replace tab with 4 spaces
        rawText = CharMatcher.anyOf("\t").replaceFrom(rawText, "    ");
        return rawText;
    }

    private Block createBlock(List<String> rawText){
        Block block = new Block();
        block.setRawText(new ArrayList<>(rawText));
        return block;
    }

    private List<Block> parseBlocks(List<String> rawText){
        List<String> blockText = null;
        List<Block> blocks = new ArrayList<>();
        for(String line: rawText){
            if(CharMatcher.anyOf(MDTokens.BLOCK_HEADLINE).matchesAnyOf(line)){
                if(blockText == null){
                    blockText = new ArrayList<>();
                    blockText.add(line);
                }else{
                    blocks.add(createBlock(blockText));
                    blockText = null;
                }
            }else{
                if(blockText != null){
                    blockText.add(line);
                }
            }
        }

        if(blockText != null){
            blocks.add(createBlock(blockText));
        }

        return blocks;
    }

    public final CheatSheet parse(String rawText){
        List<String> rawTextList = StringTools.read2List(rawText);
        if(CollectionUtils.isEmpty(rawTextList)){
            return null;
        }

        //first line always Headline for the cheat sheet
        //if first line is not header line...we give default headline: Cheat Sheet
        CheatSheet cheatSheet = new CheatSheet();
        boolean headLineFound = false;
        for(Iterator<String> iterator = rawTextList.iterator(); iterator.hasNext();){
            String line = iterator.next();
            if(CharMatcher.is(MDTokens.CHEAT_SHEET_HEADLINE).matchesAnyOf(line)) {
                cheatSheet.setHeadline(rawTextList.get(0));
                iterator.remove();
                headLineFound = true;
                break;
            }else{
                if(line.isBlank()){
                    iterator.remove();
                }else{
                    break;
                }
            }
        }
        if(!headLineFound){
            cheatSheet.setHeadline("#CHEAT SHEET");
        }

        List<Block> blocks = parseBlocks(rawTextList);
        blocks.parallelStream().forEach(Block::transform);
        cheatSheet.setBlocks(blocks);

        return cheatSheet;

    }

}
