package buaa.backend.metadata;

public enum RepairStatus {
    UNSOLVED("未处理"), RUNNING("工单建立成功"), UNEVAL("未评价"), FINISHED("已评价"), DENY("已拒绝");
    private final String text;

    RepairStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static RepairStatus fromString(String string) {
        for (RepairStatus c : RepairStatus.values()) {
            if (c.text.equals(string)) {
                return c;
            }
        }
        return null;
    }
}
