package dev.twittoapkvk.twito;

public class MessageMember {

    String message;
    String time;
    String date;
    String type;
    String senderuid;
    String receiveruid;
    String sendername;
    String uid;
    String audio;
String status;

    String url;



    String image;
    long delete;

    public MessageMember(){

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSenderuid() {
        return senderuid;
    }

    public void setSenderuid(String senderuid) {
        this.senderuid = senderuid;
    }

    public String getReceiveruid() {
        return receiveruid;
    }

    public void setReceiveruid(String receiveruid) {
        this.receiveruid = receiveruid;
    }

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getDelete() {
        return delete;
    }

    public void setDelete(long delete) {
        this.delete = delete;
    }

}

