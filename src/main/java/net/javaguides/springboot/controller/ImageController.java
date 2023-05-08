package net.javaguides.springboot.controller;

import java.io.IOException;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.javaguides.springboot.db.ImageRepository;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Image;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "images")
public class ImageController {

	private byte[] bytes;

	@Autowired
	private ImageRepository imageRepository;

	@GetMapping("/get")
	public List<Image> getImages() {
		return imageRepository.findAll();
	}

	@PostMapping("/upload")
	public void uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
		this.bytes = file.getBytes();
	}

	@PostMapping("/add")
	public void createImage(@RequestBody Image image) throws IOException {
		image.setPicByte(this.bytes);
		imageRepository.save(image);
		this.bytes = null;
	}

	@PutMapping("/update")
	public void updateImage(@RequestBody Image image) {
		imageRepository.save(image);
	}
	
    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, Boolean>> deleteImage
    (@PathVariable Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Image with id :" + id + "does not exist"));
        imageRepository.delete(image);
        Map<String, Boolean> response = new IdentityHashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}

	
	
	
	
	
	
	