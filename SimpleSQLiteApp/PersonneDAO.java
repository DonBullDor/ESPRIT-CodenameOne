package DAO;

import Entite.Personne;
import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import com.codename1.ui.CheckBox;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.BoxLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class PersonneDAO {

    private Database db;
    boolean databaseExist;

    public PersonneDAO() {
        databaseExist = Database.exists("MyDB1.db");
        try {
            db = Database.openOrCreate("MyDB1.db");
            if (!databaseExist) {
                db.execute("create table temp (id INTEGER PRIMARY KEY,nom text,prenom text);");
            }
            System.out.println(databaseExist);
            System.out.println(db);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void ajouter(Personne p) {
        try {
            System.out.println("tes");

            db.execute("insert into temp (nom,prenom) values ('" + p.getNom() + "','" + p.getPrenom() + "')");
            db.execute("insert into temp (nom,prenom) values (?,?);", new Object[]{p.getNom(), p.getPrenom()});

            System.out.println("ajouter");

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public ArrayList<Personne> getPerson() throws IOException {
        Cursor dbCursor = db.executeQuery("select * from temp");
        ArrayList<Personne> listPersonnes = new ArrayList();
        Personne pers = null;

        while (dbCursor.next()) {
            Row personneTableRow = dbCursor.getRow();
            int idPersonne = personneTableRow.getInteger(0);

            String namePersonne = personneTableRow.getString(1);
            String prenomPersonne = personneTableRow.getString(2);

            pers = new Personne(namePersonne, prenomPersonne, idPersonne);
            listPersonnes.add(pers);
        }

        return listPersonnes;
    }

    public Container getItems() throws IOException {
        Cursor dbCursor2 = db.executeQuery("select * from temp");
        
        final Container personnesContainer = new ComponentGroup();
        personnesContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        personnesContainer.setScrollable(true);
        
        while (dbCursor2.next()) {
            Row personneTableRow = dbCursor2.getRow();

            int idPersonne = personneTableRow.getInteger(0);
            String namePersonne = personneTableRow.getString(1);
            String numPersonne = personneTableRow.getString(2);

            CheckBox personnesCheckBox = new CheckBox(idPersonne + " " + namePersonne + " " + numPersonne);

            personnesContainer.addComponent(personnesCheckBox);
        }
        return personnesContainer;
    }

}
