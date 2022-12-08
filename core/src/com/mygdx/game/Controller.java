package com.mygdx.game;
import java.util.*;

public class Controller {
    public int idleTimer = 0;
    public boolean isIdle = false;

    public void swapToFromIdle() {
        isIdle = !isIdle;
    }
}
