package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.components.OnOffSwitch;
import com.codename1.components.SpanLabel;
import static com.codename1.ui.CN.*;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.ui.Toolbar;
import java.io.IOException;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.io.NetworkEvent;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Image;
import com.codename1.ui.Slider;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.UIBuilder;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename
 * One</a> for the purpose of building native mobile applications using Java.
 */
public class MyApplication {

    private Form current;
    private Resources theme;

    public void init(Object context) {
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
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
        UIBuilder ui = new UIBuilder();
        UIBuilder.registerCustomComponent("ImageViewer", ImageViewer.class);
        UIBuilder.registerCustomComponent("ComboBox", ComboBox.class);

        Form mainForm = ui.createContainer(theme, "GUI 1").getComponentForm();
        Form secondaryForm = ui.createContainer(theme, "profil").getComponentForm();

        ComboBox teachers = (ComboBox) ui.findByName("comboteach", mainForm);
        ImageViewer image = (ImageViewer) ui.findByName("Imageteach", secondaryForm);
        Label note = (Label) ui.findByName("note", secondaryForm);
        Slider sliderNote = (Slider) ui.findByName("Slider", secondaryForm);
        Button btn = (Button) ui.findByName("Button", secondaryForm);

        teachers.addActionListener(evt -> {
            String selected = teachers.getSelectedItem().toString();
            if (selected.equals("teacher 1")) {
                image.setImage(theme.getImage("Picture1.png"));
            } else if (selected.equals("teacher 2")) {
                image.setImage(theme.getImage("Picture2.jpg"));
            } else {
                image.setImage(theme.getImage("Picture3.png"));
            }

            secondaryForm.getToolbar().addCommandToSideMenu("Home", null, evt1 -> mainForm.showBack());
            secondaryForm.show();
        });
        sliderNote.addActionListener(evt -> note.setText(sliderNote.getProgress() + ""));
        btn.addActionListener(evt -> Dialog.show("NOTE!", "Confirmer la note " + sliderNote.getProgress(), "cancel", "ok !!"));
        mainForm.show();
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
