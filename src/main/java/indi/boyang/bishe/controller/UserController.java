package indi.boyang.bishe.controller;

import indi.boyang.bishe.model.AccountInfo;
import indi.boyang.bishe.model.User;
import indi.boyang.bishe.model.UserRepository;
import indi.boyang.bishe.util.IPVC;
import indi.boyang.bishe.util.MyWeb3j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web3j.crypto.Credentials;
import org.web3j.utils.Convert;

import java.math.BigInteger;
import java.util.Optional;



@RestController
public class UserController {
    public static UserRepository userRepository;

    UserController(UserRepository repository){
        userRepository = repository;
    }

    @PostMapping("/users")
    public ResponseEntity<String> login(@RequestParam("username") String username,
                                 @RequestParam("password_md5") String password_md5
    ) throws Exception {
        Optional<User> user = userRepository.findById(username);
        if(user.isEmpty()){
            System.out.println("sign in");
            String[] result = MyWeb3j.newAddress(password_md5);
            User newUser = new User(username,result[0],password_md5,result[1]);
            System.out.println("Username " + newUser.getUsername());
            System.out.println("p_md5 " + newUser.getPassword_md5());
            userRepository.save(newUser);
            return ResponseEntity.ok("SignUp");
        } else if(!password_md5.equals(user.get().getPassword_md5())){
            System.out.println("IncorrectPassword");
            System.out.println("Username " + username);
            System.out.println("Wrong p_md5 " + password_md5);
            System.out.println("p_md5 " + user.get().getPassword_md5());
            return ResponseEntity.badRequest().body("IncorrectPassword");
        } else {
            System.out.println("login");
            System.out.println("Username " + user.get().getUsername());
            System.out.println("p_md5 " + user.get().getPassword_md5());
            return ResponseEntity.ok("Success");
        }
    }

    @GetMapping("/users/account/{username}")
    public AccountInfo viewAccount(@PathVariable("username") String username) throws Exception {
        System.out.println(username);
        Optional<User> user = userRepository.findById(username);
        if(user.isPresent()){
            String address = userRepository.findById(username).get().getAddress();
            System.out.println(userRepository.findById(username).get().toString());
            return new AccountInfo(address,MyWeb3j.getERC20Balance(address));
        } else {

        }


        return null;
    }

}
