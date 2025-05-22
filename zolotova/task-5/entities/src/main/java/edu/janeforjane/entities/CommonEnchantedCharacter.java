package edu.janeforjane.entities;

import lombok.Data;

import java.util.List;

@Data
public class CommonEnchantedCharacter {

    private String id;
    private String name;
    private String shrekRole;
    private String personalityTraits;
    private String shrekStoryline;
    private String group;
    private String alignment;
    private String originStory;
    private List<String> specialAbilities;



    public String magic_toString() {
        return "★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★" +
                "\n\t\tname=" + name +
                "\n\t\tshrekRole=" + shrekRole +
                "\n\t\tpersonalityTraits=" + personalityTraits +
                "\n\t\tshrekStoryline=" + shrekStoryline +
                "\n\t\tgroup=" + group +
                "\n\t\talignment=" + alignment +
                "\n\t\toriginStory=" + originStory +
                "\n\t\tspecialAbilities=" + specialAbilities +
                "\n★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★";
    }
}
