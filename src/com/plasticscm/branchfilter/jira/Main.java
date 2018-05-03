package com.plasticscm.branchfilter.jira;

import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
    public static void main(String[] args) {
        try {
            BranchInfo[] branches = readBranches();

            BranchInfo[] filteredBranches = BranchFilter.filter(
                getJiraConfiguration(System.getenv()), branches);

            writeBranches(filteredBranches);
            System.exit(0);
        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
            System.exit(1);
        }
    }

    private static JiraConfiguration getJiraConfiguration(Map<String, String> env){
        JiraConfiguration jiraConfiguration = new JiraConfiguration();
        jiraConfiguration.Host = env.get("PLASTICSCM_JIRA_HOST");
        jiraConfiguration.User = env.get("PLASTICSCM_JIRA_USER");
        jiraConfiguration.CleanPassword = env.get("PLASTICSCM_JIRA_CLEAN_PASSWORD");
        jiraConfiguration.ProjectKey = env.get("PLASTICSCM_JIRA_PROJECT_KEY");
        jiraConfiguration.BranchPrefix = env.get("PLASTICSCM_JIRA_BRANCH_PREFIX");
        jiraConfiguration.ResolvedIssueStatus = env.get("PLASTICSCM_JIRA_RESOLVED_ISSUE_STATUS");
        return jiraConfiguration;
    }
    
    private static void writeBranches(final BranchInfo[] branches){
        Gson gson = new GsonBuilder().create();
        gson.toJson(branches, System.out);
    }

    private static BranchInfo[] readBranches() throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(reader.readLine(), BranchInfo[].class);
        } finally {
            reader.close();
        }
    }
}