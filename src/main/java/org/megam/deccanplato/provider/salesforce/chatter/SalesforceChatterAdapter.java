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
package org.megam.deccanplato.provider.salesforce.chatter;

import java.util.Map;

import org.megam.deccanplato.provider.AbstractProviderAdapter;
import org.megam.deccanplato.provider.BusinessActivity;
import org.megam.deccanplato.provider.ProviderRegistry;
import org.megam.deccanplato.provider.core.DataMap;
import org.megam.deccanplato.provider.core.MultiDataMap;

/**
 * @author ram
 * 
 * @param <T>
 */
public class SalesforceChatterAdapter<T extends Object> extends
		AbstractProviderAdapter<T> {

	private BusinessActivity activity;

	public SalesforceChatterAdapter() {
		super();
	}

	/* using the user#create key */
	public void configure() {
		activity = ProviderRegistry.instance().getBusinessActivity(
				bizInfo.getName(), bizInfo.getActivityName());		
	}

	/** get the handle responsible for the call and stick stuff into it **/
	public boolean build() {
		activity.setArguments(bizInfo, args);
		return true;
	}

	public T handle() {
		return (T) activity.run();
	}

}
