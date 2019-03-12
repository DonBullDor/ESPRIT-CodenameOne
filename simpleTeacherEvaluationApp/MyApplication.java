package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Slider;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.UIBuilder;
import java.io.IOException;
import javafx.scene.input.KeyCode;

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

        Form mainForm = ui.createContainer(theme, "GUI 1").getComponentForm();
        mainForm.show();

        Form noteToAffectForm = ui.createContainer(theme, "GUI 2").getComponentForm();

        ImageViewer teacherImage = (ImageViewer) ui.findByName("ImageViewer", noteToAffectForm);
        Label teacherNoteLabel = (Label) ui.findByName("Label", noteToAffectForm);
        Slider noteSlider = (Slider) ui.findByName("Slider", noteToAffectForm);
        Button submitButton = (Button) ui.findByName("Button", noteToAffectForm);

        noteSlider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                teacherNoteLabel.setText("Note : " + noteSlider.getProgress());
            }
        });
        ComboBox teacherComboBox = (ComboBox) ui.findByName("ComboBox", mainForm);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (Dialog.show("Confirmation", "vous avez donne la note de "
                        + noteSlider.getProgress() + " a M/Mme "
                        + teacherComboBox.getSelectedItem(),
                        "Ok", null)) {
                    mainForm.showBack();
                }
            }
        });

        teacherComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (teacherComboBox.getSelectedItem().toString().equals("Nader Rahman")) {
                    teacherImage.setImage(theme.getImage("nader.jpg"));
                }

                if (teacherComboBox.getSelectedItem().toString().equals("Bassem Htira")) {
                    teacherImage.setImage(theme.getImage("bassem.jpg"));
                }

                if (teacherComboBox.getSelectedItem().toString().equals("Sana ben Fadhel")) {
                    teacherImage.setImage(theme.getImage("sana.jpg"));
                }

                noteToAffectForm.show();
            }
        });

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
