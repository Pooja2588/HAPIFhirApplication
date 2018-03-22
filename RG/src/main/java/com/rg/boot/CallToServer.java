package com.rg.boot;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.composite.ContactPointDt;
import ca.uhn.fhir.model.dstu2.composite.HumanNameDt;
import ca.uhn.fhir.model.dstu2.resource.Person;
import ca.uhn.fhir.model.dstu2.valueset.AdministrativeGenderEnum;
import ca.uhn.fhir.model.dstu2.valueset.ContactPointSystemEnum;
import ca.uhn.fhir.model.primitive.DateDt;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.IGenericClient;
import org.springframework.stereotype.Service;

/**
 * Created by poojgamt on 3/19/2018.
 */
@Service
public class CallToServer {

    public String SaveToServer(PersonResource objPerson)
    {
        // We're connecting to a DSTU1 compliant server in this example
        FhirContext ctx = FhirContext.forDstu2();
        String serverBase = "http://35.154.54.160:9072/baseDstu2/";

        IGenericClient client = ctx.newRestfulGenericClient(serverBase);
        Person person=new Person();
        //Add Gender
        if(objPerson.Gender.equalsIgnoreCase("Female")||objPerson.Gender.equalsIgnoreCase("F"))
        person.setGender(AdministrativeGenderEnum.FEMALE);
        else if(objPerson.Gender.equalsIgnoreCase("Male")||objPerson.Gender.equalsIgnoreCase("M"))
            person.setGender(AdministrativeGenderEnum.MALE);
        else
            person.setGender(AdministrativeGenderEnum.UNKNOWN);


        //Add name and family
        HumanNameDt objName=new HumanNameDt();
        objName.addGiven(objPerson.firstName);//add first name
        objName.addFamily(objPerson.LastName);//add last name
        person.addName(objName);

        //add Email
        ContactPointDt objcontact1=new ContactPointDt();
        objcontact1.setSystem(ContactPointSystemEnum.EMAIL);
        objcontact1.setValue(objPerson.Email);
        person.addTelecom(objcontact1);

        //add phone
        ContactPointDt objcontact=new ContactPointDt();
        objcontact.setSystem(ContactPointSystemEnum.PHONE);
        objcontact.setValue(objPerson.Phone);
        person.addTelecom(objcontact);

        //add DOB
        DateDt objDate=new DateDt();
        objDate.setValueAsString(objPerson.DOB);
        person.setBirthDate(objDate);

        person.addIdentifier().setSystem("urn:system").setValue("7651");
        MethodOutcome outcome = client.create()
                .resource(person)
                .prettyPrint()
                .encodedJson()
                .execute();
        IdDt id = (IdDt) outcome.getId();

        System.out.println("Got ID: " + id.getValue());

        return id.getValue();
    }
}
