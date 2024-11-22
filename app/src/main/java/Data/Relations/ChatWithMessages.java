package Data.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import Data.Models.Chat;
import Data.Models.Message;

public class ChatWithMessages {
    @Embedded
    public Chat chat;
    @Relation(parentColumn = "chatID", entityColumn = "chatOwnerID")
    public List<Message> messages;
}
