package com.exercise.movie.movie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    private static final String POPULARITY = "popularity";
    private static final String VOTE_AVERAGE = "voteAverage";
    private static final String RELEASE_DATE = "releaseDate";

    private final Logger log = LoggerFactory.getLogger(MovieServiceImpl.class);

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie save(Movie movie) {
        log.debug("Request to save Movie : {}", movie);
        return movieRepository.save(movie);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Movie> findAll(Pageable pageable) {
        log.debug("Request to get all Movies");
        return movieRepository.findAll(pageable);
    }

    @Override
    public Page<Movie> findPopularMovies(Pageable pageable) {
        return findAllWithEagerRelationships(PageRequest.of(0, pageable.getPageSize(),
            Sort.by(Direction.DESC,
                POPULARITY)));
    }

    @Override
    public Page<Movie> findTopRatedMovies(Pageable pageable) {
        return findAllWithEagerRelationships(PageRequest.of(0, pageable.getPageSize(),
            Sort.by(Direction.DESC,
                VOTE_AVERAGE)));
    }

    @Override
    public Page<Movie> findUpcomingMovies(Pageable pageable) {
        return findAllWithEagerRelationships(PageRequest.of(0, pageable.getPageSize(),
            Sort.by(Direction.DESC,
                RELEASE_DATE)));
    }

    @Override
    public Page<Movie> findAllWithEagerRelationships(Pageable pageable) {
        return movieRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Movie> findOne(Long id) {
        log.debug("Request to get Movie : {}", id);
        return movieRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Movie : {}", id);
        movieRepository.deleteById(id);
    }
}
