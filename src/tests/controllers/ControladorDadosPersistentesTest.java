package tests.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.util.List;

import controllers.ControladorDadosPersistentes;
import dao.IDadosPersistentes;
import dao.impl.exceptions.DadosNaoPreenchidosException;
import dao.impl.exceptions.UsuarioJaCadastradoException;
import model.Usuario;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ControladorDadosPersistentesTest {

    private ControladorDadosPersistentes controladorDadosPersistentes;
    private String path = ".\\localstorage\\usuarios.ser";

    @BeforeEach
    public void setUp() throws Exception {
        controladorDadosPersistentes = ControladorDadosPersistentes.getInstance();
    }

    @Test
    @DisplayName("Sign up user with name, login and password should work")
    public void testSignUpUserWithNameLoginAndPassword()
            throws DadosNaoPreenchidosException, UsuarioJaCadastradoException {
        controladorDadosPersistentes.cadastrarUsuario("Rafael", "rafael", "rafael_pass");
        IDadosPersistentes dadosPersistentes = controladorDadosPersistentes.getDadosPersistentes();
        List<Usuario> users = dadosPersistentes.getUsuarios();
        Usuario signedUser = null;
        for (Usuario user : users) {
            if (user.getNome() == "Rafael" && user.getLogin() == "rafael" && user.getSenha() == "rafael_pass") {
                signedUser = user;
            }
        }
        assertNotNull(signedUser);
    }

    @Test
    @DisplayName("Sign up user with missing data should fail")
    public void testSignUpUserWithoutDataFails() {
        Exception exception = assertThrows(DadosNaoPreenchidosException.class, () -> {
            controladorDadosPersistentes.cadastrarUsuario(null, null, null);
        });

        assertThrows(DadosNaoPreenchidosException.class, () -> {
            controladorDadosPersistentes.cadastrarUsuario("Rafael", null, null);
        });

        assertThrows(DadosNaoPreenchidosException.class, () -> {
            controladorDadosPersistentes.cadastrarUsuario(null, "rafael", null);
        });

        assertThrows(DadosNaoPreenchidosException.class, () -> {
            controladorDadosPersistentes.cadastrarUsuario(null, null, "rafael_pass");
        });

        String msg = exception.getMessage();
        assertEquals("Os dados não foram preenchidos corretamente", msg);
    }

    @Test
    @DisplayName("Sign duplicate user should fail")
    public void testSignUpUserThatAlreadyExists() throws DadosNaoPreenchidosException, UsuarioJaCadastradoException {
        controladorDadosPersistentes.cadastrarUsuario("Mateus", "mateus", "mateus_pass");
        assertThrows(UsuarioJaCadastradoException.class, () -> {
            controladorDadosPersistentes.cadastrarUsuario(
                "Mateus",
                "mateus",
                "mateus_pass"
            );
        });
    }

    @Test
    public void testGetDadosPersistentesInstance() {
        IDadosPersistentes dadosPersistentes = controladorDadosPersistentes.getDadosPersistentes();
        assertNotNull(dadosPersistentes);
    }

    @AfterEach
    public void tearDown() {
        File file = new File(path);
        if (file.isFile()) {
            file.delete();
        }
    }
}
