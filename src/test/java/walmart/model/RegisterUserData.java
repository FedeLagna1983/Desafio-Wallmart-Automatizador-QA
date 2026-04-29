package walmart.model;

public record RegisterUserData(
        String userKey,
        String firstName,
        String lastName,
        String email,
        String telephone,
        String password,
        String address1,
        String address2,
        String city,
        String postCode,
        String country,
        String region
) {
}
