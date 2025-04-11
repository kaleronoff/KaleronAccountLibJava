package com.github.kaleronoff.accountlibjava;

import okhttp3.*;
import org.json.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Represents an account link with various permissions for interacting with the Kaleron API.
 */
public class AccountLink {
    private static final OkHttpClient CLIENT = new OkHttpClient();
    private final UUID linkToken;
    private final String accountUsername;
    private final List<String> permissions;
    private final String domain;
    private final ZonedDateTime date;

    /**
     * Constructs an AccountLink object using a given link token.
     * @param linkToken The Account Link's token
     */
    public AccountLink(UUID linkToken) {
        this.linkToken = linkToken;
        JSONObject json = fetchApiData(KaleronLinkAPI.INFO.route + "?link-token=" + linkToken);

        this.accountUsername = json.getString("username");
        this.permissions = extractList(json, "permissions");
        this.domain = json.getString("domain");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(json.getString("date"), formatter);
        this.date = localDateTime.atZone(ZoneOffset.UTC);
    }

    /**
     * Retrieves the email associated with the account link.
     * @return The email address.
     */
    public String getEmail() {
        checkPermission("kaleron/email");
        JSONObject json = fetchApiData(KaleronLinkAPI.GET_EMAIL.route + "?link-token=" + linkToken);
        return json.getString("email");
    }

    /**
     * Sends a comment on a specified video.
     * @param video The video ID.
     * @param content The content of the comment.
     */
    public void sendComment(String video, String content) throws UnsupportedEncodingException {
        checkPermission("krio/send-comment");
        String url = KaleronLinkAPI.SEND_COMMENT.route + "?link-token=" + linkToken + "&video=" + video + "&content=" + URLEncoder.encode(content, String.valueOf(StandardCharsets.UTF_8));
        executePost(url);
    }

    /**
     * Reads the comments of a given video.
     * @param video The video ID.
     * @return A list of comments.
     */
    public List<String> readComments(String video) {
        checkPermission("krio/read-comments");
        JSONObject json = fetchApiData(KaleronLinkAPI.READ_COMMENTS.route + "?link-token=" + linkToken + "&video=" + video);
        return extractList(json, "comments");
    }

    /**
     * Toggles the like status of a video.
     * @param video The video ID.
     */
    public void toggleVideoLike(String video) {
        checkPermission("krio/like");
        executePost(KaleronLinkAPI.LIKE.route + "?link-token=" + linkToken + "&video=" + video);
    }

    /**
     * Toggles the dislike status of a video.
     * @param video The video ID.
     */
    public void toggleVideoDislike(String video) {
        checkPermission("krio/like");
        executePost(KaleronLinkAPI.DISLIKE.route + "?link-token=" + linkToken + "&video=" + video);
    }

    /**
     * Fetches API data and returns it as a JSONObject.
     * @param url The API endpoint.
     * @return The JSON response.
     */
    private JSONObject fetchApiData(String url) {
        Request request = new Request.Builder().url(url).build();
        try (Response response = CLIENT.newCall(request).execute()) {
            String responseBody = Objects.requireNonNull(response.body()).string();
            JSONObject json = new JSONObject(responseBody);
            if (!response.isSuccessful()) {
                throw new KaleronAPIError("(" + url + ") API returned an error: " + json.getString("error"));
            }
            return json;
        } catch (IOException | JSONException e) {
            throw new KaleronAPIError("(" + url + ") API error", e);
        }
    }

    /**
     * Executes a POST request to the given API endpoint.
     * @param url The API endpoint.
     */
    private void executePost(String url) {
        Request request = new Request.Builder()
                .url(url)
                .method("POST", RequestBody.create(new byte[0], null))
                .build();
        try (Response response = CLIENT.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                JSONObject json = new JSONObject(Objects.requireNonNull(response.body()).string());
                throw new KaleronAPIError("(" + url + ") API error: " + json.getString("error"));
            }
        } catch (IOException | JSONException e) {
            throw new KaleronAPIError("(" + url + ") API error", e);
        }
    }

    /**
     * Checks if the required permission is granted.
     * @param requiredPermission The permission to check.
     */
    private void checkPermission(String requiredPermission) {
        if (!permissions.contains(requiredPermission)) {
            throw new KaleronAPIError("Insufficient permissions: " + requiredPermission);
        }
    }

    /**
     * Extracts a list of strings from a JSON object.
     * @param json The JSON object.
     * @param key The key containing the array.
     * @return A list of strings.
     */
    private List<String> extractList(JSONObject json, String key) {
        List<String> list = new ArrayList<>();
        for (Object obj : json.getJSONArray(key)) {
            list.add(String.valueOf(obj));
        }
        return list;
    }

    public String getAccountUsername() { return accountUsername; }
    public List<String> getPermissions() { return permissions; }
    public String getDomain() { return domain; }
    public ZonedDateTime getDate() { return date; }

    @Override
    public String toString() {
        return "AccountLink{" +
                "linkToken=" + linkToken +
                ", accountUsername='" + accountUsername + '\'' +
                ", permissions=" + permissions +
                ", domain='" + domain + '\'' +
                ", date=" + date +
                '}';
    }
}
