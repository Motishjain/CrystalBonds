package com.midwayideas.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.midwayideas.crystalbonds.RewardSelectionActivity;
import com.midwayideas.database.DBHelper;
import com.midwayideas.database.Reward;
import com.midwayideas.database.SelectedReward;
import com.midwayideas.webservice.RestEndpointInterface;
import com.midwayideas.webservice.RetrofitSingleton;
import com.midwayideas.webservice.request_objects.RewardSubmitRequest;
import com.midwayideas.webservice.response_objects.SaveServiceReponse;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 4/14/2016.
 */
public class SaveRewardsTask extends AsyncTask<RewardSelectionActivity, Void, Void> {

    Dao<SelectedReward, Integer> selectedRewardDao;
    ProgressDialog progressDialog;
    OnTaskCompleted onTaskCompleted;

    public SaveRewardsTask(ProgressDialog progressDialog, OnTaskCompleted onTaskCompleted) {
        this.progressDialog = progressDialog;
        this.onTaskCompleted = onTaskCompleted;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(RewardSelectionActivity... input) {
        final RewardSelectionActivity activity = input[0];
        List<String> rewardIdList = new ArrayList<>();
        try {
            selectedRewardDao = OpenHelperManager.getHelper(activity, DBHelper.class).getCustomDao("SelectedReward");
            DeleteBuilder<SelectedReward,Integer> selectedRewardDeleteBuilder = selectedRewardDao.deleteBuilder();
            selectedRewardDeleteBuilder.where().eq("rewardCategory", activity.getRewardCategory());
            selectedRewardDeleteBuilder.delete();

            for(Reward reward:activity.getRewardsList()) {
                if(reward.isSelected()) {
                    SelectedReward selectedReward = new SelectedReward();
                    selectedReward.setReward(reward);
                    selectedReward.setRewardCategory(activity.getRewardCategory());
                    selectedReward.setRewardCost(reward.getCost());
                    selectedRewardDao.create(selectedReward);
                    rewardIdList.add(reward.getRewardId());
                }
            }
        }
        catch(SQLException e) {
            Log.e("RewardSelectionActivity", "Failed to fetch saved rewards");
        }

        RewardSubmitRequest rewardSubmitRequest = new RewardSubmitRequest();
        rewardSubmitRequest.setOutletCode(activity.getOutletCode());
        rewardSubmitRequest.setRewardCategory(activity.getRewardCategory());
        rewardSubmitRequest.setRewardIdList(rewardIdList);
        rewardSubmitRequest.setCreatedDate(new Date());

        RestEndpointInterface restEndpointInterface = RetrofitSingleton.newInstance();
        Call<SaveServiceReponse> saveRewardsCall = restEndpointInterface.saveRewards(rewardSubmitRequest);
        saveRewardsCall.enqueue(new Callback<SaveServiceReponse>() {
            @Override
            public void onResponse(Call<SaveServiceReponse> call, Response<SaveServiceReponse> response) {
                SaveServiceReponse saveServiceReponse = response.body();
                if (saveServiceReponse.isSuccess()) {
                    Log.e("Reward Configuration","Rewards saved successfully");
                }
                else {
                    Log.e("Reward Configuration","Unable to save rewards");
                }
            }

            @Override
            public void onFailure(Call<SaveServiceReponse> call, Throwable t) {
                Log.e("Reward Configuration","Unable to save rewards");
            }
        });
        progressDialog.dismiss();
        onTaskCompleted.onTaskCompleted();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }

    public interface OnTaskCompleted {
        void onTaskCompleted();
    }
}
