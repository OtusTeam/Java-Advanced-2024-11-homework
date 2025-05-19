package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MainNioTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private String parent;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        parent = getParentPath();
    }

    @Test
    public void ByteBufferTest() throws IOException {
        run("1\n" +
                        "5\n" +
                        "2\n" +
                        parent + "/1.txt\n" +
                        "4\n" +
                        "5\n",
                "Enter 1 (Set buffer size).\n" +
                        "Enter 2 (ByteBuffer:load file).\n" +
                        "Enter 3 (MappedByteBuffer:load file).\n" +
                        "Enter 4 (Read file).\n" +
                        "Enter any number for Exit.\n" +
                        "1\n" +
                        "Enter 1 (Set buffer size).\n" +
                        "Enter 2 (ByteBuffer:load file).\n" +
                        "Enter 3 (MappedByteBuffer:load file).\n" +
                        "Enter 4 (Read file).\n" +
                        "Enter any number for Exit.\n" +
                        "2\n" +
                        "Enter file path (ByteBuffer:load): Enter 1 (Set buffer size).\n" +
                        "Enter 2 (ByteBuffer:load file).\n" +
                        "Enter 3 (MappedByteBuffer:load file).\n" +
                        "Enter 4 (Read file).\n" +
                        "Enter any number for Exit.\n" +
                        "4\n" +
                        "123\n" +
                        "Enter 1 (Set buffer size).\n" +
                        "Enter 2 (ByteBuffer:load file).\n" +
                        "Enter 3 (MappedByteBuffer:load file).\n" +
                        "Enter 4 (Read file).\n" +
                        "Enter any number for Exit.\n" +
                        "5\n" +
                        "End program");
    }

    @Test
    public void MappedByteBufferImplTest() throws IOException {
        run("1\n" +
                        "5\n" +
                        "3\n" +
                        parent + "/1.txt\n" +
                        "4\n" +
                        "5\n",
                "Enter 1 (Set buffer size).\n" +
                        "Enter 2 (ByteBuffer:load file).\n" +
                        "Enter 3 (MappedByteBuffer:load file).\n" +
                        "Enter 4 (Read file).\n" +
                        "Enter any number for Exit.\n" +
                        "1\n" +
                        "Enter 1 (Set buffer size).\n" +
                        "Enter 2 (ByteBuffer:load file).\n" +
                        "Enter 3 (MappedByteBuffer:load file).\n" +
                        "Enter 4 (Read file).\n" +
                        "Enter any number for Exit.\n" +
                        "3\n" +
                        "Enter file path (MappedByteBuffer:load): Enter 1 (Set buffer size).\n" +
                        "Enter 2 (ByteBuffer:load file).\n" +
                        "Enter 3 (MappedByteBuffer:load file).\n" +
                        "Enter 4 (Read file).\n" +
                        "Enter any number for Exit.\n" +
                        "4\n" +
                        "123\n" +
                        "Enter 1 (Set buffer size).\n" +
                        "Enter 2 (ByteBuffer:load file).\n" +
                        "Enter 3 (MappedByteBuffer:load file).\n" +
                        "Enter 4 (Read file).\n" +
                        "Enter any number for Exit.\n" +
                        "5\n" +
                        "End program");
    }

    @Test
    public void ByteBufferWithIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () ->
        run("1\n" +
                        "5\n" +
                        "2\n" +
                        parent + "/2.txt\n" +
                        "4\n" +
                        "5\n",
                "Enter 1 (Set buffer size).\n" +
                        "Enter 2 (ByteBuffer:load file).\n" +
                        "Enter 3 (MappedByteBuffer:load file).\n" +
                        "Enter 4 (Read file).\n" +
                        "Enter any number for Exit.\n" +
                        "1\n" +
                        "Enter 1 (Set buffer size).\n" +
                        "Enter 2 (ByteBuffer:load file).\n" +
                        "Enter 3 (MappedByteBuffer:load file).\n" +
                        "Enter 4 (Read file).\n" +
                        "Enter any number for Exit.\n" +
                        "2\n" +
                        "Enter file path (ByteBuffer:load): Enter 1 (Set buffer size).\n" +
                        "Enter 2 (ByteBuffer:load file).\n" +
                        "Enter 3 (MappedByteBuffer:load file).\n" +
                        "Enter 4 (Read file).\n" +
                        "Enter any number for Exit.\n" +
                        "4\n" +
                        "123\n" +
                        "Enter 1 (Set buffer size).\n" +
                        "Enter 2 (ByteBuffer:load file).\n" +
                        "Enter 3 (MappedByteBuffer:load file).\n" +
                        "Enter 4 (Read file).\n" +
                        "Enter any number for Exit.\n" +
                        "5\n" +
                        "End program"));
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
        MainNio.main(null);
        String trim = outputStreamCaptor.toString().trim().replace("\r\n", "\n");
        Assertions.assertEquals(result, trim);
    }

}
