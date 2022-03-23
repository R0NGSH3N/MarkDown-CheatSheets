package com.r0ngsh3n.mdcheatsheets.models;

import com.google.common.base.CharMatcher;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    public String relaceTable(List<String> tableText) {
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
                } else {
                    sb.append(replaceCodePart(code));
                    code = null;
                }
            } else if (line.startsWith("#")) {
                sb.append(replaceHeadLine(line));
            }else{
                sb.append(line);
            }
        }
        sb.append("</Block>");
        this.html = sb.toString();
    }

}
