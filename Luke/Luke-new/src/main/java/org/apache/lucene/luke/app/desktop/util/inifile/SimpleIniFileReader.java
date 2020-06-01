package org.apache.lucene.luke.app.desktop.util.inifile;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

/** Simple implementation of {@link IniFileReader} */
public class SimpleIniFileReader implements IniFileReader {

  private String currentSection = "";

  @Override
  public Map<String, OptionMap> readSections(Path path) throws IOException {
    final Map<String, OptionMap> sections = new LinkedHashMap<>();

    try (BufferedReader r = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
      r.lines().forEach(line -> {
        line = line.trim();

        if (isSectionLine(line)) {
          // set section if this is a valid section string
          currentSection = line.substring(1, line.length()-1);
          sections.putIfAbsent(currentSection, new OptionMap());
        } else if (!currentSection.equals("")) {
          // put option if this is a valid option string
          String[] ary = line.split("=", 2);
          if (ary.length == 2 && !ary[0].trim().equals("") && !ary[1].trim().equals("")) {
            sections.get(currentSection).put(ary[0].trim(), ary[1].trim());
          }
        }

      });
    }
    return sections;
  }

  private boolean isSectionLine(String line) {
    return line.startsWith("[") && line.endsWith("]")
        && line.substring(1, line.length()-1).matches("^[a-zA-Z0-9]+$");
  }

}
