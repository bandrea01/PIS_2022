package View.ViewModel;

import java.awt.*;

public class WideComboBox extends javax.swing.JComboBox {

    private boolean layingOut = false;
    private int widestLength = 0;
    private boolean wide = false;

    public WideComboBox(Object[] objs) {
        super(objs);
    }


    public boolean isWide() {
        return wide;
    }

    // Setting the JComboBox wide
    public void setWide(boolean wide) {
        this.wide = wide;
        widestLength = getWidestItemWidth();

    }

    public Dimension getSize() {
        Dimension dim = super.getSize();
        if (!layingOut && isWide())
            dim.width = Math.max(widestLength, dim.width);
        return dim;
    }

    public int getWidestItemWidth() {
        int numOfItems = this.getItemCount();
        Font font = this.getFont();
        FontMetrics metrics = this.getFontMetrics(font);
        int widest = 0;
        for (int i = 0; i < numOfItems; i++) {
            Object item = this.getItemAt(i);
            int lineWidth = metrics.stringWidth(item.toString());
            widest = Math.max(widest, lineWidth);
        }

        return widest + 5;
    }

    public void doLayout() {
        try {
            layingOut = true;
            super.doLayout();
        } finally {
            layingOut = false;
        }
    }
}
