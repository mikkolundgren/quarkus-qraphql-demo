package demo.mikko;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jboss.logging.Logger;

@ApplicationScoped
public class AlbumService {

    Logger logger = Logger.getLogger(getClass());

    private List<Album> albums = new ArrayList<>();
    

    public AlbumService() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Album[] tmp = mapper.readValue(getClass().getClassLoader().getResourceAsStream("1000Albums.json"), Album[].class);
            for (int i = 0; i < tmp.length; i++) {
                tmp[i].id = i;
                albums.add(tmp[i]);
            }
        } catch (IOException e) {
            logger.error("exception reading albums.", e);
        }
        logger.infov("Read {0} albums...", albums.size());

    }
    
    public List<Album> getAllAlbums() {
        return albums;
    }
 
    public List<Album> getAlbumsByTitle(String title) {
        return albums.stream().filter(album -> album.title.contains(title)).collect(Collectors.toList());
    }

    public List<Album> getAlbumsByArtist(String artist) {
        return albums.stream().filter(album -> album.artist.contains(artist)).collect(Collectors.toList());
    }


    public Optional<List<Album>> getAlbumsByYear(Integer year) {
        List<Album> result = albums.stream().filter(album -> album.year.equals(year)).collect(Collectors.toList());
        return result.isEmpty() ? Optional.empty() : Optional.of(result);

    }

    public Album addAlbum(Album album) {
        album.id = albums.size();
        albums.add(album);
        logger.infov("added album with id {0}", album.id);
        return album;
    }

    public Optional<Album> deleteAlbum(int albumId) {
        if (albumId < 0 || albumId > albums.size()) {
            return Optional.empty();
        }
        Album a = albums.get(albumId);
        albums.remove(albumId);
        logger.infov("deleted album with id {0}", a.id);
        return Optional.of(a);
    }

    public Optional<Album> getAlbum(int albumId) {
        if (albumId < 0 || albumId > albums.size()) {
            return Optional.empty();
        }
        Album a = albums.get(albumId);
        return Optional.of(a);
    }

}
