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
import dao.impl.exceptions.CategoriaNaoEncontradaException;
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

    @Test
    @DisplayName("Edit category should work")
    public void testEditCategorySucceeds() throws CategoriaJaCadastradaException, CategoriaNaoEncontradaException {
        Categoria category = repo.criarCategoria(categoryName);
        Categoria altCategory = repo.editarCategoria("Viagem para o DETRAN", category.getIdCategoria());
        assertEquals("Viagem para o DETRAN", altCategory.getNome());
    }

    @Test
    @DisplayName("On edit category that doesn't exist CategoriaNaoEncontradaException is thrown")
    public void testEditNonExistentCategoryFails() {
        Exception exception = assertThrows(CategoriaNaoEncontradaException.class, () -> {
            repo.editarCategoria("Viagem para o DETRAN", 1341234);
        });

        assertEquals("Categoria não encontrada!!", exception.getMessage());
    }

    @Test
    @DisplayName("Remove category should work")
    public void testRemoveCategorySucceeds() throws CategoriaJaCadastradaException, CategoriaNaoEncontradaException {
        Categoria category = repo.criarCategoria(categoryName);
        repo.removerCategoria(category.getIdCategoria());

        assertThrows(CategoriaNaoEncontradaException.class, () -> {
            repo.removerCategoria(category.getIdCategoria());
        });
    }

    @AfterEach
    public void tearDown() {
        File file = new File(path);
        if (file.isFile()) {
            file.delete();
        }
    }
}
