package com.eg.Makany.Services.behaviouralAnalysis;


import java.io.IOException;
import java.util.Vector;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.xml.sax.SAXException;

import com.eg.Makany.Models.Comment;
import com.eg.Makany.Models.Post;
import com.eg.Makany.Models.User;
import com.eg.Makany.Models.BA.CommentTopic;
import com.eg.Makany.Models.BA.MakanyAlchemy;
import com.eg.Makany.Models.BA.PostTopic;
import com.eg.Makany.Models.UpdateProfile.UserProfileUpdate;





@Path("/")
@Produces("text/html")
public class PostAnalysisService {
	

	@POST
	@Path("/AnalyzePosts")
	public String AnalyzePosts() throws XPathExpressionException, JSONException, IOException, SAXException, ParserConfigurationException {
		JSONObject object = new JSONObject();
		
		Vector <User> allUsers = User.getAllUsers();
		
		// Analyze Text in normal posts and comments by a user
		for (User user : allUsers)
		{
			// Analyze Text in normal posts written by user
			
			Vector <Post> myPosts = null;
			long maxID = PostTopic.getMaxPostID(user.getMail());
			String maxPostID = Long.toString(maxID);
			myPosts = Post.getFilteredPosts(user.getMail(),null,null,null,maxPostID);
			for(Post post : myPosts)
			{
				Vector<String> topics = MakanyAlchemy.getFromAlchemy(post.getContent());
				if (topics != null)
				{
					int cnt1=0;
					for (String A : topics)
					{
						String temp[]=A.split(";");
						double score = Double.parseDouble(temp[1]);
						PostTopic p = new PostTopic (user.getMail() , post.getID() , temp[0] , score);
						p.savePostTopic();
						if(++cnt1<3)
							UserProfileUpdate.saveLovedTopics(user.getMail(), temp[0]);
					}
					
					if (!post.getPhoto().isEmpty())
					{
						topics.clear();
						topics = MakanyAlchemy.AnalyzePhoto(post.getPhoto());
						if (topics != null)
						{
							int cnt2=0;
							for (String A : topics)
							{
								String temp[]=A.split(";");
								double score = Double.parseDouble(temp[1]);
								PostTopic p = new PostTopic (user.getMail() , post.getID() , temp[0] , score);
								p.savePostTopic();
								if(++cnt2<3)
									UserProfileUpdate.saveLovedTopics(user.getMail(), temp[0]);
							}
						}
					}
				}
				
			}
			
			// Analyze Text in comments written by user
			
				Vector <Comment> myComments = null;
				maxID = CommentTopic.getMaxCommentID(user.getMail());
				String maxCommentID = Long.toString(maxID);
				myComments = Comment.getFilteredComments(null,user.getMail(),maxCommentID);
				for(Comment comment : myComments)
				{
					Vector<String> topics = MakanyAlchemy.getFromAlchemy(comment.getContent());
					
					int cnt=0;
					for (String A : topics)
					{
						String temp[]=A.split(";");
						double score = Double.parseDouble(temp[1]);
						CommentTopic c = new CommentTopic (user.getMail() , comment.getPostID() , comment.getID() , temp[0] , score);
						c.saveCommentTopic();
						if(++cnt<3)
							UserProfileUpdate.saveLovedTopics(user.getMail(), temp[0]);
					}

				}
		}
		
		object.put("Status", "OK");
	    return object.toString();


	}
	

	
	

	

	


}