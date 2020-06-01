package org.apache.lucene.luke.app.desktop.util.inifile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

/** ini files interface */
public interface IniFileReader {

  Map<String, OptionMap> readSections(Path path) throws IOException;

}
