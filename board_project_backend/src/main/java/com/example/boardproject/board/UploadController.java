package com.example.boardproject.board;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api")
public class UploadController {
	
	@Value("${upload.path}")
	private String uploadPath;
	
	@Autowired
	private UploadService uploadService;
	
	// 파일 업로드
	@PostMapping("/upload")
	public ResponseEntity<String> postUploadFiles(@RequestParam("file") MultipartFile[] uploadFiles) {
		try {
			for (MultipartFile multipartFile : uploadFiles) {
				
				String originalName = multipartFile.getOriginalFilename();
				String uuid = UUID.randomUUID().toString();
				String fileName = uuid + "_" + originalName;
				
				File saveFile = new File(uploadPath, fileName);
				
				multipartFile.transferTo(saveFile);
				
				FileRequestDto fileRequestDto = new FileRequestDto();
				fileRequestDto.setPath(uploadPath);
				fileRequestDto.setUuid(uuid);
				fileRequestDto.setOriginalName(originalName);
	            fileRequestDto.setFileName(fileName);
	            
	            uploadService.addFile(fileRequestDto);
			}
            return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	// 업로드 파일 삭제
	@DeleteMapping("/upload/{fileName}")
	public ResponseEntity<Void> deleteUploadFile(@PathVariable String fileName) {
		File file = new File(uploadPath, fileName);
		
		try {
			file.delete();
			uploadService.removeFile(fileName);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
