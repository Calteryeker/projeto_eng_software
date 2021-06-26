package tests.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import controllers.ControladorDespesa;
import dao.impl.exceptions.DadosNaoPreenchidosException;
import model.Categoria;
import model.Despesa;

public class ControladorDespesaTest {

    private ControladorDespesa controller;
    private String path = ".\\localstorage\\despesas.ser";
    private LocalDate date = LocalDate.now();
    private Categoria category;

    @BeforeEach
    public void setUp() {
        controller = ControladorDespesa.getInstance();
        category = new Categoria("Viagens");
    }

    @Test
    @DisplayName("Create expense should work")
    public void testCreateExpenseSucceeds() throws DadosNaoPreenchidosException {
        Despesa expense = controller.criarDespesa("Viagem para Iputinga", 233d, date, category);
        assertEquals("Viagem para Iputinga", expense.getNome());
        assertEquals(233d, expense.getValor());
        assertEquals(date, expense.getData_criacao());
        assertEquals(category, expense.getCategoria());
    }

    @Test
    @DisplayName("Create expense with missing data should throw DadosNaoPreenchidosException")
    public void testCreateExpenseWithMissingDataFails() {
        Exception exception = assertThrows(DadosNaoPreenchidosException.class, () -> {
            controller.criarDespesa("Viagem para Iputinga", 233d, date, null);
        });

        assertThrows(DadosNaoPreenchidosException.class, () -> {
            controller.criarDespesa("Viagem para Iputinga", 233d, null, category);
        });

        assertThrows(DadosNaoPreenchidosException.class, () -> {
            controller.criarDespesa("Viagem para Iputinga", 0d, date, category);
        });

        assertThrows(DadosNaoPreenchidosException.class, () -> {
            controller.criarDespesa(null, 233d, date, category);
        });

        assertEquals("Os dados não foram preenchidos corretamente", exception.getMessage());
    }

    @AfterEach
    public void tearDown() {
        File file = new File(path);
        if (file.isFile()) {
            file.delete();
        }
    }
}
