
package ian;

// @author ian
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SideMenuPanel {

    private GroupLayout gl;
    private int x = 0;
    private final int a = 0;
    private int minWidth = 60;
    private int maxWidth = 200;
    private JPanel side;
    private JPanel main;
    private boolean isEnabled;
    private int speed = 2;
    private int responsiveMinWidth = 600;
    private final JFrame frame;
    private boolean isOpen = false;

    public boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public int getResponsiveMinWidth() {
        return responsiveMinWidth;
    }

    public void setResponsiveMinWidth(int responsiveWidth) {
        this.responsiveMinWidth = responsiveWidth;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        if (speed == 0) {
            speed = maxWidth;
        }
        this.speed = speed;
    }

    public int getMinWidth() {
        return minWidth;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public JPanel getSide() {
        return side;
    }

    public JPanel getMain() {
        return main;
    }

    public boolean setMainAnimation() {
        return isEnabled;
    }

    public void setMinWidth(int min) {
        this.minWidth = min;
    }

    public void setMaxWidth(int max) {
        this.maxWidth = max;
    }

    public void setSide(JPanel side) {

        this.side = side;

    }

    public void setMain(JPanel main) {

        this.main = main;

    }

    public void setMainAnimation(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public SideMenuPanel(JFrame frame) {

        frame.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent arg0) {
                x = 0;
            }
        });

        this.frame = frame;
    }

    public void onSideMenu() {
        int b = maxWidth % speed;
        if (x == maxWidth) {

            Thread th = new Thread() {
                @Override
                public void run() {
                    for (int i = maxWidth; i >= 0; i -= speed) {
                        try {
                            if (b == i) {
                                i = 0;
                            }
                            TimeUnit.NANOSECONDS.sleep(1);
                            side.setSize(new Dimension(minWidth + i, main.getHeight()));

                            if (side instanceof Container) {
                                for (Component child : ((Container) side).getComponents()) {
                                    child.setSize(new Dimension(maxWidth + minWidth, child.getHeight()));
                                }
                            }
                            if (frame.getWidth() >= responsiveMinWidth) {
                                if (isEnabled) {
                                    main.setLocation(minWidth + i, main.getY());
                                }
                            }

                        } catch (NullPointerException e) {

                            System.out.println("Unknown Side or Main panel \n setSide() and setMain() ");
                            return;

                        } catch (InterruptedException ex) {
                            Logger.getLogger(SideMenuPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            };
            th.start();

            x = 0;
        } else if (x == 0) {
            Thread th = new Thread() {
                @Override
                public void run() {
                    for (int i = 0; i <= x; i += speed) {
                        try {

                            TimeUnit.NANOSECONDS.sleep(1);
                            if (maxWidth - b == i) {
                                i += b;
                            }

                            side.setSize(new Dimension(minWidth + i, main.getHeight()));

                            if (side instanceof Container) {
                                for (Component child : ((Container) side).getComponents()) {
                                    child.setSize(new Dimension(maxWidth + minWidth, child.getHeight()));
                                }
                            }
                            if (frame.getWidth() >= responsiveMinWidth) {
                                if (isEnabled) {
                                    main.setLocation(minWidth + i, main.getY());
                                }
                            }
                        } catch (NullPointerException e) {
                            System.out.println("ERROR: Unknown value for setSide() or setMain()");

                            return;

                        } catch (InterruptedException ex) {
                            Logger.getLogger(SideMenuPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }
            };
            th.start();
            x = maxWidth;
        }

    }

    public void closeMenu() {
        if (getIsOpen()) {
            side.setSize(new Dimension(minWidth, side.getHeight()));
            main.setLocation(minWidth, main.getY());
            setGLSize(minWidth);
//            close();
            isOpen = false;
            x = 0;
        }
    }

    public void openMenu() {
        if (!getIsOpen()) {
            side.setSize(new Dimension(minWidth + maxWidth, side.getHeight()));
            main.setLocation(minWidth + maxWidth, main.getY());
            setGLSize(minWidth + maxWidth);
            x = maxWidth;
            isOpen = true;
        }
    }

    private void setGLSize(int size) {
        gl = new javax.swing.GroupLayout(main.getParent());
        main.getParent().setLayout(gl);
        gl.setHorizontalGroup(
                gl.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(gl.createSequentialGroup()
                                .addComponent(side, javax.swing.GroupLayout.PREFERRED_SIZE, size, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(0, 0, 0))
        );
        gl.setVerticalGroup(
                gl.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(side, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE)
                        .addComponent(main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }
}

