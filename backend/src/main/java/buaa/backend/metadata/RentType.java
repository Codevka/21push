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
}
