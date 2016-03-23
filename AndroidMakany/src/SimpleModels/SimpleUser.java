package SimpleModels;

import java.util.Vector;

public class SimpleUser 
{
		public String id;
		public String name, email, password, birthDate, district, gender;
		public String twitter, foursquare;
		public int trust;
		
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
				String district, String gender, String twitter, String foursquare, int trust,
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
			this.trust=trust;
		}
		
		public String get_email () 
		{
			return email;
		}


}
