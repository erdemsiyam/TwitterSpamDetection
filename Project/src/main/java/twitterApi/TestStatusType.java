package twitterApi;

public enum  TestStatusType {
    URL_SPAMMER ("urlSpammer"),
    FRIEND_SPAMMER("friendSpammer"),
    SIMILARITY_SPAMMER("similaritySpammer")
    ;

    private String key;

    private TestStatusType(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }
}
