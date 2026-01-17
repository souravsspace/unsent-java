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
// UnsentClient client = new UnsentClient();
```

### Emails

Send transactional emails and manage your email history.

```java
import dev.unsent.types.Types.SendEmailRequest;
import dev.unsent.types.Types.SendEmailRequestTo;

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

// Get email events
client.emails.getEvents("email_id");

// Cancel a scheduled email
client.emails.cancel("email_id");
```

### Contacts & Contact Books

Manage your audience.

```java
import dev.unsent.types.Types.CreateContactBookRequest;
import dev.unsent.types.Types.CreateContactRequest;

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

// Get Contact
client.contacts.get("contact_book_id", "contact_id");

// Update Contact
client.contacts.update("contact_book_id", "contact_id", new UpdateContactRequest().firstName("Jane"));
```

### Campaigns

Manage marketing campaigns.

```java
import dev.unsent.types.Types.CreateCampaignRequest;
import dev.unsent.types.Types.ScheduleCampaignRequest;

CreateCampaignRequest campaign = new CreateCampaignRequest()
    .name("Weekly Newsletter")
    .subject("Your Weekly Update")
    .contactBookId("contact_book_id")
    .html("<p>Here is your update...</p>");

// Create Campaign
client.campaigns.create(campaign);

// Schedule Campaign
client.campaigns.schedule("campaign_id", new ScheduleCampaignRequest().scheduledAt("tomorrow 9am"));

// Pause/Resume Campaign
client.campaigns.pause("campaign_id");
client.campaigns.resume("campaign_id");
```

### Domains

Verify and manage your sending domains.

```java
import dev.unsent.types.Types.CreateDomainRequest;

// Create Domain
client.domains.create(new CreateDomainRequest().name("example.com"));

// Verify Domain
client.domains.verify("domain_id");

// List Domains
client.domains.list();

// Get Domain Analytics
client.domains.getAnalytics("domain_id", "7d");
```

### Webhooks

Manage webhooks for email events.

```java
import dev.unsent.types.Types.CreateWebhookRequest;
import java.net.URI;
import java.util.List;

// Create a webhook using helper method
client.webhooks.create("https://your-domain.com/webhooks/unsent", List.of("email.sent", "email.delivered"));

// Or using detailed request object
CreateWebhookRequest req = new CreateWebhookRequest()
    .url(URI.create("https://your-domain.com/webhooks/unsent"))
    .eventTypes(List.of(CreateWebhookRequest.EventTypesEnum.SENT, CreateWebhookRequest.EventTypesEnum.DELIVERED));
    
client.webhooks.create(req);

// List webhooks
client.webhooks.list();

// Test webhook
client.webhooks.test("webhook_id");
```

### System & Teams

```java
// Check API Health
client.system.health();

// Get API Version
client.system.version();

// Get Current Team Info
client.teams.get();

// List Teams (if multi-tenant)
client.teams.list();
```

### Other Resources

The SDK provides access to all Unsent resources:

- `client.templates` - Manage email templates
- `client.suppressions` - Manage suppression lists (`add`, `delete`, `list`)
- `client.apiKeys` - Manage API keys programmatically
- `client.analytics` - Get aggregated analytics (`get`, `getTimeSeries`, `getReputation`)
- `client.activity` - Get activity feed (`get`)
- `client.metrics` - Get system metrics (`get`)
- `client.stats` - Get system stats (`get`)
- `client.events` - Query email events (`list`)

## License

MIT