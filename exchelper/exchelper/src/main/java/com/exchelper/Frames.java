package com.exchelper;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class FrameEXCH extends JFrame {
    FrameEXCH(String title, int width, int heigth){
        setTitle(title);
        setDefaultCloseOperation(FrameEXCH.EXIT_ON_CLOSE);
        setSize(width, heigth);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
    }
}
class TextAreaEXCH extends JTextArea{
    TextAreaEXCH(String text, int x, int y, int width, int heigth){
        setText(text);
        setBounds(x, y, width, heigth);
        setEditable(false);
        setOpaque(false);
        setFocusable(false);
    }
}
class TextFieldEXCH extends JTextField{
    TextFieldEXCH(int x, int y, int width, int heigth){
        setBounds(x, y, width, heigth);
        setHorizontalAlignment(TextFieldEXCH.CENTER);
    }
}
class ButtonEXCH extends JButton{
    ButtonEXCH(String text, int x, int y, int width, int heigth){
        setText(text);
        setBounds(x, y, width, heigth);
    }
}
