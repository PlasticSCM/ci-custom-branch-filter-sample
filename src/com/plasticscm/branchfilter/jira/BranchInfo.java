package com.plasticscm.branchfilter.jira;

import java.util.Date;

public class BranchInfo {
    private String mParent;
    private String mType;
    private String mChangeset;
    private String mId;
    private String mName;
    private String mDate;
    private String mOwner;
    private String mRepository;
    private String mRepServer;
    private String mComments;
    private Date mDateObject;

    public String getName() 
    {
        return mName;
    }
}
