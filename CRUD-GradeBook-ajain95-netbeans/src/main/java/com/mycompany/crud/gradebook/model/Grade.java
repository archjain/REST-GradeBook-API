/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crud.gradebook.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *<>
 * @author archjain
 */
@XmlRootElement(name="grade")
public class Grade {
    private String sid;
    private int gid;
    private double score;
    private String feedback;
    private String appeal;
    private String appealComment;

    
   
/*
    <grades>
<score>20.00</score>
<feedback>good</feedback>
</grades>
   
*/
    public Grade(String sid, int gid, double score, String feedback, String appeal ) {
        this.sid = sid;
        this.gid = gid;
        this.score = score;
        this.feedback = feedback;
        this.appeal=appeal;
        
    }
    
    public Grade(String sid, int gid, double score, String feedback, String appeal, String appealComment ) {
        this.sid = sid;
        this.gid = gid;
        this.score = score;
        this.feedback = feedback;
        this.appeal=appeal;
        this.appealComment = appealComment;
        
    }
    
    public Grade(String appeal, String appealComment){
        this.appeal= appeal;
        this.appealComment = appealComment;
    
    }

    
    public Grade(double score, String feedback ) {
        
        this.score = score;
        this.feedback = feedback;
       
    }
    
    public Grade(double score, String feedback, String appeal){
         this.score = score;
        this.feedback = feedback;
        this.appeal = appeal;
    
    
    }
    

    public Grade(){}
    
    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
        public String getAppeal() {
        return appeal;
    }

    public void setAppeal(String appeal) {
        this.appeal = appeal;
    }     
    
    public String getAppealComment() {
        return appealComment;
    }

    public void setAppealComment(String appealComment) {
        this.appealComment = appealComment;
    }

}
