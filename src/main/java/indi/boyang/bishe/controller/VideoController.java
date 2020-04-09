package indi.boyang.bishe.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import indi.boyang.bishe.util.MyWeb3j;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import indi.boyang.bishe.model.VideoRepository;
import indi.boyang.bishe.model.Video;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class VideoController {
	
	public static VideoRepository videoRepository;

	VideoController(VideoRepository videoRepository) {
	    VideoController.videoRepository = videoRepository;
	}
	
	@GetMapping("/videos")
    List<Video> all() {
		return videoRepository.findAll();
    }
	
	@GetMapping("/videos/{id}")
	Optional<Video> getVideo(@PathVariable("id") long id,
							 @RequestParam("operator") String operator) throws Exception {
		Video video = videoRepository.findById(id).get();
		MyWeb3j.transferERC20Token(operator,video.getUploader(),"1");
		return videoRepository.findById(id);
	}

	@PostMapping("/videos")
	public Video uploadFile(@RequestParam("title") String title,
							@RequestParam("pic") MultipartFile picFile,
							@RequestParam("video") MultipartFile videoFile,
							@RequestParam("uploader") String uploader
	) throws IOException {
		String videoHash = saveFile(videoFile);
		String picHash = saveFile(picFile);
		return videoRepository.save(new Video(videoHash,picHash,title,uploader));
	}

	@PostMapping("/videos/{id}/motivation")
	public ResponseEntity<String> like(@PathVariable("id") long id,
									   @RequestParam("operator") String operator
	) throws Exception {
		Video video = videoRepository.findById(id).get();
		MyWeb3j.transferERC20Token(operator,video.getUploader(),"2");
		System.out.println(id + operator + "liked");
		return ResponseEntity.ok("like");
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
