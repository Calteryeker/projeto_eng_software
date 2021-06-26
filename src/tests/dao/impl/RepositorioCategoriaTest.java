package tests.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dao.impl.RepositorioCategoria;
import dao.impl.exceptions.CategoriaJaCadastradaException;
import model.Categoria;

public class RepositorioCategoriaTest {

    private RepositorioCategoria repo;
    private String path;
    private String categoryName;
    
    @BeforeEach
    public void setUp() {
        path = ".\\localstorage\\categorias.ser";
        repo = new RepositorioCategoria(path);
        categoryName = "Viagem para Ribeirão";
    }

    @Test
    @DisplayName("Create category should work")
    public void testCreateCategorySucceeds() throws CategoriaJaCadastradaException {
        Categoria newCategoria = repo.criarCategoria(categoryName);
        assertEquals(categoryName, newCategoria.getNome());
    }

    @Test
    @DisplayName("Create duplicate category should throw CategoriaJaCadastradaException")
    public void testCreateDuplicateCategoryFails() {
        Exception exception = assertThrows(CategoriaJaCadastradaException.class, () -> {
            repo.criarCategoria(categoryName);
            repo.criarCategoria(categoryName);
        });

        assertEquals("Categoria já Cadastrada", exception.getMessage());
    }

    @AfterEach
    public void tearDown() {
        File file = new File(path);
        if (file.isFile()) {
            file.delete();
        }
    }
}
