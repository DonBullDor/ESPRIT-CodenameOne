package com.mycompany.myapp;

import DAO.PersonneDAO;
import Entite.Personne;
import com.codename1.db.Cursor;
import com.codename1.db.Row;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.UIBuilder;
import java.io.IOException;

public class MyApplication {

    private Form current;
    private Resources theme;
    Form menuForm, addEntriesForm, showEntriesForm;
    TextField nameToAdd, lastnameToAdd;
    Button addEntriesButton, addToDatabaseButton, showEntriesButton;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature, uncomment if you have a pro subscription
        // Log.bindCrashProtection(true);
    }

    public void start() {
        UIBuilder ui = new UIBuilder();

        Container menuFormContainer = ui.createContainer(theme, "GUI 1");
        Container addEntriesFormContainer = ui.createContainer(theme, "GUI 2");

        menuForm = (Form) menuFormContainer;
        addEntriesForm = (Form) addEntriesFormContainer;
        showEntriesForm = new Form("Afficher");

        nameToAdd = (TextField) ui.findByName("nom", addEntriesFormContainer);
        lastnameToAdd = (TextField) ui.findByName("preno", addEntriesFormContainer);
        addEntriesButton = (Button) ui.findByName("Add", menuFormContainer);
        addToDatabaseButton = (Button) ui.findByName("Add", addEntriesFormContainer);
        showEntriesButton = (Button) ui.findByName("Show", menuFormContainer);
        Button backToMenuButton = new Button("back to menu");
        
        showEntriesForm.add(backToMenuButton);
        
        backToMenuButton.addActionListener(e -> menuForm.show());
        
        addEntriesButton.addActionListener(e -> addEntriesForm.show());
        
        addToDatabaseButton.addActionListener(e -> {
            Personne p1 = new Personne(nameToAdd.getText(), lastnameToAdd.getText());
            PersonneDAO pdao = new PersonneDAO();
            pdao.ajouter(p1);
            menuForm.showBack();
            showEntriesForm.revalidate();
        });

        menuForm.show();

        showEntriesButton.addActionListener(e -> {
            PersonneDAO pdao = new PersonneDAO();
            try {
                showEntriesForm.add(pdao.getItems());
                System.out.println(pdao.getPerson());
            } catch (IOException ex) {
                System.out.println(ex);
            }
            showEntriesForm.show();
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
