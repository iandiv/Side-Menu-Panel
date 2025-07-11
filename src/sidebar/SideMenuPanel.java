package sidebar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @class SideMenuPanel
 * @brief Provides a responsive animated side menu for Swing applications.
 */
public class SideMenuPanel {

    private int minWidth = 60;
    private int maxWidth = 200;
    private int speed = 2;
    private int responsiveMinWidth = 600;

    private JPanel side;
    private JPanel main;
    private boolean mainAnimationEnabled = false;
    private boolean isOpen = false;
    private int currentWidth = 0;

    private final JFrame frame;

    /**
     * @brief Constructs a SideMenuPanel attached to a given JFrame.
     * @param frame The parent frame hosting the side and main panels.
     */
    public SideMenuPanel(JFrame frame) {
        this.frame = frame;
        this.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                currentWidth = 0;
            }
        });
    }

    /** @return Whether the side menu is currently open. */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * @brief Manually sets the open state of the side menu.
     * @param open True to set the menu as open; false to set as closed.
     */
    public void setOpen(boolean open) {
        this.isOpen = open;
    }

    /** @return The minimum frame width required to enable main panel shifting. */
    public int getResponsiveMinWidth() {
        return responsiveMinWidth;
    }

    /**
     * @brief Sets the minimum frame width that enables responsive main panel shifting.
     * @param responsiveWidth Minimum width in pixels.
     */
    public void setResponsiveMinWidth(int responsiveWidth) {
        this.responsiveMinWidth = responsiveWidth;
    }

    /** @return The animation speed in pixels per frame. */
    public int getSpeed() {
        return speed;
    }

    /**
     * @brief Sets the animation speed.
     * @param speed Speed in pixels per frame. If set to 0, uses maxWidth as speed.
     */
    public void setSpeed(int speed) {
        this.speed = (speed == 0) ? maxWidth : speed;
    }

    /** @return The minimum width of the sidebar. */
    public int getMinWidth() {
        return minWidth;
    }

    /**
     * @brief Sets the minimum sidebar width.
     * @param minWidth Width in pixels.
     */
    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    /** @return The maximum width the sidebar can expand to. */
    public int getMaxWidth() {
        return maxWidth;
    }

    /**
     * @brief Sets the maximum width of the sidebar.
     * @param maxWidth Width in pixels.
     */
    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    /** @return The sidebar panel. */
    public JPanel getSide() {
        return side;
    }

    /**
     * @brief Sets the sidebar panel.
     * @param side A JPanel representing the sidebar.
     */
    public void setSide(JPanel side) {
        this.side = side;
    }

    /** @return The main content panel. */
    public JPanel getMain() {
        return main;
    }

    /**
     * @brief Sets the main content panel. Can be null for no responsiveness.
     * @param main A JPanel or null.
     */
    public void setMain(JPanel main) {
        this.main = main;
    }

    /** @return Whether the main panel moves with the sidebar. */
    public boolean isMainAnimationEnabled() {
        return mainAnimationEnabled;
    }

    /**
     * @brief Enables or disables main panel animation on sidebar toggle.
     * @param enabled True to enable animation; false otherwise.
     */
    public void setMainAnimationEnabled(boolean enabled) {
        this.mainAnimationEnabled = enabled;
    }

    /**
     * @brief Toggles the sidebar open or closed with animation.
     *        The main panel is shifted only if set and enabled.
     */
    public void toggleMenu() {
        if (side == null) {
            System.err.println("Error: sidebar can't be null");
            return;
        }

        if (currentWidth == maxWidth) {
            animateMenu(false);  // close
        } else {
            animateMenu(true);   // open
        }
    }

    /**
     * @brief Animates the sidebar either opening or closing.
     * @param opening True to open; false to close.
     */
    private void animateMenu(boolean opening) {
        int direction = opening ? 1 : -1;
        int target = opening ? maxWidth : 0;
        int b = maxWidth % speed;

        new Thread(() -> {
            try {
                for (int i = currentWidth; opening ? i <= maxWidth : i >= 0; i += direction * speed) {
                    if (!opening && i <= b) i = 0;
                    if (opening && i >= maxWidth - b) i = maxWidth;

                    updatePanelSize(i);
                    TimeUnit.NANOSECONDS.sleep(1);
                }
                currentWidth = target;
                isOpen = opening;
            } catch (InterruptedException e) {
                Logger.getLogger(SideMenuPanel.class.getName()).log(Level.SEVERE, null, e);
            }
        }).start();
    }

    /**
     * @brief Updates the width of the sidebar and, optionally, shifts the main panel.
     * @param delta Width to add to minWidth for the sidebar.
     */
    private void updatePanelSize(int delta) {
        int width = minWidth + delta;

        side.setSize(new Dimension(width, side.getHeight()));
        for (Component child : side.getComponents()) {
            child.setSize(new Dimension(width, child.getHeight()));
        }

        if (main != null && frame.getWidth() >= responsiveMinWidth && mainAnimationEnabled) {
            main.setLocation(width, main.getY());
        }
    }

    /**
     * @brief Immediately closes the sidebar and resets layout.
     */
    public void closeMenu() {
        if (isOpen) {
            updatePanelSize(0);
            if (main != null) {
                main.setLocation(minWidth, main.getY());
                updateLayout(minWidth);
            }
            isOpen = false;
            currentWidth = 0;
        }
    }

    /**
     * @brief Immediately opens the sidebar and updates layout.
     */
    public void openMenu() {
        if (!isOpen) {
            updatePanelSize(maxWidth);
            if (main != null) {
                main.setLocation(minWidth + maxWidth, main.getY());
                updateLayout(minWidth + maxWidth);
            }
            currentWidth = maxWidth;
            isOpen = true;
        }
    }

    /**
     * @brief Updates the GroupLayout constraints of the main container
     *        to reflect the sidebar and main panel dimensions.
     * @param size The width to apply to the sidebar in the layout.
     */
    private void updateLayout(int size) {
        if (main == null || main.getParent() == null) return;

        Container parent = main.getParent();
        GroupLayout layout = new GroupLayout(parent);
        parent.setLayout(layout);

        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addComponent(side, size, size, size)
                .addGap(0)
                .addComponent(main, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(side, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(main, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }
}
