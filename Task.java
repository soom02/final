/**
 * 과제의 정보를 저장하는 Task 클래스입니다.
 */

public class Task {
    private String name;
    private String description;
    private String dueDate;

    /**
     * Task 클래스의 생성자입니다.
     *
     * @param name 과목 이름
     * @param description 과제 설명
     * @param dueDate 마감일
     */
    public Task(String name, String description, String dueDate) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
    }

    /**
     * 과목 이름을 반환합니다.
     *
     * @return 과목 이름
     */
    public String getName() { return name; }
    /**
     * 과제 설명을 반환합니다.
     *
     * @return 과제 설명
     */
    public String getDescription() { return description; }
    /**
     * 마감일을 반환합니다.
     *
     * @return 마감일
     */
    public String getDueDate() { return dueDate; }

    /**
     * 과목 이름을 설정합니다.
     *
     * @param name 설정할 과목 이름
     */
    public void setName(String name) { this.name = name; }

    /**
     * 과제 설명을 설정합니다.
     *
     * @param description 설정할 과제 설명
     */
    public void setDescription(String description) { this.description = description; }
    /**
     * 마감일을 설정합니다.
     *
     *  @param dueDate 설정할 마감일
     */
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    /**
     * 과제 정보를 문자열로 반환합니다.
     *
     * @return 과제 정보를 포함하는 문자열
      */
    @Override
    public String toString() {
        return name + " - " + description + " (마감일: " + dueDate + ")";
    }
}
