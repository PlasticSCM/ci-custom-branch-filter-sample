package com.plasticscm.branchfilter.jira;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BranchFilter {
    public static BranchInfo[] filter(
        final JiraConfiguration configuration,
        final BranchInfo[] branches) throws Exception {

            if (branches.length == 1) {
                boolean isEnabled = isBranchEnabled(configuration, branches[0]); 
                return isEnabled ? branches : new BranchInfo[0];
            }

            List<BranchInfo> openBranches = getOpenBranches(configuration, branches); 
            return openBranches.toArray(new BranchInfo[openBranches.size()]);
    }

    private static List<BranchInfo> getOpenBranches(
        final JiraConfiguration configuration,
        final BranchInfo[] branches) throws Exception {

        IssueTrackerCommand issueTrackerCmd = new IssueTrackerCommand(
            IssueTrackerCommand.JIRA, configuration.User,
            configuration.CleanPassword, configuration.Host);

        ArrayList<String> jiraIssues = issueTrackerCmd.findByStatus(
            configuration.ProjectKey, configuration.ResolvedIssueStatus);

        String branchPrefix = configuration.BranchPrefix == null ?
            "" : configuration.BranchPrefix ;

        return matchBranchesToJiraIssues(branches, jiraIssues, branchPrefix);
    }

    private static boolean isBranchEnabled(
        final JiraConfiguration configuration,
        final BranchInfo branchInfo) throws Exception {
        int issueNumber = getJiraIssueNumber(
            branchInfo.getName(), configuration.BranchPrefix);

        if (issueNumber == -1)
            return false;

        IssueTrackerCommand issueTrackerCmd = new IssueTrackerCommand(
            IssueTrackerCommand.JIRA, configuration.User,
            configuration.CleanPassword, configuration.Host);

        String jiraIssueStatus = issueTrackerCmd.getStatus(
            configuration.ProjectKey, issueNumber);

        return jiraIssueStatus.equals(configuration.ResolvedIssueStatus);
    }

    private static ArrayList<BranchInfo> matchBranchesToJiraIssues(
        final BranchInfo[] branches,
        final ArrayList<String> jiraIssues,
        final String branchPrefix) {
        ArrayList<BranchInfo> result = new ArrayList<BranchInfo>(jiraIssues.size());

        if (jiraIssues.size() == 0)
            return result;

        HashSet<String> namesToMatch = new HashSet<String>(jiraIssues.size());
        for (String jiraIssue : jiraIssues)
            namesToMatch.add(branchPrefix + jiraIssue);

        for (BranchInfo branch : branches) {
            String branchName = getSimpleBranchName(branch.getName());
            if (namesToMatch.contains(branchName)) {
                result.add(branch);
            }
        }

        return result;
    }

    private static int getJiraIssueNumber(
        final String branchName,
        final String jiraBranchPrefix){
        String cleanBranchName = removeJiraBranchPrefix(
            getSimpleBranchName(branchName), jiraBranchPrefix);

        try {
            return Integer.parseInt(cleanBranchName);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    private static String removeJiraBranchPrefix(
        final String branchName,
        final String jiraBranchPrefix){
        if (jiraBranchPrefix == null ||
            jiraBranchPrefix.isEmpty())
            return branchName;

        if (!branchName.startsWith(jiraBranchPrefix))
            return branchName;

        return branchName.substring(jiraBranchPrefix.length());
    }

    private static String getSimpleBranchName(
        final String branchName) {
        int indexOfSeparator = branchName.lastIndexOf('/');

        if (indexOfSeparator == -1)
            return branchName;

        return branchName.substring(indexOfSeparator + 1);
    }
}
