package buaa.backend.metadata;

public enum WorkStatus {
    UNSOLVED("未处理"), FINISHED("已处理");

    private final String text;

    WorkStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
