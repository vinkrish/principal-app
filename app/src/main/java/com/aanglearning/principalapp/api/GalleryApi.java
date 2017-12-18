package com.aanglearning.principalapp.api;

import com.aanglearning.principalapp.model.Album;
import com.aanglearning.principalapp.model.AlbumImage;
import com.aanglearning.principalapp.model.DeletedAlbum;
import com.aanglearning.principalapp.model.DeletedAlbumImage;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Vinay on 29-10-2017.
 */

public interface GalleryApi {

    @POST("album/new")
    Call<Album> saveAlbum(@Body Album album);

    @PUT("album")
    Call<Void> updateAlbum(@Body Album album);

    @GET("album/{albumId}")
    Call<Album> getAlbum(@Path("albumId") long albumId);

    @GET("album/{id}/school/{schoolId}")
    Call<List<Album>> getAlbumAboveId(@Path("schoolId") long schoolId,
                                      @Path("id") long id);

    @GET("album/school/{schoolId}")
    Call<List<Album>> getAlbums(@Path("schoolId") long schoolId);

    @GET("album/{id}/class/{classId}")
    Call<List<Album>> getClassAlbumAboveId(@Path("classId") long classId,
                                      @Path("id") long id);

    @GET("album/class/{classId}")
    Call<List<Album>> getClassAlbums(@Path("classId") long classId);

    @GET("album/{id}/section/{sectionId}")
    Call<List<Album>> getSecAlbumAboveId(@Path("sectionId") long sectionId,
                                             @Path("id") long id);

    @GET("album/section/{sectionId}")
    Call<List<Album>> getSecAlbums(@Path("sectionId") long sectionId);

    @POST("deletedalbum/new")
    Call<DeletedAlbum> deleteAlbum(@Body DeletedAlbum deletedAlbum);

    @GET("deletedalbum/{id}/school/{schoolId}")
    Call<List<DeletedAlbum>> getDeletedAlbumsAboveId(@Path("schoolId") long schoolId,
                                                     @Path("id") long id);

    @GET("deletedalbum/school/{schoolId}")
    Call<List<DeletedAlbum>> getDeletedAlbums(@Path("schoolId") long schoolId);

    @GET("deletedalbum/{id}/class/{classId}")
    Call<List<DeletedAlbum>> getClassDelAlbAboveId(@Path("classId") long classId,
                                                   @Path("id") long id);

    @GET("deletedalbum/class/{classId}")
    Call<List<DeletedAlbum>> getClassDelAlb(@Path("classId") long classId);

    @GET("deletedalbum/{id}/section/{sectionId}")
    Call<List<DeletedAlbum>> getSecDelAlbAboveId(@Path("sectionId") long sectionId,
                                                     @Path("id") long id);

    @GET("deletedalbum/section/{sectionId}")
    Call<List<DeletedAlbum>> getSecDelAlb(@Path("sectionId") long sectionId);

    @POST("ai")
    Call<Void> saveAlbumImages(@Body List<AlbumImage> albumImages);

    @GET("ai/{id}/album/{albumId}")
    Call<ArrayList<AlbumImage>> getAlbumImagesAboveId(@Path("albumId") long albumId,
                                                      @Path("id") long id);

    @GET("ai/album/{albumId}")
    Call<ArrayList<AlbumImage>> getAlbumImages(@Path("albumId") long albumId);

    @POST("deletedai")
    Call<Void> deleteAlbumImages(@Body List<DeletedAlbumImage> deletedAlbumImages);

    @GET("deletedai/{id}/album/{albumId}")
    Call<List<DeletedAlbumImage>> getDeletedAlbumImagesAboveId(@Path("albumId") long albumId,
                                                               @Path("id") long id);

    @GET("deletedai/album/{albumId}")
    Call<List<DeletedAlbumImage>> getDeletedAlbumImages(@Path("albumId") long albumId);

}
