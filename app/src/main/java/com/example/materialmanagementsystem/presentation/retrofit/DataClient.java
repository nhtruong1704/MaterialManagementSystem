package com.example.materialmanagementsystem.presentation.retrofit;

import com.example.materialmanagementsystem.presentation.model.Admin;
import com.example.materialmanagementsystem.presentation.model.Material;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface DataClient {
//    //Student
//    @FormUrlEncoded
//    @POST("insert.php")
//    Call<String> InsertStudentData(@Field("StudentName") String StudentName,
//                                   @Field("StudentEmail") String StudentEmail,
//                                   @Field("StudentPassword") String StudentPassword,
//                                   @Field("StudentAvatar") String StudentAvatar);
//
//    @Multipart
//    @POST("uploadImage.php")
//    Call<String> UploadStudentPhoto(@Part MultipartBody.Part photo);
//
//    @FormUrlEncoded
//    @POST("login.php")
//    Call<List<Student>> LoginStudentData(@Field("StudentEmail") String StudentEmail,
//                                         @Field("StudentPassword") String StudentPassword);
//
//    @FormUrlEncoded
//    @POST("loginWithGoogleAcc.php")
//    Call<List<Student>> LoginStudentGoogleAccData(@Field("StudentEmail") String StudentEmail);
//
//    @FormUrlEncoded
//    @POST("update.php")
//    Call<String> UpdateStudentData(@Field("StudentId") String StudentId,
//                                   @Field("StudentNo") String StudentNo,
//                                   @Field("StudentName") String StudentName,
//                                   @Field("StudentDOB") String StudentDOB,
//                                   @Field("StudentClass") String StudentClass,
//                                   @Field("StudentPhone") String StudentPhone,
//                                   @Field("StudentEmail") String StudentEmail,
//                                   @Field("StudentAvatar") String StudentAvatar,
//                                   @Field("StudentGender") String StudentGender,
//                                   @Field("StudentCurrentAvatar") String StudentCurrentAvatar);
//
    @GET("delete.php")
    Call<String> DeleteMaterialData(@Query("MaterialId") String MaterialId);

    @GET("update.php")
    Call<String> UpdateMaterialData(@Query("MaterialId") String MaterialId, @Query("MaterialAmo") String MaterialAmo);
//
//    @FormUrlEncoded
//    @POST("changePassword.php")
//    Call<String> ChangePasswordStudentData(@Field("StudentId") String StudentId,
//                                           @Field("StudentNewPassword") String StudentNewPassword);
//
//    //Report
//    @FormUrlEncoded
//    @POST("report.php")
//    Call<String> ReportStudentData(@Field("StudentId") String StudentId,
//                                   @Field("StudentReport") String StudentReport);
//
    //Admin
    @FormUrlEncoded
    @POST("admin/login.php")
    Call<List<Admin>> LoginAdminData(@Field("AdminEmail") String AdminEmail,
                                     @Field("AdminPassword") String AdminPassword);

    @Multipart
    @POST("admin/uploadImage.php")
    Call<String> UploadAdminPhoto(@Part MultipartBody.Part photo);

    @FormUrlEncoded
    @POST("admin/update.php")
    Call<String> UpdateAdminData(@Field("AdminId") String AdminId,
                                 @Field("AdminEmail") String AdminEmail,
                                 @Field("AdminName") String AdminName,
                                 @Field("AdminAvatar") String AdminAvatar,
                                 @Field("AdminCurrentAvatar") String AdminCurrentAvatar);
//
//    @FormUrlEncoded
//    @POST("admin/forgotPassword.php")
//    Call<String> ForgotPasswordAdminData(@Field("AdminId") String AdminId,
//                                         @Field("AdminEmail") String AdminEmail,
//                                         @Field("AdminNewPassword") String AdminNewPassword);
//
    @GET("admin/delete.php")
    Call<String> DeleteAdminData(@Query("AdminId") String AdminId, @Query("AdminAvatar") String AdminAvatar);
//
    @FormUrlEncoded
    @POST("admin/changePassword.php")
    Call<String> ChangePasswordAdminData(@Field("AdminId") String AdminId,
                                         @Field("AdminNewPassword") String AdminNewPassword);

    //Admin Manager
    //Add Material
    @FormUrlEncoded
    @POST("admin/addMaterial.php")
    Call<String> AdminAddMaterialData(@Field("MaterialName") String MaterialName,
                                     @Field("MaterialNo") String MaterialNo,
                                     @Field("MaterialDis") String MaterialDis,
                                     @Field("MaterialPhone") String MaterialPhone,
                                     @Field("MaterialImDate") String MaterialImDate,
                                     @Field("MaterialAmo") String MaterialAmo,
                                     @Field("MaterialUnPri") String MaterialUnPri,
                                     @Field("MaterialTotalCost") String MaterialTotalCost);


    @FormUrlEncoded
    @POST("admin/exMaterial.php")
    Call<String> AdminExMaterialData(@Field("MaterialName") String MaterialName,
                                      @Field("MaterialNo") String MaterialNo,
                                      @Field("MaterialCusName") String MaterialCusName,
                                      @Field("MaterialCusPhone") String MaterialCusPhone,
                                      @Field("MaterialExDate") String MaterialExDate,
                                      @Field("MaterialAmo") String MaterialAmo,
                                      @Field("MaterialUnPri") String MaterialUnPri,
                                      @Field("MaterialTotalCost") String MaterialTotalCost);
//
    //View All
    @POST("admin/viewAllMat.php")
    Call<List<Material>> AdminViewAllMaterialData();
//
//    //Update
//    @FormUrlEncoded
//    @POST("admin/updateStudent.php")
//    Call<String> AdminUpdateStudentData(@Field("StudentId") String StudentId,
//                                        @Field("StudentNo") String StudentNo,
//                                        @Field("StudentName") String StudentName,
//                                        @Field("StudentDOB") String StudentDOB,
//                                        @Field("StudentClass") String StudentClass,
//                                        @Field("StudentPhone") String StudentPhone,
//                                        @Field("StudentEmail") String StudentEmail,
//                                        @Field("StudentAvatar") String StudentAvatar,
//                                        @Field("StudentGender") String StudentGender,
//                                        @Field("StudentPassword") String StudentPassword,
//                                        @Field("StudentActive") String StudentActive,
//                                        @Field("StudentCurrentAvatar") String StudentCurrentAvatar);
//
//    //Send Notice
//    @FormUrlEncoded
//    @POST("admin/sendNotice.php")
//    Call<String> AdminNoticeToStudentData(@Field("StudentNotice") String StudentNotice);
//
//    //Reply
//    @FormUrlEncoded
//    @POST("admin/replyStudent.php")
//    Call<String> AdminReplyStudentData(@Field("StudentId") String StudentId,
//                                       @Field("StudentReply") String StudentReply);
}
