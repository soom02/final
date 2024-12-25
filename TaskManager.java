import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class TaskManager extends JFrame {

    private String nickname; // 사용자 이름
    private JTextField nicknameField;
    private Color pColor = new Color(255, 133, 180); // 메인 컬러

    // 시간표 화면 사용
    private JTextArea detailArea;
    private HashMap<String, String[]> courseDetails; // 과목 세부 정보 저장

    // 과제 화면 사용
    private ArrayList<Task> tasks; // 과제 목록

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

        // 과제 목록 초기화
        tasks = new ArrayList<>();

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

        // 닉네임 레이블
        JLabel timetableLabel = new JLabel(nickname + "님의 시간표", SwingConstants.CENTER);
        timetableLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        timetableLabel.setForeground(Color.BLACK);
        add(timetableLabel, BorderLayout.NORTH);

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

        // 하단 버튼 추가
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton timetableButton = new JButton("시간표");
        JButton taskButton = new JButton("과제");
        JButton chatButton = new JButton("채팅");

        // 버튼 크기 통일
        Dimension buttonSize = new Dimension(100, 35);
        timetableButton.setPreferredSize(buttonSize);
        taskButton.setPreferredSize(buttonSize);
        chatButton.setPreferredSize(buttonSize);

        timetableButton.setBackground(pColor);
        taskButton.setBackground(pColor);
        chatButton.setBackground(pColor);

        timetableButton.setEnabled(false); // 비활성화

        // 버튼 클릭 이동
        taskButton.addActionListener(e -> showTaskScreen());
        chatButton.addActionListener(e -> showChatScreen());

        buttonPanel.add(timetableButton);
        buttonPanel.add(taskButton);
        buttonPanel.add(chatButton);
        add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
        revalidate();
        repaint();
    }

    // 과목 세부 정보 표시 메소드
    private void displayCourseDetails(String courseName) {
        String[] details = courseDetails.get(courseName);
        if (details != null) {
            StringBuilder info = new StringBuilder();
            info.append(courseName).append("\n");
            if (details.length > 0) {
                info.append("교수 : ").append(details[0]).append("\n");
            }
            if (details.length > 1) {
                info.append("강의시간 및 강의실 : ").append(details[1]).append("\n");
            }
            detailArea.setText(info.toString());
        } else {
            detailArea.setText("세부 정보를 찾을 수 없습니다.");
        }
    }

    // 과제 화면
    private void showTaskScreen() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        // 타이틀 패널
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setPreferredSize(new Dimension(440, 50));
        JLabel planLabel = new JLabel(nickname + "님의 과제");
        planLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        titlePanel.add(planLabel);
        add(titlePanel, BorderLayout.NORTH);

        // 과제 추가
        JPanel inputPanel = new JPanel();
        String[] subjects = {"알고리즘설계", "GUI프로그래밍", "JAVA프로그래밍2", "운영체제", "ENGLISH3"};
        JComboBox<String> subjectComboBox = new JComboBox<>(subjects);
        JTextField descriptionField = new JTextField(10);
        JTextField dueDateField = new JTextField(10);
        JButton addButton = new JButton("과제 추가");

        inputPanel.add(new JLabel("과목:"));
        inputPanel.add(subjectComboBox);
        inputPanel.add(new JLabel("설명:"));
        inputPanel.add(descriptionField);
        inputPanel.add(new JLabel("마감일:"));
        inputPanel.add(dueDateField);
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);

        // 과제 목록
        JTextArea taskArea = new JTextArea();
        taskArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(taskArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            String selectedSubject = (String) subjectComboBox.getSelectedItem();
            String description = descriptionField.getText();
            String dueDate = dueDateField.getText();
            addTask(selectedSubject, description, dueDate);
            descriptionField.setText("");
            dueDateField.setText("");
            updateTaskArea(taskArea); // 과제 목록 업데이트
        });

        updateTaskArea(taskArea);

        // 하단 버튼 추가
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton timetableButton = new JButton("시간표");
        JButton taskButton = new JButton("과제");
        JButton chatButton = new JButton("채팅");

        // 버튼 크기 통일
        Dimension buttonSize = new Dimension(100, 35);
        timetableButton.setPreferredSize(buttonSize);
        taskButton.setPreferredSize(buttonSize);
        chatButton.setPreferredSize(buttonSize);

        timetableButton.setBackground(pColor);
        taskButton.setBackground(pColor);
        chatButton.setBackground(pColor);

        taskButton.setEnabled(false); // 비활성화

        // 버튼 클릭 이동
        timetableButton.addActionListener(e -> showTimetableScreen());
        chatButton.addActionListener(e -> showChatScreen());

        buttonPanel.add(timetableButton);
        buttonPanel.add(taskButton);
        buttonPanel.add(chatButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
        revalidate();
        repaint();
    }

    // 과제 추가 메서드
    private void addTask(String name, String description, String dueDate) {
        tasks.add(new Task(name, description, dueDate));
        saveTasksToFile();
    }

    // 과제 수정 메서드
    private void updateTaskArea(int index, String name, String description, String dueDate) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).setName(name);
            tasks.get(index).setDescription(description);
            tasks.get(index).setDueDate(dueDate);
            saveTasksToFile();
        }
    }

    // 과제 목록 수정 메서드
    private void updateTaskArea(JTextArea area) {
        area.setText(""); // 기존 내용 지우기
        for (Task task : tasks) {
            area.append(task.toString() + "\n");
        }
    }

    // 과제 삭제 메서드
    private void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            saveTasksToFile();
        }
    }

    // 저장 및 불러오기 메서드
    private void saveTasksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"))) {
            for (Task task : tasks) {
                writer.write(task.getName() + "," + task.getDescription() + "," + task.getDueDate());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTasksFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("tasks.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    addTask(parts[0], parts[1], parts[2]);
                }
            }
        } catch (FileNotFoundException e) {
            // 파일이 없으면 새로 생성
            try {
                new FileWriter("tasks.txt"); // 빈 파일 생성
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 채팅 화면
    private void showChatScreen() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        // 타이틀 레이블
        JLabel chatLabel = new JLabel(" 채팅", SwingConstants.LEFT);
        chatLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        chatLabel.setForeground(Color.WHITE);
        chatLabel.setBackground(pColor);
        chatLabel.setOpaque(true);
        add(chatLabel, BorderLayout.NORTH);

        // 채팅 내용 패널
        JPanel chatContentPanel = new JPanel();
        chatContentPanel.setLayout(new BoxLayout(chatContentPanel, BoxLayout.Y_AXIS));

        // 채팅방 추가
        JPanel message1 = createMessagePanel("▶ 자바 조별과제 (4)", "   확인하고 의견 주세용");
        JPanel message2 = createMessagePanel("▶ 운영체제 조별과제 (5)", "   넵 수정하고 보내드릴게요");

        message1.setPreferredSize(new Dimension(400, 80));
        message1.setMaximumSize(new Dimension(400, 80));
        message2.setPreferredSize(new Dimension(400, 80));
        message2.setMaximumSize(new Dimension(400, 80));

        chatContentPanel.add(message1);
        chatContentPanel.add(message2);
        chatContentPanel.add(Box.createVerticalGlue());

        // 채팅방 마우스 인터랙션
        addMouseHoverEffect(message1);
        addMouseHoverEffect(message2);

        // 스크롤 가능
        JScrollPane chatScrollPane = new JScrollPane(chatContentPanel);
        chatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        chatScrollPane.setPreferredSize(new Dimension(300, 400));
        add(chatScrollPane, BorderLayout.CENTER);

        // 새로운 채팅방 개설 버튼 추가 - 기능 X
        chatContentPanel.add(Box.createRigidArea(new Dimension(0, 200)));
        JButton newChatButton = new JButton("+ 새로운 채팅방 개설");
        newChatButton.setPreferredSize(new Dimension(250, 60));
        newChatButton.setBackground(pColor);
        newChatButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel createChatRoomPanel = new JPanel();
        createChatRoomPanel.add(newChatButton);
        chatContentPanel.add(createChatRoomPanel);

        // 하단 버튼 추가
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton timetableButton = new JButton("시간표");
        JButton taskButton = new JButton("과제");
        JButton chatButton = new JButton("채팅");

        // 버튼 크기 통일
        Dimension buttonSize = new Dimension(100, 35);
        timetableButton.setPreferredSize(buttonSize);
        taskButton.setPreferredSize(buttonSize);
        chatButton.setPreferredSize(buttonSize);

        timetableButton.setBackground(pColor);
        taskButton.setBackground(pColor);
        chatButton.setBackground(pColor);

        chatButton.setEnabled(false); // 비활성화

        // 버튼 클릭 이동
        timetableButton.addActionListener(e -> showTimetableScreen());
        taskButton.addActionListener(e -> showTaskScreen());

        buttonPanel.add(timetableButton);
        buttonPanel.add(taskButton);
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
        SwingUtilities.invokeLater(() -> {
            TaskManager manager = new TaskManager();
            manager.loadTasksFromFile(); // 프로그램 시작 시 과제 불러오기
        });
    }

}