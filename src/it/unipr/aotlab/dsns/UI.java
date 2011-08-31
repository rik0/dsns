/* Copyright */

package it.unipr.aotlab.dsns;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

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

        return true;
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
}
