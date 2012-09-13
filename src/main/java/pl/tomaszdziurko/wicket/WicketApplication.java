package pl.tomaszdziurko.wicket;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import pl.tomaszdziurko.wicket.service.UserService;
import pl.tomaszdziurko.wicket.view.HomePage;
import pl.tomaszdziurko.wicket.view.LoginPage;

public class WicketApplication extends WebApplication {

    private UserService userService = new UserService();

    @Override
    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }

    @Override
    public void init() {
        super.init();

        mountPage("login", LoginPage.class);
    }

    public static WicketApplication get() {
        return (WicketApplication) WebApplication.get();
    }

    @Override
    public Session newSession(Request request, Response response) {
        return new UserSession(request);
    }



    public UserService getUserService() {
        return userService;
    }


}
