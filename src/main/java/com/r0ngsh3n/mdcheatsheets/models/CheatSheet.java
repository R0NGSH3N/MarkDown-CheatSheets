package com.r0ngsh3n.mdcheatsheets.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheatSheet {

    private Block headline;
    private List<Block> blocks;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(headline.getHtml());
        blocks.stream().forEach(block -> sb.append(block.getHtml()));
        return sb.toString();
    }
}
