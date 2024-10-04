package Data.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import Data.Models.ChatModel;
import Data.Models.MessageModel;

public class ChatWithMessages {
    @Embedded
    public ChatModel chat;
    @Relation(parentColumn = "chatID", entityColumn = "chatOwnerID")
    public List<MessageModel> messages;
}
