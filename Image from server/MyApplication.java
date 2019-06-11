package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
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
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;

public class MyApplication {

    private Form current;
    private Resources theme;
    private EncodedImage encodedImage;
    private ImageViewer imageViewer;
    private Image imgage;
    private String imageUrl = "http://localhost/mobile/img1.jpg";

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
        try {
            encodedImage = encodedImage.create("/load.png");
        } catch (IOException ex) {

        }
        imgage = URLImage.createToStorage(encodedImage, imageUrl, imageUrl, URLImage.RESIZE_SCALE);
        imageViewer = new ImageViewer(imgage);
        Form hi = new Form("Hi World", BoxLayout.y());
        hi.add(imageViewer);
        hi.show();
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
