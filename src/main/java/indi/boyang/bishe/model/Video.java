package indi.boyang.bishe.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Video {

	private @Id @GeneratedValue Long id;
	private String title;
	private String videoURL;
	private String picURL;
	
	public Video() {}
	
	public Video(String videoHash,String picHash,String title) {
		String gateway = "https://ipfs.noroo.xyz/ipfs/";
		//String gateway = "http://10.0.2.2:8080/ipfs/";
		this.videoURL = gateway + videoHash;
		this.picURL = gateway + picHash;
		this.title = title;
	}
}
