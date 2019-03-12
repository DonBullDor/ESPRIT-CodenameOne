package com.codename1.db;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

import java.io.IOException;

public class DBDemo {

    private Form currentForm;
    private Database db;
    private String[] modulesTab = new String[]{"Java", "UML", "Networks", "C++", "Linux"};
    private int[] coefficientModules = new int[]{3, 2, 2, 1, 3};

    public void init(Object context) {
        try {
            Resources theme = Resources.openLayered("/nativeJ2METheme");
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void start() {
        try {
            boolean dbExistance = Database.exists("MyDB2.db");
            db = Database.openOrCreate("MyDB2.db");

            if (db == null) {
                Form dbErrorForm = new Form("SQLite No Supported");
                SpanLabel ErrorMessage = new SpanLabel("SQLite is not supported on this platform");

                dbErrorForm.addComponent(ErrorMessage);
                dbErrorForm.show();
                return;
            }
            if (!dbExistance) {
                db.execute("create table module (id INTEGER PRIMARY KEY,name text,num double);");
                for (int i = 0; i < modulesTab.length; i++) {
                    db.execute("insert into module (name,num) values (?,?);", new String[]{modulesTab[i], "" + coefficientModules[i]});
                }
            }

            final Form mainForm = new Form("Add/Delete Modules");
            mainForm.setLayout(new BorderLayout());
            mainForm.setScrollable(false);
            mainForm.addComponent(BorderLayout.CENTER, getItems());

            Container mainFormContainer = new Container(new GridLayout(1, 4));

            Button addModulesButton = new Button(new Command("Show") {
                
                public void actionPerformed(ActionEvent evt) {
                    final Container addModuleContainer = new ComponentGroup();
                    addModuleContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

                    for (int i = 0; i < modulesTab.length; i++) {
                        CheckBox modulesCheckBoxMainForm = new CheckBox(modulesTab[i] + " " + coefficientModules[i]);
                        addModuleContainer.addComponent(modulesCheckBoxMainForm);
                    }

                    Command cmd = Dialog.show("Add Modules", addModuleContainer, new Command[]{new Command("Add"), new Command("Cancel")});

                    if (cmd.getCommandName().equals("Add")) {
                        int countModulesToAdd = addModuleContainer.getComponentCount();
                        for (int i = 0; i < countModulesToAdd; i++) {
                            CheckBox modulesToAddCheckbox = (CheckBox) addModuleContainer.getComponentAt(i);
                            if (modulesToAddCheckbox.isSelected()) {
                                try {
                                    db.execute("insert into module (name,num) values (?,?);", new String[]{modulesTab[i], "" + coefficientModules[i]});
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }

                    Container rows = (Container) mainForm.getContentPane().getComponentAt(0);

                    try {
                        mainForm.replace(rows, getItems(), CommonTransitions.createEmpty());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    mainForm.revalidate();
                }
            });
            mainFormContainer.addComponent(addModulesButton);

            Button removeModulesButton = new Button(new Command("Remove") {
                
                public void actionPerformed(ActionEvent evt) {
                    try {
                        Container modulesContainer = (Container) mainForm.getContentPane().getComponentAt(0);
                        int countModulesToDelete = modulesContainer.getComponentCount();
                        
                        for (int i = 0; i < countModulesToDelete; i++) {
                            CheckBox moduleCheckbox = (CheckBox) modulesContainer.getComponentAt(i);
                            if (moduleCheckbox.isSelected()) {
                                String idModule = (String) moduleCheckbox.getClientProperty("id");
                                db.execute("delete from module where id=" + idModule);
                            }
                        }
                        
                        mainForm.replace(modulesContainer, getItems(), CommonTransitions.createEmpty());
                        mainForm.revalidate();
                        
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            mainFormContainer.addComponent(removeModulesButton);

            mainForm.addComponent(BorderLayout.SOUTH, mainFormContainer);
            mainForm.show();

        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex.getMessage());
        }

    }

    private Container getItems() throws IOException {
        Cursor dbCursor = db.executeQuery("select * from module");

        final Container rows = new ComponentGroup();
        rows.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        rows.setScrollable(true);

        while (dbCursor.next()) {
            Row moduleTableRow = dbCursor.getRow();

            int idModule = moduleTableRow.getInteger(0);
            String moduleName = moduleTableRow.getString(1);
            double numModule = moduleTableRow.getDouble(2);

            CheckBox moduleCheckBox = new CheckBox(idModule + " " + moduleName + " " + numModule);
            moduleCheckBox.putClientProperty("id", "" + idModule);

            rows.addComponent(moduleCheckBox);
        }
        return rows;
    }

    public void stop() {
        currentForm = Display.getInstance().getCurrent();
    }

    public void destroy() {
    }
}
