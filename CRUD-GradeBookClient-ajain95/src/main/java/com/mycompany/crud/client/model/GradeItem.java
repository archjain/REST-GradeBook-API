/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crud.client.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author archjain
 */
@XmlRootElement(name="GradeItem")
public class GradeItem {
 private int gid;
 private double weight;
 private double maxMarks;
 private String name;
 
 /*
 <GradeItem>
<name>Final</name>
<weight>40.00</weight>
<maxMarks>50.00</maxMarks>
</GradeItem>
*/
 private List<StudentModel> students = new ArrayList<StudentModel>();

    public List<StudentModel> getStudents() {
        return students;
    }

    public void setStudents(List<StudentModel> students) {
        this.students = students;
    }
 

    public GradeItem(int gid, double weight, double maxMarks, String name) {
        this.gid = gid;
        this.weight = weight;
        this.maxMarks = maxMarks;
        this.name = name;
    }
    public GradeItem(){}

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(double maxMarks) {
        this.maxMarks = maxMarks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
 
    
}
