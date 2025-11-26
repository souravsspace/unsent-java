# Unsent Java SDK

## Prerequisites

- [Unsent API key](https://app.unsent.dev/dev-settings/api-keys)
- [Verified domain](https://app.unsent.dev/domains)
- Java 11 or higher
- Maven 3.6+ or Gradle 7+

## Installation

### Maven

Add this dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>dev.unsent</groupId>
    <artifactId>unsent-java</artifactId>
    <version>1.0.1</version>
</dependency>
```

### Gradle

Add this to your `build.gradle`:

```gradle
implementation 'dev.unsent:unsent-java:1.0.1'
```

### From Source

```bash
git clone https://github.com/souravsspace/unsent-java.git
cd unsent-java
mvn install
```

## Usage

### Basic Setup

```java
import dev.unsent.UnsentClient;

// Initialize with API key
UnsentClient client = new UnsentClient("un_xxxx");
```

### Environment Variables

You can also set your API key using environment variables:

```java
// Set UNSENT_API_KEY environment variable
// Then initialize without passing the key
UnsentClient client = new UnsentClient();
```

### Sending Emails

#### Simple Email

```java
import dev.unsent.models.EmailRequest;
import dev.unsent.responses.EmailResponse;

EmailRequest request = new EmailRequest(
    "hello@acme.com",
    "hello@company.com", 
    "Unsent email",
    "<p>Unsent is the best email service provider to send emails</p>",
    "Unsent is the best email service provider to send emails"
);

try {
    EmailResponse response = client.emails.send(request);
    System.out.println("Email sent! ID: " + response.getId());
} catch (UnsentException e) {
    System.err.println("Error: " + e.getMessage());
}
```

#### Email with Attachments

```java
import dev.unsent.models.Attachment;
import java.util.Arrays;
import java.util.List;

List<Attachment> attachments = Arrays.asList(
    new Attachment("document.pdf", "base64-encoded-content-here")
);

EmailRequest request = new EmailRequest(
    "hello@acme.com",
    "hello@company.com",
    "Email with attachment", 
    "<p>Please find the attachment below</p>",
    null,
    attachments
);

EmailResponse response = client.emails.send(request);
```

#### Scheduled Email

```java
import java.time.Instant;
import java.time.temporal.ChronoUnit;

// Schedule for 1 hour from now
Instant scheduledTime = Instant.now().plus(1, ChronoUnit.HOURS);

EmailRequest request = new EmailRequest(
    "hello@acme.com",
    "hello@company.com",
    "Scheduled email",
    "<p>This email was scheduled</p>",
    null,
    null,
    scheduledTime.toString()
);

EmailResponse response = client.emails.send(request);
```

#### Batch Emails

```java
import java.util.Arrays;
import java.util.List;

List<EmailRequest> emails = Arrays.asList(
    new EmailRequest(
        "user1@example.com",
        "hello@company.com",
        "Hello User 1",
        "<p>Welcome User 1</p>",
        null
    ),
    new EmailRequest(
        "user2@example.com", 
        "hello@company.com",
        "Hello User 2",
        "<p>Welcome User 2</p>",
        null
    )
);

List<EmailResponse> responses = client.emails.batch(emails);
System.out.println("Sent " + responses.size() + " emails");
```

#### Idempotent Retries

To prevent duplicate emails when retrying failed requests, you can provide an idempotency key.

```java
import java.util.HashMap;
import java.util.Map;

Map<String, String> options = new HashMap<>();
options.put("idempotencyKey", "unique-key-123");

EmailResponse response = client.emails.send(request, options);
```

### Managing Emails

#### Get Email Details

```java
try {
    EmailResponse email = client.emails.get("email_id");
    System.out.println("Email status: " + email.getStatus());
} catch (UnsentException e) {
    System.err.println("Error: " + e.getMessage());
}
```

#### Update Email

```java
EmailRequest updateRequest = new EmailRequest(
    null, null, "Updated subject", "<p>Updated content</p>", null
);

EmailResponse updated = client.emails.update("email_id", updateRequest);
```

#### Cancel Scheduled Email

```java
try {
    client.emails.cancel("email_id");
    System.out.println("Email cancelled successfully");
} catch (UnsentException e) {
    System.err.println("Error: " + e.getMessage());
}
```

### Managing Contacts

#### Create Contact

```java
import dev.unsent.models.ContactRequest;
import java.util.HashMap;
import java.util.Map;

Map<String, Object> metadata = new HashMap<>();
metadata.put("company", "Acme Inc");
metadata.put("role", "Developer");

ContactRequest request = new ContactRequest(
    "user@example.com",
    "John",
    "Doe",
    metadata
);

try {
    ContactResponse contact = client.contacts.create("contact_book_id", request);
    System.out.println("Contact created: " + contact.getId());
} catch (UnsentException e) {
    System.err.println("Error: " + e.getMessage());
}
```

#### Get Contact

```java
ContactResponse contact = client.contacts.get("contact_book_id", "contact_id");
```

#### Update Contact

```java
Map<String, Object> metadata = new HashMap<>();
metadata.put("role", "Senior Developer");

ContactRequest updateRequest = new ContactRequest(null, "Jane", null, metadata);
ContactResponse updated = client.contacts.update("contact_book_id", "contact_id", updateRequest);
```

#### Upsert Contact

```java
// Creates if doesn't exist, updates if exists
ContactRequest request = new ContactRequest(
    "user@example.com", "John", "Doe", null
);

ContactResponse result = client.contacts.upsert("contact_book_id", "contact_id", request);
```

#### Delete Contact

```java
try {
    client.contacts.delete("contact_book_id", "contact_id");
    System.out.println("Contact deleted successfully");
} catch (UnsentException e) {
    System.err.println("Error: " + e.getMessage());
}
```

### Managing Campaigns

Create and manage email campaigns:

```java
import dev.unsent.models.CampaignRequest;
import java.time.Instant;

// Create a campaign
CampaignRequest campaignRequest = new CampaignRequest(
    "Welcome Series",
    "hello@company.com",
    "Welcome to our platform!",
    "cb_12345",
    "<h1>Welcome!</h1><p>Thanks for joining us.</p>",
    false
);

try {
    CampaignResponse campaign = client.campaigns.create(campaignRequest);
    System.out.println("Campaign created: " + campaign.getId());
    
    // Schedule a campaign
    Instant scheduledTime = Instant.parse("2024-12-01T09:00:00Z");
    CampaignScheduleRequest scheduleRequest = new CampaignScheduleRequest(
        scheduledTime.toString(), 1000
    );
    
    client.campaigns.schedule(campaign.getId(), scheduleRequest);
    System.out.println("Campaign scheduled successfully");
    
    // Get campaign details
    CampaignResponse details = client.campaigns.get(campaign.getId());
    System.out.println("Campaign status: " + details.getStatus());
    System.out.println("Total recipients: " + details.getTotal());
    
    // Pause a campaign
    client.campaigns.pause(campaign.getId());
    System.out.println("Campaign paused successfully");
    
    // Resume a campaign
    client.campaigns.resume(campaign.getId());
    System.out.println("Campaign resumed successfully");
    
} catch (UnsentException e) {
    System.err.println("Error: " + e.getMessage());
}
```

### Error Handling

The SDK throws `UnsentException` for API errors:

```java
try {
    EmailResponse response = client.emails.send(request);
    System.out.println("Email sent: " + response.getId());
} catch (UnsentException e) {
    System.err.println("Error " + e.getCode() + ": " + e.getMessage());
    // Handle error appropriately
}
```

### Configuration Options

```java
// Custom base URL
UnsentClient client = new UnsentClient("un_xxxx", "https://api.unsent.dev", true);

// Don't raise exceptions (check response status manually)
UnsentClient client = new UnsentClient("un_xxxx", null, false);
```

## API Reference

### Client Constructor

- `UnsentClient(String apiKey)` - Initialize with API key
- `UnsentClient(String apiKey, String baseUrl, boolean raiseOnError)` - Initialize with custom configuration
- `UnsentClient()` - Initialize using environment variables

### Email Methods

- `client.emails.send(EmailRequest request)` - Send an email
- `client.emails.batch(List<EmailRequest> emails)` - Send multiple emails in batch (max 100)
- `client.emails.get(String emailId)` - Get email details
- `client.emails.update(String emailId, EmailRequest request)` - Update a scheduled email
- `client.emails.cancel(String emailId)` - Cancel a scheduled email

### Contact Methods

- `client.contacts.create(String bookId, ContactRequest request)` - Create a contact
- `client.contacts.get(String bookId, String contactId)` - Get contact details
- `client.contacts.update(String bookId, String contactId, ContactRequest request)` - Update a contact
- `client.contacts.upsert(String bookId, String contactId, ContactRequest request)` - Upsert a contact
- `client.contacts.delete(String bookId, String contactId)` - Delete a contact

### Campaign Methods

- `client.campaigns.create(CampaignRequest request)` - Create a campaign
- `client.campaigns.get(String campaignId)` - Get campaign details
- `client.campaigns.schedule(String campaignId, CampaignScheduleRequest request)` - Schedule a campaign
- `client.campaigns.pause(String campaignId)` - Pause a campaign
- `client.campaigns.resume(String campaignId)` - Resume a campaign

## Features

- 🔐 **Type-safe** - Full Java type safety with strongly typed models
- ⚡ **Modern** - Built with Java 11+ and modern HTTP practices
- 📦 **Lightweight** - Minimal dependencies (Gson for JSON)
- 🔄 **Batch sending** - Send up to 100 emails in a single request
- ⏰ **Scheduled emails** - Schedule emails for later delivery
- 🏗️ **Builder Pattern** - Fluent API for request construction
- 🎯 **Exception Handling** - Comprehensive error handling with custom exceptions

## Requirements

- Java 11 or higher
- Maven 3.6+ (for Maven projects)
- Gradle 7+ (for Gradle projects)

## License

MIT

## Support

- [Documentation](https://docs.unsent.dev)
- [GitHub Issues](https://github.com/souravsspace/unsent-java/issues)
- [Discord Community](https://discord.gg/unsent)

## Development

### Building from Source

```bash
git clone https://github.com/souravsspace/unsent-java.git
cd unsent-java
mvn clean install
```

### Running Tests

```bash
mvn test
```

### Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Run tests: `mvn test`
6. Submit a pull request