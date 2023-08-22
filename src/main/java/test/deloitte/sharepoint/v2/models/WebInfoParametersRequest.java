package test.deloitte.sharepoint.v2.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nimbusds.jose.shaded.gson.annotations.SerializedName;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WebInfoParametersRequest {
    public WebInfoParametersRequest(String url, String title, String webTemplate) {
        Url = url;
        Title = title;
        WebTemplate = webTemplate;
    }
    @JsonProperty("Url")
    @SerializedName("Url")
    private String Url;
    @JsonProperty("Title")
    private String Title;
    @SerializedName("WebTemplate")
    @JsonProperty("WebTemplate")
    private String WebTemplate;

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getWebTemplate() {
        return WebTemplate;
    }

    public void setWebTemplate(String webTemplate) {
        WebTemplate = webTemplate;
    }
}
