package com.ketul.resource;

import com.ketul.module.TimeTable;
import com.ketul.module.User;
import com.ketul.module.dto.Login;
import com.ketul.module.dto.RequestDtoForTimeTable;
import com.ketul.service.TimeTableService;
import com.ketul.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private TimeTableService timeTableService;

    // section WelcomePage API
    @GetMapping("/")
//    public String welcomeTimeManagementWorld(@CookieValue(name = "remember-me") String cookie) {
    public String welcomeTimeManagementWorld() {
        return "Hello";
    }

    @GetMapping("/check-it")
    public String checkIt() {
        return "check-it";
    }

    //section save User API
    @PostMapping("/save")
    public String saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    // section conform API
    @GetMapping("/conform/{token}")
    public ResponseEntity<Object> conform(@PathVariable String token) {
        return userService.conform(token);
    }

    // section update API
    @PutMapping("/update")
    public String updateInfo() {
        return userService.updateInfo();
    }

    // section login API
    @PostMapping("/anotherLogin")
    public User login(@RequestBody Login login) {
        System.out.println("login api ");
        return userService.login(login.getEmail(), login.getPass());
    }

    // section User tasks API
    @GetMapping("/userTasks/{id}")
    public List<TimeTable> getUserTasks(@PathVariable long id) {
        return timeTableService.getUserTasks(id);
    }
}
