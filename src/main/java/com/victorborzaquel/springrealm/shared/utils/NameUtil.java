package com.victorborzaquel.springrealm.shared.utils;

public class NameUtil {
  public static String capitalize(String name) {
    return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
  }

  public static String capitalizeAll(String name) {
    String[] names = name.split(" ");
    String result = "";
    for (String n : names) {
      result += capitalize(n) + " ";
    }
    return result.trim();
  }

  public static String getFullName(String firstName, String lastName) {
    if (lastName == null || lastName.isEmpty())
      return capitalizeAll(firstName);

    return capitalizeAll(firstName) + " " + capitalizeAll(lastName);
  }
}
