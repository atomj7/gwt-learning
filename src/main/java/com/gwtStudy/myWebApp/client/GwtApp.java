package com.gwtStudy.myWebApp.client;

import com.gwtStudy.myWebApp.shared.FieldValidator;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


public class GwtApp implements EntryPoint {

    final Button confirmButton = new Button("✅");
    final TextBox nameField = new TextBox();
    final Label errorLabel = new Label();
    final Label helloLabel = new Label();

    VerticalPanel dialogVPanel = new VerticalPanel();
    final DialogBox dialogBox = new DialogBox();
    final HTML serverResponseHtml = new HTML();
    final Label sendToServerLabel = new Label();
    final Button closeButton = new Button("❌");

    private final GwtAppServiceIntfAsync gwtAppService = GWT.create(GwtAppServiceIntf.class);

    @Override
    public void onModuleLoad() {
        helloLabel.setText("Alexanders first app on gwt");

        final Label usernameLabel = new Label();
        usernameLabel.setText("Username: ");

        RootPanel.get("helloId").add(helloLabel);

        RootPanel.get("usernameLabelId").add(usernameLabel);
        RootPanel.get("usernameId").add(nameField);

        RootPanel.get("confirmButtonId").add(confirmButton);
        RootPanel.get("errorLabelContainer").add(errorLabel);

        dialogBox.setText("Remote procedure call from server");
        dialogBox.setAnimationEnabled(true);

        closeButton.getElement().setId("closeButtonId");

        dialogVPanel.addStyleName("dialogVPanel");
        dialogVPanel.add(new HTML("<b> Отправленные поля на сервер: </b>"));
        dialogVPanel.add(sendToServerLabel);
        dialogVPanel.add(new HTML("<br><b>Ответ сервера: </b>"));
        dialogVPanel.add(serverResponseHtml);
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        dialogVPanel.add(closeButton);
        dialogBox.setWidget(dialogVPanel);

        confirmButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                confirmButton.setEnabled(false);
                sendInfoToServer();
            }
        });

        nameField.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent keyUpEvent) {
                if (keyUpEvent.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    sendInfoToServer();
                }
            }
        });

        closeButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                dialogBox.hide();
                confirmButton.setEnabled(true);
                confirmButton.setFocus(true);
            }
        });
    }

    private void sendInfoToServer() {
        errorLabel.setText("");
        if (!FieldValidator.isValidData(nameField.getText())) {
            errorLabel.setText("Имя должно содержать больше трех символов");
            confirmButton.setEnabled(true);
            return;
        }
        sendToServerLabel.setText(nameField.getText());
        confirmButton.setEnabled(false);
        serverResponseHtml.setText("");
        gwtAppService.gwtAppCallServer(nameField.getText(), new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                dialogBox.setText("Remote Procedure Call - Failure");
                serverResponseHtml.addStyleName("serverResponseLabelError");
                serverResponseHtml.setHTML("ERROR ON SERVER");
                dialogBox.center();
                closeButton.setFocus(true);
            }

            public void onSuccess(String result) {
                dialogBox.setText("Remote Procedure Call");
                serverResponseHtml.removeStyleName("serverResponseLabelError");
                serverResponseHtml.setHTML(result);
                dialogBox.center();
                closeButton.setFocus(true);
            }
        });
    }
}
