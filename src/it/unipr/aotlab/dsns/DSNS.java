/* Copyright */

package it.unipr.aotlab.dsns;

import org.gudy.azureus2.plugins.PluginException;
import org.gudy.azureus2.plugins.PluginInterface;
import org.gudy.azureus2.plugins.UnloadablePlugin;
import org.gudy.azureus2.plugins.ddb.DistributedDatabase;
import org.gudy.azureus2.plugins.ddb.DistributedDatabaseException;
import org.gudy.azureus2.plugins.ddb.DistributedDatabaseKey;
import org.gudy.azureus2.plugins.logging.LoggerChannel;
import org.gudy.azureus2.plugins.ui.UIInstance;
import org.gudy.azureus2.plugins.ui.UIManagerListener;
import org.gudy.azureus2.ui.swt.plugins.UISWTInstance;

/**
 * User: enrico
 * Package: it.unipr.aotlab.DSNS_PLUGIN_CHANNEL_NAME
 * Date: 8/29/11
 * Time: 10:29 AM
 */
public class DSNS implements UnloadablePlugin {
    final String DSNS_PLUGIN_CHANNEL_NAME = "DSNS";
    final String PLUGIN_NAME = "dsns.pluginTitle";

    private PluginInterface pluginInterface;
    private UISWTInstance swtInstance;
    private LoggerChannel logChannel;

    @Override
    public void unload() throws PluginException {

    }

    @Override
    public void initialize(final PluginInterface pluginInterface) throws PluginException {
        this.pluginInterface = pluginInterface;
        initializeLogger();
        initializeUIManager();
        initializeDDB();
    }

    private void initializeDDB() {
        DistributedDatabase ddb = pluginInterface.getDistributedDatabase();
        DistributedDatabaseKey key = null;
        try {
            key = ddb.createKey(1);
        } catch (DistributedDatabaseException e) {
            e.printStackTrace();
        }
    }

    private void initializeUIManager() {
        this.pluginInterface.getUIManager().addUIListener(new UIManagerListener() {

            @Override
            public void UIAttached(final UIInstance uiInstance) {
                if (uiInstance instanceof UISWTInstance) {
                    setSwtInstance((UISWTInstance) uiInstance);
                    log("Attached UI.");
                    addMenu((UISWTInstance) uiInstance);
                }
            }

            @Override
            public void UIDetached(final UIInstance uiInstance) {
                if (uiInstance instanceof UISWTInstance) {
                    unsetSwtInstance();
                    log("Detached UI.");
                }
            }
        });
    }

    private void initializeLogger() {
        logChannel = this.pluginInterface.getLogger().getChannel(DSNS_PLUGIN_CHANNEL_NAME);
    }

    private void addMenu(final UISWTInstance uiInstance) {
        swtInstance.addView(
                UISWTInstance.VIEW_MAIN,
                PLUGIN_NAME,
                ViewListenerFactory.getViewListener()
        );
    }

    private void log(final String s) {
        logChannel.log(s);

    }

    public UIInstance getSwtInstance() {
        return swtInstance;
    }

    public boolean setSwtInstance(final UISWTInstance swtInstance) {
        if (swtInstance == null) {
            log("Trying to set NULL UI. Use unsetSWTInstance instead.");
            return false;
        }
        this.swtInstance = swtInstance;
        return true;
    }

    public void unsetSwtInstance() {
        this.swtInstance = null;
    }
}
