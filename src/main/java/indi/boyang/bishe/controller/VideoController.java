package indi.boyang.bishe.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import org.springframework.web.bind.annotation.*;

import indi.boyang.bishe.model.VideoRepository;
import indi.boyang.bishe.model.Video;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class VideoController {
	
	private final VideoRepository repository;

	VideoController(VideoRepository repository) {
	    this.repository = repository;
	}
	
	@GetMapping("/videos")
    List<Video> all() {
		return repository.findAll();
    }
	
	@GetMapping("/videos/{id}")
	Optional<Video> getVideo(@PathVariable("id") long id){
		return repository.findById(id);
	}

	@PostMapping("/videos")
	public Video uploadFile(@RequestParam("title") String title,
							@RequestParam("pic") MultipartFile picFile,
							@RequestParam("video") MultipartFile videoFile
	) throws IOException {
		String videoHash = saveFile(videoFile);
		String picHash = saveFile(picFile);
		return repository.save(new Video(videoHash,picHash,title));
	}

	private String saveFile(MultipartFile mFile) throws IOException {
		IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
		ipfs.refs.local();
		File dir = new File("upload\\");
		if(!dir.exists()){
			System.out.println(dir.mkdir());
		}

		String result = "";
		String path = dir.getAbsolutePath() + "\\" + mFile.getOriginalFilename();
		File file = new File(path);
		mFile.transferTo(file);
		NamedStreamable.FileWrapper fw = new NamedStreamable.FileWrapper(file);
		try {
			MerkleNode addResult = ipfs.add(fw).get(0);
			result = addResult.hash.toString();
		} catch (IOException e) {
			e.printStackTrace();
			result = "fail";
		}
		return result;
	}


}
