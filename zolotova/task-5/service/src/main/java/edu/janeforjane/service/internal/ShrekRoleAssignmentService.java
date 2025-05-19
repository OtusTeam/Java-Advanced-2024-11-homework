package edu.janeforjane.service.internal;

import edu.janeforjane.entities.CommonEnchantedCharacter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ShrekRoleAssignmentService {

//    private String shrekRole;
//    private String personalityTraits;
//    private String shrekStoryline;

    private static final String[] SHREK_ROLES = {
            "Main",
            "Supporting",
            "Background",
            "Scenery",
            "Cameo"
    };

    private static final String[] PERSONALITY_TRAITS = {
            "Brave",
            "Curious",
            "Kind-hearted",
            "Independent",
            "Loyal",
            "Optimistic",
            "Resourceful",
            "Stubborn",
            "Witty"
    };

    private static final String[] STORY_LINE = {
            "Eats all the pies at the fair.",
            "Dances awkwardly at the ball.",
            "Gets lost in the enchanted maze.",
            "Chases butterflies all day.",
            "Tells bad jokes to the Gingerbread Man.",
            "Plays hide-and-seek with Donkey.",
            "Naps under a talking tree.",
            "Fights Donkey's dragon-wife.",
            "Solves puzzles in the swamp.",
            "Helps Fiona in a rescue.",
            "Cooks magic potions for all.",
            "Sings in the enchanted forest.",
            "Tells tales by the campfire."
    };

    private static final Random RANDOM = new Random();
    private static final Logger log = LoggerFactory.getLogger(ShrekRoleAssignmentService.class);


    public CommonEnchantedCharacter assignCharacter(CommonEnchantedCharacter character){

        String role = SHREK_ROLES[RANDOM.nextInt(SHREK_ROLES.length)];
        String trait1 = PERSONALITY_TRAITS[RANDOM.nextInt(PERSONALITY_TRAITS.length)];
        String trait2 = PERSONALITY_TRAITS[RANDOM.nextInt(PERSONALITY_TRAITS.length)];
        String story = STORY_LINE[RANDOM.nextInt(STORY_LINE.length)];

        character.setShrekRole(role);
        character.setPersonalityTraits(trait1 + ", " + trait2);
        character.setShrekStoryline(story);

        log.info("Now {} has it's own role in Shrek: {}", character.getName(), character);

        return character;


    }
}
