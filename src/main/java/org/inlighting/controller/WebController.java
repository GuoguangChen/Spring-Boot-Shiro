package org.inlighting.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.*;
import org.apache.shiro.subject.Subject;
import org.inlighting.Entities.User;
import org.inlighting.bean.ResponseBean;
import org.inlighting.exception.UnauthorizedException;
import org.inlighting.service.UserService;
import org.inlighting.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class WebController {

    private static final Logger LOGGER = LogManager.getLogger(WebController.class);

    private UserService userService;

    @Autowired
    public void setService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseBean login(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("appid") String appid) {
        log.info("----------> Login");
        User user = userService.findByUsername(username);
        if (user.getPassword().equals(password)) {
            return new ResponseBean(200, "Login success", JWTUtil.sign(username, password, appid));
        } else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping("/article")
    public ResponseBean article() {
        log.info("----------> article");
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return new ResponseBean(200, "You are already logged in", null);
        } else {
            return new ResponseBean(200, "You are guest", null);
        }
    }

    @GetMapping("/require_auth")
    @RequiresAuthentication
    public ResponseBean requireAuth() {
        log.info("----------> require_auth");
        return new ResponseBean(200, "You are authenticated", null);
    }

    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public ResponseBean requireRole() {
        log.info("----------> require_role");
        return new ResponseBean(200, "You are visiting require_role", null);
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"user:view", "user:add"})
    public ResponseBean requirePermission() {
        log.info("----------> require_permission");
        return new ResponseBean(200, "You are visiting permission require edit,view", null);
    }

    @GetMapping("/devices")
    @RequiresRoles("admin")
    public ResponseBean requireDevices() {
        log.info("----------> require_devices");
        Subject subject = SecurityUtils.getSubject();
        String username = JWTUtil.getUsername(subject.getPrincipal().toString());
        log.info("username is: {}", username);
        String appid = JWTUtil.getAppid(subject.getPrincipal().toString());
        log.info("appid is: {}", appid);
        return new ResponseBean(200, "You are visiting require_role admin", null);
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseBean unauthorized() {
        log.info("----------> 401");
        return new ResponseBean(401, "Unauthorized", null);
    }
}
