package org.poormanscastle.products.timeo.task.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by georg on 16/02/2017.
 */
@RooJavaBean
@RooToString
@RooSerializable
@JsonIgnoreProperties(ignoreUnknown = true)
public class Resource {

    private String id;

    private String masterKey;

    private String name;

    private String email;

    @JsonProperty("phone")
    private String phoneNumber;

    private String businessAddress;

}
