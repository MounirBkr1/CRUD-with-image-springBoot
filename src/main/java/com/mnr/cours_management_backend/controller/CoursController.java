package com.mnr.cours_management_backend.controller;

import com.mnr.cours_management_backend.exception.RessourceNotFoundException;
import com.mnr.cours_management_backend.model.Cours;
import com.mnr.cours_management_backend.repository.CoursRepository;

import java.io.IOException;
import java.nio.file.Files;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@CrossOrigin : allow access from other adress
@CrossOrigin("*")

@RestController
@RequestMapping("/api/v1/")
public class CoursController {

    //System.getProperty("user.home") => renvoi vers ce path: C:\Users\miirb
    public static String uploadDirectory= System.getProperty("user.home")+"/coursPhoto/";

    //save image in a folder(where the application is installed)
    //public static String uploadDirectory= System.getProperty("user.dir")+"/src/main/webapp/imagedata";
    //and in main-app we create a directory if not existe by this code
    //new File(myController.uploadDirectory).mkdir(
;
    //inject repository here
    //@Autowired
    //private CoursRepository courseRepository;
    private CoursRepository courseRepository;

    //injection via constructor(mieux que autowired)
    public CoursController(CoursRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    //GET :getAllEmployees
    @GetMapping("/cours")
    public List<Cours> getAllCours(){
        return courseRepository.findAll();
    }

    //POST : recevoir object from service and create cours rest api
    @PostMapping("/cours")
    public Cours createCours(@RequestBody Cours cours){

        System.out.println(cours);
        return courseRepository.save(cours);

    }


    //Get byID: get  cours by id Rest api,ResponseEntity:car renvoi une reponse
    @GetMapping("/cours/{id}")
    public ResponseEntity<Cours> getCoursById(@PathVariable Long id){
        //Optional<Cours> cours= courseRepository.findById(id);  //with optional
        Cours cours= courseRepository.findById(id)
                .orElseThrow(()->new RessourceNotFoundException("cours avec l'id"+id+"n'existe pas"));

        return ResponseEntity.ok(cours);
    }

    //PUT : update cours rest api
    //L'annotation @ResponseBody indique à un contrôleur que l'objet renvoyé est automatiquement sérialisé en JSON et renvoyé dans l'objet HttpResponse.
    //l'annotation @RequestBody mappe le corps HttpRequest à un objet de transfert ou de domaine, permettant la désérialisation automatique du corps HttpRequest entrant sur un objet Java
    @PutMapping("/cours/{id}")
    public ResponseEntity<Cours> updateCours(@PathVariable Long id,@RequestBody  Cours coursDeatails){
        //get element by id
        Cours cours= courseRepository.findById(id)
                .orElseThrow(()->new RessourceNotFoundException("cours avec l'id"+id+"n'existe pas"));

        //set differents details de l 'element
        cours.setTitle(coursDeatails.getTitle());
        cours.setContent(coursDeatails.getContent());
        cours.setPrice(coursDeatails.getPrice());
        cours.setImage(coursDeatails.getImage());

        //enregistrer l'element modifié
        Cours updatedCours=courseRepository.save(cours);
        return ResponseEntity.ok(updatedCours);

    }


    //DELETE : delete cours api rest
    //Map<String,Boolean>:objet Map qui mappe les clés aux valeurs et ne peut pas contenir de clés en double. Chaque clé peut correspondre à une valeur.
    @DeleteMapping("cours/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteCours(@PathVariable Long id){

        //get element by id
        Cours cours= courseRepository.findById(id)
                .orElseThrow(()->new RessourceNotFoundException("cours avec l'id"+id+"n'existe pas"));

        courseRepository.delete(cours);

        //return an onjet map avec clé value ('deleted',true)
        Map<String,Boolean> response= new HashMap<>();
        response.put("deleted",Boolean.TRUE);

        return ResponseEntity.ok(response);
    }



    @GetMapping(path="/photoCours/{id}",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getPhoto(@PathVariable("id") Long id) throws IOException {
        //get element by id
        Cours c= courseRepository.findById(id).get();
        String img= c.getImage();


        //return Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/coursPhoto/"+c.getImage()));
        return Files.readAllBytes(Paths.get(this.uploadDirectory+c.getImage()));
    }


    //POST photo : update photo
    @PostMapping(path="/uploadPhoto/{id}")
    public void uploadPhoto(MultipartFile file,@PathVariable Long id) throws IOException {
        //MultipartFile: A representation of an uploaded file received in a multipart request.
        Cours c= courseRepository.findById(id).get();

        c.setImage(file.getOriginalFilename()); //save with name
        System.out.println(file.getOriginalFilename());
        //Files.write(Paths.get(this.uploadDirectory+c.getId()),file.getBytes());
        Files.write(Paths.get(this.uploadDirectory+ file.getOriginalFilename()),file.getBytes());

        //c.setImageName(id+".png"); //save with id

        courseRepository.save(c);
    }



    //POST photo:pour create cours, juste download foto ds dossier directory
    @PostMapping(path="/addPhoto")
    public void addImage(MultipartFile file) throws IOException{

        System.out.println("i am in controller addphoto");
        Files.write(Paths.get(this.uploadDirectory+ file.getOriginalFilename()),file.getBytes());


    }


}
