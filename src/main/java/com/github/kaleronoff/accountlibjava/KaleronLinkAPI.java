package com.github.kaleronoff.accountlibjava;

/**
 * Enum representing the API endpoints for the Kaleron Account Link API.
 * Each constant corresponds to a specific API route.
 */
public enum KaleronLinkAPI {

    /**
     * Endpoint to retrieve account link information.
     */
    INFO("https://krio.fr.nf/API/V1/GetAccountLinkInfo"),

    /**
     * Endpoint to remove an account link.
     */
    REMOVE("https://krio.fr.nf/API/V1/RemoveAccountLink"),

    /**
     * Endpoint to get the email associated with an account link.
     */
    GET_EMAIL("https://krio.fr.nf/API/V1/AccountLinks/GetEmail"),

    /**
     * Endpoint to send a comment on a video.
     */
    SEND_COMMENT("https://krio.fr.nf/API/V1/AccountLinks/SendComment"),

    /**
     * Endpoint to read comments from a video.
     */
    READ_COMMENTS("https://krio.fr.nf/API/V1/AccountLinks/ReadComments"),

    /**
     * Endpoint to toggle a like on a video.
     */
    LIKE("https://krio.fr.nf/API/V1/AccountLinks/Like"),

    /**
     * Endpoint to toggle a dislike on a video.
     */
    DISLIKE("https://krio.fr.nf/API/V1/AccountLinks/Dislike");

    /**
     * The API route URL.
     */
    public final String route;

    /**
     * Constructs a new API endpoint with its corresponding route.
     *
     * @param route The URL of the API endpoint.
     */
    KaleronLinkAPI(String route) {
        this.route = route;
    }
}
