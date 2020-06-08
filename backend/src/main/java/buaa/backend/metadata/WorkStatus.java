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

    static WorkStatus fromString(String string) {
        for (WorkStatus c : WorkStatus.values()) {
            if (c.text.equals(string)) {
                return c;
            }
        }
        return null;
    }
}
