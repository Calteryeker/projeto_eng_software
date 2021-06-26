package tests.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import controllers.ControladorCategoria;
import dao.impl.exceptions.CategoriaJaCadastradaException;
import model.Categoria;

public class ControladorCategoriaTest {

    private ControladorCategoria controller;
    private String path = ".\\localstorage\\categorias.ser";
    
    @BeforeEach
    public void setUp() {
        controller = ControladorCategoria.getInstance();
    }

    @Test
    @DisplayName("Create category should work")
    public void testCreateCategorySucceeds() throws CategoriaJaCadastradaException {
        Categoria category = controller.criarCategoria("Viagens");
        assertEquals("Viagens", category.getNome());
    }

    @Test
    @DisplayName("Save duplicate category should fail")
    public void testSaveDuplicateCategoryFails() {
        Exception exception = assertThrows(CategoriaJaCadastradaException.class, () -> {
            controller.criarCategoria("Viagens");
            controller.criarCategoria("Viagens");
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
