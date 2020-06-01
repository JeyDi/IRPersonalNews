package org.apache.lucene.luke.app.desktop.util.inifile;

import java.io.IOException;
import java.nio.file.Path;

/** Interface representing ini files */
public interface IniFile {

  void load(Path path) throws IOException;

  void store(Path path) throws IOException;

  void put(String section, String option, Object value);

  String getString(String section, String option);

  Boolean getBoolean(String section, String option);

}
