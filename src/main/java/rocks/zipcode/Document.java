package rocks.zipcode;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.apache.commons.io.*;

/**
 * @author leon on 16/11/2018.
 */
public class Document implements DocumentInterface {

    private final FileWriter fileWriter;
    private final File file;

    public Document(String fileName) throws IOException {
        this(new File(fileName));
    }

    public Document(File input_file) throws IOException {
        this.file = input_file;
        this.fileWriter = new FileWriter(file);
    }

    @Override
    public void write(String contentToBeWritten) {
        try {
            fileWriter.append(contentToBeWritten);
            fileWriter.flush();
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    @Override
    public void write(Integer lineNumber, String valueToBeWritten) {
        replaceAll(toList().get(lineNumber), valueToBeWritten);
    }

    @Override
    public String read(Integer lineNumber) {
        return toList().get(lineNumber);
    }

    @Override
    public String read() {
       StringBuilder stringBuilder = new StringBuilder();
        for (String s : toList()) {
            stringBuilder.append(s);
            if(!(s.equals(toList().get(toList().size() - 1)))) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
//        try (Stream<String> lines = Files.lines(file.toPath())) {
//            lines.forEach(System.out::println);//stringBuilder::append);
//            return stringBuilder.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
    }

    @Override
    public void replaceAll(String stringToReplace, String replacementString) {
            overWrite(read().replaceAll(stringToReplace, replacementString));
    }

    @Override
    public void overWrite(String content) {
        try {
            FileWriter fileOverwritter = new FileWriter(file, false);
            fileOverwritter.write(content);
            fileOverwritter.flush();
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    public List<String> toList() {
        try {
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return "target/" + file.getName() + "{" + read() + "}";
    }
}
