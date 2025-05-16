package edu.janeforjane.service.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class MagicalIdGenerator {

    private static final String[] MAGICAL_PHRASES = {
            "abracadabra",
            "hocuspocus",
            "alakazam",
            "simsalabim",
            "open sesame",
            "bibbidi-bobbidi-boo",
            "shazam",
            "expelliarmus",
            "wingardium leviosa"
    };
    private static final Random RANDOM = new Random();
    private static final Logger log = LoggerFactory.getLogger(MagicalIdGenerator.class);
    private Set<String> existingIds = new HashSet<>();

    public String generateId() {

        try {
            String id = null;

            while (id == null) {
                String phrase = MAGICAL_PHRASES[RANDOM.nextInt(MAGICAL_PHRASES.length)];

                StringBuilder idBuilder = new StringBuilder();
                String[] words = phrase.split(" ");

                for (String word : words) {
                    while (word.length() > 3) {
                        int pieceSize = RANDOM.nextInt(2) + 2; // ~ 2-3
                        idBuilder.append(word, 0, pieceSize);
                        word = word.substring(pieceSize); // shorten word - throw used piece
                        idBuilder.append("-").append(RANDOM.nextInt(999) + 100);
                    }
                    idBuilder.append(word); // append the remaining part of the word
                }

                String newId = idBuilder.toString();

                if (!existingIds.contains(newId)) {
                    existingIds.add(newId);
                    id = newId;
                }
            }
            return id;
        } catch (Exception e) {
            log.error("Oops - magic is broken - cannot generate id!");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
