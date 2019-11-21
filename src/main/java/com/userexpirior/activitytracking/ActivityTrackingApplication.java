package com.userexpirior.activitytracking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.userexpirior.activitytracking.process.FileProcessor;

@SpringBootApplication
public class ActivityTrackingApplication implements CommandLineRunner {

	@Autowired
	private FileProcessor processor;

	public static void main(String[] args) {
		SpringApplication.run(ActivityTrackingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		String folderPath = args[0];

		processExistingFiles(folderPath);

		addFileListener(folderPath);

	}

	private void processExistingFiles(String folderPath) {
		File folder = new File(folderPath);

		File files[] = folder.listFiles();

		for (File file : files) {

			if (file.isFile()) {
				try {
					processor.processFile(file);
					moveFileToArchive(file, folderPath + "archive/");

				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonIOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
	}

	private void addFileListener(String folderPath) throws IOException, InterruptedException {
		WatchService watchService = FileSystems.getDefault().newWatchService();

		Path path = Paths.get(folderPath);
		path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

		WatchKey key;
		while ((key = watchService.take()) != null) {
			for (WatchEvent<?> event : key.pollEvents()) {
				File file = new File(folderPath + event.context());
				if (file.isFile()) {
					try {
						processor.processFile(file);
						moveFileToArchive(file, folderPath + "archive/");
					} catch (JsonSyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JsonIOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			key.reset();
		}
	}

	private void moveFileToArchive(File file, String archivalPath) {

		File archiveFolder = new File(archivalPath);

		if (!archiveFolder.exists()) {
			archiveFolder.mkdir();
		}

		if (file.renameTo(new File(archivalPath + file.getName()))) {
			file.delete();
		}

	}

}
