/* Copyright */

package it.unipr.aotlab.dsns;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.gudy.azureus2.ui.swt.plugins.UISWTView;
import org.gudy.azureus2.ui.swt.plugins.UISWTViewEvent;
import org.gudy.azureus2.ui.swt.plugins.UISWTViewEventListener;

import javax.swing.*;

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
            new UI((Composite)uiswtViewEvent.getData(), SWT.NULL);
            break;

        case UISWTViewEvent.TYPE_REFRESH:
            break;

        case UISWTViewEvent.TYPE_DESTROY:
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
