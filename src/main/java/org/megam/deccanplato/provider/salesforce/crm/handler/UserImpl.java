/* “Copyright 2012 Megam Systems”
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.megam.deccanplato.provider.salesforce.crm.handler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;
import org.megam.deccanplato.http.TransportMachinery;
import org.megam.deccanplato.http.TransportResponse;
import org.megam.deccanplato.http.TransportTools;
import org.megam.deccanplato.provider.BusinessActivity;
import org.megam.deccanplato.provider.Constants;
import org.megam.deccanplato.provider.core.BusinessActivityInfo;
import org.megam.deccanplato.provider.core.DataMap;
import org.megam.deccanplato.provider.core.DefaultDataMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static org.megam.deccanplato.provider.salesforce.crm.Constants.*;
import static org.megam.deccanplato.provider.Constants.*;

/**
 * 
 * @author pandiyaraja
 *
 **This class implements the business activity of Salesforcecrm user method.
 * this class has four methods, to implement business functions, like create, update,
 * lisd and delete(not implemented now).
 */
public class UserImpl implements BusinessActivity {

    private static final String SALESFORCE_USER_URL="/services/data/v25.0/sobjects/User/";
	private Map<String, String> args;
	private BusinessActivityInfo bizInfo;
 
	/**
     * this method returns business method name to perform action in that Salesforce Module 
     */
	@Override
	public String name() {
		return "user";
	}

	@Override
	public void setArguments(BusinessActivityInfo tempBizInfo,
			Map<String, String> tempArgs) {
		this.args = tempArgs;
		this.bizInfo = tempBizInfo;
	}

	@Override
	public Map<String, String> run() {
		Map<String, String> outMap=null;
		switch (bizInfo.getActivityFunction()) {
		case CREATE:
			outMap=create();
			break;
		case LIST:
			outMap=list();
			break;
		case UPDATE:
			outMap=delete();
			break;
		case DELETE:
			outMap=update();
			break;
		default:
			break;
		}
		return outMap;
	}
	/**
	 * this method creates a user in salesforce.com and returns that user id.
	 * This method gets input from a MAP(contains json data) and returns a MAp.
	 * @param outMap 
	 */
	private Map<String, String> create() {
		Map<String, String> outMap=new HashMap<String, String>();
		final String SALESFORCE_CREATE_USER_URL = args.get(INSTANCE_URL)+SALESFORCE_USER_URL;		
        Map<String,String> header=new HashMap<String,String>();
        header.put(S_AUTHORIZATION, S_OAUTH+args.get(ACCESS_TOKEN));    		

		Map<String, Object> userAttrMap = new HashMap<String, Object>();
        userAttrMap.put(S_USERNAME, args.get(USERNAME));
        userAttrMap.put(S_FIRSTNAME, args.get(FIRSTNAME));
        userAttrMap.put(S_EMAIL, args.get(EMAIL));
        userAttrMap.put(S_ALIAS, args.get(ALIAS));
        userAttrMap.put(S_PROFILEID, args.get(PROFILEID));
        userAttrMap.put(S_LASTNAME, args.get(LASTNAME));
        userAttrMap.put(S_TIMEZONESIDKEY, args.get(TIMEZONESIDKEY));
        userAttrMap.put(S_LOCALESIDKEY, args.get(LOCALESIDKEY));
        userAttrMap.put(S_EMAILENCODINGKEY, args.get(EMAILENCODINGKEY));
        userAttrMap.put(S_LANGUAGELOCALEYKEY, args.get(LANGUAGELOCALEKEY));
        
        TransportTools tst=new TransportTools(SALESFORCE_CREATE_USER_URL, null, header);
        Gson obj = new GsonBuilder().setPrettyPrinting().create();
        tst.setContentType(ContentType.APPLICATION_JSON, obj.toJson(userAttrMap));
         try {
			String responseBody=TransportMachinery.post(tst).entityToString();
				outMap.put(OUTPUT, responseBody);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}        
        
		return outMap;
	}
	/**
	 * this method lists all user in salesforce.com and returns a list of all user details.
	 * This method gets input from a MAP(contains json data) and returns a MAp.
	 * @param outMap 
	 */
	private Map<String, String> list() {

		Map<String, String> outMap=new HashMap<String, String>();
		final String SALESFORCE_LIST_USER_URL = args.get(INSTANCE_URL)
				+ "/services/data/v25.0/query/?q=SELECT+Username+FROM+User";
		Map<String, String> header = new HashMap<String, String>();
		header.put(S_AUTHORIZATION, S_OAUTH + args.get(ACCESS_TOKEN));

		TransportTools tst = new TransportTools(SALESFORCE_LIST_USER_URL, null,
				header);		
		try {
			String responseBody = TransportMachinery.get(tst).entityToString();
			outMap.put(OUTPUT, responseBody);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return outMap;

	}
	/**
	 * This method delete a user in salesforce.com and returns a success message with deleted user id.
	 * This method gets input from a MAP(contains json data) and returns a MAp.
	 * @param outMap 
	 */
	private Map<String, String> delete() {
		return null;

	}
	/**
	 * This method updates a user in salesforce.com and returns a success message with updated user id.
	 * This method gets input from a MAP(contains json data) and returns a MAp.
	 * @param outMap 
	 * @param outMap 
	 */ 
	public Map<String, String> update() {
		
		final String SALESFORCE_UPDATE_USER_URL = args.get(INSTANCE_URL)+SALESFORCE_USER_URL+args.get(ID);
        Map<String, String> outMap =new HashMap<String, String>();
		Map<String,String> header=new HashMap<String,String>();
        header.put(S_AUTHORIZATION, S_OAUTH+args.get(ACCESS_TOKEN));    		
				
        Map<String, Object> userAttrMap = new HashMap<String, Object>();
        userAttrMap.put(S_USERNAME, args.get(USERNAME));
        userAttrMap.put(S_FIRSTNAME, args.get(FIRSTNAME));
        userAttrMap.put(S_EMAIL, args.get(EMAIL));
        userAttrMap.put(S_ALIAS, args.get(ALIAS));
        userAttrMap.put(S_PROFILEID, args.get(PROFILEID));
        userAttrMap.put(S_LASTNAME, args.get(LASTNAME));
        userAttrMap.put(S_TIMEZONESIDKEY, args.get(TIMEZONESIDKEY));
        userAttrMap.put(S_LOCALESIDKEY, args.get(LOCALESIDKEY));
        userAttrMap.put(S_EMAILENCODINGKEY, args.get(EMAILENCODINGKEY));
        userAttrMap.put(S_LANGUAGELOCALEYKEY, args.get(LANGUAGELOCALEKEY));
        
        TransportTools tst=new TransportTools(SALESFORCE_UPDATE_USER_URL, null, header);
        Gson obj = new GsonBuilder().setPrettyPrinting().create();
        tst.setContentType(ContentType.APPLICATION_JSON, obj.toJson(userAttrMap));
        try {
			  TransportMachinery.patch(tst);
		      outMap.put(OUTPUT, UPDATE_STRING+args.get(ID));
		
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outMap;

	}

}
