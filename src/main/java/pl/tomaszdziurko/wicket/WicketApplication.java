package pl.tomaszdziurko.wicket;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import pl.tomaszdziurko.wicket.service.CookieService;
import pl.tomaszdziurko.wicket.service.SessionProvider;
import pl.tomaszdziurko.wicket.service.UserService;
import pl.tomaszdziurko.wicket.view.HomePage;
import pl.tomaszdziurko.wicket.view.LoginPage;

public class WicketApplication extends WebApplication {

    private UserService userService = new UserService();
    private CookieService cookieService = new CookieService();
    private SessionProvider sessionProvider = new SessionProvider(userService, cookieService);

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
        return sessionProvider.createNewSession(request);
    }



    public UserService getUserService() {
        return userService;
    }

    public CookieService getCookieService() {
        return cookieService;
    }

    public SessionProvider getSessionProvider() {
        return sessionProvider;
    }


}
