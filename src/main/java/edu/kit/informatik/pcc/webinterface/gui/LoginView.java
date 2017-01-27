package edu.kit.informatik.pcc.webinterface.gui;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.navigator.View;
import de.steinwedel.messagebox.MessageBox;
import edu.kit.informatik.pcc.webinterface.datamanagement.Account;
import edu.kit.informatik.pcc.webinterface.datamanagement.AccountDataManager;
import sun.misc.MessageUtils;

import java.util.ResourceBundle;

/**
 * Created by chris on 17.01.2017.
 * The View which is showed after every start of the website.
 */
public class LoginView extends VerticalLayout implements View {

    public final static String viewID = "LoginView";
    private TextField mailField;
    private TextField passwordField;
    private Button loginButton;
    private Button registerButton;
    private MyUI ui;

    //Constructor
    public LoginView(MyUI ui) {
        //initialization
        this.ui = ui;
        ResourceBundle messages = ResourceBundle.getBundle("MessageBundle");
        mailField = new TextField(messages.getString(viewID + "mailField"));
        passwordField = new TextField(messages.getString(viewID + "passwordField"));
        loginButton = new Button(messages.getString(viewID + "loginButton"));
        registerButton = new Button(messages.getString(viewID + "registerButton"));

        loginButton.addClickListener(
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        if (AccountDataManager.authenticateAccount(mailField.getValue(), passwordField.getValue())) {
                            ui.login();
                        }
                    }
                }
        );

        registerButton.addClickListener(
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        if (AccountDataManager.createAccount(mailField.getValue(),passwordField.getValue())) {
                            MessageBox.createInfo()
                                    .withMessage(messages.getString(viewID + "registerInfo"))
                                    .open();
                        }
                    }
                }
        );

        this.addComponent(mailField);
        this.addComponent(passwordField);
        this.addComponent(loginButton);
        this.addComponent(registerButton);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
