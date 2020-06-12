package buaa.backend.metadata;

public enum ContractStatus {
    UNCHECKED("未审核"), UNPAY("未缴费"), FINISHED("已缴费"), ENDED("已退租"),
    NOT_SIGN("未签订合同"), SIGN("已签订合同"),REFUSED("已拒绝");

    private final String text;

    ContractStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static ContractStatus fromString(String string) {
        for (ContractStatus c : ContractStatus.values()) {
            if (c.text.equals(string)) {
                return c;
            }
        }
        return null;
    }
}
