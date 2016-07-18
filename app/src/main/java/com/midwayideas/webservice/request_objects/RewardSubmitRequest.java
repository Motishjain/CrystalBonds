package com.midwayideas.webservice.request_objects;

import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 3/25/2016.
 */
public class RewardSubmitRequest {

    private String outletCode;

    private String rewardCategory;

    private List<String> rewardIdList;

    private Date createdDate;

    public String getOutletCode() {
        return outletCode;
    }

    public void setOutletCode(String outletCode) {
        this.outletCode = outletCode;
    }

    public void setRewardCategory(String rewardCategory) {
        this.rewardCategory = rewardCategory;
    }

    public void setRewardIdList(List<String> rewardIdList) {
        this.rewardIdList = rewardIdList;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
