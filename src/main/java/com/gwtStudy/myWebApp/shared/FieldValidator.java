package com.gwtStudy.myWebApp.shared;

public class FieldValidator {

  public static boolean isValidData(String name) {
    if (name == null) {
      return false;
    }
    return name.length() > 3;
  }
}
