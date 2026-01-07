package dev.unsent;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CreateWebhookRequest
 * 
 * Request payload for creating a new webhook.
 * Note: Webhooks are a future feature and not yet fully implemented on the server side.
 */
public class CreateWebhookRequest {
    @SerializedName("url")
    private String url;

    @SerializedName("events")
    private List<String> events;

    public CreateWebhookRequest() {
    }

    public CreateWebhookRequest url(String url) {
        this.url = url;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CreateWebhookRequest events(List<String> events) {
        this.events = events;
        return this;
    }

    public CreateWebhookRequest addEventsItem(String eventsItem) {
        if (this.events == null) {
            this.events = new ArrayList<>();
        }
        this.events.add(eventsItem);
        return this;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CreateWebhookRequest createWebhookRequest = (CreateWebhookRequest) o;
        return Objects.equals(this.url, createWebhookRequest.url) &&
                Objects.equals(this.events, createWebhookRequest.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, events);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CreateWebhookRequest {\n");
        sb.append("    url: ").append(toIndentedString(url)).append("\n");
        sb.append("    events: ").append(toIndentedString(events)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
