package com.midwayideas.webservice;

import com.midwayideas.constants.AppConstants;
import com.midwayideas.webservice.request_objects.ExtendSubscriptionRequest;
import com.midwayideas.webservice.request_objects.OutletRequest;
import com.midwayideas.webservice.request_objects.RewardSubmitRequest;
import com.midwayideas.webservice.response_objects.DailySaleResponse;
import com.midwayideas.webservice.response_objects.FeedbackResponse;
import com.midwayideas.webservice.response_objects.QuestionResponse;
import com.midwayideas.webservice.response_objects.RewardResponse;
import com.midwayideas.webservice.response_objects.SaveServiceReponse;
import com.midwayideas.webservice.response_objects.SubscriptionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Admin on 3/16/2016.
 */
public interface RestEndpointInterface {
    @GET(AppConstants.FETCH_REWARDS)
    Call<List<RewardResponse>> fetchRewards(@Path("outletType") String outletType);

    @GET(AppConstants.FETCH_QUESTIONS)
    Call<List<QuestionResponse>> fetchQuestions(@Path("outletType") String outletType);

    @POST(AppConstants.REGISTER_OUTLET)
    Call<SaveServiceReponse> registerOutlet(@Body OutletRequest outletRequest);

    @POST(AppConstants.SAVE_REWARDS)
    Call<SaveServiceReponse> saveRewards(@Body RewardSubmitRequest rewardSubmitRequest);

    @GET(AppConstants.FETCH_FEEDBACK)
    Call<List<FeedbackResponse>> fetchFeedback(@Path("outletCode") String outletCode, @Path("fromDate") String fromDate, @Path("toDate") String toDate);

    @GET(AppConstants.FETCH_SALES_DATA)
    Call<List<DailySaleResponse>> fetchSalesData(@Path("outletCode") String outletCode, @Path("year") String year, @Path("month") String month);

    @GET(AppConstants.FETCH_SUBSCRIPTION)
    Call<SubscriptionResponse> fetchSubscription(@Path("outletCode") String outletCode);

    @POST(AppConstants.EXTEND_SUBSCRIPTION)
    Call<SaveServiceReponse> extendSubscription(@Body ExtendSubscriptionRequest extendSubscriptionRequest);

    @GET(AppConstants.SAVE_GCM_TOKEN)
    Call<SaveServiceReponse> saveGCMToken(@Path("outletCode") String outletCode, @Path("token") String token);

}
