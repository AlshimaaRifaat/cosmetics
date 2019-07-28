package com.cosmetics.cosmetics.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.cosmetics.cosmetics.model.DetailsProductSliderData;
import com.cosmetics.cosmetics.model.DetailsProductSliderResponse;
import com.cosmetics.cosmetics.model.LatestProductsData;
import com.cosmetics.cosmetics.model.LatestProductsResponse;
import com.cosmetics.cosmetics.remote.APIClient;
import com.cosmetics.cosmetics.remote.APIInterface;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsProductViewModel extends ViewModel
{
    Context context;
    private MutableLiveData<List<DetailsProductSliderData>> listProductSliderMutableLiveData;
    public LiveData<List<DetailsProductSliderData>> getDetailsProductSlider(String product_id, Context context) {
        if (listProductSliderMutableLiveData == null) {
            listProductSliderMutableLiveData = new MutableLiveData<List<DetailsProductSliderData>>();
            this.context=context;
            getDetailsProductSliderValues(product_id);

        }
        return listProductSliderMutableLiveData;

    }

    private void getDetailsProductSliderValues(String product_id) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("product_id", product_id);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<DetailsProductSliderResponse> call = apiInterface.getDetailsProductSlider(hashMap);
        call.enqueue(new Callback<DetailsProductSliderResponse>() {
            @Override
            public void onResponse(Call<DetailsProductSliderResponse> call, Response<DetailsProductSliderResponse> response) {

                if (response.code()==200) {
                    listProductSliderMutableLiveData.setValue(response.body().getData());
                } else  {
                    listProductSliderMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<DetailsProductSliderResponse> call, Throwable t) {
                listProductSliderMutableLiveData.setValue(null);

            }
        });
    }
}
