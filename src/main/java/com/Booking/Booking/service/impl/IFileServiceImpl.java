package com.Booking.Booking.service.impl;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFileServiceImpl {
	boolean saveFile(MultipartFile file); // có 2 dạng phổ biến stream, base64

	Resource loadFile(String fileName);
}
