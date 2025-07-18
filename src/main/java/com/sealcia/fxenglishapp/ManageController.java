package com.sealcia.fxenglishapp;

import com.sealcia.pojo.*;
import com.sealcia.services.CategoryServices;
import com.sealcia.services.QuestionServices;
import com.sealcia.utils.AlertUtils;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

public class ManageController implements Initializable {
    @FXML private VBox vBox;
    @FXML private ComboBox<Category> cbCategories;
    @FXML private TextField txtContent;
    @FXML private TextField txtA;
    @FXML private TextField txtB;
    @FXML private TextField txtC;
    @FXML private TextField txtD;
    @FXML private RadioButton rdoA;
    @FXML private RadioButton rdoB;
    @FXML private RadioButton rdoC;
    @FXML private RadioButton rdoD;
    @FXML private TextField txtKeywords;
    @FXML private TableView<Question> tbQuestion;
    @FXML private ToggleGroup choices;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cbCategories.prefWidthProperty().bind(this.vBox.widthProperty());
        this.txtA.prefWidthProperty().bind(this.vBox.widthProperty());
        this.txtB.prefWidthProperty().bind(this.vBox.widthProperty());
        this.txtC.prefWidthProperty().bind(this.vBox.widthProperty());
        this.txtD.prefWidthProperty().bind(this.vBox.widthProperty());
        this.choices = this.rdoA.getToggleGroup();
        try {
            this.cbCategories.setItems(
                    FXCollections.observableArrayList(CategoryServices.getCategories()));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.loadQuestion();

        this.txtKeywords
                .textProperty()
                .addListener(
                        et -> {
                            this.tbQuestion.getItems().clear();
                            try {
                                this.tbQuestion.setItems(
                                        FXCollections.observableArrayList(
                                                QuestionServices.getQuestions(
                                                        this.txtKeywords.getText())));
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });
    }

    public boolean isComboBoxChosen() {
        return this.cbCategories.getSelectionModel().isEmpty();
    }

    public boolean areChoicesFilled() {
        return this.txtA.getText().isEmpty()
                && this.txtB.getText().isEmpty()
                && this.txtC.getText().isEmpty()
                && this.txtD.getText().isEmpty();
    }

    public boolean isRadioButtonSelected() {
        return this.choices.getSelectedToggle() == null;
    }

    public void addQuestionHandler(ActionEvent event) {
        if (this.isComboBoxChosen()) {
            AlertUtils.showAlertMessage(AlertType.WARNING, "Please enter the category!");
            return;
        }

        if (this.areChoicesFilled()) {
            AlertUtils.showAlertMessage(AlertType.WARNING, "Please fill ALL 4 choices!");
            return;
        }

        if (this.isRadioButtonSelected()) {
            AlertUtils.showAlertMessage(AlertType.WARNING, "Please check ONE correct choice");
            return;
        }

        String questionId = UUID.randomUUID().toString();
        Question question =
                new Question(
                        questionId,
                        txtContent.getText(),
                        cbCategories.getSelectionModel().getSelectedItem().getId());

        ArrayList<Choice> choices = new ArrayList<>();
        choices.add(
                new Choice.Builder()
                        .id(UUID.randomUUID().toString())
                        .content(this.txtA.getText())
                        .correct(rdoA.isSelected())
                        .questionId(questionId)
                        .build());
        choices.add(
                new Choice.Builder()
                        .id(UUID.randomUUID().toString())
                        .content(this.txtB.getText())
                        .correct(rdoB.isSelected())
                        .questionId(questionId)
                        .build());
        choices.add(
                new Choice.Builder()
                        .id(UUID.randomUUID().toString())
                        .content(this.txtC.getText())
                        .correct(this.rdoC.isSelected())
                        .questionId(questionId)
                        .build());
        choices.add(
                new Choice.Builder()
                        .id(UUID.randomUUID().toString())
                        .content(this.txtD.getText())
                        .correct(this.rdoD.isSelected())
                        .questionId(questionId)
                        .build());

        try {
            QuestionServices.addQuestion(question, choices);

            this.clearField();
            this.refreshTableView();

            AlertUtils.showAlertMessage(AlertType.INFORMATION, "Add question successful!");
        } catch (SQLException e) {
            AlertUtils.showAlertMessage(AlertType.ERROR, "Failed to add question!");
            e.printStackTrace();
        }
    }

    public void refreshTableView() throws SQLException {
        this.tbQuestion.getItems().clear();
        this.tbQuestion.setItems(
                FXCollections.observableArrayList(QuestionServices.getQuestions("")));
    }

    public void clearField() {
        this.txtContent.clear();
        this.txtA.clear();
        this.txtB.clear();
        this.txtC.clear();
        this.txtD.clear();
        this.rdoA.setSelected(false);
        this.rdoB.setSelected(false);
        this.rdoC.setSelected(false);
        this.rdoD.setSelected(false);
        this.cbCategories.getSelectionModel().clearSelection();
    }

    public void resetHandler(ActionEvent event) {
        this.clearField();
    }

    private TableColumn<Question, String> createQuestionContentColumn() {
        TableColumn<Question, String> clContent = new TableColumn("Question content");
        clContent.setPrefWidth(200);
        clContent.setCellValueFactory(new PropertyValueFactory("content"));
        return clContent;
    }

    private TableColumn<Question, String> createChoiceColumn() {
        TableColumn<Question, String> clChoice = new TableColumn("Choices");
        clChoice.setCellValueFactory(new PropertyValueFactory<>("choiceView"));
        return clChoice;
    }

    private TableColumn<Question, Void> createActionColumn() {
        TableColumn<Question, Void> clAction = new TableColumn<>("Action");
        clAction.setCellFactory(
                p -> {
                    Button btn = new Button("Delete");
                    btn.setOnAction(
                            et -> {
                                AlertUtils.getAlert(
                                                AlertType.CONFIRMATION,
                                                "Are you sure to delete this question?")
                                        .showAndWait()
                                        .ifPresent(
                                                res -> {
                                                    if (res == ButtonType.OK) {
                                                        TableCell cl =
                                                                (TableCell)
                                                                        ((Button) et.getSource())
                                                                                .getParent();
                                                        Question q =
                                                                (Question)
                                                                        cl.getTableRow().getItem();
                                                        try {
                                                            QuestionServices.deleteQuestion(
                                                                    q.getId());
                                                            AlertUtils.showAlertMessage(
                                                                    AlertType.INFORMATION,
                                                                    "Delete question successful!");
                                                            this.refreshTableView();
                                                        } catch (SQLException e) {
                                                            AlertUtils.showAlertMessage(
                                                                    AlertType.ERROR,
                                                                    "Failed to delete question!");
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                });
                            });
                    TableCell cell = new TableCell();
                    cell.setGraphic(btn);
                    return cell;
                });
        return clAction;
    }

    private void loadQuestion() {
        TableColumn<Question, String> clContent = this.createQuestionContentColumn();
        TableColumn<Question, String> clChoice = this.createChoiceColumn();
        TableColumn<Question, Void> clAction = this.createActionColumn();

        this.tbQuestion.getColumns().addAll(clContent, clChoice, clAction);

        try {
            tbQuestion.setItems(
                    FXCollections.observableArrayList(QuestionServices.getQuestions("")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
