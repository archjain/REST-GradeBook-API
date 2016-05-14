/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crud.client.app;

import com.mycompany.crud.client.model.Grade;
import com.mycompany.crud.client.model.GradeItem;
import com.mycompany.crud.client.model.StudentModel;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.util.List;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author archjain
 */
public class GradeBookClient {
     private WebResource webResource;
     private WebResource webResource2;
    private Client client;
    private Client client2;
    private static final String BASE_URI = "http://localhost:8080/CRUD-GradeBook-ajain95-netbeans/webresources/GradeBook/GradeItem";

    public GradeBookClient(){
    ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI);
    }
    public <T> T getAllStudents(Class<T> responseType){
        ClientConfig config2 = new DefaultClientConfig();
        client2 = Client.create(config2);
        webResource2 = client2.resource("http://localhost:8080/CRUD-GradeBook-ajain95-netbeans/webresources/GradeBook/Students");
             
             return (T) webResource2.accept(MediaType.APPLICATION_XML).get(responseType);
    }
    public ClientResponse createGradeItem(String requestEntity){
        System.out.println(requestEntity);
        System.out.println(webResource.getURI());
        return webResource.type(MediaType.APPLICATION_XML).post(ClientResponse.class, requestEntity);
    }
    
    public <T> T getGradeItem(Class<T> responseType, String gid){
      
         return webResource.path(gid).accept(MediaType.APPLICATION_XML).get(responseType);
    }
    
    public ClientResponse createStudentGrade(String requestEntity, String gid, String sid){
        System.out.println(requestEntity);
        System.out.println(webResource.getURI());
        return webResource.path(gid).path("Student").path(sid).type(MediaType.APPLICATION_XML).post(ClientResponse.class, requestEntity);
    }
    
    
     public <T> T getStudentGrade(Class<T> responseType, String gid, String sid){
      
         return webResource.path(gid).path("Student").path(sid).accept(MediaType.APPLICATION_XML).get(responseType);
    }
     
      public ClientResponse updateStudentGrade(String requestEntity, String gid, String sid){
        System.out.println(requestEntity);
        System.out.println(webResource.getURI());
        return webResource.path(gid).path("Student").path(sid).type(MediaType.APPLICATION_XML).put(ClientResponse.class, requestEntity);
    }
      
       public <T> T deleteGradeItem(Class<T> responseType, String gid){
      
         return webResource.path(gid).accept(MediaType.APPLICATION_XML).delete(responseType);
    }
      public <T> T deleteStudentGrade(Class<T> responseType, String gid, String sid){
      
         return webResource.path(gid).path("Student").path(sid).accept(MediaType.APPLICATION_XML).delete(responseType);
    }
      public <T> T getAppealedGrades(Class<T> responseType){
        return (T) webResource.path("AppealGradeItems").accept(MediaType.APPLICATION_XML).get(responseType);
      }
      
       public ClientResponse appealGrade(String requestEntity, String gid, String sid){
        System.out.println(requestEntity);
        System.out.println(webResource.getURI());
        return webResource.path("AppealGradeItem").path(gid).path("Student").path(sid).type(MediaType.APPLICATION_XML).put(ClientResponse.class, requestEntity);
    }
      
}
