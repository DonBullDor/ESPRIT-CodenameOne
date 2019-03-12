package com.mycompany.myapp;

import com.codename1.ui.Image;

public class Contact {

    private String nom;
    private String mail;
    private Image img;

    public Contact(String nom, String mail, Image img) {
        this.nom = nom;
        this.mail = mail;
        this.img = img;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Contact{" + "nom=" + nom + ", mail=" + mail + '}';
    }

}
