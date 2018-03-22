package com.rg.boot; /**
 * Created by poojgamt on 3/19/2018.
 */
import com.fasterxml.jackson.annotation.JsonProperty;
public class PersonResource {

    @JsonProperty("first_name")
    public String firstName;
    @JsonProperty("last_name")
    public String LastName;
    @JsonProperty("email")
    public String Email;
    @JsonProperty("gender")
    public String Gender;
    @JsonProperty("phone")
    public String Phone;
    @JsonProperty("dob")
    public String DOB;
}
