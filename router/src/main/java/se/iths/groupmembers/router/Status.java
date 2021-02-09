package se.iths.groupmembers.router;

public enum Status {
    OK("OK", 200),
    NOT_FOUND("Not Found", 404);

    private final String statusString;
    private final int statusCode;

    Status(String statusString, int statusCode) {
        this.statusString = statusString;
        this.statusCode = statusCode;
    }

    public String getStatusString() {
        return statusString;
    }

    public int getStatus() {
        return statusCode;
    }
}
