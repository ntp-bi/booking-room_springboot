package com.Booking.Booking.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Booking.Booking.service.impl.IFileServiceImpl;


@Service
public class FileService implements IFileServiceImpl {

	@Value("${fileUpload.rootPath}") // định nghĩa đường dẫn đến folder lưu file
	private String rootPath;
	private Path root;

	// tạo ra folder root nếu không tồn tại
	public void init() {
		try {
			root = Paths.get(rootPath);
			if (Files.notExists(root)) { // nếu đường dẫn root không tồn tại thì tạo ra
				Files.createDirectories(root);
			}
		} catch (Exception e) {
			System.out.println("Error create folder root " + e.getMessage());
		}

	}

	@Override
	public boolean saveFile(MultipartFile file) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String formattedDate = dateFormat.format(new java.util.Date());
			init();
			Files.copy(file.getInputStream(), this.root.resolve(formattedDate + "_" + file.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			return true;
		} catch (Exception e) {
			System.out.println("Error save file " + e.getMessage());
			return false;
		}
	}

	@Override
	public Resource loadFile(String fileName) {
		try {
			init();
			Path file = root.resolve(fileName);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
		} catch (Exception e) {
			System.out.println("Error load file " + e.getMessage());
		}
		return null;
	}

}
