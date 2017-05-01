package org.poormanscastle.products.timeo.webfrontend.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A POJO holding information about the current active user.
 * Created by georg on 20/03/2017.
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

    private String loginId;

    private List<String> grantedAuthorities = new ArrayList<>();

    public void addGrantedAuthority(String grantedAuthority) {
        grantedAuthorities.add(grantedAuthority);
    }

}
