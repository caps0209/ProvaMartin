package view;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

import com.entidades.Pessoa;

import dao.PessoaDAO;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControllerMainView extends Application {

	@FXML
	private AnchorPane textFieldEmail;

	@FXML
	private Button btnAlterar;

	@FXML
	private Button btnExcluir;

	@FXML
	private Button btnIncluir;

	@FXML
	private TextField textFieldId;

	@FXML
	private TextField textFieldNome;

	@FXML
	private TextField textFieldDataNasc;

	@FXML
	private TextField textFieldPeso;

	@FXML
	private TextField textFieldBuscaId;

	@FXML
	private TextArea textArea;

	private void limpaCampos() {
		textFieldId.clear();
		textFieldNome.clear();
		textFieldDataNasc.clear();
		textFieldPeso.clear();
		textFieldBuscaId.clear();
		textFieldNome.requestFocus();
	}

	private Pessoa pegaDados() throws ParseException {
		int id = Integer.parseInt(textFieldId.getText());
		String nome = textFieldNome.getText();
		java.util.Date dataNasc = new SimpleDateFormat("yyyy-MM-dd").parse(textFieldDataNasc.getText());
		java.sql.Date dataSql = new java.sql.Date(dataNasc.getTime());

		float peso = Float.parseFloat(textFieldPeso.getText());

		return new Pessoa(id, nome, dataSql, peso);
	}

	private void configBtns(boolean containsId) {
		if (containsId) {
			btnAlterar.setDisable(true);
			btnExcluir.setDisable(true);
			btnIncluir.setDisable(false);
		} else {
			btnAlterar.setDisable(false);
			btnExcluir.setDisable(false);
			btnIncluir.setDisable(true);
		}
	}

	@FXML
	void onActionAlterar(ActionEvent event) throws ParseException {
		Pessoa pessoa = pegaDados();
		limpaCampos();

		if (new PessoaDAO().alterar(pessoa)) {
			System.out.println("ALTEROU A PESSOA ID: " + pessoa.getId());
		} else {
			System.out.println("NÃO ALTEROU A PESSOA ID: " + pessoa.getId());
		}

		textArea.clear();
		onActionListar(event);
	}

	@FXML
	void onActionExcluir(ActionEvent event) throws ParseException {
		Pessoa pessoa = pegaDados();
		limpaCampos();

		if (new PessoaDAO().excluir(pessoa)) {
			System.out.println("EXCLUIU A PESSOA ID: " + pessoa.getId());
		} else {
			System.out.println("NÃO EXCLUIU A PESSOA ID: " + pessoa.getId());
		}

		textArea.clear();
		onActionListar(event);
	}

	@FXML
	void BuscarPessoa(ActionEvent event) {
		String idString = textFieldBuscaId.getText();
		Pessoa pessoa = null;
		if (!idString.equals(" ")) {
			try {
				int id = Integer.valueOf(idString);
				pessoa = new PessoaDAO().findByID(id);
			} catch (Exception e) {

			}
			if (pessoa != null) {
				textFieldId.setText(String.valueOf(pessoa.getId()));
				textFieldNome.setText(pessoa.getNome());
				textFieldDataNasc.setText(String.valueOf(pessoa.getDataNasc()));
				textFieldPeso.setText(String.valueOf(pessoa.getPeso()));
			} else {
				limpaCampos();
			}
		}
		textFieldBuscaId.clear();
		configBtns(textFieldId.getText().isEmpty());
	}

	@FXML
	void onActionIncluir(ActionEvent event) throws ParseException {
		Pessoa pessoa = pegaDados();
		limpaCampos();

		int quantidade = new PessoaDAO().inserir(pessoa);
		textArea.clear();
		onActionListar(event);
		// Alerts.showAlert("Alerta", "Pessoa Cadastrada", null, AlertType.INFORMATION);
	}

	@FXML
	void onActionListar(ActionEvent event) {
		textArea.clear();
		List<Pessoa> listaPessoa = new PessoaDAO().listAll();
		listaPessoa.forEach(pessoa -> {
			textArea.appendText(pessoa.toString() + "\n");
		});
	}

	public void execute() {
		launch();
	}

	@Override
	public void start(Stage stage) {
		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
			stage.setTitle("Primeira tela");
			Scene sc = new Scene(pane);
			stage.setScene(sc);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
