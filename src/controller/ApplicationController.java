package controller;

import FiltrageSimple.Longueur;
import FiltrageSimple.Simple;
import SRA.SRA;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;

@FXMLController(value = "/resources/fxml/Application.fxml", title = "Login")
public class ApplicationController {

    public static int counter = 0;

    @FXMLViewFlowContext
    private ViewFlowContext flowContext;

    @FXML private StackPane root;
    @FXML private FlowPane cableFlowPane;
    @FXML private FlowPane rouleauFlowPane;
    @FXML private JFXTextField cableTextField;
    @FXML private JFXTextField rouleauTextField;
    @FXML private JFXDialog cableDialog;
    @FXML private JFXDialog rouleauDialog;
    @FXML private JFXTextField longueurSertissageTextField;
    @FXML private JFXDialog cableGenererDialog;
    @FXML private JFXDialog rouleauGenererDialog;
    @FXML private TextField minCableGenerer;
    @FXML private TextField maxCableGenerer;
    @FXML private TextField minRouleauGenerer;
    @FXML private TextField maxRouleauGenerer;
    @FXML private JFXDialog errorDialog;
    @FXML private Label headingLabel;
    @FXML private Label bodyLabel;
    @FXML private JFXRadioButton ept;
    @FXML private JFXRadioButton srea;
    @FXML private JFXRadioButton tpe;
    @FXML private ToggleGroup toggleGroup;


    @FXML
    @ActionTrigger("accept_button")
    private JFXButton acceptButton;
    @FXML
    @ActionTrigger("cable_dialog_button")
    private JFXButton cableDialogButton;
    @FXML
    @ActionTrigger("rouleau_dialog_button")
    private JFXButton rouleauDialogButton;
    @FXML
    @ActionTrigger("cable_annuler_button")
    private JFXButton cableAnnulerButton;
    @FXML
    @ActionTrigger("cable_vider_button")
    private JFXButton cableViderButton;
    @FXML
    @ActionTrigger("cable_repeter_button")
    private JFXButton cableRepeterButton;
    @FXML
    @ActionTrigger("cable_generer_button")
    private JFXButton cableGenererButton;
    @FXML
    @ActionTrigger("cable_valider_button")
    private JFXButton cableValiderButton;
    @FXML
    @ActionTrigger("rouleau_annuler_button")
    private JFXButton rouleauAnnulerButton;
    @FXML
    @ActionTrigger("rouleau_vider_button")
    private JFXButton rouleauViderButton;
    @FXML
    @ActionTrigger("rouleau_repeter_button")
    private JFXButton rouleauRepeterButton;
    @FXML
    @ActionTrigger("rouleau_generer_button")
    private JFXButton rouleauGenererButton;
    @FXML
    @ActionTrigger("rouleau_valider_button")
    private JFXButton rouleauValiderButton;
    @FXML
    @ActionTrigger("cable_generer_annuler")
    private JFXButton cableGenererAnnuler;
    @FXML
    @ActionTrigger("cable_generer_valider")
    private JFXButton cableGenererValider;
    @FXML
    @ActionTrigger("rouleau_generer_annuler")
    private JFXButton rouleauGenererAnnuler;
    @FXML
    @ActionTrigger("rouleau_generer_valider")
    private JFXButton rouleauGenererValider;
    @FXML
    @ActionTrigger("valider_button")
    private JFXButton validerButton;

    private FlowHandler flowHandler;

    private static ArrayList<TextField> cableTextFieldArrayList = new ArrayList<>() ;
    private static ArrayList<TextField> rouleauTextFieldArrayList = new ArrayList<>();

    private static ObservableList cableArrayList = FXCollections.observableArrayList();
    private static ObservableList rouleauArrayList = FXCollections.observableArrayList();

    private static int precedentNombreCable = 0;
    private static int precedentNombreRouleau = 0;
    private static int nombreCable;
    private static int nombreRouleau;
    private static double longueurSertissage;
    private static int min = 1;
    private static int max = 9;


    @PostConstruct
    public void init() {

        BooleanBinding cableBooleanBinding = cableTextField.textProperty().isEmpty();
        BooleanBinding rouleauBooleanBinding = rouleauTextField.textProperty().isEmpty();
        BooleanBinding validerBooleanBinding = Bindings.isEmpty(cableArrayList)
                                           .or(Bindings.isEmpty(rouleauArrayList)
                                           .or(longueurSertissageTextField.textProperty().isEmpty()));

        cableDialogButton.disableProperty().bind(cableBooleanBinding);
        rouleauDialogButton.disableProperty().bind(rouleauBooleanBinding);
        validerButton.disableProperty().bind(validerBooleanBinding);

        if (counter != 0) {
            cableTextField.setText(nombreCable + "");
            rouleauTextField.setText(nombreRouleau + "");
            longueurSertissageTextField.setText(longueurSertissage + "");
        }

        counter++;
    }

    @ActionMethod("accept_button")
    public void abOnAction(){
        errorDialog.close();
    }
    @ActionMethod("cable_dialog_button")
    public void cdbOnAction() {
        try {
            nombreCable = Integer.parseInt(cableTextField.getText().trim());
            if (nombreCable <= 0) {
                showErrorDialog("cable");
                return;
            }
            else {
                if (nombreCable != precedentNombreCable) {
                    cableArrayList.clear();
                    cableFlowPane.getChildren().clear();
                    cableTextFieldArrayList.clear();
                    for (int i = 0; i < nombreCable; i++) {
                        TextField textField = new TextField();
                        textField.setFont(new Font(25));
                        textField.setMaxWidth(80);
                        cableFlowPane.getChildren().add(textField);
                        cableTextFieldArrayList.add(textField);
                    }
                }
                else if (counter != 0) {
                    cableFlowPane.getChildren().clear();
                    cableTextFieldArrayList.clear();
                    for (int i = 0 ; i < nombreCable ; i++) {
                        TextField textField = new TextField(cableArrayList.get(i).toString());
                        textField.setFont(new Font(25));
                        textField.setMaxWidth(80);
                        cableFlowPane.getChildren().add(textField);
                        cableTextFieldArrayList.add(textField);
                    }
                }
            }

            BooleanBinding cableValiderBooleanBinding = cableTextFieldArrayList.get(0).textProperty().isEmpty();

            for (int i=1 ; i < cableTextFieldArrayList.size() ; i++) {
                cableValiderBooleanBinding = cableValiderBooleanBinding.or(cableTextFieldArrayList.get(i).textProperty().isEmpty());
            }

            cableValiderButton.disableProperty().bind(cableValiderBooleanBinding);

            cableDialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
            cableDialog.show(root);
            precedentNombreCable = nombreCable;
        } catch (Exception e) {
            showErrorDialog("cable");
        }
    }

    @ActionMethod("rouleau_dialog_button")
    public void rdbOnAction() {
        try {
            nombreRouleau = Integer.parseInt(rouleauTextField.getText().trim());
            if (nombreRouleau <= 0) {
                showErrorDialog("rouleau");
                return;
            } else {
                if (nombreRouleau != precedentNombreRouleau) {
                    rouleauArrayList.clear();
                    rouleauFlowPane.getChildren().clear();
                    rouleauTextFieldArrayList.clear();
                    for (int i = 0; i < nombreRouleau; i++) {
                        TextField textField = new TextField();
                        textField.setFont(new Font(25));
                        textField.setMaxWidth(80);
                        rouleauFlowPane.getChildren().add(textField);
                        rouleauTextFieldArrayList.add(textField);
                    }
                }
                else if (counter != 0) {
                    rouleauFlowPane.getChildren().clear();
                    rouleauTextFieldArrayList.clear();
                    for (int i = 0 ; i < nombreRouleau ; i++) {
                        TextField textField = new TextField(rouleauArrayList.get(i).toString());
                        textField.setFont(new Font(25));
                        textField.setMaxWidth(80);
                        rouleauFlowPane.getChildren().add(textField);
                        rouleauTextFieldArrayList.add(textField);
                    }
                }
            }

            BooleanBinding rouleauValiderBooleanBinding = rouleauTextFieldArrayList.get(0).textProperty().isEmpty();

            for (int i=1 ; i < rouleauTextFieldArrayList.size() ; i++) {
                rouleauValiderBooleanBinding = rouleauValiderBooleanBinding.or(rouleauTextFieldArrayList.get(i).textProperty().isEmpty());
            }

            rouleauValiderButton.disableProperty().bind(rouleauValiderBooleanBinding);

            rouleauDialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
            rouleauDialog.show(root);
            precedentNombreRouleau = nombreRouleau;
        } catch (Exception e) {
            showErrorDialog("rouleau");
        }
    }

    @ActionMethod("cable_generer_button")
    public void cgbOnAction() {

        minCableGenerer.setText(min + "");
        maxCableGenerer.setText(max + "");
        cableGenererDialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        cableGenererDialog.show(cableDialog);

    }

    @ActionMethod("rouleau_generer_button")
    public void rgbOnAction() {

        minRouleauGenerer.setText(min + "");
        maxRouleauGenerer.setText(max + "");
        rouleauGenererDialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        rouleauGenererDialog.show(rouleauDialog);
    }

    @ActionMethod("cable_annuler_button")
    public void cabOnAction() {
        cableDialog.close();
    }

    @ActionMethod("rouleau_annuler_button")
    public void rab() {
        rouleauDialog.close();
    }

    @ActionMethod("cable_generer_annuler")
    public void cgaOnAction() {
        cableGenererDialog.close();
    }

    @ActionMethod("rouleau_generer_annuler")
    public void rgaOnAction() {
        rouleauGenererDialog.close();
    }

    @ActionMethod("cable_vider_button")
    public void cvbOnAction() {
        for (int i=0 ; i < cableTextFieldArrayList.size() ; i++) {
            cableTextFieldArrayList.get(i).setText("");
        }
    }

    @ActionMethod("rouleau_vider_button")
    public void rvbOnAction() {
        for (int i=0 ; i < rouleauTextFieldArrayList.size() ; i++) {
            rouleauTextFieldArrayList.get(i).setText("");
        }
    }

    @ActionMethod("cable_repeter_button")
    public void crbOnAction() {
        try {
            for (int i=0 ; i < cableTextFieldArrayList.size() ; i++) {
                if (cableTextFieldArrayList.get(i).getText().trim().isEmpty()) {
                    continue;
                }
                else {
                    float valeur = Float.parseFloat(cableTextFieldArrayList.get(i).getText().trim());
                    if (valeur <= 0) {
                        showLongueurErrorDialog("cables");
                        break;
                    }
                    else {
                        for (int j = 0; j < cableTextFieldArrayList.size(); j++) {
                            cableTextFieldArrayList.get(j).setText(valeur + "");
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            showLongueurErrorDialog("cables");
        }
    }

    @ActionMethod("rouleau_repeter_button")
    public void rrbOnAction() {
        try {
            for (int i=0 ; i < rouleauTextFieldArrayList.size() ; i++) {
                if (rouleauTextFieldArrayList.get(i).getText().trim().isEmpty()) {
                    continue;
                }
                else {
                    float valeur = Float.parseFloat(rouleauTextFieldArrayList.get(i).getText().trim());
                    if (valeur <= 0) {
                        showLongueurErrorDialog("rouleaux");
                        break;
                    }
                    else {
                        for (int j = 0; j < rouleauTextFieldArrayList.size(); j++) {
                            rouleauTextFieldArrayList.get(j).setText(valeur + "");
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            showLongueurErrorDialog("rouleaux");
        }
    }

    @ActionMethod("cable_generer_valider")
    public void cgvOnAction() {
        try {
            min = Integer.parseInt(minCableGenerer.getText().trim());
            max = Integer.parseInt(maxCableGenerer.getText().trim());
            int random;

            if (min <= max) {
                for (int i = 0; i < cableTextFieldArrayList.size(); i++) {
                    random = (int) (Math.random() * (max - min + 1) + min);
                    cableTextFieldArrayList.get(i).setText(random + "");
                }
            }
            else {
                showGenererErrorDialog("cable");
                return;
            }
            cableGenererDialog.close();
        } catch (Exception e) {
            showGenererErrorDialog("cable");
        }
    }

    @ActionMethod("rouleau_generer_valider")
    public void rgvOnAction() {
        try {
            min = Integer.parseInt(minRouleauGenerer.getText().trim());
            max = Integer.parseInt(maxRouleauGenerer.getText().trim());
            int random;

            if (min <= max) {
                for (int i = 0; i < rouleauTextFieldArrayList.size(); i++) {
                    random = (int) (Math.random() * (max - min + 1) + min);
                    rouleauTextFieldArrayList.get(i).setText(random + "");
                }
            }
            else {
                showGenererErrorDialog("rouleau");
                return;
            }
            rouleauGenererDialog.close();
        } catch (Exception e) {
            showGenererErrorDialog("rouleau");
        }
    }

    @ActionMethod("cable_valider_button")
    public void cvaliderbOnAction() {
        try {
            for (int i=0 ; i < cableTextFieldArrayList.size(); i++) {
                float valeur = Float.parseFloat(cableTextFieldArrayList.get(i).getText().trim());
                if (valeur <= 0) {
                    showLongueurErrorDialog("cables");
                    return;
                }
            }
            cableArrayList.clear();
            for (int i=0 ; i < cableTextFieldArrayList.size() ; i++) {
                cableArrayList.add(Float.parseFloat(cableTextFieldArrayList.get(i).getText().trim()));
            }
            cableDialog.close();
        } catch (Exception e) {
            showLongueurErrorDialog("cables");
        }
    }

    @ActionMethod("rouleau_valider_button")
    public void rvaliderbOnAction() {
        try {
            for (int i=0 ; i < rouleauTextFieldArrayList.size(); i++) {
                float valeur = Float.parseFloat(rouleauTextFieldArrayList.get(i).getText().trim());
                if (valeur <= 0) {
                    showLongueurErrorDialog("rouleaux");
                    return;
                }
            }
            rouleauArrayList.clear();
            for (int i=0 ; i < rouleauTextFieldArrayList.size() ; i++) {
                rouleauArrayList.add(Float.parseFloat(rouleauTextFieldArrayList.get(i).getText().trim()));
            }
            rouleauDialog.close();
        } catch (Exception e) {
            showLongueurErrorDialog("rouleaux");
        }
    }

    @ActionMethod("valider_button")
    public void vbOnAction() throws VetoException, FlowException {
        try {
            if (Float.parseFloat(longueurSertissageTextField.getText().trim()) < 0) {
                showSertissageErrorDialog();
                return;
            }

            float sumCable = 0 , sumRouleau = 0;

            if ((Float) Collections.max(cableArrayList) > (Float) Collections.max(rouleauArrayList)) {
                showMaxContrainteErrorDialog();
                return;
            }

            for (int i=0 ; i < cableArrayList.size() ; i++) {
                sumCable += Float.parseFloat(cableArrayList.get(i).toString());
            }
            for (int i=0 ; i < rouleauArrayList.size() ; i++) {
                sumRouleau += Float.parseFloat(rouleauArrayList.get(i).toString());
            }
            if (sumCable > sumRouleau ) {
                showSumContrainteErrorDialog();
                return;
            }

            ArrayList<Longueur> cable = new ArrayList<>();
            ArrayList<Longueur> rouleau = new ArrayList<>();

            longueurSertissage = Double.parseDouble(longueurSertissageTextField.getText().trim());

            for (int i=0 ; i<cableArrayList.size() ; i++) {
                cable.add(new Longueur(i , Double.parseDouble(cableArrayList.sorted().get(cableArrayList.size() - i - 1).toString()) + longueurSertissage));
            }

            for (int i=0 ; i<rouleauArrayList.size() ; i++) {
                rouleau.add(new Longueur(i , Double.parseDouble(rouleauArrayList.sorted().get(rouleauArrayList.size() - i - 1).toString())));
            }

            switch (((RadioButton) toggleGroup.getSelectedToggle()).getText().trim()) {
                case "Simple Retour En Arriere" : new SRA(cable,rouleau); break;
                case "Tester Puis Enumerer (Filtrage Simple)" : new Simple(cable , rouleau , true); break;
                case "Tester Puis Enumerer (Filtrage Fort)" : new Simple(cable , rouleau , false); break;
                default: break;
            }

            flowHandler = (FlowHandler) flowContext.getRegisteredObject("FlowHandler");
            flowHandler.handle("result");
        }
        catch (Exception e) {
            showSertissageErrorDialog();
        }
    }


    public void showErrorDialog(String nom) {
        headingLabel.setText("Containte non valide");
        bodyLabel.setText("Le nombre de " + nom + " doit etre un nombre reel strictement positif");
        errorDialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        errorDialog.setDialogContainer(root);
        errorDialog.show();
    }

    public void showLongueurErrorDialog(String nom) {
        headingLabel.setText("Containte non valide");
        bodyLabel.setText("La longueur des " + nom + " doit etre un nombre reel strictement positif");
        errorDialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        if (nom.equals("cables")) errorDialog.setDialogContainer(cableDialog);
        else errorDialog.setDialogContainer(rouleauDialog);
        errorDialog.show();
    }

    public void showGenererErrorDialog(String nom) {
        headingLabel.setText("Invalid   Input");
        bodyLabel.setText("min et max doivent etre des entiers naturels et min <= max");
        errorDialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        if (nom.equals("cable")) errorDialog.setDialogContainer(cableGenererDialog);
        else errorDialog.setDialogContainer(rouleauGenererDialog);
        errorDialog.show();
    }

    public void showSertissageErrorDialog() {
        headingLabel.setText("Invalid   Input");
        bodyLabel.setText("La longueur de sertissage doit etre un nombre positif");
        errorDialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        errorDialog.setDialogContainer(root);
        errorDialog.show();
    }
    public void showMaxContrainteErrorDialog() {
        headingLabel.setText("Probleme de contrainte");
        bodyLabel.setText("La longueur maximale du cable doit etre inferieur a la longueur maximale du rouleau");
        errorDialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        errorDialog.setDialogContainer(root);
        errorDialog.show();
    }

    public void showSumContrainteErrorDialog() {
        headingLabel.setText("Probleme de contrainte");
        bodyLabel.setText("La somme des longueurs des cables doit etre inferieur a la somme des longueur des rouleaux");
        errorDialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        errorDialog.setDialogContainer(root);
        errorDialog.show();
    }
}
