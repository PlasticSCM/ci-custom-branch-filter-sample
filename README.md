# custom-branch-filter-sample

A custom branch filter for Plastic and Jira, which can be used for TeamCity CI Plugin.

When configuring the Plastic repository for TeamCity, a new checkbox named "Enable custom brach filtering" will appear.
Once it is enabled, a textbox allows specifying the command to run the branch filter and other textbox allows defining the environment variables to be passed to the filter (the format is key=value pair per line).
The external program will receive from TeamCity plugin a list of plastic branches serialized in JSON, and it is expected to return a filtered list serialized in JSON too.
The external program receives a JSON list of branches to filter from the stdin and returns a list with the actual branches that you want TeamCity to process in the stdout, also in JSON format.

## JSON format
The following is an example of a possible input and output if the external program just wants TeamCity to process DTC-14:

```
[
   {
      "BranchName":"/main/DTC-15",
      "BranchHeadChangeset":"21651",
      "BranchComments":"Change comment size",
      "BranchCreationDate":"2018-03-19 17:30:00",
      "BranchOwner":"John",
      "Repository":"devops_tc2",
      "Server":"blackmore:7070",
   }

   {
      "BranchName":"/main/DTC-14",
      "BranchHeadChangeset":"29726",
      "BranchComments":"Fix Jira 14 – null in head",
      "BranchCreationDate":"2018-03-20 18:27:28",
      "BranchOwner":"Tom",
      "Repository":"devops_tc2",
      "Server":"blackmore:7070",
   }

   {
      "BranchName":"/main/DTC-16",
      "BranchHeadChangeset":"29999",
      "BranchComments":"Fix broken unit test boo",
      "BranchCreationDate":"2018-03-21 8:19:18",
      "BranchOwner":"Bill",
      "Repository":"devops_tc2",
      "Server":"blackmore:7070",
   }

]
```
And the output:

```
[
   {
      "BranchName":"/main/DTC-14",
      "BranchHeadChangeset":"29726",
      "BranchComments":"Fix Jira 14 – null in head",
      "BranchCreationDate":"2018-03-20 18:27:28",
      "BranchOwner":"Tom",
      "Repository":"devops_tc2",
      "Server":"blackmore:7070",
   }

]
```
