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
package org.megam.deccanplato.provider.core;

import java.util.Set;

import com.google.gson.Gson;

public class ResponseDataBuilder<T extends Object>  {
	
	private ResponseData<T> resp;
	
	public ResponseDataBuilder(Set<CloudOperationOutput<T>> tempOpsSet) {
		resp = new ResponseData<T>();
		for(CloudOperationOutput<T> op : tempOpsSet) {
			if(op!=null && op.get()!=null) {
			resp.put(op.name(), op.get());
			}
		}
			
	}

	public ResponseData getResponseData() {
		System.out.println("------------->"+resp);
		return resp;
	}
	
	public String toJson(String respdata) {
		Gson gson=new Gson();
		String respJson = gson.toJson(respdata, ResponseData.class);	
		return respJson;
	}

}
