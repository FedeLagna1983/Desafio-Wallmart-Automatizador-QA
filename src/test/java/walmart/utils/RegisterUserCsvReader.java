package walmart.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import walmart.model.RegisterUserData;

public class RegisterUserCsvReader {

    private static final String REGISTER_USER_FILE = "testdata/registerUserData.csv";
    private static final DateTimeFormatter EMAIL_TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static RegisterUserData getRegisterUserData(String userKey) {
        return CsvSupport.findFirstByKey(
                REGISTER_USER_FILE,
                userKey,
                0,
                "Register user",
                (values, rowNumber) -> {
                    String emailPrefix = CsvSupport.requireColumn(
                            values,
                            3,
                            "emailPrefix",
                            REGISTER_USER_FILE,
                            rowNumber
                    );
                    String emailDomain = CsvSupport.requireColumn(
                            values,
                            4,
                            "emailDomain",
                            REGISTER_USER_FILE,
                            rowNumber
                    );
                    String timestamp = LocalDateTime.now().format(EMAIL_TIMESTAMP_FORMAT);
                    String generatedEmail = emailPrefix + timestamp + emailDomain;

                    return new RegisterUserData(
                            CsvSupport.requireColumn(values, 0, "userKey", REGISTER_USER_FILE, rowNumber),
                            CsvSupport.requireColumn(values, 1, "firstName", REGISTER_USER_FILE, rowNumber),
                            CsvSupport.requireColumn(values, 2, "lastName", REGISTER_USER_FILE, rowNumber),
                            generatedEmail,
                            CsvSupport.requireColumn(values, 5, "telephone", REGISTER_USER_FILE, rowNumber),
                            CsvSupport.requireColumn(values, 6, "password", REGISTER_USER_FILE, rowNumber),
                            CsvSupport.requireColumn(values, 7, "address1", REGISTER_USER_FILE, rowNumber),
                            CsvSupport.requireColumn(values, 8, "address2", REGISTER_USER_FILE, rowNumber),
                            CsvSupport.requireColumn(values, 9, "city", REGISTER_USER_FILE, rowNumber),
                            CsvSupport.requireColumn(values, 10, "postCode", REGISTER_USER_FILE, rowNumber),
                            CsvSupport.requireColumn(values, 11, "country", REGISTER_USER_FILE, rowNumber),
                            CsvSupport.requireColumn(values, 12, "region", REGISTER_USER_FILE, rowNumber)
                    );
                }
        );
    }
}
