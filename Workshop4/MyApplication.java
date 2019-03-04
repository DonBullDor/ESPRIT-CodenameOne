package workshop4;

import static com.codename1.ui.CN.*;

import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.ui.*;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UITimer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import com.codename1.io.NetworkEvent;

public class MyApplication {

    private Form current;
    private Resources theme;

    public void init(Object context) {
        updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");

        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);

        addNetworkErrorListener(err -> {
            // prevent the event from propagating
            err.consume();
            if (err.getError() != null) {
                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error", "There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK", null);
        });
    }

    public void start() {
        Form loginForm = new Form("login", BoxLayout.y());

        TextField loginTextField = new TextField("", "username", 20, TextField.ANY);
        TextField passwordTextField = new TextField("", "password", 20, TextField.PASSWORD);
        Button loginButton = new Button("Login");
        loginForm.add(loginTextField);
        loginForm.add(passwordTextField);
        loginForm.add(loginButton);

        ConnectionRequest connectionRequest = new ConnectionRequest();

        loginButton.addActionListener(evt -> {
            connectionRequest.setPost(false);
            connectionRequest.addArgument("name", loginTextField.getText());
            connectionRequest.addArgument("password", passwordTextField.getText());
            connectionRequest.setUrl("http://127.0.0.1/Connections/loginResponse.php?login=" + loginTextField.getText()
                    + "&password=" + passwordTextField.getText());

            connectionRequest.addResponseListener(evt1 -> {
                String textResponse = new String(connectionRequest.getResponseData(), StandardCharsets.UTF_8);
                if (textResponse.equals("welcome")) {
                    Form postLoginForm = new Form("Resultat");
                    postLoginForm.add(new Label("Welcome " + loginTextField.getText()));
                    postLoginForm.show();
                } else {
                    Dialog.show("Error", "Invalid", null, "OK");
                }
            });
            NetworkManager.getInstance().addToQueueAndWait(connectionRequest);
        });

        loginForm.show();
    }

    public void stop() {
        current = getCurrentForm();
        if (current instanceof Dialog) {
            ((Dialog) current).dispose();
            current = getCurrentForm();
        }
    }

    public void destroy() {
    }

}
