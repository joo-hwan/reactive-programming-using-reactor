package com.learnreactiveprogramming.service;

import com.learnreactiveprogramming.exception.MovieException;
import com.learnreactiveprogramming.exception.NetworkException;
import com.learnreactiveprogramming.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MovieReactiveServiceMockTest {

    @Mock
    private MovieInfoService movieInfoService;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    MovieReactiveService reactiveService;

    @Test
    void getAllMovies() {
        Mockito.when(movieInfoService.movieInfoFlux())
                .thenCallRealMethod();

        Mockito.when(reviewService.retrieveReviewsFlux(anyLong()))
                .thenCallRealMethod();

        var moviesFlux = reactiveService.getAllMovies();

        StepVerifier.create(moviesFlux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void getAllMovies_1() {
        var errorMessage = "Exception occurred in ReviewService";

        Mockito.when(movieInfoService.movieInfoFlux())
                .thenCallRealMethod();

        Mockito.when(reviewService.retrieveReviewsFlux(anyLong()))
                .thenThrow(new RuntimeException(errorMessage));

        var moviesFlux = reactiveService.getAllMovies();

        StepVerifier.create(moviesFlux)
                .expectError(MovieException.class)
                .verify();
    }

    @Test
    void getAllMovies_retry() {
        var errorMessage = "Exception occurred in ReviewService";

        Mockito.when(movieInfoService.movieInfoFlux())
                .thenCallRealMethod();

        Mockito.when(reviewService.retrieveReviewsFlux(anyLong()))
                .thenThrow(new RuntimeException(errorMessage));

        var moviesFlux = reactiveService.getAllMovies_retry();

        StepVerifier.create(moviesFlux)
                .expectErrorMessage(errorMessage)
                .verify();

        verify(reviewService, times(4))
                .retrieveReviewsFlux(isA(Long.class));
    }

    @Test
    void getAllMovies_retryWhen() {
        var errorMessage = "Exception occurred in ReviewService";

        Mockito.when(movieInfoService.movieInfoFlux())
                .thenCallRealMethod();

        Mockito.when(reviewService.retrieveReviewsFlux(anyLong()))
                .thenThrow(new NetworkException(errorMessage));

        var moviesFlux = reactiveService.getAllMovies_retryWhen();

        StepVerifier.create(moviesFlux)
                .expectErrorMessage(errorMessage)
                .verify();

        verify(reviewService, times(4))
                .retrieveReviewsFlux(isA(Long.class));
    }

    @Test
    void getAllMovies_retryWhen_1() {
        var errorMessage = "Exception occurred in ReviewService";

        Mockito.when(movieInfoService.movieInfoFlux())
                .thenCallRealMethod();

        Mockito.when(reviewService.retrieveReviewsFlux(anyLong()))
                .thenThrow(new ServiceException(errorMessage));

        var moviesFlux = reactiveService.getAllMovies_retryWhen();

        StepVerifier.create(moviesFlux)
                .expectErrorMessage(errorMessage)
                .verify();

        verify(reviewService, times(1))
                .retrieveReviewsFlux(isA(Long.class));
    }

    @Test
    void getAllMovies_repeat() {
        var errorMessage = "Exception occurred in ReviewService";

        Mockito.when(movieInfoService.movieInfoFlux())
                .thenCallRealMethod();

        Mockito.when(reviewService.retrieveReviewsFlux(anyLong()))
                .thenCallRealMethod();

        var moviesFlux = reactiveService.getAllMovies_repeat();

        StepVerifier.create(moviesFlux)
                .expectNextCount(6)
                .thenCancel()
                .verify();

        verify(reviewService, times(6))
                .retrieveReviewsFlux(isA(Long.class));
    }

    @Test
    void getAllMovies_repeat_n() {
        var errorMessage = "Exception occurred in ReviewService";

        Mockito.when(movieInfoService.movieInfoFlux())
                .thenCallRealMethod();

        Mockito.when(reviewService.retrieveReviewsFlux(anyLong()))
                .thenCallRealMethod();

        var noOfTimes = 2L;

        var moviesFlux = reactiveService.getAllMovies_repeat_n(noOfTimes);

        StepVerifier.create(moviesFlux)
                .expectNextCount(9)
                .verifyComplete();

        verify(reviewService, times(9))
                .retrieveReviewsFlux(isA(Long.class));
    }
}