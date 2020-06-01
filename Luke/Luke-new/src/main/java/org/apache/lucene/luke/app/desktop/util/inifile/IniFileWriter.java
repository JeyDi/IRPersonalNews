package org.apache.lucene.luke.app.desktop.util.inifile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

/** ini files writer */
public interface IniFileWriter {

  void writeSections(Path path, Map<String, OptionMap> sections) throws IOException;

}
