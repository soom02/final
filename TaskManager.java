import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.*;

/**
 * 시간표, 과제 관리를 할 수 있는 TaskManager 클래스입니다.
 *
 * 사용자는 닉네임을 설정하고, 시간표와 과제 목록을 확인할 수 있습니다.
 * 과목의 세부정보를 확인하거나, 과제 관리 기능도 포함되어 있습니다.
 * 기능적인 요소는 구현되지 않았으나, 채팅 화면 또한 존재합니다.
 *
 * @author 전수민
 * @version 1.0
 * @since 1.0
 */

public class TaskManager extends JFrame {

    private String nickname; // 사용자 이름
    private JTextField nicknameField;
    private Color pColor = new Color(255, 133, 180); // 메인 컬러

    // 시간표 화면 사용
    private JTextArea detailArea;
    private HashMap<String, String[]> courseDetails; // 과목 세부 정보 저장

    // 과제 화면 사용
    private ArrayList<Task> tasks; // 과제 목록

    /**
     * TaskManager 생성자입니다.
     *
     * 초기 화면을 설정하고, 닉네임 입력 UI를 생성합니다.
     */
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

        /**
         * 닉네임을 입력할 때 사용되는 리스너입니다.
         *
         * 환영 인사를 팝업창으로 출력하고 메인 화면으로 이동하게끔 돕습니다.
         */
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

    /**
     * 과목 세부 정보 초기화하는 메서드입니다.
     *
     * 과목별 세부사항을 HashMap에 저장했습니다.
     * 능력 부족으로 미리 내용을 지정하는 방향으로 구현했습니다.
     */
    private void initializeCourseDetails() {
        courseDetails = new HashMap<>();

        courseDetails.put("알고리즘설계", new String[]{"OOO 교수님", "화1-3, 06-408"});
        courseDetails.put("GUI프로그래밍", new String[]{"OOO 교수님", "수1-3, 06-408"});
        courseDetails.put("JAVA프로그래밍2", new String[]{"남수만 교수님", "수5-7, 06-310"});
        courseDetails.put("운영체제", new String[]{"OOO 교수님", "금1-3, 06-408"});
        courseDetails.put("ENGLISH3", new String[]{"OOO 교수님", "금4-5, 20-229"});
    }

    // 시간표 화면 - 메인 화면

    /**
     * 메인 화면이 될 시간표 화면을 출력하는 메서드입니다.
     *
     * 사용자의 시간표와 클릭 시 과목 세부정보를 표시합니다.
     * 시간표는 능력 부족으로 미리 내용을 지정하는 방향으로 구현했습니다.
     */
    private void showTimetableScreen() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());
        setTitle(nickname + "님의 Task Manager");

        // 타이틀 레이블
        JLabel timetableLabel = new JLabel(nickname + "님의 시간표", SwingConstants.CENTER);
        timetableLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        timetableLabel.setForeground(pColor);
        add(timetableLabel, BorderLayout.NORTH);

        // 시간표 생성
        String[] columnNames = {"시간", "월", "화", "수", "목", "금"};
        String[][] data = new String[9][6];

        // 시간표 배정
        for (int i = 0; i < 9; i++) {
            data[i][0] = String.valueOf(i + 1); // 1~9 교시
        }

        data[0][2] = "알고리즘설계";        data[1][2] = "알고리즘설계";        data[2][2] = "알고리즘설계";
        data[0][3] = "GUI프로그래밍";        data[1][3] = "GUI프로그래밍";        data[2][3] = "GUI프로그래밍";
        data[4][3] = "JAVA프로그래밍2";        data[5][3] = "JAVA프로그래밍2";        data[6][3] = "JAVA프로그래밍2";
        data[0][5] = "운영체제";        data[1][5] = "운영체제";        data[2][5] = "운영체제";
        data[3][5] = "ENGLISH3";        data[4][5] = "ENGLISH3";

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

        /**
         * 셀을 클릭할 때 사용되는 리스너입니다.
         *
         * 클릭한 시간(셀)에 따라 해당하는 세부사항을 출력하게끔 돕습니다.
         */
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


        /**
         * 여러 화면을 이리저리 이동할 수 있도록 만든 버튼 패널입니다.
         * 자신의 화면에서는 해당 버튼이 눌리지 않도록 설정했습니다.
         * (예시 : 시간표 화면에서 시간표 버튼 비활성화)
         */
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

    // 과목 세부사항 출력 메서드

    /**
     * 주어진 과목의 세부사항을 출력하는 메서드입니다.
     *
     * 과목의 교수명, 강의시간, 강의실 정보를 포함하며, 과제 추가 시에는 해당 과목의 과제 목록도 함께 출력합니다.
     *
     * @param courseName 과목 이름
     */
    private void displayCourseDetails(String courseName) {
        String[] details = courseDetails.get(courseName);
        StringBuilder info = new StringBuilder();

        info.append(courseName).append("\n");
        if (details != null) {
            if (details.length > 0) {
                info.append("교수 : ").append(details[0]).append("\n");
            }
            if (details.length > 1) {
                info.append("강의시간 및 강의실 : ").append(details[1]).append("\n");
            }
        }

        // 과목 해당 과제 추가
        List<Task> courseTasks = getTasksForCourse(courseName);
        if (!courseTasks.isEmpty()) {
            info.append("과제 목록 :\n");
            for (Task task : courseTasks) {
                info.append("- ").append(task.getDescription()).append(" (마감일 : ").append(task.getDueDate()).append(")\n");
            }
        } else {
            info.append("과제 목록 : \n" + "과제가 없습니다.\n");
        }
        detailArea.setText(info.toString());
    }

    /**
     * 주어진 과목의 과제를 확인하고 해당 내용을 반환하는 메서드입니다.
     *
     * @param courseName 과목 이름
     * @return 해당 과목의 과제 목록
     */
    private List<Task> getTasksForCourse(String courseName) {
        List<Task> courseTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getName().equals(courseName)) {
                courseTasks.add(task);
            }
        }
        return courseTasks;
    }

    /**
     * 주된 기능을 포함한 과제 화면을 출력하는 메서드입니다.
     *
     * 사용자가 직접 과제를 추가하고 삭제할 수 있으며, 추가된 과제 목록을 확인할 수 있습니다.
     */
    // 과제 화면
    private void showTaskScreen() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        // 타이틀 레이블
        JLabel taskLabel = new JLabel(nickname + "님의 과제", SwingConstants.CENTER);
        taskLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        taskLabel.setForeground(pColor);
        add(taskLabel, BorderLayout.NORTH);

        // 과제 추가 패널
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        String[] subjects = {"알고리즘설계", "GUI프로그래밍", "JAVA프로그래밍2", "운영체제", "ENGLISH3"};
        JComboBox<String> subjectComboBox = new JComboBox<>(subjects);
        JTextField descriptionField = new JTextField(20);
        JTextField dueDateField = new JTextField(20);
        JButton addButton = new JButton("과제 추가");
        JButton deleteButton = new JButton("과제 삭제");

        inputPanel.add(new JLabel("     과목"));
        inputPanel.add(subjectComboBox);
        inputPanel.add(new JLabel("     과제"));
        inputPanel.add(descriptionField);
        inputPanel.add(new JLabel("    마감일"));
        inputPanel.add(dueDateField);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        inputPanel.add(addButton);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        inputPanel.add(deleteButton);

        inputPanel.setMaximumSize(new Dimension(400, 400));

        // 메인 패널
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(inputPanel);

        // 과제 목록 텍스트 영역
        JTextArea taskArea = new JTextArea();
        taskArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(taskArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        mainPanel.add(scrollPane); // 과제 목록을 아래에 추가

        add(mainPanel, BorderLayout.CENTER);

        // 과제 추가 리스너
        addButton.addActionListener(e -> {
            String selectedSubject = (String) subjectComboBox.getSelectedItem();
            String description = descriptionField.getText();
            String dueDate = dueDateField.getText();
            addTask(selectedSubject, description, dueDate);
            descriptionField.setText("");
            dueDateField.setText("");
            updateTaskArea(taskArea); // 과제 목록 업데이트
        });

        updateTaskArea(taskArea); // 초기 과제 목록 업데이트

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
        add(buttonPanel, BorderLayout.SOUTH); // 하단에 추가

        setVisible(true);
        revalidate();
        repaint();
    }

    /**
     * 과제를 추가하는 메서드입니다.
     *
     * 과목을 선택한 후 과제를 추가할 수 있습니다.
     * 추가된 과제는 파일로 저장됩니다.
     *
     * @param name 과목 이름
     * @param description 과제 설명
     * @param dueDate 마감일
     */
    private void addTask(String name, String description, String dueDate) {
        tasks.add(new Task(name, description, dueDate));
        saveTasksToFile();
    }

    /**
     * 현재 과제 목록을 업데이트하는 메서드입니다.
     *
     * 과제 목록을 새로 추가합니다.
     *
     * @param area 업데이트 할 JTextArea
     */
    private void updateTaskArea(JTextArea area) {
        area.setText(""); // 기존 내용 지우기
        for (Task task : tasks) {
            area.append(task.toString() + "\n");
        }
    }

    /**
     * 주어진 인덱스에 해당하는 과제를 삭제하는 메서드입니다.
     *
     * 유효한 인덱스에 해당하는 과제를 목록에서 제거하고 변경된 과제 목록을 파일에 저장합니다.
     *
     * @param index 삭제할 과제의 인덱스
     */
    private void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            saveTasksToFile();
        }
    }

    // 저장 및 불러오기 메서드

    /**
     * 현재 과제 목록을 파일에 저장한느 메서드입니다.
     *
     * 각 과제의 과목 이름, 과제 설명 및 마감일을 구분하여 저장합니다.
     *
     * @throws IOException 파일 쓰기 중 발생할 수 있는 예외
     */
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

    /**
     * 파일에서 과제 목록을 불러오는 메서드입니다.
     *
     * 파일이 존재하면 과목 이름, 과제 설명 및 마감일을 불러와서 과제 목록에 추가합니다.
     * 존재하지 않는 경우에는 빈 파일을 생성합니다.
     *
     * @throws IOException 파일 읽기 중 발생할 수 있는 예외
     */
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

    /**
     * 채팅 화면을 출력하는 메서드입니다.
     *
     * 능력 부족으로 기능적인 요소 없이 채팅방이 모여있는 UI만을 구현했습니다.
     */
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

    /**
     * 메세지 패널을 생성하는 메서드입니다.
     *
     * 주어진 제목과 메세지를 포함하는 JPanel을 반환합니다.
     * 패널은 제목 레이블과 메세지 레이블로 구성되어 있습니다.
     *
     * @param title 패널의 제목
     * @param message 패널의 메세지 내용
     * @return 생성된 메세지 패널
     */
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

    /**
     * 주어진 패널에 마우스 호버 효과를 추가하는 메서드입니다.
     *
     * 마우스 커서를 채팅창(패널) 위에 올리면 배경색이 변경되고, 마우스 커서를 채팅창 다시 밖으로 꺼내면 원래 색으로 돌아옵니다.
     *
     * @param panel 효과를 추가할 JPanel
     */
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

    /**
     * 과제 관리 시스템의 메인 진입점입니다.
     *
     * TaskManager를 초기화하고, 프로그램 시작 시에 과제 데이터를 파일에서 불러옵니다.
     *
     * @param args 커맨드라인 인수 (사용되지 않음)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TaskManager manager = new TaskManager();
            manager.loadTasksFromFile(); // 프로그램 시작 시 과제 불러오기
        });
    }
}