package buaa.backend.metadata;

public enum HouseType {
    SINGLE("单人间"), DOUBLE("双人间"), TRIPLE("三人间"), QUADRUPLE("四人间");

    private final String text;

    HouseType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    static HouseType fromString(String string) {
        for (HouseType c : HouseType.values()) {
            if (c.text.equals(string)) {
                return c;
            }
        }
        return null;
    }
}
