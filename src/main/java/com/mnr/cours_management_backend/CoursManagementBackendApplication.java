package com.mnr.cours_management_backend;

import com.mnr.cours_management_backend.model.Cours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;


@SpringBootApplication
public class CoursManagementBackendApplication implements CommandLineRunner {

//    @Autowired
//    private RepositoryRestConfiguration repositoryRestConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(CoursManagementBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //si l'id ne s'envoi pas sur json, on pricede comme suit
        //qd on envoi un fichier de format json, tu expose son id
        //repositoryRestConfiguration.exposeIdsFor(Cours.class);

    }
}
