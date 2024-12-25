public class Task {
    private String name; // 과제 이름
    private String description; // 과제 설명
    private String dueDate; // 마감일

    public Task(String name, String description, String dueDate) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
    }

    // Getter 메소드
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getDueDate() { return dueDate; }

    // Setter 메소드 추가
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    // 과제 정보를 문자열로 반환
    @Override
    public String toString() {
        return name + " - " + description + " (마감일: " + dueDate + ")";
    }
}
