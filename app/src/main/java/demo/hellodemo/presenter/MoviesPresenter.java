package demo.hellodemo.presenter;

import demo.hellodemo.apis.MovieApis;
import demo.hellodemo.models.MovieSearchModel;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/*

EventBus can be used instead of simple interfaces.

 */

public class MoviesPresenter {

    private static final String BASE_URL = "http://www.omdbapi.com";

    private MovieApis.MovieSearchApi initializeRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        return retrofit.create(MovieApis.MovieSearchApi.class);
    }


    public void getMovieData(String movieName, String plotLength, final MovieDataReceivedFromPresenter presenterToView){
        initializeRetrofit().getMovieByName(movieName,plotLength,"json")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieSearchModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        presenterToView.onFailure();
                    }

                    @Override
                    public void onNext(MovieSearchModel movieSearchModel) {
                        if (movieSearchModel.getResponse().equals("False")){
                            presenterToView.onFailure();
                        }else {
                            presenterToView.onSuccess(movieSearchModel);
                        }
                    }
                });
    }


    public interface MovieDataReceivedFromPresenter{
        void onSuccess(MovieSearchModel movieSearchModel);
        void onFailure();
    }

}
