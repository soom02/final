import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class TaskManager extends JFrame {

    private JTextField nicknameField;
    private Color pColor = new Color(255, 133, 180);
    private String nickname; // 사용자 닉네임

    TaskManager() {
        // 기본 창 설정
        setTitle("Task Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 800);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // 타이틀 로고
        JLabel titleLabel = new JLabel("♥ TaskManager ♥", SwingConstants.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        titleLabel.setForeground(pColor);
        titleLabel.setBounds(75, 200, 300, 50);
        add(titleLabel);

        // 닉네임 입력
        JPanel nicknamePanel = new JPanel();
        nicknamePanel.setLayout(new FlowLayout());
        nicknamePanel.setBounds(75, 320, 300, 100);
        nicknamePanel.setBackground(pColor);

        nicknameField = new JTextField(20);
        JButton nicknameButton = new JButton("닉네임 설정");
        nicknameButton.setBackground(Color.WHITE);

        nicknamePanel.add(new JLabel("▼ 닉네임을 알려주세요 ▼"));
        nicknamePanel.add(nicknameField);
        nicknamePanel.add(nicknameButton);

        add(nicknamePanel);

        // 닉네임 입력 시 팝업창 출력 및 메인 화면 이동
        nicknameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nickname = nicknameField.getText();
                if (!nickname.isEmpty()) {
                    JOptionPane.showMessageDialog(TaskManager.this, "어서오세요, " + nickname + "님!");
                    showTimetableScreen();
                } else {
                    JOptionPane.showMessageDialog(TaskManager.this, "닉네임을 입력해주세요!", "오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    // 시간표 화면 - 메인 화면
    private void showTimetableScreen() {
        getContentPane().removeAll();

        setLayout(new BorderLayout());
        setTitle(nickname + "님의 Task Manager"); // nickname 사용

        // 닉네임 레이블 추가
        JLabel timetableLabel = new JLabel(nickname + "님의 시간표", SwingConstants.CENTER);
        timetableLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        timetableLabel.setForeground(Color.BLACK);
        add(timetableLabel, BorderLayout.NORTH); // 상단에 추가

        // 시간표 생성
        String[] columnNames = {"시간", "월", "화", "수", "목", "금"};
        String[][] data = new String[9][6];

        for (int i = 0; i < 9; i++) {
            data[i][0] = String.valueOf(i + 1);
        }

        JTable timetable = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // 테이블 스타일 설정
        timetable.setRowHeight(40);
        timetable.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        timetable.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 16));
        timetable.getTableHeader().setBackground(pColor);
        timetable.getTableHeader().setForeground(Color.BLACK);
        timetable.setBackground(Color.WHITE);

        // 테이블 셀 설정
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        timetable.setDefaultRenderer(Object.class, cellRenderer);

        JScrollPane timetableScrollPane = new JScrollPane(timetable);
        timetableScrollPane.setPreferredSize(new Dimension(400, 300));

        // 메인 패널 생성
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(timetableScrollPane, BorderLayout.CENTER);

        // 하단 버튼 추가
        JPanel buttonPanel = new JPanel();
        JButton timetableButton = new JButton("시간표");
        JButton planButton = new JButton("계획");
        JButton chatButton = new JButton("채팅");

        timetableButton.setEnabled(false); // 비활성화

        // 버튼 클릭 이동
        planButton.addActionListener(e -> showPlanScreen());
        chatButton.addActionListener(e -> showChatScreen());

        buttonPanel.add(timetableButton);
        buttonPanel.add(planButton);
        buttonPanel.add(chatButton);
        add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
        revalidate();
        repaint();
    }

    // 계획 화면
    private void showPlanScreen() {
        getContentPane().removeAll();

        setLayout(new BorderLayout());
        JLabel planLabel = new JLabel(nickname + "님의 계획 화면", SwingConstants.CENTER);
        planLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        add(planLabel, BorderLayout.CENTER);

        // 오늘의 계획, 전체 계획, 계획 추가 수정 삭제... 기능

        // 하단 버튼 추가
        JPanel buttonPanel = new JPanel();
        JButton timetableButton = new JButton("시간표");
        JButton planButton = new JButton("계획");
        JButton chatButton = new JButton("채팅");

        planButton.setEnabled(false); // 비활성화

        // 버튼 클릭 이동
        timetableButton.addActionListener(e -> showTimetableScreen());
        chatButton.addActionListener(e -> showChatScreen());

        buttonPanel.add(timetableButton);
        buttonPanel.add(planButton);
        buttonPanel.add(chatButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
        revalidate();
        repaint();
    }

    // 채팅 화면 (팀 프로젝트용)
    private void showChatScreen() {
        getContentPane().removeAll();

        setLayout(new BorderLayout());
        JLabel planLabel = new JLabel("채팅", SwingConstants.CENTER);
        planLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        add(planLabel, BorderLayout.CENTER);

        // 채팅 화면만 구현, 기능x

        // 하단 버튼 추가
        JPanel buttonPanel = new JPanel();
        JButton timetableButton = new JButton("시간표");
        JButton planButton = new JButton("계획");
        JButton chatButton = new JButton("채팅");

        // 현재 화면의 버튼 비활성화
        chatButton.setEnabled(false); // 비활성화

        // 버튼 클릭 이동
        timetableButton.addActionListener(e -> showTimetableScreen());
        planButton.addActionListener(e -> showPlanScreen());

        buttonPanel.add(timetableButton);
        buttonPanel.add(planButton);
        buttonPanel.add(chatButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TaskManager());
    }
}
