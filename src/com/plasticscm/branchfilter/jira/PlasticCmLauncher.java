package com.plasticscm.branchfilter.jira;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.util.ArrayList;

public class PlasticCmLauncher {
    public static boolean execute(String[] parameters, ArrayList<String> output) throws Exception {
        String command = getCommand(parameters);

    Process process = null;
        BufferedReader outReader = null;
        BufferedReader errorReader = null;
        try {
            process = Runtime.getRuntime().exec(command);

            if (process == null)
                throw new Exception("Unable to run plastic command: " + command);

            process.waitFor();

            outReader = new BufferedReader(new UnicodeReader(
                new BufferedInputStream(process.getInputStream()), null));
            errorReader = new BufferedReader(new UnicodeReader(
                new BufferedInputStream(process.getErrorStream()), null));

            String line = "";
            ArrayList<String> commandOutput = new ArrayList<String>();
            while ((line = outReader.readLine()) != null)
                commandOutput.add(line);

            ArrayList<String> errorOutput = new ArrayList<String>();  
            while ((line = errorReader.readLine()) != null)
                errorOutput.add(line);

            output.addAll(commandOutput);
            output.addAll(errorOutput);

            return process.exitValue() == 0 && errorOutput.size() == 0;
        } finally {
            if (outReader != null)
                outReader.close();

            if (errorReader != null)
                errorReader.close();

            if (process != null)
                process.destroy();
        }
    }

    private static String getCommand(String[] parameters){
        return "cm " + String.join(" ", parameters);
    }
}
