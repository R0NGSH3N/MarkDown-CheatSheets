package com.r0ngsh3n.mdcheatsheets.models;

import com.google.common.base.CharMatcher;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class Block {

    private String headLine;
    private List<String> rawText;
    private String html;

    private String replaceCodePart(List<String> codeText) {
        StringBuilder sb = new StringBuilder("<pre><code>");
        for(String line: codeText){
            line = line.replaceAll("<", "&lt;");
            line = line.replaceAll(">", "&gt;");
            if (line.endsWith("\n")) {
                line = line.substring(0, line.length() - "\n".length());
            }
            sb.append(line);
        }
        sb.append("</code></pre>");

        return sb.toString();
    }

    private String replaceHeadLine(String headLine) {
        int level = CharMatcher.is('#').countIn(headLine);
        return String.format("<h%d>%s</h%d>", level, headLine, level);
    }

    private String replaceQuote(String quoted) {
        return null;
    }

    public String replaceTable(List<String> tableText) {
        return null;
    }

    public String replaceList(List<String> list) {
        return null;
    }

    public void transform() {
        StringBuilder sb = new StringBuilder("<Block>");
        List<String> code = null;
        for (String line : rawText) {
            //replace code part
            if (line.startsWith(MDTokens.CODE)) {
                if (code == null) {
                    code = new ArrayList<>();
                    code.add(line);
                } else
{
                    sb.append(replaceCodePart(code));
                    code = null;
                }
            } else if (line.startsWith("#")) {
                sb.append(replaceHeadLine(line));
            }else{
                sb.append(replace(line));
            }
        }
        sb.append("</Block>");
        this.html = sb.toString();
    }

    private String replaceFirst(String line, String regex, String htmlTag, Boolean openOrClose){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        String htmlElement = openOrClose? "<" + htmlTag + ">" : "</" + htmlTag + ">";
        return matcher.replaceFirst(htmlElement);
    }

    private String replace(String line){
        boolean isBoldWord1Found = false;
        boolean isBoldWord2Found = false;
        boolean isItalicWord1Found = false;
        boolean isItalicWord2Found = false;
        boolean isCodeWordFound = false;
        while(line.contains(MDTokens.BOLD_WORD1) || line.contains(MDTokens.BOLD_WORD2) || line.contains(MDTokens.ITALIC_WORD)
        || line.contains(MDTokens.ITALIC_WORD_2) || line.contains(MDTokens.CODE_WORD) || line.contains(MDTokens.STRIKE_WORD)){
            if(!isBoldWord1Found){
                line = line.replaceFirst( "\\" + MDTokens.BOLD_WORD1, "<strong>");
                isBoldWord1Found = true;
            }else{
                line = line.replaceFirst("\\" +MDTokens.BOLD_WORD1, "</strong>");
                isBoldWord1Found = false;
            }
            if(!isBoldWord2Found){
                line = line.replaceFirst(MDTokens.BOLD_WORD2, "<strong>");
                isBoldWord2Found = true;
            }else{
                line = line.replaceFirst(MDTokens.BOLD_WORD2, "</strong>");
                isBoldWord2Found = false;
            }
            if(!isItalicWord1Found){
                line = line.replaceFirst(MDTokens.ITALIC_WORD + "?", "<em>");
                isItalicWord1Found = true;
            }else{
                line = line.replaceFirst(MDTokens.ITALIC_WORD + "?", "</em>");
                isItalicWord1Found = false;
            }
            if(!isItalicWord2Found){
                line = line.replaceFirst("\\" +MDTokens.ITALIC_WORD_2, "<em>");
                isItalicWord2Found = true;
            }else{
                line = line.replaceFirst("\\" +MDTokens.ITALIC_WORD_2, "</em>");
                isItalicWord2Found = false;
            }

            if(!isCodeWordFound){
                line = line.replaceFirst("\\" +MDTokens.CODE_WORD, "<code>");
            }else{
                line = line.replaceFirst("\\" +MDTokens.CODE_WORD, "</code>");
            }
        }

        return line;
    }


}
