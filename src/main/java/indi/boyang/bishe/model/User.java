package indi.boyang.bishe.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Configurable;
import org.web3j.crypto.Credentials;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@Configurable
public class User {
    private @Id String username;
    private String address;
    private String password_md5;
    private String privateKey;

    public User(){}

    public User(String username, String address, String password_md5){
        this.username = username;
        this.address = address;
        this.password_md5 = password_md5;
    }

    public User(String username, String address, String password_md5, String privateKey){
        this.username = username;
        this.address = address;
        this.password_md5 = password_md5;
        this.privateKey = privateKey;
    }

}
