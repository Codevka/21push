package buaa.backend.metadata;

public enum ContractStatus {
    UNCHECKED("未审核"), UNPAY("未缴费"), FINISHED("已缴费");

    private final String text;

    ContractStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}