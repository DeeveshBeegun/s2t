package org.example.controller;

import org.example.BirdFormDetails;
import org.example.service.FormExtractorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/form")
public class FormController {

    private final FormExtractorService formExtractorService; 

    public FormController(FormExtractorService formExtractorService) {
        this.formExtractorService = formExtractorService;
    }

     @PostMapping("/extract")
     public BirdFormDetails extractData(@RequestBody String text) {
         return formExtractorService.extractForm(text);
     }

}
