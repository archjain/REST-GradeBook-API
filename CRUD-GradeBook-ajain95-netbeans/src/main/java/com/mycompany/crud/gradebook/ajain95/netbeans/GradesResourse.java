/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crud.gradebook.ajain95.netbeans;

import com.mycompany.crud.gradebook.model.Grade;
import com.mycompany.crud.gradebook.model.GradeItem;
import com.mycompany.crud.gradebook.model.StudentModel;
import com.mycompany.crud.gradebook.services.GradeBookServices;
import com.mycompany.crud.gradebook.utils.Converter;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * REST Web Service
 *
 * @author archjain
 */
@Path("GradeBook")
public class GradesResourse {
    GradeBookServices serv = new GradeBookServices();
   // private static GradeItem gradeItem;
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GradesResourse
     */
    public GradesResourse() {
    }

   // create grade items 
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("GradeItem")
    public Response createGradeItem(String content){
       GradeItem gradeItem; 
       Response response;
       
        try{
            gradeItem = (GradeItem) Converter.convertFromXmlToObject(content, GradeItem.class);
            
            GradeItem newItem = serv.createGradeItem(gradeItem);
             
            if(newItem!=null){
            String xmlString = Converter.convertFromObjectToXml(newItem, GradeItem.class);
           
            URI locationURI = URI.create(context.getAbsolutePath()  +"/"+ Integer.toString(newItem.getGid()));
        
            response = Response.status(Response.Status.CREATED).location(locationURI).entity(xmlString).build();
        System.out.println("response status"+ response.getStatus());
            }
            else{
              response = Response.status(Response.Status.CONFLICT).entity(content).build();
            }
        }  catch (JAXBException ex) {
               Logger.getLogger(GradesResourse.class.getName()).log(Level.SEVERE, null, ex);
               System.out.println("bad request");
               response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();
              
           }
        catch (RuntimeException e) {
               // LOG.debug("Catch All exception");
                System.out.println("server side error");
                //LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
                
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
            }
        
          //  response = Response.status(Response.Status.CONFLICT).entity(content).build();
       
      // System.out.println(response.getStatus());
    //   System.out.println(response.getLocation().toString());
        return response;
    }
    
    // retreive all gradeitems
    @GET
    @Path("GradeItems")
    @Produces(MediaType.APPLICATION_XML)
    public List<GradeItem> getGradeItems() {
        return serv.getAllGradeItems();
    }
    
    
    //retrive particular project item
    @GET
    @Path("GradeItem/{gid}")
    public Response getGradeItem(@PathParam("gid")int gid){
      //return serv.getGradeItem(gid);
    // GradeItem gradeItem;
     Response response;
      
     GradeItem newItem = serv.getGradeItem(gid);
            if(newItem!=null){
                String xmlString = Converter.convertFromObjectToXml(newItem, GradeItem.class);
                response = Response.status(Response.Status.OK).entity(xmlString).build();
            } 
            else {
                
                response = Response.status(Response.Status.NOT_FOUND).entity("No grade item Resource to return").build();
            }
      
      return response;
    }
    
    //create new grade for student
    @POST
    @Path("GradeItem/{gid}/Student/{sid}")
    @Consumes(MediaType.APPLICATION_XML)
    public Response gradeStudent(@PathParam("gid")int gid, @PathParam("sid")String sid, String content){
        Response response;
         Grade grade ;
                 try{
            grade = (Grade) Converter.convertFromXmlToObject(content, Grade.class);
            grade.setGid(gid);
            grade.setSid(sid);
            grade.setAppeal("No");
            
            for(int i=0;i<GradeBookServices.grades.size();i++){
                if(GradeBookServices.grades.get(i).getGid() == gid && GradeBookServices.grades.get(i).getSid().equalsIgnoreCase(sid) ){
                  response = Response.status(Response.Status.CONFLICT).entity("Item already graded for this student, use update to grade again").build();
                   return response;
                }
            }
         
            Grade newGrade = serv.gradeStudent(grade);
             
            if(newGrade!=null){
            String xmlString = Converter.convertFromObjectToXml(newGrade, Grade.class);
           
            URI locationURI = URI.create(context.getAbsolutePath().toString());
        
            response = Response.status(Response.Status.CREATED).location(locationURI).entity(xmlString).build();
            // System.out.println("response status"+ response.getStatus());
            }
            else{
              response = Response.status(Response.Status.NOT_FOUND).entity("student id or grade item id not found").build();
            }
        }  catch (JAXBException ex) {
               Logger.getLogger(GradesResourse.class.getName()).log(Level.SEVERE, null, ex);
               System.out.println("bad request");
               response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();
              
           }
        catch (RuntimeException e) {
               // LOG.debug("Catch All exception");
                System.out.println("server side error");
                //LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
                
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
            }
        
        return response;
         
    }
    
    //get student grade
    @GET
    @Path("GradeItem/{gid}/Student/{sid}")
    public Response getStudentGrade(@PathParam("sid") String sid, @PathParam("gid")int gid){
    Response response;
    Grade newGrade;
    boolean foundgid = false;
    boolean foundsid = false;
    for(int i=0;i<GradeBookServices.gradeItems.size();i++){
      if(GradeBookServices.gradeItems.get(i).getGid()==gid){
      foundgid = true;
      break;
      }
    }
    for(int i=0;i<GradeBookServices.students.size();i++){
      if(GradeBookServices.students.get(i).getSid().equalsIgnoreCase(sid)){
      foundsid = true;
      break;
      }
    }
    if(foundgid==false || foundsid == false){
     return Response.status(Response.Status.NOT_FOUND).entity("grade item or student id not found").build();
    }
        newGrade = serv.getStudentGrade(sid,gid);
            if(newGrade!=null){
                String xmlString = Converter.convertFromObjectToXml(newGrade, Grade.class);
                response = Response.status(Response.Status.OK).entity(xmlString).build();
            } 
            else {
                System.out.println("here");
                response = Response.status(Response.Status.NO_CONTENT).entity("requested gradeItem is not graded").build();
            }
      
      return response;
    
    }
    
   //get all student grades 
     @GET
    @Path("GradeItems/Student/{sid}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Grade> getStudentAllGrades(@PathParam("sid")String sid){
          
          return serv.getStudentAllGrades(sid);
    }
    
    //get all Students
    @GET
    @Path("Students")
    @Produces(MediaType.APPLICATION_XML)
    public List<StudentModel> getAllStudents(){
          
          return serv.getAllStudents();
    }
    
    
    
    //updating student grade
    @PUT
    @Path("GradeItem/{gid}/Student/{sid}")
    public Response updateStudentGrades(@PathParam("sid")String sid, @PathParam("gid")int gid, String content){
        Response response;
         Grade grade ;
                 try{
            grade = (Grade) Converter.convertFromXmlToObject(content, Grade.class);
            grade.setGid(gid);
            grade.setSid(sid);
            grade.setAppeal("no");
            Grade newGrade = serv.updateStudentGrade(grade);
             
            if(newGrade!=null){
            String xmlString = Converter.convertFromObjectToXml(newGrade, Grade.class);
           
      //      URI locationURI = URI.create(context.getAbsolutePath()  + Integer.toString(newGrade.getGid()));
        
            response = Response.status(Response.Status.OK).entity(xmlString).build();
            // System.out.println("response status"+ response.getStatus());
            }
            else{
              response = Response.status(Response.Status.NOT_FOUND).entity("This gradeitem is not graded").build();
            }
        }  catch (JAXBException ex) {
               Logger.getLogger(GradesResourse.class.getName()).log(Level.SEVERE, null, ex);
               System.out.println("bad request");
               response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();
              
           }
        catch (RuntimeException e) {
               // LOG.debug("Catch All exception");
                System.out.println("server side error");
                //LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
                
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
            }
        
        return response;
        
    }
    
   
    
    
    
    
    
    @DELETE
    @Path("GradeItem/{gid}")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response deleteGradeItem(@PathParam("gid")int gid){
    Response response;
    boolean isDeleted=serv.deleteGradeItem(gid);
    if(isDeleted==true){
    response = Response.status(Response.Status.NO_CONTENT).build();
    }
    else{
    response = Response.status(Response.Status.NOT_FOUND).build();
    }
    return response;
    }
    
    //delete grade
    @DELETE
    @Path("GradeItem/{gid}/Student/{sid}")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response deleteGrade(@PathParam("gid")int gid, @PathParam("sid") String sid){
    Response response;
    boolean isDeleted=serv.deleteGrade(gid,sid);
    if(isDeleted==true){
    response = Response.status(Response.Status.NO_CONTENT).build();
    }
    else{
    response = Response.status(Response.Status.NOT_FOUND).build();
    }
    return response;
    }
    
    //appealing grades
    @PUT
    @Path("GradeItem/AppealGradeItem/{gid}/Student/{sid}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response appealGrade(@PathParam("gid")int gid, @PathParam("sid") String sid, Grade grade){
        Response response;
        for(int i=0;i<GradeBookServices.grades.size();i++){
           if(GradeBookServices.grades.get(i).getGid()==gid && 
                   GradeBookServices.grades.get(i).getSid().equals(sid) && 
                   GradeBookServices.grades.get(i).getAppeal().equalsIgnoreCase("appealed") ){
           return Response.status(Response.Status.CONFLICT ).build();
           }
        }
        boolean isAppealed = serv.appealGrades(gid, sid, grade);
        if(isAppealed==true){
    response = Response.status(Response.Status.OK).build();
    }
    else{
    response = Response.status(Response.Status.NOT_FOUND).build();
    }
    return response;
    }
    
    //get all appealed Grades
    @GET
    @Path("GradeItem/AppealGradeItems")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_XML)
    public List<Grade> getAllAppealedGrades(){
      return serv.getAllAppealedGrades();
      
     // return newlIs
    }
    
}
