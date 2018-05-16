package com.plasticscm.branchfilter.jira;

public class Branch {
    private String BranchId;
    private String BranchName;
    private String BranchHeadChangeset;
    private String BranchComments;
    private String BranchCreationDate;
    private String BranchOwner;
    private String Repository;
    private String Server;

    public String getName() {
        return BranchName;
    }
}
