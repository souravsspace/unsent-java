package dev.unsent.types;

import java.util.Date;

public class CampaignCreate {
    private String name;
    private String subject;
    private String from;
    private String html;
    private String text;
    private String replyTo;
    private String contactBookId;
    private Date scheduledAt;

    public CampaignCreate() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getHtml() { return html; }
    public void setHtml(String html) { this.html = html; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getReplyTo() { return replyTo; }
    public void setReplyTo(String replyTo) { this.replyTo = replyTo; }

    public String getContactBookId() { return contactBookId; }
    public void setContactBookId(String contactBookId) { this.contactBookId = contactBookId; }

    public Date getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(Date scheduledAt) { this.scheduledAt = scheduledAt; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private CampaignCreate campaign;

        public Builder() {
            campaign = new CampaignCreate();
        }

        public Builder name(String name) {
            campaign.name = name;
            return this;
        }

        public Builder subject(String subject) {
            campaign.subject = subject;
            return this;
        }

        public Builder from(String from) {
            campaign.from = from;
            return this;
        }

        public Builder html(String html) {
            campaign.html = html;
            return this;
        }

        public Builder text(String text) {
            campaign.text = text;
            return this;
        }

        public Builder replyTo(String replyTo) {
            campaign.replyTo = replyTo;
            return this;
        }

        public Builder contactBookId(String contactBookId) {
            campaign.contactBookId = contactBookId;
            return this;
        }

        public Builder scheduledAt(Date scheduledAt) {
            campaign.scheduledAt = scheduledAt;
            return this;
        }

        public CampaignCreate build() {
            return campaign;
        }
    }
}
