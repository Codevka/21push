package buaa.backend.metadata;

public enum ComplaintStatus {
    UNSOLVED("未处理"), UNEVAL("未评价"), FINISHED("已评价");
    private final String text;

    ComplaintStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static ComplaintStatus fromString(String string) {
        for (ComplaintStatus c : ComplaintStatus.values()) {
            if (c.text.equals(string)) {
                return c;
            }
        }
        return null;
    }
}
