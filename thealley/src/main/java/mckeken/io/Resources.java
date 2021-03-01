package mckeken.io;

import java.io.*;

public class Resources {

  private static final String ResourcesRelativePathPrefix = "../";
  private static final String ResourcesRelativePath = "resources";

  public static File getResourceAsFile(String fileName) {
    return new File(ResourcesRelativePathPrefix + ResourcesRelativePath);
  }

}
