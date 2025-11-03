package com.urlshortner.controller;

import com.urlshortner.entity.Analytics;
import com.urlshortner.entity.URL;
import com.urlshortner.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
//@CrossOrigin(origins = "http://127.0.0.1:5500")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/") //GetMapping he hai but frontend ke liye secured hai yeh
    public ResponseEntity<?> welcome(Principal principal){
        System.out.println("Hello");
        return new ResponseEntity<>(userService.getUser(principal.getName()), HttpStatus.OK);
    }

    @PostMapping("/getUser")
    public ResponseEntity<?> getUser(@RequestBody Map<String,String> map){
        return new ResponseEntity<>(
                userService.getUser(map.get("email")),HttpStatus.OK
        );
    }

    @PatchMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody Map<String,String> map){
        String userId = map.getOrDefault("userId",null);
        String userName = map.getOrDefault("userName",null);
        String password = map.getOrDefault("password",null);
        System.out.println(userId+" "+userName+" "+password);
        if(userId==null || userName == null || password == null || password.isEmpty())
            return new ResponseEntity<>("Credentials Can't be Null", HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(userService.updateUser(userId,userName,password),HttpStatus.OK);
    }

    @PostMapping("/shortURL")
    public ResponseEntity<?> shortURL(@RequestParam String urlString,Principal principal){
        System.out.println(urlString);
        URL url = userService.generateShortURL(urlString, principal.getName());
        return new ResponseEntity<>(url,HttpStatus.OK);
    }

    @PostMapping("/url/{urlId}") //GetMapping he hai but frontend ke liye secured hai yeh
    public ResponseEntity<?> getAnalytics(@PathVariable Integer urlId,Principal principal){
        List<Analytics> analytics = userService.getAnalytics(urlId, principal.getName());
        return new ResponseEntity<>(analytics,HttpStatus.OK);
    }

    @PatchMapping("/url/active/{urlId}")
    public ResponseEntity<?> updateIsActive(@PathVariable Integer urlId,@RequestParam int value){
        System.out.println(urlId+" "+value);
        boolean status = userService.updateUrlActive(urlId,value);
        return new ResponseEntity<>(status,HttpStatus.OK);
    }
}
