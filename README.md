# Side Menu Panel

Side Menu for Java Swing Application.

![Alt text](ScreenShot-1628841429-130821.gif?raw=true "Optional Title")


| Method  | Description |
| ------------- | ------------- |
|  setMain(JPanel panel)    | Sets the main content panel (can be null for no shifting)   |
|  setSide(JPanel panel)    | Sets the sidebar panel  |
|  setMinWidth(int px)    | Sets the minimum (collapsed) width of the sidebar  |
|  setMaxWidth(int px)    | Sets the maximum (expanded) width of the sidebar  |
|  setMainAnimationEnabled(boolean enabled)    | Enables or disables shifting of the main panel during sidebar animation.    |
|  setSpeed(int px)    | Sets the animation speed (pixels per step) |
|  openMenu()    | Opens the sidebar instantly (no animation)  |
|  closeMenu()    | Closes the sidebar instantly (no animation)  |
|  toggleMenu()    | Toggles the sidebar with animation.  |
|  setResponsiveMinWidth(int px)    | Sets the frame width threshold for enabling main panel animation  |


# Usage
```Java
 SidemenuPanel sp;
 sp = new SideMenuPanel(this);
        sp.setMain(mainPanel);
        sp.setSide(sidebar);
        sp.setMinWidth(55);
        sp.setMaxWidth(150);
        sp.setMainAnimation(true);
        sp.setSpeed(4);
        sp.setResponsiveMinWidth(600);
 ```

<aside>
       Tip: If you call `setMain(null)`, the main panel will not shift during sidebar animation, this is ideal for fixed UIs.

       *example:*
       ```Java
       SidemenuPanel sp;
              sp = new SideMenuPanel(this);
              sp.setMain(null);
              sp.setSide(sidebar);
              sp.setMinWidth(55);
              sp.setMaxWidth(150);
              sp.setMainAnimation(true);
              sp.setSpeed(4);
       ```

       In this case `sp.setResponsiveMinWidth(...)` will be ignore because there is no main panel to shift
</aside>

(c) 2020-2021 | IanDiv
