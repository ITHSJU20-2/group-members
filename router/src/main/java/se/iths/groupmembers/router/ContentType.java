package se.iths.groupmembers.router;

public enum ContentType {
    HTML("text/html"),
    CSS("text/css");

    private final String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
