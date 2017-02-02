package edu.kit.informatik.pcc.webinterface.gui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import edu.kit.informatik.pcc.webinterface.datamanagement.AccountDataManager;

import java.util.ResourceBundle;

/**
 * Created by chris on 17.01.2017.
 * This View shows account data and options to change
 */
public class AccountView extends VerticalLayout implements View{

    //attributes
    public final static String viewID = "AccountView";
    private Label mailLabel;
    private TextField mailChangeField;
    private TextField passwordChangeField;
    private TextField passwordField;
    private Button changeButton;

    //constructors
    public AccountView(MyUI ui) {
        //initialization
        ResourceBundle messages = ResourceBundle.getBundle("MessageBundle");
        String mail = AccountDataManager.getAccount().getMail();
        mailLabel = new Label(mail);
        mailChangeField = new TextField(messages.getString(viewID + "mailChangeField"));
        passwordChangeField = new TextField(messages.getString(viewID + "passwordChangeField"));
        passwordField = new TextField(messages.getString(viewID + "passwordField"));
        changeButton = new Button(messages.getString(viewID + "changeButton"));

        changeButton.addClickListener(
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        if (AccountDataManager.changeAccount(passwordField.getValue(), mailChangeField.getValue(), passwordChangeField.getValue())) {
                            ui.init(null);
                        }
                    }
                }
        );
    }

    //methods
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        this.addComponent(mailLabel);
        this.addComponent(mailChangeField);
        this.addComponent(passwordChangeField);
        this.addComponent(passwordField);
        this.addComponent(changeButton);
    }
}
