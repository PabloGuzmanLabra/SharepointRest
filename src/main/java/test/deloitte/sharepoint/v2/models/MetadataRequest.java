package test.deloitte.sharepoint.v2.models;

public class MetadataRequest {
    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public Boolean getAccess() {
        return Access;
    }

    public void setAccess(Boolean access) {
        Access = access;
    }

    public MetadataRequest(String category, Boolean access) {
        Category = category;
        Access = access;
    }

    public MetadataRequest() {
    }

    public String Category;
    public Boolean Access;

}
