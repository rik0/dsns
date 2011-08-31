/* Copyright */

package it.unipr.aotlab.dsns;

import org.gudy.azureus2.ui.swt.plugins.UISWTViewEvent;
import org.gudy.azureus2.ui.swt.plugins.UISWTViewEventListener;

/**
 * User: enrico
 * Package: it.unipr.aotlab.dsns
 * Date: 8/31/11
 * Time: 10:01 AM
 */
public class StandardViewListener implements UISWTViewEventListener {
    @Override
    public boolean eventOccurred(final UISWTViewEvent uiswtViewEvent) {
        switch (uiswtViewEvent.getType()) {
        case UISWTViewEvent.TYPE_CREATE:
            System.out.println("Creating plugin view...");
            break;

        case UISWTViewEvent.TYPE_INITIALIZE:
            System.out.println("Initializing plugin...");
            break;

        case UISWTViewEvent.TYPE_REFRESH:
            //refresh();
            break;

        case UISWTViewEvent.TYPE_DESTROY:
            System.out.println("Exit, destroying plugin...");
            break;

        case UISWTViewEvent.TYPE_DATASOURCE_CHANGED:
            //System.out.println("TYPE_DATASOURCE_CHANGED Called");
            break;

        case UISWTViewEvent.TYPE_FOCUSGAINED:
            //System.out.println("TYPE_FOCUSGAINED Called");
            break;

        case UISWTViewEvent.TYPE_FOCUSLOST:
            //System.out.println("TYPE_FOCUSLOST Called");
            break;

        case UISWTViewEvent.TYPE_LANGUAGEUPDATE:
            //System.out.println("TYPE_LANGUAGEUPDATE Called " + Locale.getDefault().toString());
            break;
        }
        return true;

    }
}
