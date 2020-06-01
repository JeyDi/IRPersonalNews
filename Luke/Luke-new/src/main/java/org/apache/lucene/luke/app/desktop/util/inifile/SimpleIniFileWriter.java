package org.apache.lucene.luke.app.desktop.util.inifile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/** Simple implementation of {@link IniFileWriter} */
public class SimpleIniFileWriter implements IniFileWriter {

  @Override
  public void writeSections(Path path, Map<String, OptionMap> sections) throws IOException {
    try (BufferedWriter w = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
      for (Map.Entry<String, OptionMap> section : sections.entrySet()) {
        w.write("[" + section.getKey() + "]");
        w.newLine();

        for (Map.Entry<String, String> option : section.getValue().entrySet()) {
          w.write(option.getKey() + " = " + option.getValue());
          w.newLine();
        }

        w.newLine();
      }
      w.flush();
    }
  }
}
