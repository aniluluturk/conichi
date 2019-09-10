package conichiapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TimeResponse {
    private String humanStr;
    private String defaultStr;

    public TimeResponse() {

    }

    public TimeResponse(String humanStr, String defaultStr) {
        this.humanStr = humanStr;
        this.defaultStr = defaultStr;
    }

    public void setHumanStr(String humanStr) {
        this.humanStr = humanStr;
    }

    @JsonProperty("human_date")
    public String getHumanStr() {
        return humanStr;
    }

    public void setDefaultStr(String defaultStr) {
        this.defaultStr = defaultStr;
    }

    @JsonProperty("default_date")
    public String getDefaultStr() {
        return defaultStr;
    }
}
