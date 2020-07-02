package com.winbee.adarshsardarshahar.WebApi;

import com.winbee.adarshsardarshahar.Models.AssignmentToSubmit;
import com.winbee.adarshsardarshahar.Models.AttendenceModel;
import com.winbee.adarshsardarshahar.Models.BannerModel;
import com.winbee.adarshsardarshahar.Models.ForgetMobile;
import com.winbee.adarshsardarshahar.Models.LiveClass;
import com.winbee.adarshsardarshahar.Models.OtpVerify;
import com.winbee.adarshsardarshahar.Models.PurchasedMainModel;
import com.winbee.adarshsardarshahar.Models.RefCode;
import com.winbee.adarshsardarshahar.Models.RefUser;
import com.winbee.adarshsardarshahar.Models.ResetPassword;
import com.winbee.adarshsardarshahar.Models.ResultModel;
import com.winbee.adarshsardarshahar.Models.SIACDetailsMainModel;
import com.winbee.adarshsardarshahar.Models.SIADMainModel;
import com.winbee.adarshsardarshahar.Models.SectionDetailsMainModel;
import com.winbee.adarshsardarshahar.Models.SemesterName;
import com.winbee.adarshsardarshahar.Models.StartTestModel;
import com.winbee.adarshsardarshahar.Models.SubmitAssignment;
import com.winbee.adarshsardarshahar.Models.SubmittedAssignment;
import com.winbee.adarshsardarshahar.Models.UrlName;
import com.winbee.adarshsardarshahar.Models.UrlNewQuestion;
import com.winbee.adarshsardarshahar.Models.UrlQuestion;
import com.winbee.adarshsardarshahar.Models.UrlQuestionSolution;
import com.winbee.adarshsardarshahar.Models.UrlSolution;
import com.winbee.adarshsardarshahar.Models.ViewResult;
import com.winbee.adarshsardarshahar.Models.WhatsAppData;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ClientApi {
    @POST("fetch_user_cover_information.php")
    Call<RefCode> refCodeSignIn(
            @Query("SubURL") int SubURL,
            @Query("username") String username,
            @Query("password") String password,
            @Query("refcode") String refcode
    );

    @POST("user_registration_information.php")
    Call<RefUser> refUserSignIn(
            @Query("SubURL") int SubURL,
            @Query("name") String name,
            @Query("email") String email,
            @Query("mobile") String mobile,
            @Query("refcode") String refcode,
            @Query("password") String password);
    @POST("send-otp.php")
    Call<ForgetMobile> getForgetMobile(
            @Query("SubURL") int SubURL,
            @Query("username") String username
    );


    @POST("reset-password.php")
    Call<ResetPassword> getResetPassword(
            @Query("SubURL") int SubURL,
            @Query("username") String username,
            @Query("otp") String otp,
            @Query("new_password") String new_password
    );

    @POST("verify-otp.php")
    Call<OtpVerify> getOtpVerify(
            @Query("SubURL") int SubURL,
            @Query("username") String username,
            @Query("otp") String otp
    );


    @POST("fetch_live_classes.php")
    Call<ArrayList<LiveClass>> getLive(
            @Query("SubURL") int SubURL,
            @Query("ORG_ID") String ORG_ID,
            @Query("USER_ID") String USER_ID
    );

    @POST("fetch_purchased_bucket_information.php")
    Call<PurchasedMainModel> getCourseById(
            @Query("SubURL") int SubURL,
            @Query("USER_ID") String USER_ID,
            @Query("ORG_ID") String ORG_ID
    );

    @POST("fetch_bucket_cover_information.php")
    Call<ArrayList<SemesterName>> getCourseSubject(
            @Query("SubURL") int SubURL,
            @Query("ORG_ID") String ORG_ID,
            @Query("PARENT_ID") String PARENT_ID
    );

    @POST("fetch_bucket_cover_information.php")
    Call<ArrayList<UrlName>> getTopic(
            @Query("SubURL") int SubURL,
            @Query("ORG_ID") String ORG_ID,
            @Query("PARENT_ID") String PARENT_ID
    );
    @FormUrlEncoded
    @POST("submit-doubt.php")
    Call<UrlNewQuestion> getNewQuestion(
            @Field("title") String title,
            @Field("question") String question,
            @Field("userid") String userid,
            @Field("DocumentId") String DocumentId
    );


    @POST("doubt-session.php")
    Call<ArrayList<UrlQuestion>> getQuestion(
            @Query("DocumentId") String DocumentId
    );

    @POST("doubt-session.php")
    Call<ArrayList<UrlSolution>> getSolution(
            @Query("DocumentId") String DocumentId,
            @Query("filename") String filename
    );

    @FormUrlEncoded
    @POST("submit-doubt.php")
    Call<UrlQuestionSolution> getQuestionSolution(
            @Field("filename") String filename,
            @Field("answer") String answer,
            @Field("DocumentId") String DocumentId,
            @Field("userid") String userid
    );

    @POST("fetch_assignment_data.php")
    Call<AssignmentToSubmit> getAllAssignment(
            @Query("org_id") String org_id,
            @Query("user_id") String user_id
    );

    @POST("fetch_assignment_submitted_student.php")
    Call<SubmittedAssignment> getSubmitedAssignment(
            @Query("org_id") String org_id,
            @Query("user_id") String user_id
    );



    @POST("insert_assignment_data.php")
    @FormUrlEncoded
    Call<SubmitAssignment> getSubmitAssignment(
            @Field("org_id") String org_id,
            @Field("user_id") String user_id,
            @Field("course_id") String course_id,
            @Field("assignment_id") String assignment_id,
            @Field("attachment") String attachment,
            @Field("description") String description
    );




    @POST("fetch-section-details.php")
    Call<SectionDetailsMainModel> fetchSectionDetails(
            @Query("org_code") String org_code,
            @Query("auth_code") String auth_code,
            @Query("user_id") String user_id
    );

    @POST("fetch-section-individual-assessment-cover-details.php")
    @FormUrlEncoded
    Call<SIACDetailsMainModel> fetchSIACDetails(
            @Field("org_code") String org_code,
            @Field("auth_code") String auth_code,
            @Field("bucket_code") String bucket_code
    );

    @POST("fetch-section-individual-assessment-data.php")
    @FormUrlEncoded
    Call<SIADMainModel> fetchSIADDATA(
            @Field("org_code") String org_code,
            @Field("auth_code") String auth_code,
            @Field("bucket_code") String bucket_code,
            @Field("paper_code") String paper_code
    );

    @POST("Submit-Exam-Paper.php")
    @FormUrlEncoded
    Call<ResultModel> submitResponse(
            @Field("CoachingID") String CoachingID,
            @Field("PaperID") String PaperID,
            @Field("UserID") String UserID,
            @Field("Response") JSONArray jsonArray,
            @Field("Staticstics") String Staticstics,
            @Field("Submit_Exam_Paper") boolean Submit_Exam_Paper
    );
    @POST("Start-Exam-Paper.php")
    @FormUrlEncoded
    Call<StartTestModel> getStartTest(
            @Field("OrgID") String OrgID,
            @Field("UserID") String UserID,
            @Field("PaperID") String PaperID,
            @Field("ExamStatus") String ExamStatus,
            @Field("Read_Instruction") String Read_Instruction
    );

    @POST("view-result.php")
    @FormUrlEncoded
    Call<ViewResult> viewResult(
            @Field("OrgID") String OrgID,
            @Field("PaperID") String PaperID,
            @Field("UserID") String UserID
    );


    @FormUrlEncoded
    @POST("whatsapp-update.php")
    Call<WhatsAppData> getWhatsApp(
            @Field("user_id") String user_id,
            @Field("org_id") String org_id,
            @Field("whatsapp_mobile") String whatsapp_mobile,
            @Field("Enable_WhatsApp") String Enable_WhatsApp
    );

    @POST("record-attendence.php")
    Call<AttendenceModel> fetchAttendence(
            @Query("user_id") String user_id
    );

    @POST("fetch-cover-banner.php")
    Call<ArrayList<BannerModel>> getBanner(
            @Query("org_id") String org_id
    );


}
