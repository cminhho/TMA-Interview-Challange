package com.exercise.movie.core;

import static com.exercise.movie.core.Constants.SPRING_PROFILE_DEV;
import static com.exercise.movie.core.Constants.SPRING_PROFILE_LOCAL;
import static com.exercise.movie.core.Constants.SPRING_PROFILE_PROD;

import com.exercise.movie.comment.MovieComment;
import com.exercise.movie.comment.MovieCommentRestRepository;
import com.exercise.movie.genre.MovieGenre;
import com.exercise.movie.genre.MovieGenreRestRepository;
import com.exercise.movie.playlist.Playlist;
import com.exercise.movie.playlist.PlaylistMovie;
import com.exercise.movie.playlist.PlaylistMovieRestRepository;
import com.exercise.movie.playlist.PlaylistRestRepository;
import com.exercise.movie.movie.Movie;
import com.exercise.movie.movie.MovieRestRepository;
import java.util.Arrays;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Slf4j
@Profile({SPRING_PROFILE_LOCAL, SPRING_PROFILE_DEV, SPRING_PROFILE_PROD})
public class LoadDatabase {

  private Playlist play1ist1;
  private Playlist play1ist2;
  private Playlist play1ist3;

  private MovieGenre actionGenre;
  private MovieGenre comedyGenre;
  private MovieGenre animationGenre;

  private Movie movie1;
  private Movie movie2;
  private Movie movie3;

  @Bean
  CommandLineRunner initDatabase(MovieRestRepository movieRestRepository,
      MovieCommentRestRepository movieCommentRestRepository,
      MovieGenreRestRepository movieGenreRestRepository,
      PlaylistRestRepository playlistRestRepository,
      PlaylistMovieRestRepository playlistMovieRestRepository) {
    return args -> {
      log.debug("Initial db records");
      initMovieGenresRecords(movieGenreRestRepository);
      initMovieRecords(movieRestRepository);
      initMovieCommentsRecords(movieCommentRestRepository);
      initPlaylistRecords(playlistRestRepository);
      assignMoviesToPlaylist(playlistMovieRestRepository);
    };
  }

  private void initMovieRecords(MovieRestRepository movieRestRepository) {
    movie1 = new Movie()
        .title("The Lion King")
        .backdropPath("1TUg5pO1VZ4B0Q1amk3OlXvlpXV.jpg")
        .posterPath("dzBtMocZuJbjLOXvrl4zGYigDzh.jpg")
        .genres(Collections.singleton(actionGenre))
        .comments(Collections.emptySet())
        .playlistMovies(Collections.emptySet())
        .voteAverage(1L)
        .popularity(1L);
    movie2 = new Movie()
        .title("Avengers: Endgame")
        .backdropPath("7RyHsO4yDXtBv1zUU3mTpHeQ0d5.jpg")
        .posterPath("or06FN3Dka5tukK1e9sl16pB3iy.jpg")
        .genres(Collections.singleton(actionGenre))
        .comments(Collections.emptySet())
        .playlistMovies(Collections.emptySet())
        .voteAverage(2L)
        .popularity(2L);
    movie3 = new Movie()
        .title("Alita: Battle Angel")
        .backdropPath("xRWht48C2V8XNfzvPehyClOvDni.jpg")
        .posterPath("86Y6qM8zTn3PFVfCm9J98Ph7JEB.jpg")
        .genres(Collections.singleton(actionGenre))
        .comments(Collections.emptySet())
        .playlistMovies(Collections.emptySet())
        .voteAverage(3L)
        .popularity(3L);

    log.debug("Initial movie records");
    movieRestRepository.saveAll(Arrays.asList(movie1, movie2, movie3));
  }

  void initMovieCommentsRecords(MovieCommentRestRepository movieCommentRestRepository) {
    log.debug("Create movie comments and assign to specific movie");
    MovieComment comment1 = new MovieComment().review("The movie 1 was exciting");
    comment1.setMovie(movie1);

    MovieComment comment2 = new MovieComment().review("The movie 2 was exciting");
    comment2.setMovie(movie2);

    MovieComment comment3 = new MovieComment().review("The movie 3 was exciting");
    comment3.setMovie(movie3);

    log.debug("Initial movie comment records");
    movieCommentRestRepository.save(comment1);
    movieCommentRestRepository.save(comment2);
    movieCommentRestRepository.save(comment3);
  }

  private void initMovieGenresRecords(MovieGenreRestRepository movieGenreRestRepository) {
    log.debug("Create movie genre records");
    actionGenre = new MovieGenre().title("Action");
    comedyGenre = new MovieGenre().title("Comedy");
    animationGenre = new MovieGenre().title("Animation");
    movieGenreRestRepository.saveAll(Arrays.asList(actionGenre, comedyGenre, animationGenre));
  }

  private void initPlaylistRecords(PlaylistRestRepository playlistRestRepository) {
    log.debug("Create movie list records");
    play1ist1 = new Playlist().title("Play1ist 1");
    play1ist2 = new Playlist().title("Play1ist 2");
    play1ist3 = new Playlist().title("Play1ist 3");
    playlistRestRepository.saveAll(Arrays.asList(play1ist1, play1ist2, play1ist3));
  }

  private void assignMoviesToPlaylist(PlaylistMovieRestRepository playlistMovieRestRepository) {
    PlaylistMovie playlistMovie1 = new PlaylistMovie(movie1, play1ist1);
    playlistMovieRestRepository.save(playlistMovie1);
  }
}
