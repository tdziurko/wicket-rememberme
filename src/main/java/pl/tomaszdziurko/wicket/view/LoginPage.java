package pl.tomaszdziurko.wicket.view;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import pl.tomaszdziurko.wicket.UserSession;
import pl.tomaszdziurko.wicket.WicketApplication;
import pl.tomaszdziurko.wicket.model.User;
import pl.tomaszdziurko.wicket.service.CookieService;
import pl.tomaszdziurko.wicket.service.UserService;

import static pl.tomaszdziurko.wicket.service.SessionProvider.REMEMBER_ME_DURATION_IN_DAYS;
import static pl.tomaszdziurko.wicket.service.SessionProvider.REMEMBER_ME_LOGIN_COOKIE;
import static pl.tomaszdziurko.wicket.service.SessionProvider.REMEMBER_ME_PASSWORD_COOKIE;

public class LoginPage extends BasePage {

    private String login;
    private String password;
    private boolean rememberMe;

    public LoginPage() {

        Form<Void> loginForm = new Form<Void>("form");
        add(loginForm);

        loginForm.add(new FeedbackPanel("feedback"));
        loginForm.add(new RequiredTextField<String>("login", new PropertyModel<String>(this, "login")));
        loginForm.add(new PasswordTextField("password", new PropertyModel<String>(this, "password")));
        loginForm.add(new CheckBox("rememberMe", new PropertyModel<Boolean>(this, "rememberMe")));

        Button submit = new Button("submit") {
            @Override
            public void onSubmit() {
                UserService userService = WicketApplication.get().getUserService();

                User user = userService.findByLoginAndPassword(login, password);

                if(user == null) {
                    error("Invalid login and/or password. Please try again.");
                }
                else {
                    UserSession.get().setUser(user);

                    if(rememberMe) {
                        CookieService cookieService = WicketApplication.get().getCookieService();
                        cookieService.saveCookie(getResponse(), REMEMBER_ME_LOGIN_COOKIE, user.getLogin(), REMEMBER_ME_DURATION_IN_DAYS);
                        cookieService.saveCookie(getResponse(), REMEMBER_ME_PASSWORD_COOKIE, user.getPassword(), REMEMBER_ME_DURATION_IN_DAYS);
                    }

                    setResponsePage(HomePage.class);
                }
            }
        };

        loginForm.add(submit);
    }
}
