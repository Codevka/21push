package buaa.backend.metadata;

public enum RepairStatus {
    UNSOLVED("未处理"), UNEVAL("未评价"), FINISHED("已评价");
    private final String text;

    RepairStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}