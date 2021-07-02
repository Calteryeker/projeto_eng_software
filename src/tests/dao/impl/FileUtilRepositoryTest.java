package tests.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dao.impl.FileUtilRepository;
import model.Usuario;

public class FileUtilRepositoryTest {

    private List<Usuario> users = new ArrayList<>();
    private String filename;

    @BeforeEach
    public void setUp() {
        users.add(new Usuario("Mateus", "mateus", "mateus_pass"));
        filename = ".\\localstorage\\usuarios.ser";
    }

    @Test
    @DisplayName("Save a file with object instance and file name should work")
    public void testSaveFileWithInstanceAndFileName() {
        FileUtilRepository.saveFile(users, filename);
        File file = new File(filename);
        assertTrue(file.isFile());
    }

    @Test
    @DisplayName("Read a file with file name should work")
    public void testReadFileWithFileName() {
        FileUtilRepository.saveFile(users, filename);
        Object localInstance = FileUtilRepository.readFile(filename);
        assertNotNull(localInstance);
    }

    @AfterEach
    public void tearDown() {
        File file = new File(".\\localstorage\\usuarios.ser");
        if (file.isFile()) {
            file.delete();
        }
    }
}
