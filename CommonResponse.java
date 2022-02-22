package spring.boot.playwright.pdf.response;

public class CommonResponse {

    private boolean success;
    private String errorCode;
    private String errorMessage;
    private String webRequestId;

    public CommonResponse(boolean success, String errorCode, String errorMessage, String webRequestId) {
        super();
        this.success = success;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.webRequestId = webRequestId;
    }

    public CommonResponse() {
        super();
    }

    public String getWebRequestId() {
        return webRequestId;
    }

    public void setWebRequestId(String webRequestId) {
        this.webRequestId = webRequestId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
