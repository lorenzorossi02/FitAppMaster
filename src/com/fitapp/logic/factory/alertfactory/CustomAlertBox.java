package com.fitapp.logic.factory.alertfactory;


import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.StageStyle;

import java.io.PrintWriter;
import java.io.StringWriter;

public class CustomAlertBox extends Alert {

    // Default case, for exception not mapped yet.

    public CustomAlertBox(Exception e) {
        super(AlertType.ERROR);
        this.setTitle("Exception not mapped");
        this.setHeaderText("This exception is not mapped");
        this.setContentText("If you want to help our team, please contact us\n"
                + "and send the text in the dialog below.");
        setDialog(this, e);
        display();
    }

    // For mapped exception

    public CustomAlertBox(AlertType alertType, Exception e) {
        super(alertType);
        String title = e.getMessage();
        String header = e.getCause().toString();
        this.setTitle(title);
        this.setHeaderText(header);
        setDialog(this, e);
        display();
    }

    // Default case, for user warnings
    public CustomAlertBox(AlertType type, String title, String header, String content) {
        super(type);
        setAlert(title, header, content);
    }


    // Edit alert style and display it.
    public void display() {
        this.initStyle(StageStyle.UNDECORATED);
        this.showAndWait();
    }

    public void display(Button button) {
        this.initStyle(StageStyle.UNDECORATED);
        this.showAndWait().ifPresent(btnType -> {
            if (btnType == ButtonType.OK) {
                button.fireEvent(new ActionEvent());
            } else if (btnType == ButtonType.CANCEL) {
                this.close();
            }
        });
    }

    // Setting up the dialog in case of unmapped exceptions.

    public void setDialog(CustomAlertBox alert, Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        TextArea area = new TextArea(sw.toString());
        alert.getDialogPane().setExpandableContent(area);
    }

    public void setAlert(String title, String header, String content) {
        this.setTitle(title);
        this.setHeaderText(header);
        this.setContentText(content);
    }

}
