package buaa.backend.metadata;

public enum HouseStatus {
    INT("暂停出租"), OK("未租满"), FULL("已租满");

    public String getText() {
        return text;
    }

    private final String text;

    HouseStatus(String text) {
        this.text = text;
    }

    public static HouseStatus fromString(String string) {
        for (HouseStatus c : HouseStatus.values()) {
            if (c.text.equals(string)) {
                return c;
            }
        }
        return null;
    }
}
