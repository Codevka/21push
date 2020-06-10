package buaa.backend.metadata;

public enum UserType {
    RENTER("租客"), ADMIN("客服"), WORKER("维修人员");
    private final String text;

    UserType(String s) {
        text = s;
    }

    public String getText() {
        return text;
    }
}
