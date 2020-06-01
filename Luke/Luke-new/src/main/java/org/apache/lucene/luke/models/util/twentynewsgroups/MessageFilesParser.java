package org.apache.lucene.luke.models.util.twentynewsgroups;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/** 20 Newsgroups (http://kdd.ics.uci.edu/databases/20newsgroups/20newsgroups.html) message files parser */
public class MessageFilesParser  extends SimpleFileVisitor<Path> {

  private static final Logger log = LoggerFactory.getLogger(MessageFilesParser.class);

  private final Path root;

  private final List<Message> messages = new ArrayList<>();

  public MessageFilesParser(Path root) {
    this.root = root;
  }

  public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
    try {
      if (attr.isRegularFile()) {
        Message message = parse(file);
        if (message != null) {
          messages.add(parse(file));
        }
      }
    } catch (IOException e) {
      log.warn("Invalid file? " + file.toString());
    }
    return FileVisitResult.CONTINUE;
  }

  Message parse(Path file) throws IOException {
    try (BufferedReader br = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
      String line = br.readLine();

      Message message = new Message();
      while (!line.equals("")) {
        String[] ary = line.split(":", 2);
        if (ary.length < 2) {
          line = br.readLine();
          continue;
        }
        String att = ary[0].trim();
        String val = ary[1].trim();
        switch (att) {
          case "From":
            message.setFrom(val);
            break;
          case "Newsgroups":
            message.setNewsgroups(val.split(","));
            break;
          case "Subject":
            message.setSubject(val);
            break;
          case "Message-ID":
            message.setMessageId(val);
            break;
          case "Date":
            message.setDate(val);
            break;
          case "Organization":
            message.setOrganization(val);
            break;
          case "Lines":
            try {
              message.setLines(Integer.parseInt(ary[1].trim()));
            } catch (NumberFormatException e) {}
          default:
              break;
        }

        line = br.readLine();
      }

      StringBuilder sb = new StringBuilder();
      while (line != null) {
        sb.append(line);
        sb.append(" ");
        line = br.readLine();
      }
      message.setBody(sb.toString());

      return message;
    }
  }

  public List<Message> parseAll() throws IOException {
    Files.walkFileTree(root, this);
    return messages;
  }

}
