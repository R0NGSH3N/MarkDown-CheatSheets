package com.r0ngsh3n.mdcheatsheets.parsers.syntaxfilters;

import com.r0ngsh3n.mdcheatsheets.models.Block;

import java.util.List;

public abstract class SyntaxFilter {
    private SyntaxFilter nextFilter;

    public abstract List<Block> invoke(List<String> rawText);

    public SyntaxFilter(SyntaxFilter nextFilter){
        this.nextFilter = nextFilter;
    }


}
