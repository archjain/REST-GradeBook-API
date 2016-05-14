/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crud.gradebook.services;

import com.mycompany.crud.gradebook.model.Grade;
import com.mycompany.crud.gradebook.model.GradeItem;
import com.mycompany.crud.gradebook.model.StudentModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author archjain
 */
public class GradeBookServices {
    public static List<GradeItem> gradeItems = new ArrayList<GradeItem>();
    static int id;
    public static List<StudentModel> students = new ArrayList<StudentModel>();
    public static List<Grade> grades = new ArrayList<Grade>();
 //   static List<Grade> appealedGrades = new ArrayList<Grade>();
    static{
    System.out.println("gbs");
       StudentModel student1 = new StudentModel("ajain", "Archit Jain");
       students.add(student1);
        StudentModel student2 = new StudentModel("john", "John Anderson");
       students.add(student2);
       StudentModel student3 = new StudentModel("sam", "Sameer Reddy");
       students.add(student3);
       StudentModel student4 = new StudentModel("Joe", "Joe Root");
       students.add(student4);
       StudentModel student5 = new StudentModel("vkohli", "Virat Kohli");
       students.add(student5);
       StudentModel student6 = new StudentModel("sach", "Sachin Tendulkar");
       students.add(student6);
       StudentModel student7 = new StudentModel("blara", "Brian Lara");
       students.add(student7);
       StudentModel student8 = new StudentModel("wakram", "Wasim Akram");
       students.add(student8);
       StudentModel student9 = new StudentModel("blee", "Brett Lee");
       students.add(student9);
    }
    public GradeBookServices(){
        
       
       
    }
    //static public List<S>
    public GradeItem createGradeItem(GradeItem item){
        for(int i=0;i<gradeItems.size();i++){
                if(gradeItems.get(i).getName().equalsIgnoreCase(item.getName())){
                    return null;
                }
            }
        id++;
        item.setGid(id);
        System.out.println("size "+gradeItems.size());
        System.out.println("id "+id);
        GradeBookServices.gradeItems.add(item);
        return item;
    }
    
    public List<GradeItem> getAllGradeItems(){
        
        return gradeItems;
    }
    
    public List<StudentModel> getAllStudents(){
        System.out.println("size==== "+students.size());
        return students;
    }
    
    
    public GradeItem getGradeItem(int  gid){
        
        for(int i=0;i<gradeItems.size();i++){
            if(gid==gradeItems.get(i).getGid())
                return gradeItems.get(i);
        
        }
        return null;
    }
    
    public Grade gradeStudent(Grade grade){
        String sid = grade.getSid();
        int gid = grade.getGid();
        boolean sidPresent = false;
        boolean gidPresent = false;
        
        for(int i=0;i<gradeItems.size();i++){
         if(gradeItems.get(i).getGid()==gid){
           gidPresent = true;
           break;
         }
        }
         for(int i=0;i<students.size();i++){
         if(students.get(i).getSid().equals(sid)){
           sidPresent = true;
           break;
         }
        }
         if(sidPresent==false || gidPresent == false)
             return null;
        
        grades.add(grade);
        return grade;
    }
    
    public List<Grade> getStudentAllGrades(String sid){
        List<Grade> newList = new ArrayList<Grade>(); 
        for(int i=0;i<grades.size();i++){
            if(grades.get(i).getSid().equals(sid)){
                newList.add(grades.get(i));
            }
        }
        return newList;
    }
    
    
    public Grade getStudentGrade(String sid, int gid){
        for(int i=0;i<grades.size();i++){
        if(grades.get(i).getGid()==gid && grades.get(i).getSid().equals(sid)){
          return grades.get(i);
        }
        }
        return null;
    }
    
    public Grade updateStudentGrade(Grade grade){
        boolean flag = false;
        for(int i=0;i<grades.size();i++){
            if(grades.get(i).getGid()==grade.getGid() && grades.get(i).getSid().equals(grade.getSid())){
                flag=true;
                grades.remove(i);
                
            }
        }
        if(flag==true){
        grades.add(grade);
        return grade;
        
    }else{
        return null;
        }
        
    }
    
    public boolean deleteGradeItem(int gid){
        boolean flag=false;
    for(int i=0;i<gradeItems.size();i++){
        if(gradeItems.get(i).getGid()==gid){
            gradeItems.remove(i);
            flag=true;
        }
    }
    for(int i=0;i<grades.size();i++){
        if(grades.get(i).getGid()==gid){
            grades.remove(i);
        }
    }
    return flag;
    }
    
    public boolean deleteGrade(int gid, String sid){
        boolean flag=false;
        
    for(int i=0;i<grades.size();i++){
        if(grades.get(i).getSid().equals(sid)&& grades.get(i).getGid()==gid)
        {
            System.out.println(grades.get(i).getSid()+" -- " + grades.get(i).getGid());
            grades.remove(i);
            flag=true;
        }
    }
    return flag;
    }
    
    public List<Grade> getAllAppealedGrades(){
     List<Grade> appealedGrades = new ArrayList<Grade>();
     for(int i=0;i<grades.size();i++)   {
      if(grades.get(i).getAppeal().equalsIgnoreCase("Appealed") ){
          appealedGrades.add(grades.get(i));
      }
      
     }
     return appealedGrades;
    }
    
    public boolean appealGrades(int gid, String sid, Grade grade ){
       boolean resp = false;
      for(int i=0;i<grades.size();i++)   {
      if(grades.get(i).getSid().equals(sid)&& grades.get(i).getGid()==gid ){
       grades.get(i).setAppeal(grade.getAppeal());
       grades.get(i).setAppealComment(grade.getAppealComment());
       resp = true;
       break;
    }
      }
    return resp;
     }
      
}
