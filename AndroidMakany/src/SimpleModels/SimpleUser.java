package SimpleModels;

import java.util.Vector;

public class SimpleUser 
{
		private String id;
		private String name, email, password, birthDate, district, gender;
		private String twitter, foursquare;
		private Vector<String> interests;
		
		public SimpleUser(String email)
		{
			this.id="";
			this.name="";
			this.email=email;
			this.password="";
			this.birthDate="";
			this.district="";
			this.gender="";
			this.twitter="";
			this.foursquare="";
			this.interests=null;
		}

		
		public SimpleUser(String id, String name,String email,String password,String birthDate,
				String district, String gender, String twitter, String foursquare,
				Vector<String> interests)
		{
			this.id=id;
			this.name=name;
			this.email=email;
			this.password=password;
			this.birthDate=birthDate;
			this.district=district;
			this.gender=gender;
			this.twitter=twitter;
			this.foursquare=foursquare;
			this.interests=interests;
		}
		
		public String get_email () 
		{
			return email;
		}


}
