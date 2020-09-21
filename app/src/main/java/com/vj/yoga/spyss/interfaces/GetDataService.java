package com.vj.yoga.spyss.interfaces;

import com.vj.yoga.spyss.model.BranchList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("branchLocation")
    Call<List<BranchList>> getBranchList();
}
