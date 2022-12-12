package com.project.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.project.domain.UploadFile;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {
	
	@Value("${file.dir}")
	private String fileDir;
	
	public String getFullPath(String filename) {
		return fileDir + filename;
	}
	
	public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
		List<UploadFile> storeFileResult = new LinkedList<>();
		for (MultipartFile multipartFile : multipartFiles) {
			if (!multipartFile.isEmpty()) {
				storeFileResult.add(storeFile(multipartFile));
			}
		}
		return storeFileResult;
	}
	public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
		if (multipartFile.isEmpty()) {
			return null;
		}
		String originalFilename = multipartFile.getOriginalFilename();
		String storeFileName = createStoreFileName(originalFilename);
		multipartFile.transferTo(new File(getFullPath(storeFileName)));
		return new UploadFile(originalFilename, storeFileName);
	}
	private String createStoreFileName(String originalFilename) {
		String ext = extractExt(originalFilename);
		String uuid = UUID.randomUUID().toString();
		return uuid + "." + ext;
	}
	private String extractExt(String originalFilename) {
		int pos = originalFilename.lastIndexOf(".");
		return originalFilename.substring(pos + 1);
	}
}