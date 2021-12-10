package Router_synchronization;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Files {
	final String currentDirectory = System.getProperty("user.dir");
	private File logFile;
	public FileWriter Writer;

	public Files(String content) throws IOException {
        logFile = new File(currentDirectory + "\\" + "logFile.txt");
        Writer = new FileWriter(logFile, true);
        if (!logFile.exists()) {
            logFile.createNewFile();
        }
        Writer.write(content);// using this method we will push the output in the file
        Writer.write(System.getProperty("line.separator")); // to take a separate line 
        Writer.close();
    }

	public Files getInstance() {
		return this;
	}

}
