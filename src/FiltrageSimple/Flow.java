//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import io.datafx.controller.ViewConfiguration;
import io.datafx.controller.context.ViewMetadata;
import io.datafx.controller.flow.FlowContainer;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowView;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.FlowAction;
import io.datafx.controller.flow.action.FlowBackAction;
import io.datafx.controller.flow.action.FlowLink;
import io.datafx.controller.flow.action.FlowMethodAction;
import io.datafx.controller.flow.action.FlowTaskAction;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.ViewFlowContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Flow {
    private Class<?> startViewControllerClass;
    private Map<Class<?>, Map<String, FlowAction>> viewFlowMap;
    private Map<String, FlowAction> globalFlowMap;
    private ViewConfiguration viewConfiguration;

    public Flow(Class<?> startViewControllerClass, ViewConfiguration viewConfiguration) {
        this.startViewControllerClass = startViewControllerClass;
        this.globalFlowMap = new HashMap();
        this.viewFlowMap = new HashMap();
        this.viewConfiguration = viewConfiguration;
    }

    public Flow(Class<?> startViewControllerClass) {
        this(startViewControllerClass, new ViewConfiguration());
    }

    public ViewConfiguration getViewConfiguration() {
        return this.viewConfiguration;
    }

    public FlowHandler createHandler(ViewFlowContext flowContext) {
        return new FlowHandler(this, flowContext);
    }

    public FlowHandler createHandler() {
        return this.createHandler(new ViewFlowContext());
    }

    public Flow withGlobalAction(String actionId, FlowAction action) {
        this.addGlobalAction(actionId, action);
        return this;
    }

    public Flow withGlobalTaskAction(String actionId, Class<? extends Runnable> actionClass) {
        this.addGlobalAction(actionId, new FlowTaskAction(actionClass));
        return this;
    }

    public Flow withGlobalTaskAction(String actionId, Runnable action) {
        this.addGlobalAction(actionId, new FlowTaskAction(action));
        return this;
    }

    public Flow withGlobalLink(String actionId, Class<?> controllerClass) {
        this.addGlobalAction(actionId, new FlowLink(controllerClass));
        return this;
    }

    public Flow withGlobalBackAction(String actionId) {
        this.addGlobalAction(actionId, new FlowBackAction());
        return this;
    }

    public Flow withAction(Class<?> controllerClass, String actionId, FlowAction action) {
        this.addActionToView(controllerClass, actionId, action);
        return this;
    }

    public Flow withLink(Class<?> fromControllerClass, String actionId, Class<?> toControllerClass) {
        this.addActionToView(fromControllerClass, actionId, new FlowLink(toControllerClass));
        return this;
    }

    public Flow withTaskAction(Class<?> controllerClass, String actionId, Class<? extends Runnable> actionClass) {
        this.addActionToView(controllerClass, actionId, new FlowTaskAction(actionClass));
        return this;
    }

    public Flow withTaskAction(Class<?> controllerClass, String actionId, Runnable action) {
        this.addActionToView(controllerClass, actionId, new FlowTaskAction(action));
        return this;
    }

    public Flow withGlobalCallMethodAction(String actionId, String actionMethodName) {
        this.addGlobalAction(actionId, new FlowMethodAction(actionMethodName));
        return this;
    }

    public Flow withCallMethodAction(Class<?> controllerClass, String actionId, String actionMethodName) {
        this.addActionToView(controllerClass, actionId, new FlowMethodAction(actionMethodName));
        return this;
    }

    public Flow withBackAction(Class<?> controllerClass, String actionId) {
        this.addActionToView(controllerClass, actionId, new FlowBackAction());
        return this;
    }

    public Flow addActionToView(Class<?> controllerClass, String actionId, FlowAction action) {
        if(this.viewFlowMap.get(controllerClass) == null) {
            this.viewFlowMap.put(controllerClass, new HashMap());
        }

        ((Map)this.viewFlowMap.get(controllerClass)).put(actionId, action);
        return this;
    }

    public Flow addGlobalAction(String actionId, FlowAction action) {
        this.globalFlowMap.put(actionId, action);
        return this;
    }

    public FlowAction getGlobalActionById(String actionId) {
        return (FlowAction)this.globalFlowMap.get(actionId);
    }

    public Class<?> getStartViewControllerClass() {
        return this.startViewControllerClass;
    }

    public <U> void addActionsToView(FlowView<U> newView) {
        Map viewActionMap = (Map)this.viewFlowMap.get(newView.getViewContext().getController().getClass());
        if(viewActionMap != null) {
            Iterator var3 = viewActionMap.keySet().iterator();

            while(var3.hasNext()) {
                String actionId = (String)var3.next();
                newView.addAction(actionId, (FlowAction)viewActionMap.get(actionId));
            }
        }

        Method[] var8 = newView.getViewContext().getController().getClass().getMethods();
        int var9 = var8.length;

        for(int var5 = 0; var5 < var9; ++var5) {
            Method method = var8[var5];
            ActionMethod actionMethod = (ActionMethod)method.getAnnotation(ActionMethod.class);
            if(actionMethod != null) {
                newView.addAction(actionMethod.value(), new FlowMethodAction(method.getName()));
            }
        }

    }

    public StackPane start() throws FlowException {
        return (StackPane)this.start(new DefaultFlowContainer());
    }

    public void startInStage(Stage stage) throws FlowException {
        FlowHandler handler = this.createHandler();
        stage.setScene(new Scene((Parent)handler.start(new DefaultFlowContainer())));
        handler.getCurrentViewMetadata().addListener((e) -> {
            stage.titleProperty().unbind();
            ViewMetadata metadata = (ViewMetadata)handler.getCurrentViewMetadata().get();
            if(metadata != null) {
                stage.titleProperty().bind(metadata.titleProperty());
            }

        });
        stage.titleProperty().unbind();
        ViewMetadata metadata = (ViewMetadata)handler.getCurrentViewMetadata().get();
        if(metadata != null) {
            stage.titleProperty().bind(metadata.titleProperty());
        }

        stage.show();
    }

    public Tab startInTab() throws FlowException {
        return this.startInTab(new DefaultFlowContainer());
    }

    public <T extends Node> Tab startInTab(FlowContainer<T> container) throws FlowException {
        return this.createHandler().startInTab(container);
    }

    public <T extends Node> T start(FlowContainer<T> flowContainer) throws FlowException {
        this.createHandler().start(flowContainer);
        return flowContainer.getView();
    }
}

