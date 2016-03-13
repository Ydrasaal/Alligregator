
package ydrasaal.alligregator.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FindResults {

    @SerializedName("responseData")
    @Expose
    private FindResponseData responseData;
    @SerializedName("responseDetails")
    @Expose
    private Object responseDetails;
    @SerializedName("responseStatus")
    @Expose
    private Integer responseStatus;

    /**
     * 
     * @return
     *     The responseData
     */
    public FindResponseData getResponseData() {
        return responseData;
    }

    /**
     * 
     * @param responseData
     *     The responseData
     */
    public void setResponseData(FindResponseData responseData) {
        this.responseData = responseData;
    }

    /**
     * 
     * @return
     *     The responseDetails
     */
    public Object getResponseDetails() {
        return responseDetails;
    }

    /**
     * 
     * @param responseDetails
     *     The responseDetails
     */
    public void setResponseDetails(Object responseDetails) {
        this.responseDetails = responseDetails;
    }

    /**
     * 
     * @return
     *     The responseStatus
     */
    public Integer getResponseStatus() {
        return responseStatus;
    }

    /**
     * 
     * @param responseStatus
     *     The responseStatus
     */
    public void setResponseStatus(Integer responseStatus) {
        this.responseStatus = responseStatus;
    }

}
