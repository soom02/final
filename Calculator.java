import java.awt.*;
import javax.swing.*;

public class Calculator extends JFrame{
    JTextField text;
    JButton[] button;
    String[] key = {"BackSpace", "", "", "CE", "C", "7", "8", "9",
            "/", "sqrt", "4", "5", "6", "x", "%", "1", "2",
            "3", "-", "1/x", "0", "+/-", ".", "+", "="};

    Calculator() {
        text = new JTextField();
        getContentPane().add(text, BorderLayout.NORTH);
        text.setBackground(Color.WHITE);
        text.setText("0");
        text.setEditable(false); // 입력 불가 및 글자 색상 하얀색 고정 setEnabled(false), 입력 불가 및 글자 색상 변경 setEditable(false)
        text.setForeground(Color.GRAY);

        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(5, 5, 2, 2));

        button = new JButton[25];
        for (int i = 0; i < button.length; i++) {
            button[i] = new JButton(key[i]);
            button[i].setBackground(Color.yellow);
            button[i].setFont(new Font("Arial", Font.BOLD, 12)); // 볼드체 지정을 위해서 글씨체랑 크기도 지정해야 함

            if (i % 5 == 3 || i % 5 == 4) // 4, 5번째 열이면 빨간색 글씨, 1-3열은 파란색 글씨
                button[i].setForeground(Color.red);
            else
                button[i].setForeground(Color.BLUE);

            panel.add(button[i]);
        }

        setTitle("계산기");
        setSize(520, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args) {
        new Calculator();
    }
}