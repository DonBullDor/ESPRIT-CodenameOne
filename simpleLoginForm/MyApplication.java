package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Button;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.UIBuilder;

public class MyApplication {

    private Form current;
    private Resources theme;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature, uncomment if you have a pro subscription
        // Log.bindCrashProtection(true);
    }

    public void start() {

        UIBuilder ui = new UIBuilder();
        UIBuilder.registerCustomComponent("ImageViewer", ImageViewer.class);
        UIBuilder.registerCustomComponent("SpanLabel", SpanLabel.class);

        Form loginForm = (Form) ui.createContainer(theme, "Login").getComponentForm();
        Form homeForm = (Form) ui.createContainer(theme, "Home").getComponentForm();
        Form aboutForm = (Form) ui.createContainer(theme, "About").getComponentForm();
               
        ImageViewer profileImage = (ImageViewer) ui.findByName("ImageViewer", aboutForm);
        SpanLabel informationAboutUser = (SpanLabel) ui.findByName("SpanLabel", aboutForm);

        homeForm.getToolbar().addCommandToSideMenu("Login", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                loginForm.show();
            }
        });
        homeForm.getToolbar().addCommandToSideMenu("About", null, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                profileImage.setImage(theme.getImage("nader.jpg"));
                informationAboutUser.setText("some informations  \n here");
                aboutForm.show();
            }
        });

        Label postLoginText = (Label) ui.findByName("Label", homeForm);

        aboutForm.getToolbar().addCommandToOverflowMenu("Logout", null, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                loginForm.showBack();
            }
        });
        
        TextField loginTexField = (TextField) ui.findByName("tfLogin", loginForm);
        TextField passwordTexfield = (TextField) ui.findByName("tfPass", loginForm);
        Button loginButton = (Button) ui.findByName("btOk", loginForm);
        loginButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {

                if (loginTexField.getText().equals("nader") && passwordTexfield.getText().equals("nader")) {
                    homeForm.show();

                    postLoginText.setText("Welcome " + loginTexField.getText());

                } else {
                    Dialog.show("Error !", "Login ou mot de passe incorrect !", "OK", null);

                }

            }
        });

        loginForm.show();

    }

    public void stop() {
        current = Display.getInstance().getCurrent();
        if (current instanceof Dialog) {
            ((Dialog) current).dispose();
            current = Display.getInstance().getCurrent();
        }
    }

    public void destroy() {
    }

}
