package base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class TextNote extends Note {
	private String content;
	private static final long serialVersionUID = 1L;
	
	public TextNote(String title) {
		super(title);
	}
	
	public TextNote(String title, String content) {
		super(title);
		this.content = content;
	}
	
	/**
	 * load a TextNote from File f
	 * 
	 * the title of the TextNote is the name of the file
	 * the content of the TextNote is the content of the file
	 * @param File f
	 */
	public TextNote (File f) {
		super(f.getName());
		this.content = getTextFromFile(f.getAbsolutePath());
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * get the content of a file
	 * 
	 * @param absolutePath of the file
	 * @return the content of the file
	 */
	private String getTextFromFile(String absolutePath) {
		String result = "";
		
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(absolutePath);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			
			int i;
			
			do {
				result += in.readLine();
			} while ((i = in.read()) != -1);
			
			in.close();
		}
		catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * export text note to a file
	 * 
	 * @param pathFolder path to the folder directory to export the note
	 * the file has to be named as the title of the note with extension ".txt"
	 * 
	 * if the title contains white spaces, they have to be replaced with underscores
	 */
	public void exportTextToFile (String pathFolder) {
		if (pathFolder == "") {
			pathFolder = ".";
		}
		
		File file = new File(pathFolder + File.separator + this.getTitle().replaceAll(" ", "_") + ".txt");
		BufferedWriter bw = null;
		
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			file.setWritable(true);
			FileWriter fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			bw.write(this.content);
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		finally {
			try {
				if (bw != null)
					bw.close();
			}
			catch (Exception e) {
				System.out.println("Error in closing the BufferedWriter" + e);
			}
		}
	}
}
