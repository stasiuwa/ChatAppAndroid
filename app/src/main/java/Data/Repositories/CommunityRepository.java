package Data.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import Data.DAO.CommunityDAO;
import Data.Databases.CommunityDB;
import Data.Models.ChannelModel;
import Data.Models.ChatModel;
import Data.Models.CommunityModel;
import Data.Models.MessageModel;
import Data.Models.UserModel;
import Data.Relations.ChatWithMessages;
import Data.Relations.CommunityWithChannels;
import Data.Relations.CommunityWithChats;

public class CommunityRepository {
    private CommunityDAO dao;
    private LiveData<List<CommunityModel>> allCommunities;
    private LiveData<List<CommunityWithChannels>> allChannels;
    private LiveData<List<CommunityWithChats>> allChats;
    private LiveData<List<UserModel>> allUsers;

    public CommunityRepository(Application application){
        CommunityDB mCommunityDB = CommunityDB.getDataBase(application);
        dao = mCommunityDB.communityDAO();
        allCommunities = dao.getAllCommunities();
        allChannels = dao.getAllChannels();
        allChats = dao.getAllChats();

    }

//    Communities
    public void addCommunity(CommunityModel community){
        CommunityDB.databaseWriteExecutor.execute(() -> {
            dao.addCommunity(community);
        });
    }
    public LiveData<List<CommunityModel>> getCommunities() {
        return allCommunities;
    }
    public void deleteCommunity(CommunityModel community) {
        CommunityDB.databaseWriteExecutor.execute(() -> {
            dao.deleteCommunity(community);
        });
    }
    public LiveData<CommunityModel> getCommunity(long id){
        return dao.getCommunity(id);
    }

    public LiveData<List<CommunityWithChannels>> getChannels(long id) {
        return dao.getChannels(id);
    }
    public LiveData<List<CommunityWithChats>> getChats(long id){
        return dao.getChats(id);
    }
    public LiveData<List<CommunityWithChats>> getAllChats(){
        return allChats;
    }

//    Channels
    public void addChannel(ChannelModel channel) {
        CommunityDB.databaseWriteExecutor.execute(() -> {
            dao.addChannel(channel);
        });
    }
    public void deleteChannel(ChannelModel channel) {
        CommunityDB.databaseWriteExecutor.execute(() -> {
            dao.deleteChannel(channel);
        });
    }

//    Chats
    public void addChat(ChatModel chat) {
        CommunityDB.databaseWriteExecutor.execute(() -> {
            dao.addChat(chat);
        });
    }
    public void deleteChat(ChatModel chat) {
        CommunityDB.databaseWriteExecutor.execute(() -> {
            dao.deleteChat(chat);
        });
    }

//    Messages
    public void addMessage(MessageModel message){
        CommunityDB.databaseWriteExecutor.execute(() -> {
            dao.addMessage(message);
        });
    }
    public LiveData<List<ChatWithMessages>> getMessages(long chatId){
        return dao.getMessages(chatId);
    }
//    TODO dokonczyc
    public void editMessage(MessageModel message){

    }

//    Users
    public void addUser(UserModel user) {
        CommunityDB.databaseWriteExecutor.execute(() -> {
            dao.addUser(user);
        });
    }
    public boolean readLoginData(String username) {
        return dao.readLoginData(username);
    }
    public LiveData<List<UserModel>> getAllUsers() {
        return allUsers;
    }
    public void deleteUser(UserModel user) {
        CommunityDB.databaseWriteExecutor.execute(() -> {
        });
    }
}
