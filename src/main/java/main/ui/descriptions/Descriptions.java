package main.ui.descriptions;

import javafx.scene.Parent;
import main.fxml.Fxml;
import main.fxml.FxmlLoaderUtils;
import main.ui.main.Mainfx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Descriptions {

    private static Map<String, Parent> descriptions = new HashMap<>();

    private static String DESCRIPTION_PATH = "componentDescriptions/";
    private static String CHARACTER_AFTER_TYPE = "D";

    private static Logger logger = LogManager.getLogger(Descriptions.class);

    static {

        logger.info(Mainfx.class.getResource(DESCRIPTION_PATH).getFile());

        List<String> listOfFiles = null;
        try {
            listOfFiles = getFilenamesForDirnameFromCP(DESCRIPTION_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(listOfFiles != null) {
            if(listOfFiles.isEmpty()) logger.error(String.format("no descriptions found"));
            for(String fileName : listOfFiles) {
                logger.info(String.format("loading %s", fileName));
                String type = fileName.substring(0,fileName.lastIndexOf(CHARACTER_AFTER_TYPE));
                Fxml fxml = FxmlLoaderUtils.loadFxml(Mainfx.class.getResource(DESCRIPTION_PATH + fileName));
                if(fxml != null) {
                    Parent description = fxml.getNode();
                    descriptions.put(type, description);
                } else {
                    logger.error(String.format("Could not load description for %s", fileName));
                }
            }
        } else {
            logger.error(String.format("cannot find resources at %s",DESCRIPTION_PATH));
        }
    }

    private static List<String> getFilenamesForDirnameFromCP(String directoryName) throws IOException {
        List<String> filenames = new ArrayList<>();

        URL url = Mainfx.class.getResource(directoryName);
        if (url != null) {
            if (url.getProtocol().equals("file")) {
                File folder = new File(Mainfx.class.getResource(DESCRIPTION_PATH).getFile());
                List<File> files = new ArrayList<>(Arrays.asList(folder.listFiles()));
                for(File file : files) {
                    filenames.add(file.getName());
                }
            } else if (url.getProtocol().equals("jar")) {
                String dirname = "main/ui/main/" + directoryName;
                String path = url.getPath();
                String jarPath = path.substring(5, path.indexOf("!"));
                try (JarFile jar = new JarFile(URLDecoder.decode(jarPath, StandardCharsets.UTF_8.name()))) {
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();
                        if (name.startsWith(dirname) && !dirname.equals(name)) {
                            filenames.add(name.substring(dirname.length()));
                        }
                    }
                }
            }
        }
        return filenames;
    }

    public static Parent getDescription(String type) {
        return descriptions.get(type);
    }

    public static boolean hasDescription(String type) {
        return descriptions.containsKey(type);
    }
}
