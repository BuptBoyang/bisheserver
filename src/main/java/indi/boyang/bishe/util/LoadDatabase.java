package indi.boyang.bishe.util;

import indi.boyang.bishe.model.User;
import indi.boyang.bishe.model.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import indi.boyang.bishe.model.VideoRepository;
import indi.boyang.bishe.model.Video;
import org.web3j.crypto.Credentials;

@Component
class LoadDatabase {
	
  @Bean
  CommandLineRunner init(VideoRepository videoRepository, UserRepository userRepository) {
    return args -> {
      videoRepository.save(new Video("Qma4LJa9F4uNv2UkCpxhQDTT7nC86uPaq3HdY7jhZrq2Zc","QmPjfQtSGzJHte4q8GSejRFaPobcCsFCKYaYE866fLXHZg","测试视频1","admin"));
      videoRepository.save(new Video("QmWjBaLMD6sSALPuNs1EGdwJ2rp5fLC54hL6svCWaHR8iD","QmZCYzf7XsEdQu4Fu4GfDhuSUStCCacgvA3gMW2vahWKvA","My second video","admin"));
      //repository.save(new Video("QmUtohyb8jmj3o1G2pKBB9ycv4F4H9yxtfen42hRkJMC14","Qmdu5YEKuNuR8ZLbpPNR5s4z4wCpoiHy1R5hWKfiAKTgAv","My third video","admin"));
      userRepository.save(new User("admin","0x91aB9203968290402EdE6484dA278F27139cB48b","1234"));
      userRepository.save(new User("test","0x75e7560072f224af7ecc6423340d2f487d4bd2fa","81dc9bdb52d04dc20036dbd8313ed055", "8ef0b62b36d57a3a3ba5f1e49e7e0afb129022017943d539d3fa9b7b0e589858"));
    };
  }
}
