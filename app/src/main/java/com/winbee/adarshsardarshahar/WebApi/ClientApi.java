package com.winbee.adarshsardarshahar.WebApi;

import com.winbee.adarshsardarshahar.Models.AskDoubtQuestion;
import com.winbee.adarshsardarshahar.Models.AssignmentToSubmit;
import com.winbee.adarshsardarshahar.Models.AttendenceModel;
import com.winbee.adarshsardarshahar.Models.BannerModel;
import com.winbee.adarshsardarshahar.Models.ForgetMobile;
import com.winbee.adarshsardarshahar.Models.InstructionsModel;
import com.winbee.adarshsardarshahar.Models.LiveClass;
import com.winbee.adarshsardarshahar.Models.LogOut;
import com.winbee.adarshsardarshahar.Models.McqAskedQuestionModel;
import com.winbee.adarshsardarshahar.Models.McqQuestionModel;
import com.winbee.adarshsardarshahar.Models.McqQuestionSolutionModel;
import com.winbee.adarshsardarshahar.Models.McqSolutionModel;
import com.winbee.adarshsardarshahar.Models.NewDoubtQuestion;
import com.winbee.adarshsardarshahar.Models.NotificationModel;
import com.winbee.adarshsardarshahar.Models.OtpVerify;
import com.winbee.adarshsardarshahar.Models.PurchasedMainModel;
import com.winbee.adarshsardarshahar.Models.RefCode;
import com.winbee.adarshsardarshahar.Models.RefUser;
import com.winbee.adarshsardarshahar.Models.ResendOtp;
import com.winbee.adarshsardarshahar.Models.ResetPassword;
import com.winbee.adarshsardarshahar.Models.ResultModel;
import com.winbee.adarshsardarshahar.Models.SIACDetailsMainModel;
import com.winbee.adarshsardarshahar.Models.SIADMainModel;
import com.winbee.adarshsardarshahar.Models.SIADSolutionMainModel;
import com.winbee.adarshsardarshahar.Models.SectionDetailsMainModel;
import com.winbee.adarshsardarshahar.Models.SemesterName;
import com.winbee.adarshsardarshahar.Models.SolutionDoubtQuestion;
import com.winbee.adarshsardarshahar.Models.SolutionQuestion;
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
import com.winbee.adarshsardarshahar.NewModels.AskDoubtContent;
import com.winbee.adarshsardarshahar.NewModels.Attendence;
import com.winbee.adarshsardarshahar.NewModels.LiveClassContent;
import com.winbee.adarshsardarshahar.NewModels.SubjectContent;
import com.winbee.adarshsardarshahar.NewModels.TestTopRanker;
import com.winbee.adarshsardarshahar.NewModels.TopicContent;

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
    //login
    @POST("fetch_user_cover_information.php")
    Call<RefCode> refCodeSignIn(
            @Query("SubURL") int SubURL,
            @Query("username") String username,
            @Query("password") String password,
            @Query("refcode") String refcode,
            @Query("IMEI") String IMEI
    );

    //logout
    @POST("fetch_user_cover_information.php")
    Call<LogOut> refCodeLogout(
            @Query("SubURL") int SubURL,//3
            @Query("username") String username,
            @Query("password") String password,
            @Query("refcode") String refcode,
            @Query("IMEI") String IMEI
    );

    //force login
    @POST("fetch_user_cover_information.php")
    Call<RefCode> refCodeForceLogout(
            @Query("SubURL") int SubURL,//4
            @Query("username") String username,
            @Query("password") String password,
            @Query("refcode") String refcode,
            @Query("IMEI") String IMEI
    );
    //send otp
    @POST("send-otp.php")
    Call<ResendOtp> getResendOtp(
            @Query("username") String username,
            @Query("SubURL") int SubURL
    );



    //user registration
    @POST("user_registration_information.php")
    Call<RefUser> refUserSignIn(
            @Query("SubURL") int SubURL,
            @Query("name") String name,
            @Query("father_name") String father_name,
            @Query("class_for_reg ") String class_for_reg,
            @Query("email") String email,
            @Query("mobile") String mobile,
            @Query("refcode") String refcode,
            @Query("state") String state,
            @Query("district") String district,
            @Query("password") String password
    );
    //send otp for forget password
    @POST("send-otp.php")
    Call<ForgetMobile> getForgetMobile(
            @Query("SubURL") int SubURL,
            @Query("username") String username
    );

    //for reseting the password
    @POST("reset-password.php")
    Call<ResetPassword> getResetPassword(
            @Query("SubURL") int SubURL,
            @Query("username") String username,
            @Query("otp") String otp,
            @Query("new_password") String new_password
    );

    //for otp verfication
    @POST("verify-otp.php")
    Call<OtpVerify> getOtpVerify(
            @Query("SubURL") int SubURL,
            @Query("username") String username,
            @Query("otp") String otp
    );


    //get all live class details
    @POST("fetch_live_classes.php")
    Call<LiveClassContent> getLive(
            @Query("SubURL") int SubURL,
            @Query("ORG_ID") String ORG_ID,
            @Query("USER_ID") String USER_ID,
            @Query("DEVICE_ID") String DEVICE_ID
    );

    // fetch course according to user
    @POST("fetch_purchased_bucket_information.php")
    Call<PurchasedMainModel> getCourseById(
            @Query("SubURL") int SubURL,
            @Query("USER_ID") String USER_ID,
            @Query("ORG_ID") String ORG_ID,
            @Query("DEVICE_ID") String DEVICE_ID
    );
    // fetch subject related to course
    @POST("fetch_bucket_cover_information.php")
    Call<SubjectContent> getCourseSubject(
            @Query("SubURL") int SubURL,
            @Query("ORG_ID") String ORG_ID,
            @Query("PARENT_ID") String PARENT_ID,
            @Query("USER_ID") String USER_ID,
            @Query("DEVICE_ID") String DEVICE_ID
    );

    // fetch topic related to subject
    @POST("fetch_bucket_cover_information.php")
    Call<TopicContent> getTopic(
            @Query("SubURL") int SubURL,
            @Query("ORG_ID") String ORG_ID,
            @Query("PARENT_ID") String PARENT_ID,
            @Query("USER_ID") String USER_ID,
            @Query("DEVICE_ID") String DEVICE_ID
    );

    // submitting the doubt
    @FormUrlEncoded
    @POST("submit-doubt.php")
    Call<UrlNewQuestion> getNewQuestion(
            @Field("title") String title,
            @Field("question") String question,
            @Field("userid") String userid,
            @Field("DocumentId") String DocumentId
    );


    // fetch all doubt
    @POST("doubt-session.php")
    Call<ArrayList<UrlQuestion>> getQuestion(
            @Query("DocumentId") String DocumentId
    );

    //fetch solution of one doubt
    @POST("doubt-session.php")
    Call<ArrayList<UrlSolution>> getSolution(
            @Query("DocumentId") String DocumentId,
            @Query("filename") String filename
    );

    //submitting the solution of any doubt
    @FormUrlEncoded
    @POST("submit-doubt.php")
    Call<UrlQuestionSolution> getQuestionSolution(
            @Field("filename") String filename,
            @Field("answer") String answer,
            @Field("DocumentId") String DocumentId,
            @Field("userid") String userid
    );

    //fetch all assignment data
    @POST("fetch_assignment_data.php")
    Call<AssignmentToSubmit> getAllAssignment(
            @Query("org_id") String org_id,
            @Query("user_id") String user_id,
            @Query("device_id") String device_id
    );

    //fetch all submitted assignment by user
    @POST("fetch_assignment_submitted_student.php")
    Call<SubmittedAssignment> getSubmitedAssignment(
            @Query("org_id") String org_id,
            @Query("user_id") String user_id,
            @Query("device_id") String device_id
    );



    //submit assignment
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




    //fetch all class test series
    @POST("fetch-section-details.php")
    Call<SectionDetailsMainModel> fetchSectionDetails(
            @Query("org_code") String org_code,
            @Query("auth_code") String auth_code,
            @Query("user_id") String user_id
    );

    // fetch all test seires related to class
    @POST("fetch-section-individual-assessment-cover-details.php")
    @FormUrlEncoded
    Call<SIACDetailsMainModel> fetchSIACDetails(
            @Field("org_code") String org_code,
            @Field("auth_code") String auth_code,
            @Field("bucket_code") String bucket_code,
            @Field("user_code") String user_code
    );

    //fetch all question of any test
    @POST("fetch-section-individual-assessment-data.php")
    @FormUrlEncoded
    Call<SIADMainModel> fetchSIADDATA(
            @Field("org_code") String org_code,
            @Field("auth_code") String auth_code,
            @Field("bucket_code") String bucket_code,
            @Field("paper_code") String paper_code
    );

    // submitting the test
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

    // for starting the test
    @POST("Start-Exam-Paper.php")
    @FormUrlEncoded
    Call<StartTestModel> getStartTest(
            @Field("OrgID") String OrgID,
            @Field("UserID") String UserID,
            @Field("PaperID") String PaperID,
            @Field("ExamStatus") String ExamStatus,
            @Field("Read_Instruction") String Read_Instruction
    );

    // for showing the result
    @POST("view-result.php")
    @FormUrlEncoded
    Call<ViewResult> viewResult(
            @Field("OrgID") String OrgID,
            @Field("PaperID") String PaperID,
            @Field("UserID") String UserID
    );


    // for updateing the whatapp number
    @FormUrlEncoded
    @POST("whatsapp-update.php")
    Call<WhatsAppData> getWhatsApp(
            @Field("user_id") String user_id,
            @Field("org_id") String org_id,
            @Field("whatsapp_mobile") String whatsapp_mobile,
            @Field("Enable_WhatsApp") String Enable_WhatsApp
    );

    // sending the attendence of user
    @POST("record-attendence.php")
    Call<Attendence> fetchAttendence(
            @Query("user_id") String user_id,
            @Query("DEVICE_ID") String DEVICE_ID
    );

    // fetch all image for banner
    @POST("fetch-cover-banner.php")
    Call<ArrayList<BannerModel>> getBanner(
            @Query("org_id") String org_id
    );


    // fetch user notification for in-app
    @POST("fetch-user-notification.php")
    Call<ArrayList<NotificationModel>> getNotification(
            @Query("SubURL") int SubURL,
            @Query("ORG_ID") String ORG_ID,
            @Query("USER_ID") String USER_ID
    );

    // fetch instruction for test
    @POST("fetch-exam-instruction.php")
    @FormUrlEncoded
    Call<InstructionsModel> getInstruction(
            @Field("PaperID") String PaperID
    );

    //submit doubt
    @FormUrlEncoded
    @POST("ask-doubt.php")
    Call<AskDoubtContent> getNewDoubt(
            @Field("title") String title,
            @Field("question") String question,
            @Field("userid") String userid,
            @Field("device_id") String device_id
    );


    // get all doubt
    @POST("beta-doubt-storage.php")
    Call<ArrayList<AskDoubtQuestion>> getQuestion();

    //
    @FormUrlEncoded
    @POST("ask-doubt.php")
    Call<SolutionDoubtQuestion> getNewSolution(
            @Field("userid") String userid,
            @Field("filename") String filename,
            @Field("answer") String answer
    );

    @POST("doubt-storage.php")
    Call<ArrayList<SolutionQuestion>> getSolution(
            @Query("filename") String filename
    );

    @POST("insert_mcq.php")
    Call<McqQuestionModel> mcqQuestionYes(
            @Query("UserId") String UserId,
            @Query("Question") String Question,
            @Query("QuestionTitle") String QuestionTitle,
            @Query("Opt1") String Opt1,
            @Query("Opt2") String Opt2,
            @Query("Opt3") String Opt3,
            @Query("Opt4") String Opt4,
            @Query("SubURL") int SubURL,
            @Query("SolutionFlag") int SolutionFlag,
            @Query("Solution") String Solution
    );


    @POST("insert_mcq.php")
    Call<McqQuestionModel> mcqQuestionNo(
            @Query("UserId") String UserId,
            @Query("Question") String Question,
            @Query("QuestionTitle") String QuestionTitle,
            @Query("Opt1") String Opt1,
            @Query("Opt2") String Opt2,
            @Query("Opt3") String Opt3,
            @Query("Opt4") String Opt4,
            @Query("SubURL") int SubURL,
            @Query("SolutionFlag") int SolutionFlag,
            @Query("Solution") String Solution
    );

    //submit the mcq question
    @POST("insert_mcq.php")
    Call<McqAskedQuestionModel> mcqAskedQuestion(
            @Query("UserId") String UserId,
            @Query("SubURL") int SubURL
    );


    //fetch all the solution
    @POST("insert_mcq.php")
    Call<McqQuestionSolutionModel> getMcqSolution(
            @Query("UserId") String UserId,
            @Query("SubURL") int SubURL,
            @Query("Solution") String Solution,
            @Query("QuestionId") String QuestionId
    );

    // solution for mcq question
    @POST("view-MCQ-data.php")
    Call<ArrayList<McqSolutionModel>> getMcqQuestionSolution(
            @Query("question_id") String question_id,
            @Query("user_id") String user_id,
            @Query("user_name") String user_name

            //user_id,user_name
    );

    //fetch solution for given solution
    @POST("view-solutions.php")
    @FormUrlEncoded
    Call<SIADSolutionMainModel> getTestSolution(
            @Field("OrgID") String OrgID,
            @Field("paper_code") String paper_code,
            @Field("UserID") String UserID
    );

    // fetch top ranker for test
    @POST("fetch-top-scorers.php")
    @FormUrlEncoded
    Call<TestTopRanker> fetchTopRanker(
            @Field("PaperID") String PaperID,
            @Field("UserID") String UserID
    );

}
