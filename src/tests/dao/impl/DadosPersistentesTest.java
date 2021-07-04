package tests.dao.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dao.impl.DadosPersistentes;
import dao.impl.FileUtilRepository;
import dao.impl.exceptions.UsuarioJaCadastradoException;
import model.Meta;
import model.Usuario;

public class DadosPersistentesTest {

    private DadosPersistentes persistentData;
    private Usuario signedUp;
    private String path = "src\\tests\\localstorage\\usuarios.ser";

    @BeforeEach
    public void setUp() {
        persistentData = new DadosPersistentes(path);
        signedUp = new Usuario("Danilo", "danilo", "danilo_pass");
    }

    @Test
    @DisplayName("Sign up user with name, login and password should work")
    public void testSignUpUserWithNameLoginAndPassword() throws UsuarioJaCadastradoException {
        persistentData.cadastrarNovoUsuario(signedUp.getNome(), signedUp.getLogin(), signedUp.getSenha());
        List<Usuario> users = persistentData.getUsuarios();
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    @DisplayName("Sign up user that already exists should throw UsuarioJaCadastradoException")
    public void testSignUpDuplicateUser() {
        Exception exception = assertThrows(UsuarioJaCadastradoException.class, () -> {
            persistentData.cadastrarNovoUsuario(signedUp.getNome(), signedUp.getLogin(), signedUp.getSenha());
        persistentData.cadastrarNovoUsuario(signedUp.getNome(), signedUp.getLogin(), signedUp.getSenha());
        });
        assertTrue(exception.getMessage().contentEquals("Usuario já Cadastrado"));
    }

    @Test
    public void testeSalvarDadosUser() throws UsuarioJaCadastradoException{
        persistentData.cadastrarNovoUsuario(signedUp.getNome(), signedUp.getLogin(), signedUp.getSenha());
        File aux = new File(path);
        aux.delete();
        persistentData.salvarDadosUsuario(signedUp.getLogin());
        assertTrue(aux.exists());

        List<Usuario> usrSv = (List<Usuario>) FileUtilRepository.readFile(path);
        List<Usuario> expList = persistentData.getUsuarios();
        assertTrue(expList.equals(usrSv));
    }

    @AfterEach
    public void tearDown() {
        File file = new File(path);
        if (file.isFile()) {
            file.delete();
        }
    }
}
