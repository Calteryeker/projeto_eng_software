package tests.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dao.impl.RepositorioMeta;
import model.Meta;

public class RepositorioMetaTest {
    
    private RepositorioMeta repo;
    private String path;
    private File metaFile;

    @BeforeEach
    public void setUp() {
        path = ".\\localstorage\\metas.ser";
        metaFile = new File(path);
        repo = new RepositorioMeta(path);
    }

    @Test
    @DisplayName("Create meta should work")
    public void testCreateMetaSucceeds() {
        Meta meta = repo.criarMeta(14d, "mock_description");
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
    @DisplayName("Create duplicate meta should throw ")
    public void testCreateDuplicateMetaFails() {

    }

    @AfterEach
    public void tearDown() {
        File file = new File(path);
        if (file.isFile()) {
            file.delete();
        }
    }
}
