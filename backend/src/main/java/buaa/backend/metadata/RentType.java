package buaa.backend.metadata;

public enum RentType {
    SHORT("短租"), LONG("长租");
    private final String text;

    RentType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static RentType fromString(String string) {
        for (RentType r : RentType.values()) {
            if (r.text.equals(string)) {
                return r;
            }
        }
        return null;
    }
}
