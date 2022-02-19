enum USER_TYPE {
    VENDOR,
    CUSTOMER,
    ADMIN
}

public class User {
    public static String name;
    public static String email;
    public static int user_id;
    public USER_TYPE user_type;
    public String address;
    private String password;
}