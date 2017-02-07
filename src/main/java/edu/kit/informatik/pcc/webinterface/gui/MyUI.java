package edu.kit.informatik.pcc.webinterface.gui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import edu.kit.informatik.pcc.webinterface.datamanagement.AccountDataManager;
import edu.kit.informatik.pcc.webinterface.datamanagement.VideoDataManager;
import edu.kit.informatik.pcc.webinterface.gui.navigation.Menu;

import javax.servlet.annotation.WebServlet;
import java.util.ResourceBundle;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("valo")
public class MyUI extends UI {

    private static ResourceBundle messages = ResourceBundle.getBundle("MessageBundle");
    //attributes
    private HorizontalLayout background;
    private VerticalLayout menuArea;
    private VerticalLayout contentArea;
    private Menu menu;
    private Navigator navigator;

    /**
     * This method is called whenever somebody openes the UI, we do the initialization of
     * our prameters here.
     *
     * @param vaadinRequest the request
     */
    @Override
    protected void init(VaadinRequest vaadinRequest) {

        VideoDataManager.setUI(this);

        initializeGraphicalComponents();

    }


    //methods

    public void initializeGraphicalComponents() {
        background = new HorizontalLayout();
        menuArea = new VerticalLayout();
        contentArea = new VerticalLayout();
        background.setSizeFull();

        AccountDataManager.setAccount(null);
        VideoDataManager.removeVideos();

        LoginView login = new LoginView(this);
        background.addComponent(login);

        setContent(background);
    }

    public void login() {
        //set User after login and add menu to the view
        //set up the menu
        background.removeAllComponents();

        contentArea.setSizeFull();
        navigator = new Navigator(this, contentArea);
        navigator.addView(AccountView.viewID, new AccountView(this));
        navigator.addView(VideoView.viewID, new VideoView());

        navigator.setErrorView(new VideoView());
        this.setNavigator(navigator);

        menu = new Menu(this);
        menu.addMenuItem(messages.getString(AccountView.viewID), AccountView.viewID);
        menu.addMenuItem(messages.getString(VideoView.viewID), VideoView.viewID);
        menu.addLogout();

        menuArea.setHeight("800px");
        menuArea.setWidth("400px");
        menuArea.addComponent(menu);

        menu.addUserMenu(AccountDataManager.getAccount().getMail());

        background.addComponent(menuArea);
        background.addComponent(contentArea);

        navigator.navigateTo(VideoView.viewID);
    }

    public void logout() {
        initializeGraphicalComponents();
    }

    /**
     * The Servlet in which the site runs.
     */
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
