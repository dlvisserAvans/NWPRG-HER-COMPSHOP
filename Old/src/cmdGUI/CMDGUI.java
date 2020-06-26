package cmdGUI;

import cmdGUI.MenuItem.BuildingAdministrationItem;
import cmdGUI.MenuItem.MenuItem;
import cmdGUI.MenuItem.MenuType;
import cmdGUI.MenuItem.UserAdministrationItem;

import java.util.ArrayList;

public class CMDGUI {

    private String menuName;
    private String menuDescription;

    ArrayList<MenuItem> menuItems;

    public CMDGUI() {
        this.menuItems = setupMenuItems();
    }

    public ArrayList<MenuItem> setupMenuItems(){
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new UserAdministrationItem("UserAdd","Add user used for logins", MenuType.ADD));
        menuItems.add(new UserAdministrationItem("UserDel","Del user used for logins", MenuType.DELETE));
        menuItems.add(new UserAdministrationItem("UserShow","Show all users used for logins", MenuType.SHOW));
        menuItems.add(new BuildingAdministrationItem("BuildingAdd","Add buildings for sale", MenuType.ADD));
        menuItems.add(new BuildingAdministrationItem("BuildingDel","Del buildings for sale", MenuType.DELETE));
        menuItems.add(new BuildingAdministrationItem("BuildingShow","Show buildings for sale", MenuType.SHOW));

        return menuItems;
    }

    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(ArrayList<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public MenuItem getMenuItemByName(ArrayList<MenuItem> menuItems, String name) {
        for (MenuItem menuItem : menuItems) {
            if (menuItem.getMenuName().equals(name))
                return menuItem;
        }
        return null;
    }
}
