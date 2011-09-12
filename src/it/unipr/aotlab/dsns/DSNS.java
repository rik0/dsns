/* Copyright */

package it.unipr.aotlab.dsns;

import org.gudy.azureus2.plugins.PluginException;
import org.gudy.azureus2.plugins.PluginInterface;
import org.gudy.azureus2.plugins.PluginListener;
import org.gudy.azureus2.plugins.UnloadablePlugin;
import org.gudy.azureus2.plugins.ddb.*;
import org.gudy.azureus2.plugins.logging.LoggerChannel;
import org.gudy.azureus2.plugins.ui.UIInstance;
import org.gudy.azureus2.plugins.ui.UIManagerListener;
import org.gudy.azureus2.ui.swt.plugins.UISWTInstance;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

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

    @Override
    public void unload() throws PluginException {

    }

    @Override
    public void initialize(final PluginInterface pluginInterface) throws PluginException {
        this.pluginInterface = pluginInterface;
        initializeLogger();
        initializeUIManager();
        pluginInterface.addListener(
                new PluginListener() {
                    @Override
                    public void initializationComplete() {
                        pluginInterface.getUtilities().createThread(
                                "CHECK DHT",
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        if ((ddb = pluginInterface.getDistributedDatabase()) == null
                                                || !ddb.isAvailable()) {
                                            ddb = null;
                                        }
                                        setAndReadKey(ddb);
                                    }
                                }
                        );
                    }

                    @Override
                    public void closedownInitiated() {

                    }

                    @Override
                    public void closedownComplete() {

                    }
                }
        );
    }


    private void setAndReadKey(final DistributedDatabase ddb) {
        try {
            final DistributedDatabaseKey key = ddb.createKey("foo");
            final DistributedDatabaseValue val = ddb.createValue("val");
            ddb.write(new DistributedDatabaseListener() {

                @Override
                public void event(final DistributedDatabaseEvent event) {
                    switch (event.getType()) {
                        case DistributedDatabaseEvent.ET_OPERATION_COMPLETE:
                            try {
                                ddb.read(new DistributedDatabaseListener() {

                                    private Object deserializeValue(final DistributedDatabaseValue value) {
                                        try {
                                            byte[] byteValue = (byte[]) value.getValue(byte[].class);
                                            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(byteValue));
                                            return ois.readObject();
                                        } catch (DistributedDatabaseException e) {
                                            return null;
                                        } catch (IOException e) {
                                            return null;
                                        } catch (ClassNotFoundException e) {
                                            return null;
                                        }
                                    }

                                    @Override
                                    public void event(final DistributedDatabaseEvent event) {
                                        switch (event.getType()) {
                                            case DistributedDatabaseEvent.ET_OPERATION_TIMEOUT:
                                                log("TIMEOUT!");
                                                break;
                                            case DistributedDatabaseEvent.ET_VALUE_READ:
                                                DistributedDatabaseValue value = event.getValue();
                                                Object o = deserializeValue(value);
                                                log(Util.show(o));
                                                if (o != null) {
                                                    log(o);
                                                } else {
                                                    log("<<NULL>>");
                                                }
                                                break;
                                            case DistributedDatabaseEvent.ET_OPERATION_COMPLETE:
                                                log("LOOKUP COMPLETE!");
                                            default:
                                                break;
                                        }
                                    }
                                }, key, 2000);
                            } catch (DistributedDatabaseException e) {
                                e.printStackTrace();
                            }
                        case DistributedDatabaseEvent.ET_OPERATION_STARTS:
                            log("WRITE STARTED");
                            break;
                        case DistributedDatabaseEvent.ET_OPERATION_TIMEOUT:
                            log("TIMEOUT");
                            break;
                        case DistributedDatabaseEvent.ET_VALUE_WRITTEN:
                            log("WROTE");
                            break;
                        default:
                            log(event);
                            break;
                    }
                }
            }, key, val);
        } catch (DistributedDatabaseException e) {
            e.printStackTrace();
        }

    }


    private void initializeUIManager() {
        pluginInterface.getUIManager().addUIListener(new UIManagerListener() {

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

    private void log(final Object s) {
        logChannel.log(s.toString());
        System.out.println(s);
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

    protected boolean
    checkDHTAvailable() {
        final DistributedDatabase distributedDatabase = pluginInterface.getDistributedDatabase();
        if (!distributedDatabase.isAvailable()) {

            logChannel.logAlert(
                    LoggerChannel.LT_ERROR,
                    PLUGIN_NAME + " initialisation failed, Distributed Database unavailable");
            return false;
        }

        return true;
    }


}
