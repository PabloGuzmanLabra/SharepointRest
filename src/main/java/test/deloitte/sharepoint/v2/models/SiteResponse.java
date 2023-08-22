package test.deloitte.sharepoint.v2.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Array;

public class SiteResponse {

    public SiteResponse(Array[] d) {
        this.d = d;
    }

    @JsonProperty("d")
    private Array[] d;

    public Array[] getD() {
        return d;
    }

    public void setD(Array[] d) {
        this.d = d;
    }
}
