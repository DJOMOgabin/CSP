import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import FiltrageSimple.FlowHandler;
import FiltrageSimple.Flow;

import com.jfoenix.controls.JFXDecorator;

import controller.ApplicationController;
import controller.ResultController;

/**
 * Created by Sahamene Armel on 10/18/16.
 */

public class Main extends Application {

    @FXMLViewFlowContext
    private ViewFlowContext flowContext;

    private FlowHandler flowHandler;

    @Override
    public void start(Stage primaryStage) throws FlowException {

        //Flow flow = new Flow(ApplicationController.class).withGlobalLink("result" , ResultController.class)
          //                  .withGlobalBackAction("back");
        flowContext = new ViewFlowContext();
        //flowHandler = flow.createHandler(flowContext);

        DefaultFlowContainer container = new DefaultFlowContainer();
        flowHandler.start(container);

        JFXDecorator decorator = new JFXDecorator(primaryStage, container.getView());
        decorator.setCustomMaximize(true);

        Scene scene = new Scene(decorator, 600, 600);
        scene.getStylesheets().add(getClass().getResource("/resources/css/Application.css").toExternalForm());

        flowContext.register("FlowHandler", flowHandler);
        flowContext.register("Stage", primaryStage);
        flowContext.register("Scene", scene);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(false);
        primaryStage.setMaximized(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
