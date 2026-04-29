package walmart.model;

public record CheckoutData(
        String scenario,
        String firstName,
        String lastName,
        String email,
        String telephone,
        String address1,
        String address2,
        String city,
        String postCode,
        String country,
        String region
) {
}
