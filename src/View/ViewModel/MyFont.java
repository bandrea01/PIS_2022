package View.ViewModel;

import javax.swing.*;
import java.awt.*;

public class MyFont extends Font {
    public MyFont(int size) {
        super(new JLabel().getFont().getFontName(),new JLabel().getFont().getStyle(), size);
    }
}
