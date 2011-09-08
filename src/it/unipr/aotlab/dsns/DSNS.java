/* Copyright */

package it.unipr.aotlab.dsns;

import org.gudy.azureus2.plugins.*;
import org.gudy.azureus2.plugins.ddb.*;
import org.gudy.azureus2.plugins.logging.LoggerChannel;
import org.gudy.azureus2.plugins.ui.UIInstance;
import org.gudy.azureus2.plugins.ui.UIManagerListener;
import org.gudy.azureus2.ui.swt.plugins.UISWTInstance;

import java.util.Observable;
import java.util.Observer;

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
    private DistributedDatabase ddb;

    private DHTInitializationObservable dhtReady;


    @Override
    public void unload() throws PluginException {

    }

    @Override
    public void initialize(final PluginInterface pluginInterface) throws PluginException {
        this.pluginInterface = pluginInterface;
        initializeLogger();
        initializeUIManager();
        pluginInterface.getUtilities().createThread(
                "DSNS:DHTInitializationSignaler",
                new DHTInitializationSignaler()
        );
        pluginInterface.getUtilities().createThread(
                "DSNS:DHTInitializationSignaler",
                new DHTInitializationSignaler()
        );
        dhtReady = new DHTInitializationObservable();
        dhtReady.addObserver(
                new DHTInitializationObserver()
        );
    }


    private void setAndReadKey(DistributedDatabase ddb) {
        DistributedDatabaseKey key = null;
        DistributedDatabaseValue val = null;
        try {
            key = ddb.createKey("foo");
            val = ddb.createValue("val");
            ddb.write(new DistributedDatabaseListener() {
                @Override
                public void event(final DistributedDatabaseEvent event) {
                    log("WHOOO!");
                }
            }, key, val);
            Thread.sleep(1000);
            ddb.read(new DistributedDatabaseListener() {
                @Override
                public void event(final DistributedDatabaseEvent event) {
                    log("HAAAA!");
                    log(event.toString());
                }
            }, key, 2000);
        } catch (DistributedDatabaseException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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

    class DHTInitializationObservable extends Observable {
        public boolean isDhtReady() {
            return dhtReady;
        }

        public void setDhtReady(final boolean dhtReady) {
            this.dhtReady = dhtReady;
            this.setChanged();
        }

        boolean dhtReady = false;
    }

    class DHTInitializationObserver implements Observer {
        @Override
        public void update(final Observable observable, final Object o) {
            assert observable == dhtReady;
            pluginInterface.addListener(
                    new PluginListener() {
                        @Override
                        public void initializationComplete() {
                            //ddb = pluginInterface.getDistributedDatabase();
                            //setAndReadKey(ddb);
                        }

                        @Override
                        public void closedownInitiated() {

                        }

                        @Override
                        public void closedownComplete() {

                        }
                    }
            );
            pluginInterface.addEventListener(
                    new PluginEventListener() {
                        @Override
                        public void handleEvent(final PluginEvent ev) {
                            System.out.println(ev);
                        }
                    }
            );
        }
    }

    class DHTInitializationSignaler extends Thread {
        final private DistributedDatabase dht;

        DHTInitializationSignaler() {
            dht = pluginInterface.getDistributedDatabase();
        }

        @Override
        public void run() {
            do {
                if (dht.isAvailable()) {
                    dhtReady.setDhtReady(true);
                    break;
                } else {
                    try {
                        sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        continue;
                    }
                }
            } while (true);
        }
    }

}
