package org.ydq.docker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ydq.docker.service.DockerService;

/**
 * @author daiqing.ydq
 * @version 1.0.0
 * @ClassName IndexController.java
 * @Description TODO
 * @createTime 2019年01月08日 14:28:00
 */
@RestController
public class IndexController {
    @Autowired
    private DockerService dockerService;

    @GetMapping("/build")
    public String build(@RequestParam(value = "path") String path, @RequestParam(value = "tag") String tag) {
        String imageId = dockerService.build(path, tag);
        return "build id " + imageId;
    }

    @GetMapping("/")
    public String hello() {
        return "index";
    }
}
