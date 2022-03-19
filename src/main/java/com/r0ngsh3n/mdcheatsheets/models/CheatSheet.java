package com.r0ngsh3n.mdcheatsheets.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheatSheet {

    private String headline;
    private List<Block> blocks;
}
