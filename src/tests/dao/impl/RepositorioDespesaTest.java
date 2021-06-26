package tests.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dao.impl.FileUtilRepository;
import dao.impl.RepositorioDespesa;
import dao.impl.exceptions.DespesaNaoEncontradaException;
import model.Categoria;
import model.Despesa;

public class RepositorioDespesaTest {

    private Despesa expense;
    private RepositorioDespesa expenseRepo;
    private String path;
    private File expenseFile;
    private LocalDate localDate = LocalDate.now();
    private Categoria category;

    @BeforeEach
    public void setUp() throws IOException {
        path = ".\\localstorage\\despesas.ser";
        expenseRepo = new RepositorioDespesa(path);
        category = new Categoria("Viagens");
        expense = new Despesa("Viagem para recife", 245d, localDate, category);
        expenseFile = new File(path);
    }

    @Test
    @DisplayName("Save expense in file should work")
    public void testCreateExpense() {
        expenseRepo.criarDespesa("Viagem para recife", 245d, localDate, category);
        assertTrue(expenseFile.isFile() && expenseFile.exists());
        List<Despesa> expenses = (List<Despesa>) FileUtilRepository.readFile(path);
        String name = expenses.get(0).getNome();
        Double cost = expenses.get(0).getValor();
        LocalDate date = expenses.get(0).getData_criacao();
        Categoria categoria = expenses.get(0).getCategoria();
        assertEquals(name, expense.getNome());
        assertEquals(cost, (Double) expense.getValor());
        assertEquals(date, expense.getData_criacao());
        assertEquals(categoria, expense.getCategoria());
    }

    @Test
    @DisplayName("Edit expense should work")
    public void testEditExpense() throws DespesaNaoEncontradaException {
        expense = expenseRepo.criarDespesa("Viagem para recife", 245d, localDate, category);
        Despesa alteredExpense = expenseRepo.editarDespesa("Viagem para Iputinga", expense.getOrdem(),
                expense.getValor(), expense.getData_criacao(), expense.getCategoria());
        assertNotNull(alteredExpense);
        assertEquals("Viagem para Iputinga", alteredExpense.getNome());
    }

    @Test
    @DisplayName("Edit expense that doesn't exist should throw DespesaNaoEncontradaException")
    public void test() {
        Exception exception = assertThrows(DespesaNaoEncontradaException.class, () -> {
            expenseRepo.editarDespesa("Viagem para Iputinga", 123412333, expense.getValor(), expense.getData_criacao(),
                    expense.getCategoria());
        });
        assertEquals("Despesa não encontrada!!", exception.getMessage());
    }

    @Test
    @DisplayName("On delete expense that doesn't exist it should be thrown DespesaNaoEncontradaException")
    public void testDeleteNonExistentExpenseFails() {
        assertThrows(DespesaNaoEncontradaException.class, () -> {
            expenseRepo.removerDespesa(12341234);
        });
    }

    @Test
    @DisplayName("On delete expense it shouldn't be in local file anymore")
    public void testDeleteExpense() throws DespesaNaoEncontradaException {
        expense = expenseRepo.criarDespesa("Viagem para recife", 245d, localDate, category);
        Despesa delExpense = expenseRepo.removerDespesa(expense.getOrdem());
        assertThrows(DespesaNaoEncontradaException.class, () -> {
            expenseRepo.removerDespesa(delExpense.getOrdem());
        });
    }

    @Test
    @DisplayName("Show expenses per category should work")
    public void testShowExpensesPerCategory() {
        expense = expenseRepo.criarDespesa("Viagem para recife", 245d, localDate, category);
        List<Despesa> list = expenseRepo.visualizarDespesasPorCategoria(category);
        assertNotNull(list);
        assertEquals(expense, list.get(0));
    }

    @AfterEach
    public void tearDown() {
        File file = new File(".\\localstorage\\despesas.ser");
        if (file.isFile()) {
            file.delete();
        }
    }

}
