package indi.boyang.bishe.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import indi.boyang.bishe.model.VideoRepository;
import indi.boyang.bishe.model.Video;

@Component
class LoadDatabase {
	
  @Bean
  CommandLineRunner init(VideoRepository repository) {
    return args -> {
      repository.save(new Video("Qma4LJa9F4uNv2UkCpxhQDTT7nC86uPaq3HdY7jhZrq2Zc","QmPjfQtSGzJHte4q8GSejRFaPobcCsFCKYaYE866fLXHZg","测试视频1"));
      repository.save(new Video("QmWjBaLMD6sSALPuNs1EGdwJ2rp5fLC54hL6svCWaHR8iD","QmZCYzf7XsEdQu4Fu4GfDhuSUStCCacgvA3gMW2vahWKvA","My second video"));
      //repository.save(new Video("QmUtohyb8jmj3o1G2pKBB9ycv4F4H9yxtfen42hRkJMC14","Qmdu5YEKuNuR8ZLbpPNR5s4z4wCpoiHy1R5hWKfiAKTgAv","My third video"));
    };
  }
}
