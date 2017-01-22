package demo.hellodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.hellodemo.models.MovieSearchModel;
import demo.hellodemo.presenter.MoviesPresenter;

public class MainActivity extends AppCompatActivity implements MoviesPresenter.MovieDataReceivedFromPresenter {

    @Bind(R.id.edit_movie) EditText movieEdit;
    @Bind(R.id.txt_title) TextView title;
    @Bind(R.id.txt_genre) TextView genre;
    @Bind(R.id.txt_date) TextView releaseDate;
    @Bind(R.id.txt_plot) TextView plot;
    @Bind(R.id.txt_rating) TextView rating;
    @Bind(R.id.txt_error) TextView error;
    @Bind(R.id.holder_details_movie) View holder;
    @Bind(R.id.progress_bar) View progressBar;


    /*
    Fragment can be used instead of a viewholder but would have been an overkill for such small data and operations.
     */

    private MoviesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MoviesPresenter();

    }

    @OnClick(R.id.search_btn)
    public void search(){
        String searchable = movieEdit.getEditableText().toString();
        if (searchable.length()>0){
            holder.setVisibility(View.GONE);
            error.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            presenter.getMovieData(searchable,"short",MainActivity.this);
        }
    }

    @Override
    public void onSuccess(MovieSearchModel movieSearchModel) {
        progressBar.setVisibility(View.GONE);
        error.setVisibility(View.GONE);
        title.setText(movieSearchModel.getTitle());
        genre.setText(movieSearchModel.getGenre());
        releaseDate.setText(movieSearchModel.getReleased());
        plot.setText(movieSearchModel.getPlot());
        rating.setText(movieSearchModel.getImdbRating());
        holder.setVisibility(View.VISIBLE);


    }

    @Override
    public void onFailure() {
        progressBar.setVisibility(View.GONE);
        holder.setVisibility(View.GONE);
        error.setVisibility(View.VISIBLE);
    }
}
