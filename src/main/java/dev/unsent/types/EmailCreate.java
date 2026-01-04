package dev.unsent.types;

import java.util.List;
import java.util.Date;

public class EmailCreate {
    private String to;
    private String from;
    private String subject;
    private String html;
    private String text;
    private String replyTo;
    private List<String> cc;
    private List<String> bcc;
    private List<Attachment> attachments;
    private java.util.Map<String, String> headers;
    private Date scheduledAt;

    public EmailCreate() {}

    public EmailCreate(String to, String from, String subject) {
        this.to = to;
        this.from = from;
        this.subject = subject;
    }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getHtml() { return html; }
    public void setHtml(String html) { this.html = html; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getReplyTo() { return replyTo; }
    public void setReplyTo(String replyTo) { this.replyTo = replyTo; }

    public List<String> getCc() { return cc; }
    public void setCc(List<String> cc) { this.cc = cc; }

    public List<String> getBcc() { return bcc; }
    public void setBcc(List<String> bcc) { this.bcc = bcc; }

    public List<Attachment> getAttachments() { return attachments; }
    public void setAttachments(List<Attachment> attachments) { this.attachments = attachments; }

    public java.util.Map<String, String> getHeaders() { return headers; }
    public void setHeaders(java.util.Map<String, String> headers) { this.headers = headers; }

    public Date getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(Date scheduledAt) { this.scheduledAt = scheduledAt; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private EmailCreate email;

        public Builder() {
            email = new EmailCreate();
        }

        public Builder to(String to) {
            email.to = to;
            return this;
        }

        public Builder from(String from) {
            email.from = from;
            return this;
        }

        public Builder subject(String subject) {
            email.subject = subject;
            return this;
        }

        public Builder html(String html) {
            email.html = html;
            return this;
        }

        public Builder text(String text) {
            email.text = text;
            return this;
        }

        public Builder replyTo(String replyTo) {
            email.replyTo = replyTo;
            return this;
        }

        public Builder cc(List<String> cc) {
            email.cc = cc;
            return this;
        }

        public Builder bcc(List<String> bcc) {
            email.bcc = bcc;
            return this;
        }

        public Builder attachments(List<Attachment> attachments) {
            email.attachments = attachments;
            return this;
        }

        public Builder headers(java.util.Map<String, String> headers) {
            email.headers = headers;
            return this;
        }

        public Builder scheduledAt(Date scheduledAt) {
            email.scheduledAt = scheduledAt;
            return this;
        }

        public EmailCreate build() {
            return email;
        }
    }
}
