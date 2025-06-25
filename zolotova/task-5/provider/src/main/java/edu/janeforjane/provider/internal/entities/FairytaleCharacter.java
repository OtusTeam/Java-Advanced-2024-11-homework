package edu.janeforjane.provider.internal.entities;

import lombok.Data;

import java.util.List;

@Data
public class FairytaleCharacter {
    private String name;
    private String group;
    private String alignment;
    private String originStory;
    private List<String> specialAbilities;
}
