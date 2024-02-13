package com.noflix.movieapi.service;

import com.noflix.movieapi.dto.MovieDto;
import com.noflix.movieapi.entities.Movie;
import com.noflix.movieapi.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;
    private final FileService fileService;

    @Value("${project.poster}")
    private String path;

    @Value("${base.url}")
    private String baseUrl;

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }


    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {

        //1 upload the file
       String uploadedFileName = fileService.uploadFile(path,file);

       //2 set the value for field poster as filename
        movieDto.setPoster(uploadedFileName);

        //3 map dto to movie object

        Movie movie = new Movie(
                null,
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );

        // 4. save Movie object to DB -> return Movie object
        Movie savedMovie = movieRepository.save(movie);

        // 6. Get base url and construct poster's Url
        var posterUrl = baseUrl + "/file/" + uploadedFileName;

        // 5. convert to MovieDto object, and return this object
        var responseObj = new MovieDto(
                savedMovie.getMovieId(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getStudio(),
                savedMovie.getMovieCast(),
                savedMovie.getReleaseYear(),
                savedMovie.getPoster(),
                posterUrl
        );

        return responseObj;
    }

    @Override
    public MovieDto getMovie(Integer movieId) {
        return null;
    }

    @Override
    public List<MovieDto> getAllMovies() {
        return null;
    }
}
