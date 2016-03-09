package com.controllers;

import java.util.Vector;

public class FilteredPost 
{
	
	 public String id,postType, content, photo, userEmail, district,numOfApprovals,numofDisapprovals;
     public String numOfReports,reportmail, approvalmail, disapprovalmail;

     public FilteredPost() 
     {
			this.id="";
			this.postType="";
			this.content = "";
			this.photo="";
			this.userEmail="";
			this.district="";
			//this.comments=new Vector<Comment>();
			//this.reports=new Vector<Report>();
			this.numOfApprovals="";
			this.numofDisapprovals="";
			this.numOfReports="";
			this.reportmail="";
			this.approvalmail="";
			this.disapprovalmail="";
		}
     
     
     public FilteredPost(String id,String type,String content,String photo,String userEmail,
    		 String district,String numofapprovals, String approvalMails, String numofdisapprovals,
    		 String disapprovalMails, String reports,String reportmail ) {
			this.id=id;
			this.postType=type;
			this.content = content;
			this.photo=photo;
			this.userEmail=userEmail;
			this.district=district;
			//this.comments=new Vector<Comment>();
			//this.reports=new Vector<Report>();
			this.numOfApprovals=numofapprovals;
			this.approvalmail=approvalMails;
			this.numofDisapprovals=numofdisapprovals;
			this.disapprovalmail=disapprovalMails;
			this.numOfReports=reports;
			this.reportmail=reportmail;
		}
     
}
