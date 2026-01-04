package dev.unsent.types;

import java.util.Map;

public class ContactCreate {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Map<String, Object> attributes;
    private Boolean subscribed;

    public ContactCreate() {}

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Map<String, Object> getAttributes() { return attributes; }
    public void setAttributes(Map<String, Object> attributes) { this.attributes = attributes; }

    public Boolean getSubscribed() { return subscribed; }
    public void setSubscribed(Boolean subscribed) { this.subscribed = subscribed; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ContactCreate contact;

        public Builder() {
            contact = new ContactCreate();
        }

        public Builder firstName(String firstName) {
            contact.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            contact.lastName = lastName;
            return this;
        }

        public Builder email(String email) {
            contact.email = email;
            return this;
        }

        public Builder phone(String phone) {
            contact.phone = phone;
            return this;
        }

        public Builder attributes(Map<String, Object> attributes) {
            contact.attributes = attributes;
            return this;
        }

        public Builder subscribed(Boolean subscribed) {
            contact.subscribed = subscribed;
            return this;
        }

        public ContactCreate build() {
            return contact;
        }
    }
}
