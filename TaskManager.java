import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TaskManager extends JFrame {

    private JTextField nicknameField;

    TaskManager() {
        // 기본 창 설정
        setTitle("Task Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 800);
        setLayout(null);

        // 타이틀 로고
        JLabel titleLabel = new JLabel("TaskManager", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(75, 200, 300, 50);
        add(titleLabel);

        // 닉네임 입력
        JPanel nicknamePanel = new JPanel();
        nicknamePanel.setLayout(new FlowLayout());
        nicknamePanel.setBounds(75, 320, 300, 100);

        nicknameField = new JTextField(20);
        JButton nicknameButton = new JButton("닉네임 설정");

        nicknamePanel.add(new JLabel("▼ 닉네임을 알려주세요 ▼"));
        nicknamePanel.add(nicknameField);
        nicknamePanel.add(nicknameButton);

        add(nicknamePanel, BorderLayout.CENTER);

        // 닉네임 입력 시 팝업창 출력 및 메인 화면 이동
        nicknameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nickname = nicknameField.getText();
                if (!nickname.isEmpty()) {
                    JOptionPane.showMessageDialog(TaskManager.this, "어서오세요, " + nickname + "님!");
                    showMainScreen(nickname);
                } else {
                    JOptionPane.showMessageDialog(TaskManager.this, "닉네임을 입력해주세요!", "오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    // 메인 화면 이동
    private void showMainScreen(String nickname) {
        getContentPane().removeAll();
        revalidate();
        repaint();

        setTitle(nickname + "님의 Task Manager");

        // 여기에 기능 구현

        setVisible(true);
    }

    public static void main(String[] args) {
        new TaskManager();
    }
}