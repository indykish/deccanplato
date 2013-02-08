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
package org.megam.deccanplato.provider.box;

import java.util.Map;

import org.megam.deccanplato.provider.AbstractProviderAdapter;
import org.megam.deccanplato.provider.BusinessActivity;
import org.megam.deccanplato.provider.ProviderRegistry;
import org.megam.deccanplato.provider.core.DataMap;
import org.megam.deccanplato.provider.core.MultiDataMap;

public class BoxAdapter extends AbstractProviderAdapter {

	private BusinessActivity activity;
	
	public BoxAdapter() {
		super();
	}

	

	public void configure() {
		/* using the user#create key */
		//activity = registry.getBusinessActivity(cloud_app, business_function);
		activity = ProviderRegistry.instance().getBusinessActivity(bizInfo.getName(),
				bizInfo.getActivityName());
	}

	public boolean build() {
		/** get the handle responsible for the call and stick stuff into it **/
		activity.setArguments(bizInfo, args);
		return true;
	}
	
	public Map<String,String> handle() {
		return activity.run();
	}

	
	
	 
}
