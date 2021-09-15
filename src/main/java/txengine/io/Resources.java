package txengine.io;

import txengine.ui.component.LogUtils;

import java.io.*;

public class Resources {

  private static final String ResourcesRelativePathPrefix = "";
  private static final String ResourcesRelativePath = "resources/";

  // A simple accessor method that returns a File object for a file with a given file name. 
  // Automatically adjusts location of the file via a relative reference. This is determined by
  // the ResourceRelativePathPrefix and ResourceRelativePath constants.
  public static File getResourceAsFile(String fileName) {
  	File file = new File(ResourcesRelativePathPrefix + ResourcesRelativePath + fileName); // Create a file object
    
    if (file.exists()) return file; // return the object if the file exists

    LogUtils.error("Couldn't load " + file + " as a resource! Does it exist?\n");

    return null; // if it doesn't, return null

  }

}
