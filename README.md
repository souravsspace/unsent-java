# Unsent Java SDK

The official Java SDK for the [Unsent.dev](https://unsent.dev) API.

## Prerequisites

- [Unsent API key](https://app.unsent.dev/dev-settings/api-keys)
- [Verified domain](https://app.unsent.dev/domains)
- Java 11 or higher
- Maven 3.6+

## Installation

### Maven

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>dev.unsent</groupId>
    <artifactId>unsent-java</artifactId>
    <version>1.0.2</version>
</dependency>
```

## Usage

### Initialization

```java
import dev.unsent.UnsentClient;

// Initialize with API key
UnsentClient client = new UnsentClient("un_xxxx");

// Or use environment variable UNSENT_API_KEY
UnsentClient client = new UnsentClient();
```

### Emails

```java
import dev.unsent.types.SendEmailRequest;
import dev.unsent.types.SendEmailRequestTo;

// Send an email
SendEmailRequest email = new SendEmailRequest()
    .to(new SendEmailRequestTo("user@example.com"))
    .from("noreply@yourdomain.com")
    .subject("Welcome to Unsent")
    .html("<strong>Hello!</strong>");

client.emails.send(email);

// Send with options (e.g. idempotency key)
client.emails.send(email, Map.of("idempotencyKey", "unique-key-123"));

// Batch sending
List<SendEmailRequest> batch = List.of(email1, email2);
client.emails.batch(batch);

// Get email details
client.emails.get("email_id");

// List emails
client.emails.list(1, 10);
```

### Contacts & Contact Books

```java
import dev.unsent.types.CreateContactBookRequest;
import dev.unsent.types.CreateContactRequest;

// Create Contact Book
CreateContactBookRequest bookReq = new CreateContactBookRequest().name("Newsletter");
client.contactBooks.create(bookReq);

// Create Contact in Book
CreateContactRequest contact = new CreateContactRequest()
    .email("user@example.com")
    .firstName("John")
    .lastName("Doe");

client.contacts.create("contact_book_id", contact);

// List Contacts
client.contacts.list("contact_book_id");
```

### Campaigns

```java
import dev.unsent.types.CreateCampaignRequest;

CreateCampaignRequest campaign = new CreateCampaignRequest()
    .name("Weekly Newsletter")
    .subject("Your Weekly Update")
    .contactBookId("contact_book_id")
    .html("<p>Here is your update...</p>");

// Create Campaign
client.campaigns.create(campaign);

// Schedule Campaign
client.campaigns.schedule("campaign_id", new ScheduleCampaignRequest().scheduledAt("tomorrow 9am"));
```

### Domains

```java
import dev.unsent.types.CreateDomainRequest;

// Create Domain
client.domains.create(new CreateDomainRequest().name("example.com"));

// Verify Domain
client.domains.verify("domain_id");

// List Domains
client.domains.list();
```

### Helper Clients

The SDK provides access to all Unsent resources:

- `client.templates` - Manage email templates
- `client.suppressions` - Manage suppression lists
- `client.apiKeys` - Manage API keys programmatically
- `client.analytics` - internal analytics
- `client.settings` - internal settings
- `client.system` - System health check

### Webhooks (Future Feature)

> **Note**: Webhooks are currently a roadmap feature and may not be fully supported by the API yet.

```java
import java.util.List;

String webhookUrl = "https://your-domain.com/webhooks/unsent";
List<String> events = List.of("email.sent", "email.delivered");

// Create a webhook
client.webhooks.create(webhookUrl, events);

// List webhooks
client.webhooks.list();
```

## License

MIT