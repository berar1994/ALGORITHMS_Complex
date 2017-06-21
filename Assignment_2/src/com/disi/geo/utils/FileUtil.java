package com.disi.geo.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtil {

	public static List<String> readDataFromFile(String fileName) {
		List<String> fileContent = new ArrayList<>();
		try (InputStream fis = new FileInputStream(fileName);
				InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
				BufferedReader br = new BufferedReader(isr);) {
			// read from file
			fileContent = br.lines().collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		System.out.println("Read from file finished.");
		return fileContent;
	}

	public static void writeToFile(String fileName, int[] solution, int totalValue) {
		int arrayLength = solution.length;

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
			String content = "";
			for (int i = 0; i < arrayLength; i++) {
				content += solution[i] + " ";
			}
			content += totalValue + "\n";
			bw.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeToFile(String fileName, String text) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
			bw.write(text + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void deleteFileIfExists(String fileName) {
		try {
			File fileTemp = new File(fileName);
			if (fileTemp.exists()) {
				fileTemp.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
