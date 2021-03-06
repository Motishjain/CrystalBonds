package com.midwayideas.crystalbonds;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.midwayideas.database.Reward;
import com.midwayideas.tasks.BuildSelectRewardFragmentsTask;
import com.midwayideas.tasks.SaveRewardsTask;
import com.midwayideas.view.CustomProgressDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import layout.SelectRewardsBoxFragment;

public class RewardSelectionActivity extends AppCompatActivity implements SelectRewardsBoxFragment.OnFragmentInteractionListener,SaveRewardsTask.OnTaskCompleted {

    List<Reward> rewardsList;
    Map<Integer, List<Reward>> levelRewardsMap;
    private ProgressDialog progressDialog;
    private String rewardCategory;
    private String outletCode;

    List<SelectRewardsBoxFragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_selection);
        setTitle("");
        progressDialog = CustomProgressDialog.createCustomProgressDialog(this);
        progressDialog.show();
        try {
            BuildSelectRewardFragmentsTask buildSelectRewardFragmentsTask = new BuildSelectRewardFragmentsTask(progressDialog);
            buildSelectRewardFragmentsTask.execute(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void rewardClicked(int level, int index, boolean checked) {
        List<Reward> levelRewards = levelRewardsMap.get(level);
        levelRewards.get(index).setSelected(checked);
    }

    @Override
    public void fragmentCreated(int level) {
    }

    public void saveSelectedRewards(View v) {
        progressDialog.setMessage("Saving Rewards...");
        progressDialog.show();
        SaveRewardsTask saveRewardsTask = new SaveRewardsTask(progressDialog,this);
        saveRewardsTask.execute(this);
    }

    public void close(View v) {
        finish();
    }

    public String getOutletCode() {
        return outletCode;
    }

    public void setOutletCode(String outletCode) {
        this.outletCode = outletCode;
    }

    public String getRewardCategory() {
        return rewardCategory;
    }

    public void setRewardCategory(String rewardCategory) {
        this.rewardCategory = rewardCategory;
    }

    public List<SelectRewardsBoxFragment> getFragmentList() {
        return fragmentList;
    }

    public List<Reward> getRewardsList() {
        return rewardsList;
    }

    public void setRewardsList(List<Reward> rewardsList) {
        this.rewardsList = rewardsList;
    }

    public Map<Integer, List<Reward>> getLevelRewardsMap() {
        return levelRewardsMap;
    }

    public void setLevelRewardsMap(Map<Integer, List<Reward>> levelRewardsMap) {
        this.levelRewardsMap = levelRewardsMap;
    }

    @Override
    public void onTaskCompleted() {
        Intent rewardsSaved = new Intent();
        rewardsSaved.putExtra("rewardsSelected", true);
        setResult(200, rewardsSaved);
        finish();
    }
}
