package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.dao.ContatoDAO;
import org.model.Contato;

import java.util.List;

public class AgendaController {

    @FXML private TextField txtNome;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEmail;

    @FXML private TableView<Contato> tabelaContatos;
    @FXML private TableColumn<Contato, Integer> colId;
    @FXML private TableColumn<Contato, String> colNome;
    @FXML private TableColumn<Contato, String> colTelefone;
    @FXML private TableColumn<Contato, String> colEmail;

    private final ContatoDAO dao = new ContatoDAO();
    private Contato contatoSelecionado = null;

    // ✅ INICIALIZA A TELA
    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        atualizarTabela();

        tabelaContatos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                contatoSelecionado = newVal;
                txtNome.setText(newVal.getNome());
                txtTelefone.setText(newVal.getTelefone());
                txtEmail.setText(newVal.getEmail());
            }
        });
    }

    // ✅ ADICIONAR NOVO CONTATO
    @FXML
    public void adicionar() {
        try {
            if (txtNome.getText().isBlank()) {
                mostrarAlerta("Aviso", "Preencha o nome!");
                return;
            }

            Contato novo = new Contato(
                    null,
                    txtNome.getText(),
                    txtTelefone.getText(),
                    txtEmail.getText()
            );

            dao.salvar(novo);

            limparCampos();
            atualizarTabela();
            mostrarAlerta("Sucesso", "Contato adicionado com sucesso!");

        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao salvar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ✅ EDITAR CONTATO (COM ATUALIZAÇÃO VISUAL CORRIGIDA)
    @FXML
    public void editar() {
        if (contatoSelecionado == null) {
            mostrarAlerta("Aviso", "Selecione um contato para editar!");
            return;
        }

        try {
            contatoSelecionado.setNome(txtNome.getText());
            contatoSelecionado.setTelefone(txtTelefone.getText());
            contatoSelecionado.setEmail(txtEmail.getText());

            dao.salvar(contatoSelecionado);

            // ✅ Atualiza a tabela
            atualizarTabela();

            // ✅ Re-seleciona o item editado e atualiza o painel
            for (Contato c : tabelaContatos.getItems()) {
                if (c.getId().equals(contatoSelecionado.getId())) {
                    tabelaContatos.getSelectionModel().select(c);
                    txtNome.setText(c.getNome());
                    txtTelefone.setText(c.getTelefone());
                    txtEmail.setText(c.getEmail());
                    contatoSelecionado = c;
                    break;
                }
            }

            mostrarAlerta("Sucesso", "Contato atualizado com sucesso!");

        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao editar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ✅ EXCLUIR CONTATO
    @FXML
    public void excluir() {
        if (contatoSelecionado == null) {
            mostrarAlerta("Aviso", "Selecione um contato para excluir!");
            return;
        }

        try {
            dao.excluir(contatoSelecionado.getId());

            limparCampos();
            atualizarTabela();
            mostrarAlerta("Sucesso", "Contato excluído com sucesso!");

        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao excluir: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ✅ ATUALIZA A TABELA
    private void atualizarTabela() {
        List<Contato> lista = dao.listar();
        ObservableList<Contato> dados = FXCollections.observableArrayList(lista);
        tabelaContatos.setItems(dados);
        tabelaContatos.refresh();
    }

    // ✅ LIMPA CAMPOS
    private void limparCampos() {
        txtNome.clear();
        txtTelefone.clear();
        txtEmail.clear();
        contatoSelecionado = null;
        tabelaContatos.getSelectionModel().clearSelection();
    }

    // ✅ ALERTAS PADRONIZADOS
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.show();
    }
}
