package dev.unsent.types;

public class DomainCreate {
    private String name;
    private String region;

    public DomainCreate() {}

    public DomainCreate(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private DomainCreate domain;

        public Builder() {
            domain = new DomainCreate();
        }

        public Builder name(String name) {
            domain.name = name;
            return this;
        }

        public Builder region(String region) {
            domain.region = region;
            return this;
        }

        public DomainCreate build() {
            return domain;
        }
    }
}
