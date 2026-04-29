package walmart.utils;

import walmart.model.UserData;

public class UsersCsvReader {

    private static final String USER_DATA_FILE = "testdata/users.csv";

    public static UserData getUserData(String userKey) {
        return CsvSupport.findFirstByKey(
                USER_DATA_FILE,
                userKey,
                0,
                "User",
                (values, rowNumber) -> new UserData(
                        CsvSupport.requireColumn(values, 0, "userKey", USER_DATA_FILE, rowNumber),
                        CsvSupport.requireColumn(values, 1, "email", USER_DATA_FILE, rowNumber),
                        CsvSupport.requireColumn(values, 2, "password", USER_DATA_FILE, rowNumber)
                )
        );
    }
}
