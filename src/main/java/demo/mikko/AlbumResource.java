package demo.mikko;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
public class AlbumResource {
    
    @Inject
    AlbumService albumService;

    @Query("allAlbums")
    @Description("Get all albums")
    public List<Album> getAllAlbums() {
        return albumService.getAllAlbums();
    }

    @Query("albumsByTitle")
    @Description("Get albums by title")
    public CompletionStage<List<Album>> getAlbumsByTitle(@Name("title") String title) {
        return CompletableFuture.completedFuture(albumService.getAlbumsByTitle(title));
    }

    @Query("albumsByArtist")
    public CompletionStage<List<Album>> getAlbumsByArtist(@Name("artist") String artist) {
        return CompletableFuture.completedFuture(albumService.getAlbumsByArtist(artist));
    }

    @Query("albumsByYear")
    public CompletionStage<List<Album>> getAlbumsByYear(@Name("year") Integer year) {
        return CompletableFuture.completedFuture(albumService.getAlbumsByYear(year).orElseThrow(() -> new NotFoundException("year", "" + year)));
    }

    @Query("album")
    public Album getAlbum(int id) {
        return albumService.getAlbum(id).orElseThrow(() -> new NotFoundException("id", "" + id));
    }

    @Mutation
    public Album createAlbum(Album album) {
        return albumService.addAlbum(album);
    }

    @Mutation
    public Album deleteAlbum(int id) {
        return albumService.deleteAlbum(id).orElseThrow(() -> new NotFoundException("id", "" + id));
    }

}
