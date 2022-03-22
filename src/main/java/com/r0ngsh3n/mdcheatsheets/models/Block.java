package com.r0ngsh3n.mdcheatsheets.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Block {

    private String headLine;
    private List<String> rawText;
}
