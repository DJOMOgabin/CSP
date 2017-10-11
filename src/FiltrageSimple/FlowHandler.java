//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.datafx.controller.flow;

import io.datafx.controller.FxmlLoadException;
import io.datafx.controller.ViewConfiguration;
import io.datafx.controller.context.ViewContext;
import io.datafx.controller.context.ViewMetadata;
import io.datafx.controller.flow.FlowContainer;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowView;
import io.datafx.controller.flow.ViewHistoryDefinition;
import io.datafx.controller.flow.action.FlowAction;
import io.datafx.controller.flow.action.FlowLink;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.flow.event.AfterFlowActionEvent;
import io.datafx.controller.flow.event.AfterFlowActionHandler;
import io.datafx.controller.flow.event.BeforeFlowActionEvent;
import io.datafx.controller.flow.event.BeforeFlowActionHandler;
import io.datafx.controller.flow.event.VetoableBeforeFlowActionHandler;
import io.datafx.controller.util.Veto;
import io.datafx.controller.util.VetoException;
import io.datafx.controller.util.VetoHandler;

import java.util.ResourceBundle;
import java.util.UUID;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;

import javax.swing.text.ViewFactory;

import com.sun.corba.se.impl.presentation.rmi.ExceptionHandler;

public class FlowHandler {
    private final ObservableList<ViewHistoryDefinition<?>> controllerHistory;
    private ReadOnlyObjectWrapper<FlowView<?>> currentViewWrapper;
    private ReadOnlyObjectWrapper<FlowContainer> containerWrapper;
    private ReadOnlyObjectWrapper<ViewFlowContext> flowContextWrapper;
    private ReadOnlyObjectWrapper<Flow> flowWrapper;
    private SimpleObjectProperty<BeforeFlowActionHandler> beforeFlowActionHandler;
    private SimpleObjectProperty<AfterFlowActionHandler> afterFlowActionHandler;
    private SimpleObjectProperty<VetoableBeforeFlowActionHandler> vetoableBeforeFlowActionHandler;
    private SimpleObjectProperty<VetoHandler> vetoHandler;
    private ViewConfiguration viewConfiguration;
    private ExceptionHandler exceptionHandler;
    private ReadOnlyObjectWrapper<ViewMetadata> currentViewMetadataWrapper;

    public FlowHandler(Flow flow, ViewFlowContext flowContext) {
        this(flow, flowContext, new ViewConfiguration());
    }

    public FlowHandler(Flow flow, ViewFlowContext flowContext, ViewConfiguration viewConfiguration) {
        this(flow, flowContext, viewConfiguration, ExceptionHandler.getDefaultInstance());
    }

    public FlowHandler(Flow flow, ViewFlowContext flowContext, ViewConfiguration viewConfiguration, ExceptionHandler exceptionHandler) {
        this.flowWrapper = new ReadOnlyObjectWrapper(flow);
        this.viewConfiguration = viewConfiguration;
        this.exceptionHandler = exceptionHandler;
        this.controllerHistory = FXCollections.observableArrayList();
        this.currentViewWrapper = new ReadOnlyObjectWrapper();
        this.containerWrapper = new ReadOnlyObjectWrapper();
        this.flowContextWrapper = new ReadOnlyObjectWrapper(flowContext);
        this.currentViewMetadataWrapper = new ReadOnlyObjectWrapper();
        ((ViewFlowContext)this.flowContextWrapper.get()).currentViewContextProperty().addListener((e) -> {
            this.currentViewMetadataWrapper.set(((ViewFlowContext)this.flowContextWrapper.get()).getCurrentViewContext().getMetadata());
        });
        ((ViewFlowContext)this.flowContextWrapper.get()).register(new FlowActionHandler(this));
    }

    public StackPane start() throws FlowException {
        return (StackPane)this.start(new DefaultFlowContainer());
    }

    public Tab startInTab() throws FlowException {
        return this.startInTab(new DefaultFlowContainer());
    }

    public <T extends Node> Tab startInTab(FlowContainer<T> container) throws FlowException {
        Tab tab = new Tab();
        this.getCurrentViewMetadata().addListener((e) -> {
            tab.textProperty().unbind();
            tab.graphicProperty().unbind();
            tab.textProperty().bind(((ViewMetadata)this.getCurrentViewMetadata().get()).titleProperty());
            tab.graphicProperty().bind(((ViewMetadata)this.getCurrentViewMetadata().get()).graphicsProperty());
        });
        tab.setOnClosed((e) -> {
            try {
                this.destroy();
            } catch (Exception var3) {
                this.exceptionHandler.setException(var3);
            }

        });
        tab.setContent(this.start(container));
        return tab;
    }

    public void destroy() {
    }

    public <T extends Node> T start(FlowContainer<T> container) throws FlowException {
        this.containerWrapper.set(container);
        ((ViewFlowContext)this.flowContextWrapper.get()).register(this);
        if(this.viewConfiguration != null) {
            ((ViewFlowContext)this.flowContextWrapper.get()).register(ResourceBundle.class.toString(), this.viewConfiguration.getResources());
        }

        try {
            FlowView e = new FlowView(ViewFactory.getInstance().createByController(((Flow)this.flowWrapper.get()).getStartViewControllerClass(), (String)null, this.getViewConfiguration(), new Object[]{this.flowContextWrapper.get()}));
            this.setNewView(e, false);
        } catch (FxmlLoadException var3) {
            throw new FlowException(var3);
        }

        return container.getView();
    }

    public ViewConfiguration getViewConfiguration() {
        return this.viewConfiguration;
    }

    public void handle(String actionId) throws VetoException, FlowException {
        FlowAction action = null;
        if(this.getCurrentView() != null) {
            action = this.getCurrentView().getActionById(actionId);
        }

        if(action == null) {
            action = ((Flow)this.flowWrapper.get()).getGlobalActionById(actionId);
        }

        if(action == null) {
            throw new FlowException("Can\'t find an action with id " + actionId);
        } else {
            this.handle(action, actionId);
        }
    }

    public ExceptionHandler getExceptionHandler() {
        return this.exceptionHandler;
    }

    public ViewFlowContext getFlowContext() {
        return (ViewFlowContext)this.flowContextWrapper.get();
    }

    public ReadOnlyObjectProperty<ViewMetadata> getCurrentViewMetadata() {
        return this.currentViewMetadataWrapper.getReadOnlyProperty();
    }

    public ReadOnlyObjectProperty<ViewFlowContext> getFlowContextProperty() {
        return this.flowContextWrapper.getReadOnlyProperty();
    }

    public ReadOnlyObjectProperty<FlowView<?>> getCurrentViewProperty() {
        return this.currentViewWrapper.getReadOnlyProperty();
    }

    public ReadOnlyObjectProperty<FlowContainer> getContainerProperty() {
        return this.containerWrapper.getReadOnlyProperty();
    }

    public FlowView<?> getCurrentView() {
        return (FlowView)this.currentViewWrapper.get();
    }

    public ViewContext<?> getCurrentViewContext() {
        return this.getCurrentView().getViewContext();
    }

    public Class<?> getCurrentViewControllerClass() {
        return this.getCurrentViewContext().getController().getClass();
    }

    public void handle(FlowAction action, String actionId) throws FlowException, VetoException {
        if(this.beforeFlowActionHandler != null && this.beforeFlowActionHandler.getValue() != null) {
            ((BeforeFlowActionHandler)this.beforeFlowActionHandler.getValue()).handle(new BeforeFlowActionEvent(actionId, action, (ViewFlowContext)this.flowContextWrapper.get()));
        }

        if(this.vetoableBeforeFlowActionHandler != null && this.vetoableBeforeFlowActionHandler.getValue() != null) {
            try {
                ((VetoableBeforeFlowActionHandler)this.vetoableBeforeFlowActionHandler.getValue()).handle(new BeforeFlowActionEvent(actionId, action, (ViewFlowContext)this.flowContextWrapper.get()));
            } catch (Veto var4) {
                if(this.vetoHandler != null && this.vetoHandler.getValue() != null) {
                    ((VetoHandler)this.vetoHandler.get()).onVeto(var4);
                }

                throw new VetoException(var4);
            }
        }

        action.handle(this, actionId);
        if(this.afterFlowActionHandler != null && this.afterFlowActionHandler.getValue() != null) {
            ((AfterFlowActionHandler)this.afterFlowActionHandler.getValue()).handle(new AfterFlowActionEvent(actionId, action, (ViewFlowContext)this.flowContextWrapper.get()));
        }

    }

    public <U> ViewContext<U> setNewView(FlowView<U> newView, boolean addOldToHistory) throws FlowException {
        if(this.getCurrentView() != null && addOldToHistory) {
            ViewHistoryDefinition oldView = new ViewHistoryDefinition(this.getCurrentView().getViewContext().getController().getClass(), "", (Node)null);
            this.controllerHistory.add(0, oldView);
        }

        ((Flow)this.flowWrapper.get()).addActionsToView(newView);
        FlowView oldView1 = this.getCurrentView();
        if(oldView1 != null) {
            ViewContext lastViewContext = oldView1.getViewContext();
            if(lastViewContext != null) {
                try {
                    lastViewContext.destroy();
                } catch (Exception var6) {
                    throw new FlowException("Last ViewContext can\'t be destroyed!", var6);
                }
            }
        }

        this.currentViewWrapper.set(newView);
        ((ViewFlowContext)this.flowContextWrapper.get()).setCurrentViewContext(this.getCurrentView().getViewContext());
        ((FlowContainer)this.containerWrapper.get()).setViewContext(this.getCurrentView().getViewContext());
        return newView.getViewContext();
    }

    public void navigateBack() throws VetoException, FlowException {
        this.navigateToHistoryIndex(0);
    }

    public ObservableList<ViewHistoryDefinition<?>> getControllerHistory() {
        return FXCollections.unmodifiableObservableList(this.controllerHistory);
    }

    public void navigateToHistoryIndex(int index) throws VetoException, FlowException {
        this.handle(new FlowLink(((ViewHistoryDefinition)this.controllerHistory.remove(index)).getControllerClass(), false), "backAction-" + UUID.randomUUID().toString());
    }

    public void navigateTo(Class<?> controllerClass) throws VetoException, FlowException {
        this.handle(new FlowLink(controllerClass), "navigateAction-" + UUID.randomUUID().toString());
    }

    public void attachAction(Node node, Runnable action) {
        if(node instanceof ButtonBase) {
            ((ButtonBase)node).setOnAction((e) -> {
                action.run();
            });
        } else {
            node.setOnMouseClicked((ev) -> {
                if(ev.getClickCount() > 1) {
                    action.run();
                }

            });
        }

    }

    public void attachAction(MenuItem menuItem, Runnable action) {
        menuItem.setOnAction((e) -> {
            action.run();
        });
    }

    public void attachEventHandler(Node node, String actionId) {
        if(node instanceof ButtonBase) {
            ((ButtonBase)node).setOnAction((e) -> {
                this.handleActionWithExceptionHandler(actionId);
            });
        } else {
            node.setOnMouseClicked((e) -> {
                if(e.getClickCount() > 1) {
                    this.handleActionWithExceptionHandler(actionId);
                }

            });
        }

    }

    public void attachBackEventHandler(MenuItem menuItem) {
        menuItem.setOnAction((e) -> {
            this.handleBackActionWithExceptionHandler();
        });
    }

    public void attachBackEventHandler(Node node) {
        if(node instanceof ButtonBase) {
            ((ButtonBase)node).setOnAction((e) -> {
                this.handleBackActionWithExceptionHandler();
            });
        } else {
            node.setOnMouseClicked((e) -> {
                if(e.getClickCount() > 1) {
                    this.handleBackActionWithExceptionHandler();
                }

            });
        }

    }

    public void attachEventHandler(MenuItem menuItem, String actionId) {
        menuItem.setOnAction((e) -> {
            this.handleActionWithExceptionHandler(actionId);
        });
    }

    private void handleActionWithExceptionHandler(String id) {
        try {
            this.handle(id);
        } catch (FlowException | VetoException var3) {
            this.getExceptionHandler().setException(var3);
        }

    }

    private void handleBackActionWithExceptionHandler() {
        try {
            this.navigateBack();
        } catch (FlowException | VetoException var2) {
            this.getExceptionHandler().setException(var2);
        }

    }
}

