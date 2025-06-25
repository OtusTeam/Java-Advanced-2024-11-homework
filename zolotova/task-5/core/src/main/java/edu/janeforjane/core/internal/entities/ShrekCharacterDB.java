package edu.janeforjane.core.internal.entities;

import lombok.Data;

import java.util.List;

@Data
public class ShrekCharacterDB {

    private String id;
    private String name;
    private String shrekRole;
    private String personalityTraits;
    private String shrekStoryline;
    private String group;
    private String alignment;
    private String originStory;
    private List<String> specialAbilities;
}
