# Side Menu Panel

Side Menu for Java Swing Application. 

![Alt text](ScreenShot-1628841429-130821.gif?raw=true "Optional Title")


 
| Methods  | Functions |
| ------------- | ------------- |
|  setMain()    | set Main Panel   |
|  setSide()    | set Sidebar/Side Panel  |
|  setMinWidth()    | set Sidebar Minimum Width   |
|  setMaxWidth()    | set Sidebar Maximum Width   |
|  setMainAnimation()    | enable/disable Main Animation    |
|  setSpeed()    | set Sliding Animation Speed |



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
(c) 2020-2021 | IanDiv
