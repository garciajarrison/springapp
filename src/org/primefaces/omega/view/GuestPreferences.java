/*
 * Copyright 2009-2014 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.omega.view;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class GuestPreferences implements Serializable {

    private String theme = "orange";

    private String menuLayout = "static";

	public String getTheme() {
		return theme;
	}

  public String getMenuLayout() {
      if(this.menuLayout.equals("static"))
        return "menu-layout-static";
      else if(this.menuLayout.equals("overlay"))
        return "menu-layout-overlay";
      else
        return "menu-layout-static";
  }

	public void setTheme(String theme) {
		this.theme = theme;
	}

  public void setMenuLayout(String menuLayout) {
      this.menuLayout = menuLayout;
  }
}
