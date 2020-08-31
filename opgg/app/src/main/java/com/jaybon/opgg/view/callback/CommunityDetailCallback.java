package com.jaybon.opgg.view.callback;

public interface CommunityDetailCallback {
    void sendReply(int postId, String replyContent);

    void deletePost(int postId);

    void deleteReply(int replyId);

    void updateLikeCount(int postId);
}
