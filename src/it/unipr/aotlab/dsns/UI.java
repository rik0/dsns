/* Copyright */

package it.unipr.aotlab.dsns;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import java.util.ResourceBundle;


/**
 * User: enrico
 * Package: it.unipr.aotlab.dsns
 * Date: 8/31/11
 * Time: 10:22 AM
 */
public class UI extends Composite {
    String RESOURCE_BUNDLE_NAME = "it.unipr.aotlab.dsns.messages.Messages";
    ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME);
    private Text insertTextEdit;

    /**
     * Constructs a new instance of this class given its parent
     * and a style value describing its behavior and appearance.
     * <p>
     * The style value is either one of the style constants defined in
     * class <code>SWT</code> which is applicable to instances of this
     * class, or must be built by <em>bitwise OR</em>'ing together
     * (that is, using the <code>int</code> "|" operator) two or more
     * of those <code>SWT</code> style constants. The class description
     * lists the style constants that are applicable to the class.
     * Style bits are also inherited from superclasses.
     * </p>
     *
     * @param parent a widget which will be the parent of the new instance (cannot be null)
     * @param style  the style of widget to construct
     * @throws IllegalArgumentException     <ul>
     *                                      <li>ERROR_NULL_ARGUMENT - if the parent is null</li>
     *                                      </ul>
     * @throws org.eclipse.swt.SWTException <ul>
     *                                      <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the parent</li>
     *                                      </ul>
     * @see org.eclipse.swt.SWT#NO_BACKGROUND
     * @see org.eclipse.swt.SWT#NO_FOCUS
     * @see org.eclipse.swt.SWT#NO_MERGE_PAINTS
     * @see org.eclipse.swt.SWT#NO_REDRAW_RESIZE
     * @see org.eclipse.swt.SWT#NO_RADIO_GROUP
     * @see org.eclipse.swt.SWT#EMBEDDED
     * @see org.eclipse.swt.SWT#DOUBLE_BUFFERED
     * @see org.eclipse.swt.widgets.Widget#getStyle
     */
    public UI(Composite parent, int style) {
        super(parent, style);
        initializeUI(parent);
    }

    boolean initializeUI(Composite parent) {
        this.setLayout(new GridLayout(1, false));

        createTitleLabel();
        createInsertField();

        return true;
    }

    private void createInsertField() {
        Group insertionGroup = new Group(this, SWT.SHADOW_IN);
        insertionGroup.setText(resourceBundle.getString("dsns.insertionGroupLabel"));
        insertionGroup.setLayout(new GridLayout(4, false));
        insertionGroup.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 4, 10));

//        Label lblDescription = new Label(insertionGroup, SWT.LEFT);
//        lblDescription.setText(resourceBundle.getString("dsns.insertMessageLabel"));

        insertTextEdit = new Text(insertionGroup, SWT.MULTI);
        insertTextEdit.setText("");
        insertTextEdit.setLayoutData(
                new GridData(SWT.FILL, SWT.FILL, true, true, 4, 10)
        );
        insertTextEdit.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyPressed(final KeyEvent e) {
                        if (e.keyCode == '\r' &&
                                (e.stateMask & (SWT.SHIFT | SWT.ALT)) == 0) {
                            System.out.println(extractNewMessage());
                            e.doit = false;
                        }
                    }

                    @Override
                    public void keyReleased(final KeyEvent e) {

                    }
                }
        );
    }

    private void createTitleLabel() {
        Label mainLbl = new Label(this, SWT.CENTER);

        mainLbl.setText(resourceBundle.getString("dsns.pluginTitle"));
        mainLbl.setFont(new Font(
                this.getDisplay(),
                "normal",
                18,
                SWT.BOLD
        ));
        mainLbl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

    }

    private String extractNewMessage() {
        String msg = insertTextEdit.getText();
        insertTextEdit.setText("");
        return msg;
    }
}
