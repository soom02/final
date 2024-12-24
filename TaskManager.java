import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class TaskManager extends JFrame {

    private JTextField nicknameField;
    private Color pColor = new Color(255, 133, 180);
    private String nickname; // 사용자 닉네임
    private JLabel selectedCourseLabel; // 클릭한 과목을 표시할 레이블
    private JTextArea detailArea; // 과목 세부 정보 표시할 텍스트 영역

    // 과목 세부 정보 저장
    private HashMap<String, String[]> courseDetails;

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

        // 과목 세부 정보 초기화
        initializeCourseDetails();

        setVisible(true);
    }

    // 과목 세부 정보 초기화
    private void initializeCourseDetails() {
        courseDetails = new HashMap<>();
        courseDetails.put("알고리즘설계", new String[]{"OOO 교수님", "화1-3, 06-408"});
        courseDetails.put("GUI프로그래밍", new String[]{"OOO 교수님", "수1-3, 06-408"});
        courseDetails.put("JAVA프로그래밍2", new String[]{"남수만 교수님", "수5-7, 06-310"});
        courseDetails.put("운영체제", new String[]{"OOO 교수님", "금1-3, 06-408"});
        courseDetails.put("ENGLISH3", new String[]{"OOO 교수님", "금4-5, 20-229"});
    }

    // 시간표 화면 - 메인 화면
    private void showTimetableScreen() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());
        setTitle(nickname + "님의 Task Manager");

        // 닉네임 레이블 추가
        JLabel timetableLabel = new JLabel(nickname + "님의 시간표", SwingConstants.CENTER);
        timetableLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        timetableLabel.setForeground(Color.BLACK);
        add(timetableLabel, BorderLayout.NORTH);

        // 클릭한 과목을 표시할 레이블 추가
        selectedCourseLabel = new JLabel("클릭한 과목이 여기에 표시됩니다.", SwingConstants.CENTER);
        selectedCourseLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        add(selectedCourseLabel, BorderLayout.SOUTH);

        // 시간표 생성
        String[] columnNames = {"시간", "월", "화", "수", "목", "금"};
        String[][] data = new String[9][6];

        // 시간표 배정
        for (int i = 0; i < 9; i++) {
            data[i][0] = String.valueOf(i + 1); // 1~9 교시
        }

        data[0][2] = "알고리즘설계"; data[1][2] = "알고리즘설계"; data[2][2] = "알고리즘설계";
        data[0][3] = "GUI프로그래밍"; data[1][3] = "GUI프로그래밍"; data[2][3] = "GUI프로그래밍";
        data[4][3] = "JAVA프로그래밍2"; data[5][3] = "JAVA프로그래밍2"; data[6][3] = "JAVA프로그래밍2";
        data[0][5] = "운영체제"; data[1][5] = "운영체제"; data[2][5] = "운영체제";
        data[3][5] = "ENGLISH3"; data[4][5] = "ENGLISH3";

        JTable timetable = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // 셀 클릭 이벤트 리스너 추가
        timetable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = timetable.rowAtPoint(e.getPoint());
                int column = timetable.columnAtPoint(e.getPoint());
                String cellValue = (String) timetable.getValueAt(row, column);
                if (cellValue != null) {
                    displayCourseDetails(cellValue); // 세부 정보 표시
                }
            }
        });

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

        // 세부 정보 텍스트 영역 초기화
        detailArea = new JTextArea();
        detailArea.setEditable(false);
        detailArea.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        detailArea.setLineWrap(true);
        detailArea.setWrapStyleWord(true);
        detailArea.setBorder(BorderFactory.createTitledBorder("세부 정보"));
        JScrollPane detailScrollPane = new JScrollPane(detailArea);

        detailArea.setPreferredSize(new Dimension(400, 200));

        // 메인 패널 생성
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(timetableScrollPane, BorderLayout.CENTER);
        mainPanel.add(detailScrollPane, BorderLayout.SOUTH);

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

    // 과목 세부 정보 표시
    private void displayCourseDetails(String courseName) {
        String[] details = courseDetails.get(courseName);
        if (details != null) {
            StringBuilder info = new StringBuilder();
            info.append(courseName).append("\n");
            if (details.length > 0) {
                info.append("교수: ").append(details[0]).append("\n");
            }
            if (details.length > 1) {
                info.append("강의시간 및 강의실: ").append(details[1]).append("\n");
            }
            detailArea.setText(info.toString());
        } else {
            detailArea.setText("세부 정보를 찾을 수 없습니다.");
        }
    }

    // 계획 화면
    private void showPlanScreen() {
        getContentPane().removeAll();

        // 타이틀 레이블
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

    // 채팅 화면
    private void showChatScreen() {
        getContentPane().removeAll();

        // 타이틀 레이블
        setLayout(new BorderLayout());
        JLabel chatLabel = new JLabel(" 채팅", SwingConstants.LEFT);
        chatLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        chatLabel.setForeground(Color.WHITE);
        chatLabel.setBackground(pColor);
        chatLabel.setOpaque(true);
        add(chatLabel, BorderLayout.NORTH);

        // 채팅 내용 패널
        JPanel chatContentPanel = new JPanel();
        chatContentPanel.setLayout(new BoxLayout(chatContentPanel, BoxLayout.Y_AXIS));

        // 단톡방 추가
        JPanel message1 = createMessagePanel("▶ 자바 조별과제 (4)", "   확인하고 의견 주세용");
        JPanel message2 = createMessagePanel("▶ 운영체제 조별과제 (5)", "   넵 수정하고 보내드릴게요");

        // 단톡방 크기 설정
        message1.setPreferredSize(new Dimension(400, 80));
        message1.setMaximumSize(new Dimension(400, 80));
        message2.setPreferredSize(new Dimension(400, 80));
        message2.setMaximumSize(new Dimension(400, 80));

        chatContentPanel.add(message1);
        chatContentPanel.add(message2);
        chatContentPanel.add(Box.createVerticalGlue());

        // 단톡방 마우스 상호작용
        addMouseHoverEffect(message1);
        addMouseHoverEffect(message2);

        // 스크롤 가능
        JScrollPane chatScrollPane = new JScrollPane(chatContentPanel);
        chatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        chatScrollPane.setPreferredSize(new Dimension(300, 400));
        add(chatScrollPane, BorderLayout.CENTER);

        // 새로운 채팅방 개설 버튼 추가
        chatContentPanel.add(Box.createRigidArea(new Dimension(0, 200)));
        JButton newChatButton = new JButton("새로운 채팅방 개설");
        newChatButton.setPreferredSize(new Dimension(250, 60));
        newChatButton.setBackground(pColor);
        newChatButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel createChatRoomPanel = new JPanel();
        createChatRoomPanel.add(newChatButton);
        chatContentPanel.add(createChatRoomPanel);

        // 하단 버튼 추가
        JPanel buttonPanel = new JPanel();
        JButton timetableButton = new JButton("시간표");
        JButton planButton = new JButton("계획");
        JButton chatButton = new JButton("채팅");

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

    // 메세지 패널 생성
    private JPanel createMessagePanel(String title, String message) {
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());
        messagePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        messagePanel.setBackground(Color.WHITE);

        // 제목 레이블
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        messagePanel.add(titleLabel, BorderLayout.NORTH);

        // 메시지 레이블
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        messagePanel.add(messageLabel, BorderLayout.CENTER);

        return messagePanel;
    }

    // 마우스 상호작용
    private void addMouseHoverEffect(JPanel panel) {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(new Color(255, 200, 200)); // 배경색 변경
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(Color.WHITE); // 원래 색으로 복구
            }
        });
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TaskManager());
    }
}