/* Copyright */

package it.unipr.aotlab.dsns;

import org.gudy.azureus2.ui.swt.plugins.UISWTInstance;
import org.gudy.azureus2.ui.swt.plugins.UISWTView;
import org.gudy.azureus2.ui.swt.plugins.UISWTViewEventListener;

/**
 * User: enrico
 * Package: it.unipr.aotlab.dsns
 * Date: 8/31/11
 * Time: 10:00 AM
 */
public class ViewListenerFactory {
    private static UISWTViewEventListener vl = null;

    public static UISWTViewEventListener getViewListener() {
        if (vl == null) {
            vl = new StandardViewListener();
        }
        return vl;
    }
}
