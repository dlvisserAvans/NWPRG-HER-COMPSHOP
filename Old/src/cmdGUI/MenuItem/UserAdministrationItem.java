package cmdGUI.MenuItem;

public class UserAdministrationItem implements MenuItem {

    private String menuName;
    private String menuDescription;
    private MenuType menuType;

    public UserAdministrationItem(String menuName, String menuDescription, MenuType menuType) {
        this.menuName = menuName;
        this.menuDescription = menuDescription;
        this.menuType = menuType;
    }

    @Override
    public String getMenuName() {
        return this.menuName;
    }

    @Override
    public String getDescription() {
        return this.menuDescription;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuDescription() {
        return menuDescription;
    }

    public void setMenuDescription(String menuDescription) {
        this.menuDescription = menuDescription;
    }

    public MenuType getMenuType() {
        return menuType;
    }

    public void setMenuType(MenuType menuType) {
        this.menuType = menuType;
    }

    @Override
    public void Action() {
        System.out.println("You have chosen the User-Administration");
    }
}
