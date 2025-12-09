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

    // --- Componentes da Tela (Têm que ter o mesmo fx:id do SceneBuilder/FXML) ---
    @FXML private TextField txtNome;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEmail;

    @FXML private TableView<Contato> tabelaContatos;
    @FXML private TableColumn<Contato, Integer> colId;
    @FXML private TableColumn<Contato, String> colNome;
    @FXML private TableColumn<Contato, String> colTelefone;
    @FXML private TableColumn<Contato, String> colEmail;

    // --- Instância do DAO para falar com o Banco (Hibernate) ---
    private ContatoDAO dao = new ContatoDAO();

    // Variável para guardar qual contato estamos editando (se houver)
    private Contato contatoSelecionado;

    // --- Método que roda assim que a tela abre ---
    @FXML
    public void initialize() {
        // 1. Configura as colunas da tabela para lerem os atributos da classe Contato
        if (colId != null) colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        // 2. Carrega os dados do banco na tabela
        atualizarTabela();

        // 3. Configura o evento de clique na tabela (para preencher os campos)
        if (tabelaContatos != null) {
            tabelaContatos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    contatoSelecionado = newVal; // Guarda quem foi clicado
                    txtNome.setText(newVal.getNome());
                    txtTelefone.setText(newVal.getTelefone());
                    txtEmail.setText(newVal.getEmail());
                }
            });
        }
    }

    // --- Ação do Botão ADICIONAR ---
    @FXML
    public void adicionar() {
        try {
            // ID null indica para o Hibernate que é um NOVO registro
            Contato novo = new Contato(null, txtNome.getText(), txtTelefone.getText(), txtEmail.getText());

            dao.salvar(novo); // O DAO do Hibernate decide se salva ou atualiza

            limparCampos();
            atualizarTabela();
            mostrarAlerta("Sucesso", "Contato salvo com sucesso!");
        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao salvar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- Ação do Botão EDITAR ---
    @FXML
    public void editar() {
        if (contatoSelecionado != null) {
            try {
                // Atualiza os dados do objeto selecionado com o que está na tela
                contatoSelecionado.setNome(txtNome.getText());
                contatoSelecionado.setTelefone(txtTelefone.getText());
                contatoSelecionado.setEmail(txtEmail.getText());

                dao.salvar(contatoSelecionado); // O Hibernate vê que tem ID e faz Update

                limparCampos();
                atualizarTabela();
                mostrarAlerta("Sucesso", "Contato atualizado!");
            } catch (Exception e) {
                mostrarAlerta("Erro", "Erro ao editar: " + e.getMessage());
            }
        } else {
            mostrarAlerta("Aviso", "Selecione um contato na tabela para editar.");
        }
    }

    // --- Ação do Botão EXCLUIR ---
    @FXML
    public void excluir() {
        if (contatoSelecionado != null) {
            try {
                dao.excluir(contatoSelecionado.getId());

                limparCampos();
                atualizarTabela();
                mostrarAlerta("Sucesso", "Contato excluído!");
            } catch (Exception e) {
                mostrarAlerta("Erro", "Erro ao excluir: " + e.getMessage());
            }
        } else {
            mostrarAlerta("Aviso", "Selecione um contato na tabela para excluir.");
        }
    }

    // --- Métodos Auxiliares ---

    private void atualizarTabela() {
        if (tabelaContatos != null) {
            List<Contato> lista = dao.listar();
            ObservableList<Contato> dados = FXCollections.observableArrayList(lista);
            tabelaContatos.setItems(dados);
        }
    }

    private void limparCampos() {
        txtNome.clear();
        txtTelefone.clear();
        txtEmail.clear();
        contatoSelecionado = null;
        if (tabelaContatos != null) tabelaContatos.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.show();
    }
}