package com.aanglearning.principalapp.api;

import com.aanglearning.principalapp.attendance.AttendanceSet;
import com.aanglearning.principalapp.model.AppVersion;
import com.aanglearning.principalapp.model.Chat;
import com.aanglearning.principalapp.model.Clas;
import com.aanglearning.principalapp.model.DeletedGroup;
import com.aanglearning.principalapp.model.DeletedMessage;
import com.aanglearning.principalapp.model.Evnt;
import com.aanglearning.principalapp.model.GroupUsers;
import com.aanglearning.principalapp.model.Groups;
import com.aanglearning.principalapp.model.Homework;
import com.aanglearning.principalapp.model.Message;
import com.aanglearning.principalapp.model.MessageRecipient;
import com.aanglearning.principalapp.model.Section;
import com.aanglearning.principalapp.model.Student;
import com.aanglearning.principalapp.model.Timetable;
import com.aanglearning.principalapp.model.UserGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Vinay on 29-03-2017.
 */

public interface PrincipalApi {

    @GET("appversion/{versionId}/{appName}")
    Call<AppVersion> getAppVersion(@Path("versionId") int versionId,
                                   @Path("appName") String appName);

    @GET("class/school/{schoolId}")
    Call<List<Clas>> getClassList(@Path("schoolId") long schoolId);

    @GET("class/teacher/{teacherId}")
    Call<List<Clas>> getSectionTeacherClasses(@Path("teacherId") long teacherId);

    @GET("section/class/{classId}")
    Call<List<Section>> getSectionList(@Path("classId") long classId);

    @GET("section/class/{classId}/teacher/{teacherId}")
    Call<List<Section>> getSectionTeacherSections(@Path("classId") long classId,
                                                  @Path("teacherId") long teacherId);

    @GET("student/section/{sectionId}")
    Call<List<Student>> getStudents(@Path("sectionId") long sectionId);

    //Groups and MessageGroup API

    @POST("groups")
    Call<Groups> saveGroup(@Body Groups groups);

    @GET("groups/principal/{schoolId}/group/{id}")
    Call<List<Groups>> getGroupsAboveId(@Path("schoolId") long schoolId,
                                        @Path("id") long id);

    @GET("groups/principal/teacher/{teacherId}/group/{id}")
    Call<List<Groups>> getPrincipalGroupsAboveId(@Path("teacherId") long teacherId,
                                                 @Path("id") long id);

    @GET("groups/principal/{schoolId}")
    Call<List<Groups>> getGroups(@Path("schoolId") long schoolId);

    @GET("groups/principal/teacher/{id}")
    Call<List<Groups>> getPrincipalGroups(@Path("id") long id);

    @GET("groups/{groupId}")
    Call<Void> deleteGroup(@Path("groupId") long groupId);

    @POST("deletedgroup")
    Call<DeletedGroup> deleteGroup(@Body DeletedGroup deletedGroup);

    @GET("deletedgroup/{id}/school/{schoolId}")
    Call<List<DeletedGroup>> getDeletedGroupsAboveId(@Path("schoolId") long schoolId,
                                                          @Path("id") long id);

    @GET("deletedgroup/school/{schoolId}")
    Call<List<DeletedGroup>> getDeletedGroups(@Path("schoolId") long schoolId);

    @POST("message")
    Call<Message> saveMessage(@Body Message message);

    @GET("message/group/{groupId}/messagesUp/{messageId}")
    Call<ArrayList<Message>> getGroupMessagesAboveId(@Path("groupId") long groupId,
                                                     @Path("messageId") long messageId);

    @GET("message/group/{groupId}")
    Call<ArrayList<Message>> getGroupMessages(@Path("groupId") long groupId);

    @GET("message/group/{groupId}/message/{messageId}")
    Call<ArrayList<Message>> getGroupMessagesFromId(@Path("groupId") long groupId,
                                                    @Path("messageId") long messageId);

    @POST("deletedmessage")
    Call<DeletedMessage> deleteMessage(@Body DeletedMessage deletedMessage);

    @GET("deletedmessage/{id}/group/{groupId}")
    Call<ArrayList<DeletedMessage>> getDeletedMessagesAboveId(@Path("groupId") long groupId,
                                                              @Path("id") long id);

    @GET("deletedmessage/group/{groupId}")
    Call<ArrayList<DeletedMessage>> getDeletedMessages(@Path("groupId") long groupId);

    @GET("messagerecipient/{groupId}/{groupMessageId}")
    Call<ArrayList<MessageRecipient>> getMessageRecipients(@Path("groupId") long groupId,
                                                      @Path("groupMessageId") long groupMessageId);

    @GET("messagerecipient/school/{groupId}/{groupMessageId}")
    Call<ArrayList<MessageRecipient>> getSchoolRecipients(@Path("groupId") long groupId,
                                                          @Path("groupMessageId") long groupMessageId);

    @GET("messagerecipient/school/{groupId}/{groupMessageId}/{id}")
    Call<ArrayList<MessageRecipient>> getSchoolRecipientsFromId(@Path("groupId") long groupId,
                                                                @Path("groupMessageId") long groupMessageId,
                                                                @Path("id") long id);

    //UserGroup API

    @GET("usergroup/groupusers/groups/{groupId}")
    Call<GroupUsers> getUserGroup(@Path("groupId") long groupId);

    @POST("usergroup")
    Call<Void> saveUserGroupList(@Body ArrayList<UserGroup> userGroupList);

    @POST("usergroup/delete")
    Call<Void> deleteUserGroupUsers(@Body ArrayList<UserGroup> userGroups);

    //Chat API
    @POST("chat")
    Call<Chat> saveChat(@Body Chat chat);

    @GET("chat/principal/{id}")
    Call<List<Chat>> getChats(@Path("id") long id);

    @DELETE("chat/{chatId}")
    Call<Void> deleteChat(@Path("chatId") long chatId);

    @GET("message/{senderRole}/{senderId}/{recipientRole}/{recipientId}/messagesUp/{messageId}")
    Call<ArrayList<Message>> getChatMessagesAboveId(@Path("senderRole") String senderRole,
                                                    @Path("senderId") long senderId,
                                                    @Path("recipientRole") String recipientRole,
                                                    @Path("recipientId") long recipientId,
                                                    @Path("messageId") long messageId);

    @GET("message/{senderRole}/{senderId}/{recipientRole}/{recipientId}")
    Call<ArrayList<Message>> getChatMessages(@Path("senderRole") String senderRole,
                                             @Path("senderId") long senderId,
                                             @Path("recipientRole") String recipientRole,
                                             @Path("recipientId") long recipientId);

    @GET("message/{senderRole}/{senderId}/{recipientRole}/{recipientId}/message/{messageId}")
    Call<ArrayList<Message>> getChatMessagesFromId(@Path("senderRole") String senderRole,
                                                   @Path("senderId") long senderId,
                                                   @Path("recipientRole") String recipientRole,
                                                   @Path("recipientId") long recipientId,
                                                   @Path("messageId") long messageId);

    //Attendance API

    @GET("timetable/section/{sectionId}/day/{dayOfWeek}")
    Call<List<Timetable>> getTimetable(@Path("sectionId") long sectionId,
                                       @Path("dayOfWeek") String dayOfWeek);

    @GET("app/attendance/section/{sectionId}/date/{dateAttendance}/session/{session}")
    Call<AttendanceSet> getAttendanceSet(@Path("sectionId") long sectionId,
                                         @Path("dateAttendance") String dateAttendance,
                                         @Path("session") int session);

    //Homework API

    @GET("homework/section/{sectionId}/date/{homeworkDate}")
    Call<List<Homework>> getHomework(@Path("sectionId") long sectionId,
                                     @Path("homeworkDate") String homeworkDate);

    //Timetable API

    @GET("app/timetable/section/{sectionId}")
    Call<List<Timetable>> getTimetable(@Path("sectionId") long sectionId);

    //Event API

    @GET("event/school/{schoolId}")
    Call<List<Evnt>> getEvents(@Path("schoolId") long schoolId);

}
