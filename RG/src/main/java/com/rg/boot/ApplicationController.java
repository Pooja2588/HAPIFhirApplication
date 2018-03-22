package com.rg.boot; /**
 * Created by poojgamt on 3/21/2018.
 */
/**
 * Created by poojgamt on 3/19/2018.
 */

import ca.uhn.fhir.model.dstu2.resource.Bundle;
import ca.uhn.fhir.model.dstu2.resource.Person;
import ca.uhn.fhir.model.dstu2.valueset.AdministrativeGenderEnum;
import com.fasterxml.jackson.core.JsonGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.*;

import java.io.IOException;

@RestController
public class ApplicationController {

    @Autowired
    private CallToServer callToServer;


    /* controller takes son object and save it on fhir server through fhir client*/
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    String SaveToHapiFhir(@RequestBody PersonResource objPerson) {
        String response = callToServer.SaveToServer(objPerson);

        return "Response from server" + response;
    }

//API test hard coded json
    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public void sayTest() {
     try {

            ObjectMapper mapper = new ObjectMapper();
            String json = "{\"gender\":\"male\",\n" +
                    "\"phone\":\"+918745051022\",\n" +
                    "\"dob\":\"1985-12-23\",\n" +
                    "\"last_name\":\"Joshi\",\n" +
                    "\"first_name\":\"Abhay\",\n" +
                    "\"email\":\"abhay.joshi@round.glass\"}";


//JSON from String to Object
            PersonResource obj = mapper.readValue(json, PersonResource.class);
            System.out.println(obj.Email);
         String response = callToServer.SaveToServer(obj);
         System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
  }
    }
}






