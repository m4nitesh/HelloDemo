package demo.hellodemo.apis;

import demo.hellodemo.models.MovieSearchModel;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public class MovieApis {

    public interface MovieSearchApi {
        @GET("/?")
        Observable<MovieSearchModel> getMovieByName(@Query("t") String movieName,@Query("plot") String plotLength,@Query("r") String responseType);
    }


}
