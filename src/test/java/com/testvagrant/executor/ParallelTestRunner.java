package com.testvagrant.executor;

import io.cucumber.core.cli.Main;

import java.io.File;

public class ParallelTestRunner {
    public static void main(String[] args) {
        // Set the directory for feature files
        String featureDir = "src/test/resources/Features";

        // Find all feature files in the directory
        File[] featureFiles = new File(featureDir).listFiles((dir, name) -> name.endsWith(".feature"));

        // Create an array of threads
        Thread[] threads = new Thread[featureFiles.length];

        // Create and start a thread for each feature file
        for (int i = 0; i < featureFiles.length; i++) {
//            for(int j=0; j<featureFiles.length; j++) {
                String featureFile = featureFiles[i].getPath();
                String[] cucumberOptions = {
                        "--plugin", "pretty",
                        "--plugin", "html:target/cucumber-reports/" + featureFiles[i].getName() + ".html",
                        "--glue", "com.testvagrant.stepdef",
                        featureFile,
                        "--tags", ""  // Change this to specify which tags to run
                };

                threads[i] = new Thread(() -> {
                    try {
                        Main.run(cucumberOptions, Thread.currentThread().getContextClassLoader());
                    } catch (Exception e) {
                        System.err.println("Error running feature: " + featureFile);
                        e.printStackTrace();
                    }
                });
                threads[i].start();
//            }
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted");
                e.printStackTrace();
            }
        }
    }
}
