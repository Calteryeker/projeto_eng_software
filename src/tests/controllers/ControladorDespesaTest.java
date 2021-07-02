package tests.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import controllers.ControladorDespesa;
import dao.impl.exceptions.CategoriaNulaException;
import dao.impl.exceptions.DadosNaoPreenchidosException;
import dao.impl.exceptions.DespesaNaoEncontradaException;
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

    @Test
    @DisplayName("Edit expense should work")
    public void testEditExpense() throws DadosNaoPreenchidosException, DespesaNaoEncontradaException {
        Despesa expense = controller.criarDespesa("Viagem para Iputinga", 233d, date, category);
        Despesa altExpense = controller.alterarDespesa("Viagem para o DETRAN", expense.getOrdem(), expense.getValor(),
                expense.getData_criacao(), expense.getCategoria());
        assertEquals("Viagem para o DETRAN", altExpense.getNome());
    }

    @Test
    @DisplayName("Edit expense with an expense that doesn't exist")
    public void testEditWithNonExistentExpenseFails() throws DadosNaoPreenchidosException {
        Despesa expense = controller.criarDespesa("Viagem para Iputinga", 233d, date, category);
        Exception exception = assertThrows(DespesaNaoEncontradaException.class, () -> {
            controller.alterarDespesa("Viagem para o DETRAN", 12341234, expense.getValor(),
                expense.getData_criacao(), expense.getCategoria());
        });
        assertEquals("Despesa não encontrada!!", exception.getMessage());
    }

    @Test
    @DisplayName("Remove expense should work")
    public void testRevomeExpenseFails() throws DadosNaoPreenchidosException, DespesaNaoEncontradaException {
        Despesa expense = controller.criarDespesa("Viagem para Iputinga", 233d, date, category);
        Despesa delExpense = controller.removerDespesa(expense.getOrdem());
        assertThrows(DespesaNaoEncontradaException.class, () -> {
            controller.removerDespesa(delExpense.getOrdem());
        });
    }

    @Test
    @DisplayName("Show expenses per category should work")
    public void testShowExpensesPerCategorySucceeds() throws DadosNaoPreenchidosException, CategoriaNulaException {
        Despesa expense = controller.criarDespesa("Viagem para Iputinga", 233d, date, category);
        List<Despesa> expenses = controller.visualizarDespesasPorCategoria(category);
        assertEquals(expense.getNome(), expenses.get(0).getNome());
    }

    @Test
    @DisplayName("Show expenses per category with missing data should throw CategoriaNulaException")
    public void testShowExpensesWithMissingDataFails() {
        Exception exception = assertThrows(CategoriaNulaException.class, () -> {
            controller.visualizarDespesasPorCategoria(null);
        });

        assertEquals("Categoria Nula!!", exception.getMessage());
    }

    @AfterEach
    public void tearDown() {
        File file = new File(path);
        if (file.isFile()) {
            file.delete();
        }
    }
}
