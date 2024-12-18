package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MainCacheTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private String parent;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        parent = getParentPath();
    }

    @Test
    public void Set2FilesAngGetInCacheTest() throws IOException {
        run("1\n" + parent +
                        "\n" +
                        "2\n" +
                        "1.txt\n" +
                        "2\n" +
                        "2.txt\n" +
                        "2\n" +
                        "1.txt\n" +
                        "3\n",
                "Enter 1 (directory path).\n" +
                        "Enter 2 (get value by filename).\n" +
                        "Enter any number for Exit.\n" +
                        "1\n" +
                        "Enter 1 (directory path).\n" +
                        "Enter 2 (get value by filename).\n" +
                        "Enter any number for Exit.\n" +
                        "2\n" +
                        "f1\n" +
                        "Enter 1 (directory path).\n" +
                        "Enter 2 (get value by filename).\n" +
                        "Enter any number for Exit.\n" +
                        "2\n" +
                        "f2\n" +
                        "Enter 1 (directory path).\n" +
                        "Enter 2 (get value by filename).\n" +
                        "Enter any number for Exit.\n" +
                        "2\n" +
                        "f1\n" +
                        "Enter 1 (directory path).\n" +
                        "Enter 2 (get value by filename).\n" +
                        "Enter any number for Exit.\n" +
                        "3\n" +
                        "End program");
    }
    @Test
    public void TestNoSuchFileException() {
        assertThrows(NoSuchFileException.class, () ->
                run("1\n" + parent +
                                "\n" +
                                "2\n" +
                                "no.txt\n",
                        ""));
    }

    private String getParentPath() {
        String parent;
        try {
            parent = Paths.get(getClass().getClassLoader().getResource("1.txt").toURI()).getParent().toString();
        } catch (URISyntaxException e) {
                throw new RuntimeException(e);
        }
        return parent;
    }

    private void run(String si, String result) throws IOException {
        byte[] bytes = si.getBytes();
        ByteArrayInputStream in1 = new ByteArrayInputStream(bytes);
        System.setIn(in1);
        MainCache.main(null);
        String trim = outputStreamCaptor.toString().trim().replace("\r\n", "\n");
        Assertions.assertEquals(result, trim);
    }

}
