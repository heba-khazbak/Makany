package Try;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.Category;
import fi.foyt.foursquare.api.entities.CheckinGroup;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.CompleteVenue;
import fi.foyt.foursquare.api.entities.Recommendation;
import fi.foyt.foursquare.api.entities.Recommended;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;



public class Try {

	public static void main(String[] args) throws FoursquareApiException {
		// First we need a initialize FoursquareApi. 
		String CLIENT_ID = "CMK22P15BVQF2DVECGOOGBLUEBZGDVFCG3MZPUC2DLE2Y3TJ";
		String CLIENT_SECTET = "1CLOSPRBXZDAEWGNL5NNJJOH43YWOO5FVCH1O0EGG0MZLWLK";
		String CALLBACK = "GFWZS3H0TIIAAMEEDO4T0FAFDEUHCEB2IKJKGUAU2OT2HQRR";
	    FoursquareApi foursquareApi = new FoursquareApi(CLIENT_ID, CLIENT_SECTET, CALLBACK);
	    
	    // After client has been initialized we can make queries.
	  // Result<VenuesSearchResult> result = foursquareApi.venuesSearch("coffee", null, null, null, null, null, null, null, null, null, null, null, CALLBACK);
	    Result<Category[]> result = foursquareApi.venuesCategories();
	    System.out.println(result.getResult()[0].getName());
	    
	    Result<Recommended> R = foursquareApi.venuesExplore("33.3,37.2");
	    Recommendation[] myRec = R.getResult().getGroups()[0].getItems();
	    
	    System.out.println(myRec[0].getVenue().getName());
	    /*long l1 = 1279044824;
	    long l2 = 1279044824;
	    Result<CheckinGroup> R2 = foursquareApi.usersCheckins("1234", 10, 1, l1,l2 );
	    System.out.println(R2.getResult().getName());
	    */
	    
	   /* if (result.getMeta().getCode() == 200) {
	      // if query was ok we can finally we do something with the data
	      for (CompactVenue venue : result.getResult().getVenues()) {
	        // TODO: Do something we the data
	        System.out.println(venue.getName());
	      }
	    } else {
	      // TODO: Proper error handling
	      System.out.println("Error occured: ");
	      System.out.println("  code: " + result.getMeta().getCode());
	      System.out.println("  type: " + result.getMeta().getErrorType());
	      System.out.println("  detail: " + result.getMeta().getErrorDetail()); 
	    }*/

	}

}
