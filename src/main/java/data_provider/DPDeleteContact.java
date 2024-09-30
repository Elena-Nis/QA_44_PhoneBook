package data_provider;

import dto.UserDto;
import org.testng.annotations.DataProvider;

public class DPDeleteContact {

    @DataProvider
    public Object[][] addNewUserDP() {
        UserDto userWithContacts = new UserDto("asd@qwe.com", "Privet$123457890");
        UserDto userWithoutContacts = new UserDto("anton@mail.com", "Anton$123457890");
        return new Object[][]{{userWithContacts}, {userWithoutContacts}};
    }
}



