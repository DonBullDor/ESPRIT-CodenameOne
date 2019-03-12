package com.mycompany.myapp;

import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Image;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import java.io.IOException;
import java.util.ArrayList;

public class MyApplication {

    private Form current;
    private Resources theme;
    Image img1, img2, img3;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");

        Toolbar.setGlobalToolbar(true);
    }

    public void start() {

        try {
            img1 = Image.createImage("/a.png");
            img3 = Image.createImage("/b.png");
            img2 = Image.createImage("/c.png");
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
        Contact firstContact = new Contact("sana", "sana.es@gmail", img1);
        Contact secondContact = new Contact("ons", "ons.es@gmail", img2);
        Contact thirdContact = new Contact("sinda", "sinda.es@gmail", img3);
        
        ArrayList<Contact> contactsList = new ArrayList<>();
        contactsList.add(firstContact);
        contactsList.add(secondContact);
        contactsList.add(thirdContact);

        Container contactListContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        for (Contact contactIterator : contactsList) {
            contactListContainer.add(addContact(contactIterator));
        }

        Form contactForm = new Form("Contacts");
        contactForm.add(contactListContainer);    
        contactForm.show();
    }

    public Container addContact(Contact c) {
        Label userMail = new Label(c.getMail());
        Button userNameButton = new Button(c.getNom());
        Label userImage = new Label(c.getImg());

        Container contactContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        contactContainer.add(userMail);
        contactContainer.add(userNameButton);
        Container cnt2 = BorderLayout.center(contactContainer);
        cnt2.add(BorderLayout.EAST, userImage);

        userNameButton.addActionListener(e -> {
            System.out.println("cliked" + c);
        });
        cnt2.setLeadComponent(userNameButton);
        return cnt2;
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
