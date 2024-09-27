package com.mohitjoshi.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.sym.Name;
import com.mohitjoshi.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		String fileName = file.getOriginalFilename();
		
		String randomId = UUID.randomUUID().toString();
		String fileName1 = randomId.concat(fileName.substring(fileName.lastIndexOf(".")));
		
		String filePath = path + File.separator + fileName1;
		
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		Files.copy(file.getInputStream(), Paths.get(filePath));
		return fileName1;
	}

	@Override
	public InputStream getSource(String path, String fileName) throws FileNotFoundException {

		String filePath = path + File.separator + fileName;
		InputStream iStream = new FileInputStream(filePath);
		
		return iStream;
	}

}
