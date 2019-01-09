package org.ydq.docker.service;

import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.ProgressHandler;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.Info;
import com.spotify.docker.client.messages.ProgressMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author daiqing.ydq
 * @version 1.0.0
 * @ClassName DockerService.java
 * @Description TODO
 * @createTime 2019年01月08日 14:21:00
 */
@Service
public class DockerService {
    private static Logger logger = LoggerFactory.getLogger(DockerService.class);

    private static DockerClient dockerClient = new DefaultDockerClient("unix:///var/run/docker.sock");

    @PostConstruct
    public void test() {
        try {
            Info info = dockerClient.info();
            System.out.println(info.osType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String build(String dockerDir, String tagName) {
        String imageId = null;
        try {
            imageId = dockerClient.build(Paths.get(dockerDir), tagName, new ProgressHandler() {
                @Override
                public void progress(ProgressMessage message) throws DockerException {
                }
            });

            ContainerConfig containerConfig = ContainerConfig.builder().image(tagName).build();
            ContainerCreation containerCreation = dockerClient.createContainer(containerConfig, "test");
            dockerClient.startContainer(containerCreation.id());

            return imageId;
        } catch (Exception e) {
            logger.error("build failed, " + e.toString());
        }

        return imageId;
    }
}
