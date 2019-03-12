package com.mycompany.myapp;

import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.UIBuilder;

public class MyApplication {

    private Form current;
    private Resources theme;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");
        Toolbar.setGlobalToolbar(true);
    }

    public void start() {

        UIBuilder ui = new UIBuilder();

        Form HomeForm = ui.createContainer(theme, "Home").getComponentForm();
        Form firstForm = ui.createContainer(theme, "GUI 1").getComponentForm();
        Form secondForm = ui.createContainer(theme, "GUI 2").getComponentForm();
        Form thirdForm = ui.createContainer(theme, "GUI 3").getComponentForm();
        
        HomeForm.getToolbar().addCommandToSideMenu("first form", null, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                firstForm.show();
            }
        });

        HomeForm.getToolbar().addCommandToSideMenu("second form", null, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                secondForm.show();
            }
        });

        HomeForm.getToolbar().addCommandToSideMenu("third form", null, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                thirdForm.show();
            }
        });

        firstForm.getToolbar().addCommandToLeftBar("Back to Home", theme.getImage("back-command.png"), new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                HomeForm.showBack();
            }
        });

        secondForm.getToolbar().addCommandToRightBar("Back to Home", theme.getImage("back-command.png"), new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                HomeForm.showBack();
            }
        });

        thirdForm.getToolbar().addCommandToOverflowMenu("Back to Home", theme.getImage("back-command.png"), new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                HomeForm.showBack();
            }
        });
        HomeForm.show();
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
