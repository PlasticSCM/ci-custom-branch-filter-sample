package com.plasticscm.branchfilter.jira;

import java.util.ArrayList;

public class IssueTrackerCommand {
    public final static String JIRA = "jira";

    public IssueTrackerCommand(String issueTracker, String user, String password, String host) {
        mIssueTrackerName = issueTracker;
        mUser = user;
        mPassword = password;
        mHost = host;
    }

    public ArrayList<String> findByStatus(String projectKey, String value) throws Exception {
        String[] parameters = new String[] {
            "issuetracker",
            mIssueTrackerName,
            "status",
            "find",
            value,
            ParameterBuilder.getUser(mUser),
            ParameterBuilder.getPassword(mPassword),
            ParameterBuilder.getHost(mHost),
            ParameterBuilder.getProjectKey(projectKey)};
        return execute(parameters);
    }

    public String getStatus(String projectKey, int issueNumber) throws Exception {
        String[] parameters = new String[] {
            "issuetracker",
            mIssueTrackerName,
            "status",
            "get",
            String.valueOf(issueNumber),
            ParameterBuilder.getUser(mUser),
            ParameterBuilder.getPassword(mPassword),
            ParameterBuilder.getHost(mHost),
            ParameterBuilder.getProjectKey(projectKey)};
        ArrayList<String> result = execute(parameters);

        if (result.size() != 1)
            return null;

        return result.get(0).toString();
    }

    private static ArrayList<String> execute(String[] parameters) throws Exception {
        ArrayList<String> output = new ArrayList<String>();

        if (!PlasticCmLauncher.execute(parameters, output))
            return null;

        return output;
    }

    private final static class ParameterBuilder {
        private static String getUser(String user) {
            return getParameter("--user", user);
        }

        private static String getPassword(String password) {
            return getParameter("--password", password);
        }

        private static String getHost(String host) {
            return getParameter("--host", host);
        }

        private static String getProjectKey(String projectKey) {
            return getParameter("--projectKey", projectKey);
        }

        private static String getParameter(String name, String value) {
            if (value == null || value.isEmpty())
                return "";

            return name + "=" + value;
        }
    }

    String mHost;
    String mPassword;
    String mUser;
    String mIssueTrackerName;
}
