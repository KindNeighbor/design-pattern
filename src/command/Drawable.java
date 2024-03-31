package command;

import java.awt.Color;

public interface Drawable {
    public abstract void draw(int x, int y);
    public abstract void init();
    public abstract void setColor(Color color);
}
