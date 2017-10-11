
package controller;

import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;

import FiltrageSimple.Discours;
import FiltrageSimple.FlowHandler;

import com.jfoenix.controls.JFXButton;

@FXMLController(value = "/resources/fxml/Result.fxml" , title = "Resultat")
public class ResultController {

    @FXMLViewFlowContext private ViewFlowContext flowContext;

    @FXML private Label mainLabel;
    @FXML
    @ActionTrigger("modifier_button")
    private JFXButton modifierButton;

    @FXML
    @ActionTrigger("retourner_button")
    private JFXButton retournerButton;

    private FlowHandler flowHandler;

    @PostConstruct
    public void init() {

        Stage stage = (Stage)flowContext.getRegisteredObject("Stage");
        stage.setFullScreen(true);

        mainLabel.setText(Discours.getDiscours());
    }

    @ActionMethod("modifier_button")
    public void mbOnAction() throws VetoException, FlowException {
        flowHandler = (FlowHandler) flowContext.getRegisteredObject("FlowHandler");
        flowHandler.handle("back");
    }

    @ActionMethod("retourner_button")
    public void rbOnAction() throws VetoException, FlowException {
        ApplicationController.counter = 0;
        flowHandler = (FlowHandler) flowContext.getRegisteredObject("FlowHandler");
        flowHandler.handle("back");
    }
}
