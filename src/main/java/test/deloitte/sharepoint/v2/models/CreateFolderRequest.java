package test.deloitte.sharepoint.v2.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateFolderRequest {

    @JsonProperty("ServerRelativeUrl")
    public String ServerRelativeUrl;

    public CreateFolderRequest(String serverRelativeUrl) {
        ServerRelativeUrl = serverRelativeUrl;
    }

    public CreateFolderRequest() {
    }

    public String getServerRelativeUrl() {
        return ServerRelativeUrl;
    }

    public void setServerRelativeUrl(String serverRelativeUrl) {
        ServerRelativeUrl = serverRelativeUrl;
    }
}
