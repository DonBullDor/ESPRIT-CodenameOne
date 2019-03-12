package com.mycompany.myapp;

import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Container;
import com.codename1.ui.Tabs;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.UIBuilder;

public class MyApplication {

    private Form current;
    private Resources theme;
    Container tab1Container, tab2Container, tab3Container;
    Tabs tab;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");
        Toolbar.setGlobalToolbar(true);
    }

    public void start() {
  
        UIBuilder ui = new UIBuilder();
        tab1Container = ui.createContainer(theme, "GUI 1");
        tab2Container = ui.createContainer(theme, "GUI 2");
        tab3Container = ui.createContainer(theme, "GUI 3");
        
        Form mainFormContainTab = new Form("Hi World", new BorderLayout());
        
        tab = new Tabs();
        tab.addTab("Tab1", tab1Container);
        tab.addTab("Tab2", tab2Container);
        tab.addTab("Tab3", tab3Container);
        
        mainFormContainTab.add(BorderLayout.CENTER, tab);       
        mainFormContainTab.show();
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
