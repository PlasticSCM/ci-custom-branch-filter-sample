# custom-branch-filter-sample

A custom branch filter for Plastic and Jira, which can be used for TeamCity CI Plugin.

When configuring the Plastic repository for TeamCity, a new checkbox named "Enable custom brach filtering" will appear.
Once it is enabled, a textbox allows specifying the command to run the branch filter and other textbox allows defining the environment variables to be passed to the filter (the format is key=value pair per line).
The external program will receive from TeamCity plugin a list of plastic branches serialized in JSON, and it is expected to return a filtered list serialized in JSON too.

## JSON format
An example of a list of just one plastic branch in JSON format is shown below:

```
[
   {
      "mParent":"/main",
      "mType":"T",
      "mChangeset":"29726",
      "mId":"70",
      "mName":"/main/TLK-10972",
      "mDate":"2018-03-20T18:27:28",
      "mOwner":"eric",
      "mRepository":"toolkit",
      "mRepServer":"blackmore:8087",
      "mComments":"Fix Jira 10972 - API to get user email",
      "mDateObject":"Mar 20, 2018 6:27:28 PM"
   }

]
```
