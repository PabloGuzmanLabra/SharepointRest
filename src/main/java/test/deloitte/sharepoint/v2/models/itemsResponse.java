package test.deloitte.sharepoint.v2.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class itemsResponse {
    public itemsResponse() {
    }

    public String getVti_x005f_listid() {
        return vti_x005f_namespacelistid;
    }

    public void setVti_x005f_listid(String vti_x005f_listid) {
        this.vti_x005f_namespacelistid = vti_x005f_listid;
    }

    @JsonProperty("vti_x005f_namespacelistid")
    public String vti_x005f_namespacelistid;

    public itemsResponse(String vti_x005f_listid) {
        this.vti_x005f_namespacelistid = vti_x005f_listid;
    }

}
