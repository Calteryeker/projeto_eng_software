package tests.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dao.impl.RepositorioMeta;
import dao.impl.exceptions.MetaJaCadastradaException;
import model.Meta;

public class RepositorioMetaTest {

    private RepositorioMeta repo;
    private String path;
    private File metaFile;
    private LocalDate date;

    @BeforeEach
    public void setUp() {
        path = ".\\localstorage\\metas.ser";
        metaFile = new File(path);
        repo = new RepositorioMeta(path);
        date = LocalDate.now();
    }

    @Test
    @DisplayName("Create meta should work")
    public void testCreateMetaSucceeds() throws MetaJaCadastradaException {
        Meta meta = repo.criarMeta(14d, "mock_description", date);
        Meta otherMeta = null;
        assertTrue(metaFile.isFile() && metaFile.exists());
        List<Meta> metas = repo.getMetas();
        for (Meta m : metas) {
            if (m.equals(meta)) {
                otherMeta = m;
            }
        }
        assertNotNull(otherMeta);
    }

    @Test
    @DisplayName("Create duplicate meta should throw MetaJaCadastradaException")
    public void testCreateDuplicateMetaFails() {
        Exception exception = assertThrows(MetaJaCadastradaException.class, () -> {
            repo.criarMeta(14d, "mock_description", date);
            repo.criarMeta(14d, "mock_description", date);
        });

        assertEquals("Meta já Cadastrada", exception.getMessage());
    }

    @AfterEach
    public void tearDown() {
        File file = new File(path);
        if (file.isFile()) {
            file.delete();
        }
    }
}
